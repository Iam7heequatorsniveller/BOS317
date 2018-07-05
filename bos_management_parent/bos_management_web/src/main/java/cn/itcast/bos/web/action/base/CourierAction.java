package cn.itcast.bos.web.action.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
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

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.service.base.CourierService;
import cn.itcast.bos.web.action.common.BaseAction;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
@Results({
	@Result(name="list",type="redirect",location="/pages/base/courier.jsp"),
	@Result(name="unauthorized", location="/unauthorized.jsp")
})
@ExceptionMappings({
	@ExceptionMapping(exception="org.apache.shiro.authz.UnauthorizedException",result="unauthorized")
})
public class CourierAction extends BaseAction<Courier> {

	private Courier model = new Courier();
	public Courier getModel() {
		return model;
	}
	
	@Autowired
	private CourierService courierService;
	
	/**
	  * @Description: 快递员保存
	  * @return
	  * @throws Exception
	  *	  
	 */
	@Action("courierAction_save")
	public String save() throws Exception {
//		Subject subject = SecurityUtils.getSubject();
//		subject.checkPermission("courier_save");
		courierService.save(model);
		return "list";
	}

	//接收提交当前页；每页显示记录数
	private int page;
	private int rows;
	public void setPage(int page) {
		this.page = page;
	}


	public void setRows(int rows) {
		this.rows = rows;
	}
	
	
	/**
	  * @Description: 分页查询（条件分页查询）
	  * @return 
	  * @throws Exception
	  *	  
	 */
	@Action("courierAction_pageQuery")
	public String pageQuery() throws Exception {
		Pageable pageable = new PageRequest(page-1, rows);
		
		//model中封装查询条件
		Page<Courier> page = courierService.pageQuery(model, pageable);
		
		this.java2Json(page, new String[]{"fixedAreas"});
		/*Map<String, Object> map = new HashMap<>();
		map.put("total", page.getTotalElements());
		map.put("rows", page.getContent());
		
		//将不需要转为json字符串排除掉；由于快递员实体中fixedAreas集合属性会引起no-Session问题，将引起问题属性fixedAreas排除掉
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[]{"fixedAreas"});
		
		String json = JSONObject.fromObject(map, jsonConfig).toString();
		System.out.println(json);
		
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().write(json);*/
		return NONE;
	}
	
	//接收url提交多个快递员id
	private String ids;
	public void setIds(String ids) {
		this.ids = ids;
	}


	/**
	  * @Description: 快递员批量逻辑删除（将数据库中删除标识deltag改为1）
	 */
	@Action("courierAction_delete")
	public String delete() throws Exception {
		courierService.delete(ids);
		return "list";
	}
	
	
	//接收定区id
	private String fixedAreaId;
	public void setFixedAreaId(String fixedAreaId) {
		this.fixedAreaId = fixedAreaId;
	}


	/**
	  * @Description: 查询快递员记录（在职，为关联到某个定区）
	  * @return
	  * @throws Exception
	  *	  
	 */
	@Action("courierAction_listajax")
	public String listajax() throws Exception {
		List<Courier> list = courierService.listajax(fixedAreaId);
		this.java2Json(list, new String[]{"fixedAreas"});
		return NONE;
	}
}
