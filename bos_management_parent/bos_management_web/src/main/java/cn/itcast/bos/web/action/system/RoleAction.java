package cn.itcast.bos.web.action.system;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.service.system.RoleService;
import cn.itcast.bos.web.action.common.BaseAction;

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
@Results({
	@Result(name="list",type="redirect",location="/pages/system/role.jsp")
})
public class RoleAction extends BaseAction<Role> {

	@Autowired
	private RoleService roleService;
	
	
	//接收页面提交菜单ID,权限ID
	private String menuIds;
	private Integer[] permissionIds;
	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}

	public void setPermissionIds(Integer[] permissionIds) {
		this.permissionIds = permissionIds;
	}

	/**
	  * @Description: 1、保存角色  2、角色关联权限  3、角色关联菜单
	 */
	@Action("roleAction_save")
	public String save() throws Exception {
		roleService.save(model, permissionIds, menuIds);
		return "list";
	}
	
	@Action("roleAction_findAll")
	public String findAll() throws Exception {
		List<Role> list = roleService.findAll();
		this.java2Json(list, new String[]{"users", "permissions", "menus"});
		return NONE;
	}
}
