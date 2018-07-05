package cn.itcast.bos.dao.base;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.bos.domain.base.Standard;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class StandardDaoTest {

	@Autowired
	private StandardDao standardDao;
	
	@Test
	public void testFindAll() {
		List<Standard> list = standardDao.findAll();
		System.out.println(list);
	}
	
	@Test
	public void testSave() {
		Standard entity = new Standard();
		entity.setName("标准二（0-200公斤）");
		standardDao.save(entity);
	}
	
	@Test
	public void testFindByName() {
//		List<Standard> list = standardDao.findByName("标准一（0-100公斤）");
//		List<Standard> list = standardDao.findByAbc("标准一（0-100公斤）");
		List<Standard> list = standardDao.findByXyz("标准一（0-100公斤）");
//		List<Standard> list = standardDao.findByAaa("标准一（0-100公斤）");
		System.out.println(list);
	}

}
