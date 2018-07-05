
package cn.itcast.bos.service.base.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.SubAreaDao;
import cn.itcast.bos.domain.base.SubArea;
import cn.itcast.bos.service.base.SubAreaService;

@Service
@Transactional
public class SubAreaServiceImpl implements SubAreaService {

	@Autowired
	private SubAreaDao subAreaDao;

	/**
	  * @Description: 分区实体主键类型是String类型
	  * @return 
	*/
	public void save(SubArea model) {
		model.setId(UUID.randomUUID().toString());
		subAreaDao.save(model);
	}

	public Page<SubArea> pageQuery(Pageable pageable) {
		return subAreaDao.findAll(pageable);
	}

	public List<SubArea> findNotAssocaiton() {
		return subAreaDao.findByFixedAreaIsNull();
	}

	public List<SubArea> findAll() {
		return subAreaDao.findAll();
	}

	public List<Object[]> showChart() {
		return subAreaDao.findCountByProvince();
	}
}
