package cn.itcast.bos.service.take_delivery;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.jws.WebParam.Mode;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.AreaDao;
import cn.itcast.bos.dao.base.FixedAreaDao;
import cn.itcast.bos.dao.take_delivery.OrderDao;
import cn.itcast.bos.dao.take_delivery.WorkBillDao;
import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.domain.base.SubArea;
import cn.itcast.bos.domain.take_delivery.Order;
import cn.itcast.bos.domain.take_delivery.WorkBill;
import cn.itcast.bos.utils.AliSmsUtil;
import cn.itcast.crm.service.CustomerService;

@Service("orderService")
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private AreaDao areaDao;
	
	@Autowired
	private CustomerService customerProxy;	
	
	@Autowired
	private FixedAreaDao fixedAreaDao;
	
	
	@Autowired
	private WorkBillDao workBillDao;
	
	/**
	  * @Description: 1、保存订单  2、尝试自动分单
	  * @return 
	*/
	public void save(Order order) {
		System.out.println("服务端保存订单方法被调用");
		order.setOrderTime(new Date());
		order.setOrderNum(UUID.randomUUID().toString());
		//在前台系统中订单中封装区域对象，区域对象状态：瞬时态  问题：order对象引用瞬时态区域对象
		//解决：方式一将区域对象置为null order.setRecArea(null); 将来订单表中区域id无值
		//方式二：根据省市区查询区域对象（持久态）
		Area sendArea = order.getSendArea();
		//寄件人所属区域对象
		sendArea = areaDao.findByProvinceAndCityAndDistrict(sendArea.getProvince(), sendArea.getCity(), sendArea.getDistrict());
		
		Area recArea = order.getRecArea();
		recArea = areaDao.findByProvinceAndCityAndDistrict(sendArea.getProvince(), sendArea.getCity(), sendArea.getDistrict());
		
		order.setSendArea(sendArea);
		order.setRecArea(recArea);
		
		orderDao.save(order);
		//尝试自动分单-两种策略
		//根据寄件人详细地址 去 CRM中查询该地址对应定区ID
		String fixedAreaId = customerProxy.findFixedAreaIdByAddress(order.getSendAddress());
		if(StringUtils.isNotBlank(fixedAreaId)){
			//查询定区对象
			FixedArea fixedArea = fixedAreaDao.findOne(fixedAreaId);
			if(fixedArea!=null){
				//定区-快递员：多对多  
				Set<Courier> couriers = fixedArea.getCouriers();
				//TODO 简化操作 判断上班时间   取件任务数量
				for (Courier courier : couriers) {
					//完成自动分单-1、产生快递员工单  2、发送取件短信
					WorkBill workBill = new WorkBill();
					workBill.setAttachbilltimes(0);//追单次数
					workBill.setBuildtime(new Date());//工单时间
					workBill.setCourier(courier);  //工单关联快递员
					workBill.setOrder(order); //工单关联订单
					workBill.setPickstate("待取件");
					workBill.setRemark(order.getRemark());
					workBill.setType("新单");
					workBillDao.save(workBill);
					
					Map<String, Object> map = new HashMap<>();
					map.put("customerAddress", order.getSendAddress());
					map.put("customerName", order.getSendName());
					map.put("customerTelephone", order.getSendMobile());
					map.put("customerRemark", order.getSendMobileMsg());
					//发送短信
					Boolean flag = AliSmsUtil.sendMessage(courier.getTelephone(), "SMS_121136520", map);
					workBill.setSmsNumber(flag.toString());
					
					//自动分单
					order.setOrderType("自动分单");
					order.setStatus("待取件");
					order.setCourier(courier);  //订单关联快递员
					return;
				}
			}
		}
		//策略二尝试自动分单
		
		//查询寄件人所在区域，查询该区域下分区记录
		Set<SubArea> subareas = sendArea.getSubareas();
		for (SubArea subArea : subareas) {
			//确定寄件人详细地址所属分区
			String sendAddress = order.getSendAddress();  //寄件人详细地址
			if(sendAddress.contains(subArea.getKeyWords())||sendAddress.contains(subArea.getAssistKeyWords())){
				//分区-定区：多对一
				FixedArea fixedArea = subArea.getFixedArea();
				if(fixedArea!=null){
					//定区-快递员：多对多  
					Set<Courier> couriers = fixedArea.getCouriers();
					if(couriers.isEmpty()){
						//TODO 简化操作 判断上班时间   取件任务数量
						for (Courier courier : couriers) {
							//完成自动分单-1、产生快递员工单  2、发送取件短信
							WorkBill workBill = new WorkBill();
							workBill.setAttachbilltimes(0);//追单次数
							workBill.setBuildtime(new Date());//工单时间
							workBill.setCourier(courier);  //工单关联快递员
							workBill.setOrder(order); //工单关联订单
							workBill.setPickstate("待取件");
							workBill.setRemark(order.getRemark());
							workBill.setType("新单");
							workBillDao.save(workBill);
							
							Map<String, Object> map = new HashMap<>();
							map.put("customerAddress", order.getSendAddress());
							map.put("customerName", order.getSendName());
							map.put("customerTelephone", order.getSendMobile());
							map.put("customerRemark", order.getSendMobileMsg());
							//发送短信
							Boolean flag = AliSmsUtil.sendMessage(courier.getTelephone(), "SMS_121136520", map);
							workBill.setSmsNumber(flag.toString());
							
							//自动分单
							order.setOrderType("自动分单");
							order.setStatus("待取件");
							order.setCourier(courier);  //订单关联快递员
							return;
						}
					}
				}
			}
		}
		
		//人工分单
		order.setOrderType("人工分单");
		
	}

}
