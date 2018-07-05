package cn.itcast.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.base.SubArea;

public interface SubAreaDao extends JpaRepository<SubArea, String> {

	//sql:select t.*, t.rowid from T_SUB_AREA t where t.c_fixedarea_id is null;
	List<SubArea> findByFixedAreaIsNull();

	/*sql:
		select a.c_province,count(*)
		from t_sub_area sa,t_area a 
		where sa.c_area_id = a.c_id
		group by a.c_province;*/
	@Query("select a.province,count(*) from SubArea s inner join s.area a group by a.province")
	List<Object[]> findCountByProvince();

}
