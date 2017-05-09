package com.creatoo.hn.services.home.event;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.WhBrandMapper;
import com.creatoo.hn.model.WhBrand;

@Service
public class SpecialService {
	@Autowired
	private WhBrandMapper whBrandMapper;

	/**
	 * 
	 * @param braid
	 * @return
	 */
	public WhBrand findDetail(String braid)throws Exception {
		
		return this.whBrandMapper.selectByPrimaryKey("2016110200000019");
	}


}
