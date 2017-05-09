package com.creatoo.hn.mapper;



import java.util.HashMap;
import java.util.List;

import com.creatoo.hn.model.WhTraitm;
import tk.mybatis.mapper.common.Mapper;

public interface WhTraitmMapper extends Mapper<WhTraitm> {
	public List<HashMap> selectTraitm();
	
	public List<HashMap> selAllTitle();
}