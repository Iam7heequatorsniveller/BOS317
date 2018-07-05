package cn.itcast.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.itcast.bos.domain.base.SubArea;

public interface SubAreaService {

	void save(SubArea model);

	Page<SubArea> pageQuery(Pageable pageable);

	List<SubArea> findNotAssocaiton();

	List<SubArea> findAll();

	List<Object[]> showChart();

}
