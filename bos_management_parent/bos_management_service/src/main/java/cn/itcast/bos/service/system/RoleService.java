package cn.itcast.bos.service.system;

import java.util.List;

import cn.itcast.bos.domain.system.Role;

public interface RoleService {

	void save(Role model, Integer[] permissionIds, String menuIds);

	List<Role> findAll();

}
