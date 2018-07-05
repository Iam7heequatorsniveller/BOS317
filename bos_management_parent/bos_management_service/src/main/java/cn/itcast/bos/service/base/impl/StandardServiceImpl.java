package cn.itcast.bos.service.base.impl;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.StandardDao;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.base.StandardService;

@Service
@Transactional
public class StandardServiceImpl implements StandardService {

	@Autowired
	private StandardDao standardDao;

	public void save(Standard model) {
		standardDao.save(model);
	}

	@RequiresPermissions("standard_pageQuery")
	public Page<Standard> pageQuery(Pageable pageable) {
		return standardDao.findAll(pageable);
	}

	public List<Standard> findAll() {
		return standardDao.findAll();
	}
}
