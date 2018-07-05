package cn.itcast.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.CourierDao;
import cn.itcast.bos.dao.base.FixedAreaDao;
import cn.itcast.bos.dao.base.SubAreaDao;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.domain.base.SubArea;
import cn.itcast.bos.domain.base.TakeTime;
import cn.itcast.bos.service.base.FixedAreaService;

@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {

	@Autowired
	private FixedAreaDao fixedAreaDao;
	
	@Autowired
	private SubAreaDao subAreaDao;
	
	@Autowired
	private CourierDao courierDao;

	/**
	  * @Description: 1、保存定区  2、让分区关联定区
	  * 对象三种状态 1、持久态（被session管理对象-一级缓存中有对象） 2、托管态（有OID标识，数据库中有记录）  3、瞬时态 new对象
	      
	            对象关联：持久态可以关联持久态 / 持久态关联托管态
	*/
	public void save(FixedArea model, String[] subAreaId) {
		//问题:保存完成参数定区对象是瞬时态
		//解决：使用save方法返回对象（返回结果是持久态）
		//结论：1、当保存实体主键类型如果是java基本类型，save方法参数对象保存完后就是持久态
		//    2、当保存实体主键类型如果不是java基本类型，save方法返回值才是持久态
		model = fixedAreaDao.save(model);
		//方式一：执行sql语句完成关联-update t_sub_area s set s.c_fixedarea_id = ? where s.c_id = ?
		//方式二：通过对象关联
		//假设model对象是持久态
		if(subAreaId!=null && subAreaId.length>0){
			for (String sId : subAreaId) {
				//查询到分区对象是持久态
				SubArea subArea = subAreaDao.findOne(sId);
				//查询实体配置:由分区维护关系
				subArea.setFixedArea(model);  //更新分区记录中定区外键
			}
		}
	}

	public Page<FixedArea> pageQuery(Pageable pageable) {
		return fixedAreaDao.findAll(pageable);
	}

	/**
	  * @Description: 1、定区关联快递员  2、快递员关联收派时间
	*/
	public void associationCourierToFixedArea(String id, Integer courierId, Integer takeTimeId) {
		//持久态对象关联持久态对象
		FixedArea fixedArea = fixedAreaDao.findOne(id);
		Courier courier = courierDao.findOne(courierId);	
		//通过查看实体配置 快递员放弃维护
		//定区关联快递员
		fixedArea.getCouriers().add(courier); //向中间表T_fixedarea_courier表新增记录
		
		
		TakeTime takeTime = new TakeTime();
		takeTime.setId(takeTimeId);  //托管态
		courier.setTakeTime(takeTime  );
	}
}
