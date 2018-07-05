package cn.itcast.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.itcast.bos.domain.base.Courier;


public interface CourierService {

	void save(Courier model);

	Page<Courier> pageQuery(Pageable pageable);

	Page<Courier> pageQuery(Courier model, Pageable pageable);

	void delete(String ids);

	List<Courier> listajax(String fixedAreaId);
	
}
