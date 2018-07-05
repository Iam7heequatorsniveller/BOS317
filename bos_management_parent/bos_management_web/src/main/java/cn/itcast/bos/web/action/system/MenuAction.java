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

import cn.itcast.bos.domain.system.Menu;
import cn.itcast.bos.service.system.MenuService;
import cn.itcast.bos.web.action.common.BaseAction;

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
@Results({
	@Result(name="list",type="redirect",location="/pages/system/menu.jsp")
//	@Result(name="login",location="/login.jsp")
})
public class MenuAction extends BaseAction<Menu> {

	@Autowired
	private MenuService menuService;
	
	
	/**
	  * @Description: 查询所有菜单
	  * @return json数组  菜单对象中有name,children
	   [{},]
	  * @throws Exception
	  *	  
	 */
	@Action("menuAction_findAll")
	public String findAll() throws Exception {
		List<Menu> list = menuService.findAll();
		this.java2Json(list, new String[]{"roles", "childrenMenus", "parentMenu"});
		return NONE;
	}
	
	/**
	  * @Description: 菜单数据保存
	  * @return
	  * @throws Exception
	  *	  
	 */
	@Action("menuAction_save")
	public String save() throws Exception {
		menuService.save(model);
		return "list";
	}
	
	/**
	  * @Description: 查询所有的菜单
	  * @return ztree要求简单json数据格式
	        标准数据格式：  list长度：1
	   [{"id":1,"name":"一级菜单","children:[{"id":2,"name":"二级菜单"]}]
	         简单数据格式：list长度：2
	   [{"id":1,"name":"一级菜单","pId":0},{"id":2,"name":"二级菜单",pId:1}]
	 */
	@Action("menuAction_findAllBySimple")
	public String findAllBySimple() throws Exception {
		List<Menu> list = menuService.findAllBySimple();
		this.java2Json(list, new String[]{"roles", "parentMenu", "children", "childrenMenus"});
		return NONE;
	}
	
	/**
	  * @Description: 根据用户ID动态查询菜单数据
	  * @return ztree 简单json数据格式
	 */
	@Action("menuAction_showMenu")
	public String showMenu() throws Exception {
		List<Menu> list = menuService.showMenu();
		this.java2Json(list, new String[]{"roles", "parentMenu", "children", "childrenMenus"});
		return NONE;
	}
}
