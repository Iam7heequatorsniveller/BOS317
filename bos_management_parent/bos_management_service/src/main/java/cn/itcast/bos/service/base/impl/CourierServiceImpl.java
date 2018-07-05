package cn.itcast.bos.service.base.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.CourierDao;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.base.CourierService;

@Service
@Transactional
public class CourierServiceImpl implements CourierService {

	@Autowired
	private CourierDao courierDao;

	public void save(Courier model) {
		courierDao.save(model);
	}

	/**
	  * @Description: 无条件分页查询
	*/
	public Page<Courier> pageQuery(Pageable pageable) {
		return courierDao.findAll(pageable);
	}

	/**
	  * @Description: 有条件分页查询
	  * ORM思想 实体跟数据库中表对应  最终设置 查询sql语句     
	*/
	public Page<Courier> pageQuery(Courier model, Pageable pageable) {
		final String courierNum = model.getCourierNum();
		final String company = model.getCompany();
		final String type = model.getType();
//		model.getStandard().getName()
		final Standard standard = model.getStandard();
		//spec相当于hibernate中离线查询对象---封装查询条件
		Specification<Courier> spec = new Specification<Courier>() {
			
			//参数一：根实体:代表Criteria查询的根对象
			//参数二：代表一个specific的顶层查询对象，它包含着查询的各个部分，比如：select 、from、where、group by、order by等
			//参数三：Predicate对象实例工厂:产生Predicate对象
			//Predicate:一个简单或复杂的谓词类型，其实就相当于条件或者是条件组合
			public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// sql筛选条件 ： where 列名1  =  ?      列名2 like  %?%     列名3 = ？
				List<Predicate> list = new ArrayList<>();
				//参数一：查询根实体下属性    参数二：查询条件值
				if(StringUtils.isNotBlank(courierNum)){
					Predicate p1 = cb.equal(root.get("courierNum").as(String.class), courierNum);
					list.add(p1);
				}
				if(StringUtils.isNotBlank(company)){
					Predicate p2 = cb.like(root.get("company").as(String.class), "%"+company+"%");
					list.add(p2);
				}
				if(StringUtils.isNotBlank(type)){
					Predicate p3 = cb.equal(root.get("type").as(String.class), type);
					list.add(p3);
				}
				
				//关联查询
				if(standard!=null){
					String standardName = standard.getName();
					if(StringUtils.isNotBlank(standardName)){
						//创建关联对象
						Join<Object, Object> join = root.join("standard", JoinType.INNER);
						Predicate p4 = cb.equal(join.get("name").as(String.class), standardName);
						list.add(p4);
					}
				}
				
				if(list.size()==0){
					return null;
				}
				
				Predicate[] restrictions = new Predicate[list.size()];
				//将List集合转为数组
				restrictions = list.toArray(restrictions);
				//cb.and   多个条件使用  and 拼接
				//cb.or    多个条件使用  or 拼接
				return cb.and(restrictions);
			}
		};
		return courierDao.findAll(spec , pageable);
	}

	@RequiresPermissions("courier_delete") //访问此方法，必须要求当前用户有某个权限
//	@RequiresRoles("admin")
	public void delete(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] strings = ids.split(",");
			for (String courierId : strings) {
				courierDao.logicDelete(Integer.parseInt(courierId));
			}
		}
	}

	public List<Courier> listajax(String fixedAreaId) {
		return courierDao.listajax(fixedAreaId);
	}
	
}
