package com.creatoo.hn.services.admin.arts;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.WhUserTroupeMapper;
import com.creatoo.hn.mapper.WhUserTroupeuserMapper;
import com.creatoo.hn.model.WhArtExhibition;
import com.creatoo.hn.model.WhTra;
import com.creatoo.hn.model.WhUserTroupe;
import com.creatoo.hn.model.WhUserTroupeuser;
import com.creatoo.hn.model.WhZypx;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.UploadUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 用户艺术团队展示类
 *
 */
@Service
public class TroupeService {
	@Autowired
	public CommService commService;
	@Autowired
	private WhUserTroupeMapper whUserTroupeMapper;
	@Autowired
	private WhUserTroupeuserMapper whUserTroupeuserMapper;
	/**
	 * 查询艺术团队信息
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> troselect(Map<String, Object> param)throws Exception {
		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));
				
		PageHelper.startPage(page, rows);
		Example example = new Example(WhUserTroupe.class);
		Criteria criteria = example.createCriteria();
		
		//排序
		if(param.containsKey("sort") && param.get("sort")!= null){
			StringBuffer sb = new StringBuffer((String)param.get("sort"));
			if(param.containsKey("order") && param.get("order") !=  null){
				sb.append(" ").append(param.get("order"));
			}
			example.setOrderByClause(sb.toString());
		}else {
			example.setOrderByClause("crtdate desc, troupeid desc");
		}
		
		//标题
		if(param.containsKey("troupename") && param.get("troupename") != null){
			String troupename = (String)param.get("troupename");
			if(!"".equals(troupename.trim())){
				criteria.andLike("troupename", "%"+troupename.trim()+"%");
			}
		}
		
		//艺术类型
		if(param.containsKey("troupearttyp") && param.get("troupearttyp") != null){
			String troupearttyp = (String)param.get("troupearttyp");
			if(!"".equals(troupearttyp.trim())){
				criteria.andEqualTo("troupearttyp", troupearttyp);
			}
		}
		//状态
		if(param.containsKey("troupestate") && param.get("troupestate") != null){
			String troupestate = (String)param.get("troupestate");
			if(!"".equals(troupestate.trim())){
			 criteria.andEqualTo("troupestate", troupestate);
			}
		}	
		List<WhUserTroupe> list = this.whUserTroupeMapper.selectByExample(example);
		PageInfo<WhUserTroupe> pageInfo = new PageInfo<WhUserTroupe>(list);
	    Map<String, Object> rtnMap = new HashMap<String, Object>();
	    rtnMap.put("total", pageInfo.getTotal());
	    rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	}
	/**
	 * 添加艺术团队信息
	 * @param whu
	 */
	public Object addgroup(WhUserTroupe whu)throws Exception {
		return this.whUserTroupeMapper.insert(whu);
		
	}
	/**
	 * 删除艺术团队信息
	 * @throws Exception
	 */
	public int delgroup(String troupeid)throws Exception {
		return this.whUserTroupeMapper.deleteByPrimaryKey(troupeid);
		
	}
	/**
	 * 更新艺术团队信息
	 * @param whu
	 * @throws Exception
	 */

	public Object upgroup(WhUserTroupe whu)throws Exception {
		return this.whUserTroupeMapper.updateByPrimaryKeySelective(whu);
	}
	/**
	 * 审核状态的改变
	 * @param
	 * @param
	 */
	public void checkTrou(WhUserTroupe whu) {
		Date date=new Date();
		whu.setTroupeopttime(date);
		this.whUserTroupeMapper.updateByPrimaryKeySelective(whu);
	}
	//----------------------艺术团员管理----------------------------------------
	/**
	 * 找拿到所有的艺术团团员信息
	 * @return
//	 */
	public Object findTroupeUser(Map<String, Object> param)throws Exception {
		//分页信息
				int page = Integer.parseInt((String)param.get("page"));
				int rows = Integer.parseInt((String)param.get("rows"));
				
				//带条件的分页查询
				PageHelper.startPage(page, rows);
				List<Map> list = this.whUserTroupeuserMapper.selTroupeUser(param);
				
				// 取分页信息
		        PageInfo<Map> pageInfo = new PageInfo<Map>(list);
		       
		        Map<String, Object> rtnMap = new HashMap<String, Object>();
		        rtnMap.put("total", pageInfo.getTotal());
		        rtnMap.put("rows", pageInfo.getList());
				return rtnMap;
	}
	/**
	 * 删除艺术团团员
	 */
	public void delTroupeUser(String uploadPath,String tuid)throws Exception {
		WhUserTroupeuser user = this.whUserTroupeuserMapper.selectByPrimaryKey(tuid);
		String tupic = user.getTupic();
		this.whUserTroupeuserMapper.deleteByPrimaryKey(tuid);
		UploadUtil.delUploadFile(uploadPath, tupic);
		
	}
	/**
	 * 添加艺术团团员
	 * @param user                                                                                                                                                                                                                                                                                                                                                                       
	 */
	public void saveUser(WhUserTroupeuser user)throws Exception {
		this.whUserTroupeuserMapper.insertSelective(user);
		
	}
	
	/**
	 * 拿到艺术团名称
	 * @return 
	 */
	public Object findTroupe() {
		return this.whUserTroupeMapper.selectAll();
	}
	/**
	 * 更新团员信息
	 * @param user
	 */
	public void updTroupeuser(WhUserTroupeuser user)throws Exception {
		
	this.whUserTroupeuserMapper.updateByPrimaryKeySelective(user);
	}
	/**
	 * 上首页 排序
	 * @param whu
	 */
	public void goHomePage(WhUserTroupe whu)throws Exception {
		this.whUserTroupeMapper.updateByPrimaryKeySelective(whu);
	}
	/**
	 * 批量审核
	 * @param troupeid
	 * @param fromstate
	 * @param tostate
	 */
	public void goPage(String troupeid, int fromstate, int tostate)throws Exception {
		List<String> list = new ArrayList<String>();
		String[] tro = troupeid.split(",");
		for (int i = 0; i < tro.length; i++) {
			list.add(tro[i]);
		}
		WhUserTroupe whu = new WhUserTroupe();
		whu.setTroupestate(tostate);
		whu.setTroupeopttime(new Date());
		Example example = new Example(WhUserTroupe.class);
		example.createCriteria().andIn("troupeid", list).andEqualTo("troupestate", fromstate);
		this.whUserTroupeMapper.updateByExampleSelective(whu, example);
		
	}
	
	/**
     * 查询单个团队信息
     * @param id 主键
     * @return 团队对象
     * @throws Exception
     */
    public WhUserTroupe t_srchOne(String id)throws Exception{
    	WhUserTroupe record = new WhUserTroupe();
        record.setTroupeid(id);
        return this.whUserTroupeMapper.selectOne(record);
    }

}
