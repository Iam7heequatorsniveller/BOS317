package cn.itcast.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.base.Courier;

public interface CourierDao extends JpaRepository<Courier, Integer>, JpaSpecificationExecutor<Courier> {

	//sql :update t_courier set c_deltag = 1 where c_id = ?
	@Query("update Courier set deltag = '1' where id = ?")
	@Modifying
	void logicDelete(int courierId);

	@Query(nativeQuery=true, value="select t1.* from t_courier t1 where t1.c_deltag = '0' and t1.c_id not in (select fc.c_courier_id from t_fixedarea_courier fc  where fc.c_fixed_area_id = ?)")
	List<Courier> listajax(String fixedareaId);

}
