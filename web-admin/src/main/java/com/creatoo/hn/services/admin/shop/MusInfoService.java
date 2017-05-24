package com.creatoo.hn.services.admin.shop;

import java.util.*;

import com.creatoo.hn.ext.bean.ResponseBean;
import com.creatoo.hn.model.WhgSysCult;
import com.creatoo.hn.model.WhgSysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.WhZxColinfoMapper;
import com.creatoo.hn.model.WhArtExhibition;
import com.creatoo.hn.model.WhZxColinfo;
import com.creatoo.hn.services.comm.CommService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
@SuppressWarnings("ALL")
@Service
public class MusInfoService {
	@Autowired
	public CommService commService;
	@Autowired
    private WhZxColinfoMapper whZxColinfoMapper;

    public Object srchOne(String id) throws Exception{
        return this.whZxColinfoMapper.selectByPrimaryKey(id);
    }

    public void t_add(WhZxColinfo info, WhgSysUser user) throws Exception{
        info.setClnfid(commService.getKey("whzxcolinfo"));
		info.setTotop(0);//默认不置顶
		info.setUpindex(0); //默认不上首页
		info.setClnfopttime(new Date());
        this.whZxColinfoMapper.insert(info);
    }

    public void t_edit(WhZxColinfo info, WhgSysUser user) throws Exception{
		if (info.getClnfstata() != null) {
			info.setClnfopttime(new Date());
		}
		this.whZxColinfoMapper.updateByPrimaryKeySelective(info);
    }

    /**
     * 查询
     * @param param
     * @return
     */
	public Map<String, Object> selein(Map<String, Object> param) {
		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));
				
		PageHelper.startPage(page, rows);
		Example example = new Example(WhZxColinfo.class);
		Criteria criteria = example.createCriteria();
		
		//排序
		if(param.containsKey("sort") && param.get("sort")!= null){
			StringBuffer sbf = new StringBuffer((String)param.get("sort"));
			if(param.containsKey("order") && param.get("order") !=  null){
				sbf.append(" ").append(param.get("order"));
			}
			example.setOrderByClause(sbf.toString());
		}else {
			example.setOrderByClause("totop desc,clnfcrttime desc");
		}
		//标题
		if(param.containsKey("clnftltle") && param.get("clnftltle") != null){
			String clnftltle = (String)param.get("clnftltle");
			if(!"".equals(clnftltle.trim())){
				criteria.andLike("clnftltle", "%"+clnftltle.trim()+"%");
			}
		}
//		example.setOrderByClause("clnfcrttime desc");
		//栏目
		if(param.containsKey("clnftype") && param.get("clnftype") != null){
			String clnftype = (String)param.get("clnftype");
			if(!"".equals(clnftype.trim())){
			 criteria.andEqualTo("clnftype", clnftype);
			}
		}
		List<WhZxColinfo> list = this.whZxColinfoMapper.selectByExample(example);
		
		PageInfo<WhZxColinfo> pageInfo = new PageInfo<WhZxColinfo>(list);
	    Map<String, Object> rtnMap = new HashMap<String, Object>();
	    rtnMap.put("total", pageInfo.getTotal());
	    rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	}
    /**
     * 添加栏目信息
     * @param whc
     * @return
     */
	public Object addinfo(WhZxColinfo whc) {
		return this.whZxColinfoMapper.insert(whc);
	}
    /**
     * 编辑栏目信息
     * @param whc
     * @return
     */
	public Object upmusinfo(WhZxColinfo whc) {
		return this.whZxColinfoMapper.updateByPrimaryKeySelective(whc);
	}
    /**
     * 根据主键删除栏目信息
     * @param clnfid
     * @return
     */
	public int delete(String clnfid) {
		return this.whZxColinfoMapper.deleteByPrimaryKey(clnfid);
		
	}
    /**
     * 审核状态改变
     * @param whz
     */
	public void checkin(WhZxColinfo whz) {
		this.whZxColinfoMapper.updateByPrimaryKeySelective(whz);
		
	}
	/**
	 * 设置上首页 及排序
	 * @param whz
	 */
	public void goHomePage(WhZxColinfo whz) {
		this.whZxColinfoMapper.updateByPrimaryKeySelective(whz);
	}
	/**
	 * 批量审核
	 * @param clnfid
	 * @param fromstate
	 * @param tostate
	 */
	public void checkexhi(String clnfid, int fromstate, int tostate) {
		List<String> list = new ArrayList<String>();
		String[] cln = clnfid.split(",");
		for (int i = 0; i < cln.length; i++) {
			list.add(cln[i]);
		}
		WhZxColinfo whz = new WhZxColinfo();
		whz.setClnfstata(tostate);
		whz.setClnfopttime(new Date());
		Example example = new Example(WhZxColinfo.class);
		example.createCriteria().andIn("clnfid", list).andEqualTo("clnfstata", fromstate);
		this.whZxColinfoMapper.updateByExampleSelective(whz, example);
		
	}

	/**
	 * 置顶
	 * @param whz
     */
	public void toTop(WhZxColinfo whz) {
//		if (whz.getTotop() != null && whz.getTotop() == 1 && !"".equals(whz.getClnftype())) {
//			WhZxColinfo co = this.whZxColinfoMapper.findIsTop(whz.getClnftype());
//			if (co != null) {
//				co.setTotop(0);
//				this.whZxColinfoMapper.updateByPrimaryKeySelective(co);
//			}
//		}
		this.whZxColinfoMapper.updateByPrimaryKeySelective(whz);
	}

	/**
	 * 上首页
	 * @param ids
	 * @param formupindex
	 * @param toupindex
	 * @return
	 */
	public ResponseBean t_upindex(String ids, String formupindex, int toupindex) {
		ResponseBean res = new ResponseBean();
		if(ids == null){
			res.setSuccess(ResponseBean.FAIL);
			res.setErrormsg("资讯主键丢失");
			return res;
		}
		Example example = new Example(WhZxColinfo.class);
		Example.Criteria c = example.createCriteria();
		c.andIn("clnfid", Arrays.asList( ids.split("\\s*,\\s*") ));
		c.andIn("upindex", Arrays.asList( formupindex.split("\\s*,\\s*") ));
		WhZxColinfo zx = new WhZxColinfo();
		zx.setUpindex(toupindex);
		this.whZxColinfoMapper.updateByExampleSelective(zx,example);
		return res;
	}
}
