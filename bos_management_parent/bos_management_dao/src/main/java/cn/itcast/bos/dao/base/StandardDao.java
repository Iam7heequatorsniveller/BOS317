package cn.itcast.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.base.Standard;

/**
 * @Description: 收派标准dao
 *JpaRepository T:操作实体class  Serializable:主键属性类型
 * @Title: StandardDao.java
 * @date 2018年6月12日 上午11:23:34
 */
public interface StandardDao extends JpaRepository<Standard, Integer>{

	//实现增删改查分页方法
	//需求：根据收派标准名称查询记录
	
	//*********************方式一定义方法：根据springDataJpa规范定义方法名****************************///
	public List<Standard> findByName(String sname);
	
	
//	public List<Standard> findByAaa(String sname);
	
	
	
	
	
	//*********************方式二定义方法：自定义方法名（指定执行语句）****************************///
	/**
	  *	nativeQuery:是否执行本地查询（sql语句）    默认值：false 执行jpql语句
	 */
	@Query(nativeQuery=true,value="select * from T_STANDARD where C_NAME = ?")
	public List<Standard> findByAbc(String sname);
	
	//执行jpql语句
	@Query(value="from Standard where name = ?")
	public List<Standard> findByXyz(String sname);
	
	
	//如果Query注解中执行增删改语句，必须在方法上加注解@Modifying
	@Query("update Standard set name =? where id = ?")
	@Modifying
	public void update(String name , int id);
	
	
}
