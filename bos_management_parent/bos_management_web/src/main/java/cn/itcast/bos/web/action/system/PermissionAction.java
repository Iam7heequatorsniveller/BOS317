package cn.itcast.bos.web.action.system;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.system.Permission;
import cn.itcast.bos.service.system.PermissionService;
import cn.itcast.bos.web.action.common.BaseAction;
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
@Results({
//	@Result(name="index",type="redirect",location="/index.jsp"),
	@Result(name="list",location="/pages/system/permission.jsp")
})
public class PermissionAction extends BaseAction<Permission> {
	
	@Autowired
	private  PermissionService permissionService;
	
	
	@Action("permissionAction_add")
	public String add() throws Exception {
		permissionService.save(model);
		
		return "list";
	}
	
	/**
	  * @Description: 查询所有的权限
	  * @return
	  * @throws Exception
	  *	  
	 */
	@Action("permissionAction_findAll")
	public String findAll() throws Exception {
		List<Permission> list = permissionService.findAll();
		this.java2Json(list, new String[]{"roles"});
		return NONE;
	}
	
	
	
	
	
}
