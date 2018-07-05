package cn.itcast.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.system.Menu;

public interface MenuDao extends JpaRepository<Menu, Integer> {

	//sql:select * from T_MENU t where t.c_pid is null;
	List<Menu> findByParentMenuIsNull();

	/*sql:
		select distinct m.*
		from t_menu m
		inner join t_role_menu rm on rm.c_menu_id = m.c_id
		inner join t_role r on rm.c_role_id = r.c_id
		inner join t_user_role ur on ur.c_role_id = r.c_id
		inner join t_user u on ur.c_user_id = u.c_id
		where u.c_id = 82
	 */
//	@Query("from Menu m inner join m.roles r inner join r.users u where u.id = ?")
	@Query("from Menu m inner join fetch m.roles r inner join fetch r.users u where u.id = ? order by m.priority")
	List<Menu> findByUserId(Integer userId);

}
