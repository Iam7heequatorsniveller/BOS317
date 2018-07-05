package cn.itcast.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.system.Role;

public interface RoleDao extends JpaRepository<Role, Integer> {

	/*sql:
	select r.*
	from t_role r 
	inner join t_user_role ur on ur.c_role_id = r.c_id
	inner join t_user u on ur.c_user_id = u.c_id
	where u.c_id = 76;*/
	@Query("select r from Role r inner join r.users u where u.id = ?")
	List<Role> findByUserId(Integer id);

}
