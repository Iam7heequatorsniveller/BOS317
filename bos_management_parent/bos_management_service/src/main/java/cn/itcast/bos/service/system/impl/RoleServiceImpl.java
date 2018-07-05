package cn.itcast.bos.service.system.impl;

import java.util.List;

import javax.swing.plaf.MenuItemUI;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.system.MenuDao;
import cn.itcast.bos.dao.system.PermissionDao;
import cn.itcast.bos.dao.system.RoleDao;
import cn.itcast.bos.domain.system.Menu;
import cn.itcast.bos.domain.system.Permission;
import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.service.system.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private PermissionDao permissionDao;
	
	@Autowired
	private MenuDao menuDao;

	/**
	  * @Description:  1、保存角色  2、角色关联权限  3、角色关联菜单
	  * @return 
	*/
	public void save(Role model, Integer[] permissionIds, String menuIds) {
		//角色对象主键是java基本类型，保存完参数就是持久态
		roleDao.save(model);
		if(permissionIds!=null && permissionIds.length>0){
			//关联方式一：通过对象关联
			//关联方式：执行sql语句
			for (Integer permissionId : permissionIds) {
				//查询到权限对象持久态
				Permission permission = permissionDao.findOne(permissionId);
				//查询多对多配置，确定谁维护关系
				model.getPermissions().add(permission); //向角色权限关系表中t_role_permission添加记录
			}
		}
		if(StringUtils.isNotBlank(menuIds)){
			String[] strings = menuIds.split(",");
			for (String menuId : strings) {
				//有角色对象维护关系
				Menu menu = new Menu();  //创建托管态菜单对象
				menu.setId(Integer.parseInt(menuId));
				model.getMenus().add(menu);  //向角色菜单关系表t_role_menu添加记录
			}
		}
	}

	public List<Role> findAll() {
		return roleDao.findAll();
	}
}
