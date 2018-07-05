package cn.itcast.bos.web.action.take_delivery;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.take_delivery.WayBill;
import cn.itcast.bos.service.take_delivery.WayBillService;
import cn.itcast.bos.web.action.common.BaseAction;

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
@Results({
//	@Result(name="list",type="redirect",location="/pages/base/area.jsp")
})
public class WayBillAction extends BaseAction<WayBill> {

	@Autowired
	private WayBillService wayBillService;
	
	/**
	  * @Description: 运单录入
	  * @return 
	  * @throws Exception
	  *	  
	 */
	@Action("wayBillAction_save")
	public String save() throws Exception {
		try {
			wayBillService.save(model);
		} catch (Exception e) {
			e.printStackTrace();
			ServletActionContext.getResponse().getWriter().write("0");
		}
		return NONE;
	}
}
