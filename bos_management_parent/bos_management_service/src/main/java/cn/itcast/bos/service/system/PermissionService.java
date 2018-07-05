package cn.itcast.bos.service.system;

import java.util.List;

import cn.itcast.bos.domain.system.Permission;

public interface PermissionService {

	void save(Permission model);

	List<Permission> findAll();

}
