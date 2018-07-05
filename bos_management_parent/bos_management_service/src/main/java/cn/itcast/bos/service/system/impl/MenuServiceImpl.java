package cn.itcast.bos.service.system.impl;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.system.MenuDao;
import cn.itcast.bos.domain.system.Menu;
import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.system.MenuService;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuDao menuDao;

	/**
	  * @Description: 查询所有菜单
	  * menuDao.findAll():List长度33
	  * 查询顶级菜单：长度4；子菜单都在childrenMenus集合属性中，getChildren方法返回childrenMenus集合属性
	  * @return 
	*/
	public List<Menu> findAll() {
		return menuDao.findByParentMenuIsNull();
	}

	/**
	  * @Description: 如果没有选择上级菜单，上级菜单对象parmentMenu是瞬时态实例
	  * @return 
	*/
	public void save(Menu model) {
		Menu parentMenu = model.getParentMenu();
		if(parentMenu!=null && parentMenu.getId()==null){
			//新增顶级菜单
			model.setParentMenu(null);
		}
		menuDao.save(model);
	}

	public List<Menu> findAllBySimple() {
		return menuDao.findAll();
	}

	public List<Menu> showMenu() {
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		if(user.getUsername().equals("admin")){
			return menuDao.findAll();
		}else{
			//根据用户ID查询
			return menuDao.findByUserId(user.getId());
		}
	}
}
