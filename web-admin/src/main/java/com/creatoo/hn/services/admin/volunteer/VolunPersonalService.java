package com.creatoo.hn.services.admin.volunteer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.creatoo.hn.services.comm.CommService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.WhZyfcGerenMapper;
import com.creatoo.hn.mapper.WhZypxMapper;
import com.creatoo.hn.model.WhZyfcGeren;
import com.creatoo.hn.model.WhZypx;
import com.creatoo.hn.utils.UploadUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**
 * 志愿者--先进个人业务类
 * @author dzl
 *
 */
@Service
public class VolunPersonalService {
	@Autowired
	private WhZyfcGerenMapper personalMapper;
	@Autowired
	private WhZypxMapper whZypxMapper;
	@Autowired
	public CommService commService;
	
	/**
	 * 分页查询--先进个人信息
	 * @param
	 * @return
	 */
	public Map<String, Object> findPerson(Map<String, Object> param)throws Exception {
		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));
		
		//带条件的分页查询
		PageHelper.startPage(page, rows);
		List<Map> list = this.whZypxMapper.selectPersonal(param);
		
		// 取分页信息
        PageInfo<Map> pageInfo = new PageInfo<Map>(list);
       
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	}
	
	/**
	 * 更新先进人才
	 * @param whGr
	 */
	public void upPerson(WhZyfcGeren whGr)throws Exception {
		this.personalMapper.updateByPrimaryKeySelective(whGr);
		
	}

	/**
	 * 添加先进人才
	 * @param whGr
	 */
	public void addPerson(WhZyfcGeren whGr)throws Exception {
		this.personalMapper.insertSelective(whGr);
		
	}

	/**
	 * 新增或修改先进个人
	 * @param whgr
	 * @throws Exception
     */
	public void addOrUpdatePerson(WhZyfcGeren whgr) throws Exception {
		if (whgr.getZyfcgrid() != null && !"".equals(whgr.getZyfcgrid())) {
			this.personalMapper.updateByPrimaryKeySelective(whgr);
		} else {
			whgr.setZyfcgrid(this.commService.getKey("wh_zyfc_geren"));
			whgr.setZyfcgrstate(0);//新增默认状态0:为可编辑
			whgr.setZyfcgropttime(new Date());
			this.personalMapper.insertSelective(whgr);
		}
	}

    /**
     * 查询单条记录
     * @param zyfcgrid
     * @return
     * @throws Exception
     */
	public WhZyfcGeren search_one(String zyfcgrid) throws Exception{
        return this.personalMapper.selectByPrimaryKey(zyfcgrid);
    }
	/**
	 * 删除先进个人（old）
	 * @param uploadPath
	 * @param zyfcgrid
	 * @throws Exception
	 */
	public void deletePerson(String uploadPath, String zyfcgrid)throws Exception {
		WhZyfcGeren whGr = this.personalMapper.selectByPrimaryKey(zyfcgrid);
		String grpic = whGr.getZyfcgrpic();
		String grbigpic = whGr.getZyfcgrbigpic();
		this.personalMapper.deleteByPrimaryKey(zyfcgrid);
		UploadUtil.delUploadFile(uploadPath, grpic);
		UploadUtil.delUploadFile(uploadPath, grbigpic);
	}

    /**
     * 删除
     * @param zyfcgrid
     * @throws Exception
     */
    public void delete(String zyfcgrid) throws Exception {
        this.personalMapper.deleteByPrimaryKey(zyfcgrid);
    }

    /**
	 * 审核先进个人
	 * @param zyfcgrid
	 * @param fromstate
	 * @param tostate
	 * @return
	 */
	public Object checkPerson(String zyfcgrid, int fromstate, int tostate) {
		WhZyfcGeren whGr = new WhZyfcGeren();
		whGr.setZyfcgrstate(tostate);
		whGr.setZyfcgropttime(new Date());
		Example example = new Example(WhZyfcGeren.class);
		example.createCriteria().andEqualTo("zyfcgrid", zyfcgrid).andEqualTo("zyfcgrstate", fromstate);
		this.personalMapper.updateByExampleSelective(whGr, example);
		return "操作成功";
	}
	
	/**
	 * 批量审核或者发布等操作
	 * @param zyfcgrids
	 * @param fromstate
	 * @param tostate
	 * @return
	 */
	public Object checkAllPer(String zyfcgrids, int fromstate, int tostate) {
		List<String> list = new ArrayList<String>();
		String[] grid = zyfcgrids.split(",");
		for (int i = 0; i < grid.length; i++) {
			list.add(grid[i]);
		}
		WhZyfcGeren whGr = new WhZyfcGeren();
		whGr.setZyfcgrstate(tostate);
		whGr.setZyfcgropttime(new Date());
		Example example = new Example(WhZyfcGeren.class);
		example.createCriteria().andIn("zyfcgrid", list).andEqualTo("zyfcgrstate", fromstate);
		this.personalMapper.updateByExampleSelective(whGr, example);
		return "操作成功";
	}
}
