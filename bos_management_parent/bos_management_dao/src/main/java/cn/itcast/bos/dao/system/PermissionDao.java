package cn.itcast.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sun.xml.bind.v2.model.core.ID;

import cn.itcast.bos.domain.system.Permission;

public interface PermissionDao extends JpaRepository<Permission, Integer> {

	/*sql:
		select distinct p.*
		from t_permission p
		inner join t_role_permission rp on rp.c_permission_id = p.c_id
		inner join t_role r on rp.c_role_id = r.c_id
		inner join t_user_role ur on ur.c_role_id = r.c_id
		inner join t_user u on ur.c_user_id = u.c_id
		where u.c_id = 82*/
	@Query("select distinct p from Permission p inner join p.roles r inner join r.users u where u.id = ?")
	List<Permission> findByUserId(Integer userId);

}
