package cn.itcast.bos.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.system.RoleDao;
import cn.itcast.bos.dao.system.UserDao;
import cn.itcast.bos.domain.system.Role;
import cn.itcast.bos.domain.system.User;
import cn.itcast.bos.service.system.UserService;
import cn.itcast.bos.utils.Md5Util;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;

	public void save(User model, Integer[] roleIds) {
		model.setPassword(Md5Util.encode(model.getPassword()));
		userDao.save(model);
		if(roleIds!=null && roleIds.length>0){
			for (Integer roleId : roleIds) {
				Role role = roleDao.findOne(roleId);
				model.getRoles().add(role); //向用户角色关系表t_user_role新增记录
			}
		}
	}
}
