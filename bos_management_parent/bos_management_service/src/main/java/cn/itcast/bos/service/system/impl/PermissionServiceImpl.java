package cn.itcast.bos.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.system.PermissionDao;
import cn.itcast.bos.domain.system.Permission;
import cn.itcast.bos.service.system.PermissionService;
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {
	
	@Autowired
	private PermissionDao permissionDao;

	@Override
	public void save(Permission model) {
		permissionDao.save(model);
		
	}

	public List<Permission> findAll() {
		return permissionDao.findAll();
	}
}
