
package cn.itcast.bos.web.action.base;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.service.base.FixedAreaService;
import cn.itcast.bos.web.action.common.BaseAction;
import cn.itcast.crm.service.Customer;
import cn.itcast.crm.service.CustomerService;

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
@Results({
	@Result(name="list",type="redirect",location="/pages/base/fixed_area.jsp")
})
public class FixedAreaAction extends BaseAction<FixedArea> {

	@Autowired
	private FixedAreaService fixedAreaService;
	
	
	//接收提交多个分区id
	private String[] subAreaId;
	public void setSubAreaId(String[] subAreaId) {
		this.subAreaId = subAreaId;
	}

	/**
	  * @Description: 1、保存定区  2、让分区关联定区
	  * @return
	  * @throws Exception
	  *	  
	 */
	@Action("fixedAreaAction_save")
	public String save() throws Exception {
		fixedAreaService.save(model, subAreaId);
		return "list";
	}
	
	/**
	 * @Description: 定区分页查询
	 * @return
	 * @throws Exception
	 *	  
	 */
	@Action("fixedAreaAction_pageQuery")
	public String pageQuery() throws Exception {
		Pageable pageable = new PageRequest(page-1, rows);
		Page<FixedArea> page = fixedAreaService.pageQuery(pageable);
		this.java2Json(page, new String[]{"subareas", "couriers"});
		return NONE;
	}
	
	
	@Autowired
	private CustomerService customerProxy;
	
	/**
	  * @Description: 调用CRM查询未关联到定区客户记录
	  * @return json数组
	 */
	@Action("fixedAreaAction_findNotAssociation")
	public String findNotAssociation() throws Exception {
		List<Customer> list = customerProxy.findNotAssociation();
		this.java2Json(list, null);
		return NONE;
	}
	
	/**
	 * @Description: 调用CRM查询已经关联到定区客户记录
	 * @return json数组
	 */
	@Action("fixedAreaAction_findHasAssociation")
	public String findHasAssociation() throws Exception {
		List<Customer> list = customerProxy.findHasAssociation(model.getId());
		this.java2Json(list, null);
		return NONE;
	}
	
	//接收客户id
	private List<Integer> customerIds;
	public void setCustomerIds(List<Integer> customerIds) {
		this.customerIds = customerIds;
	}

	/**
	  * @Description: 远程调用CRM完成定区关联客户
	 */
	@Action("fixedAreaAction_assignCustomers2FixedArea")
	public String assignCustomers2FixedArea() throws Exception {
		customerProxy.assignCustomers2FixedArea(model.getId(), customerIds);
		return "list";
	}
	
	private Integer courierId;
	private Integer takeTimeId;
	public void setCourierId(Integer courierId) {
		this.courierId = courierId;
	}

	public void setTakeTimeId(Integer takeTimeId) {
		this.takeTimeId = takeTimeId;
	}

	/**
	  * @Description: 1、定区关联快递员  2、快递员关联收派时间
	 */
	@Action("fixedAreaAction_associationCourierToFixedArea")
	public String associationCourierToFixedArea() throws Exception {
		fixedAreaService.associationCourierToFixedArea(model.getId(), courierId, takeTimeId);
		return "list";
	}
}
