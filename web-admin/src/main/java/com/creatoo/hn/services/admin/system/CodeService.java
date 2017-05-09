package com.creatoo.hn.services.admin.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.creatoo.hn.utils.ReqParamsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.WhCodeMapper;
import com.creatoo.hn.model.WhCode;
import com.creatoo.hn.services.comm.CommService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.web.context.request.WebRequest;
import tk.mybatis.mapper.entity.Example;

/**
 * 验证码业务类
 * @author dzl
 *
 */
@Service
public class CodeService {
	@Autowired
	private WhCodeMapper codeMapper;
	  
    @Autowired
	public CommService commService;
	
	 /**
     * 显示验证码列表
     * @return
     */
    public List<WhCode> getList()throws Exception{
    	return this.codeMapper.selectAll();	
    }
    
    /**
     * 添加验证码记录
     * @throws Exception 
     */
    public Object addCode(WhCode whcode){
 	   try {
 		  whcode.setId(this.commService.getKey("whcode"));
 		this.codeMapper.insert(whcode);
 		return "success";
 	} catch (Exception e) {
 		e.printStackTrace();
 		  return "error";
 	}
 } 
   /**
    * 根据id获得验证码信息
    * @param id
    * @return
    */
   public Object getCodeId(Object id){
		return this.codeMapper.selectByPrimaryKey(id);
	}
  
   /**
    * 删除验证码
    */
    public int removeCode(String id){
    	return this.codeMapper.deleteByPrimaryKey(id);
    }
    
    /**
     * 修改验证码
     */
   public Object modifyCode(WhCode whcode){
	   return this.codeMapper.updateByPrimaryKey(whcode);
   }
   
   /**
    * 分页管理
    */
   public Object findPage(int page, int rows){
	   //带条件的分页查询
	   Example example = new Example(WhCode.class);
	   PageHelper.startPage(page, rows);
	   List<WhCode> list = this.codeMapper.selectByExample(example);
		
		// 取分页信息
       PageInfo<WhCode> pageInfo = new PageInfo<WhCode>(list);
      
       Map<String, Object> rtnMap = new HashMap<String, Object>();
       rtnMap.put("total", pageInfo.getTotal());
       rtnMap.put("rows", pageInfo.getList());
	   return rtnMap;
	}

	/**
	 * 工具栏加载 数据
	 * @param page
	 * @param rows
	 * @param request
	 * @return
     * @throws Exception
     */
	public Object loadMsg(int page, int rows, WebRequest request) throws Exception{
		Map<String, Object> param = ReqParamsUtil.parseRequest(request);
		//带条件的分页查询
		Example example = new Example(WhCode.class);

		Example.Criteria criteria=example.createCriteria();
		//根据手机号码查找验证码信息
		if(param.containsKey("msgphone") && param.get("msgphone") != null){
			String msgphone = (String)param.get("msgphone");
			if(!"".equals(msgphone.trim())){
				criteria.andLike("msgphone", "%"+msgphone.trim()+"%");
			}
		}
		//根据邮箱地址查找验证码信息
		if(param.containsKey("emailaddr") && param.get("emailaddr") != null){
			String emailaddr = (String)param.get("emailaddr");
			if(!"".equals(emailaddr.trim())){
				criteria.andLike("emailaddr", "%"+emailaddr.trim()+"%");
			}
		}
		//根据时间查找验证码信息
		/*if(param.containsKey("msgtime_s") && param.get("msgtime_s") != null ||
			param.containsKey("msgtime_e") && param.get("msgtime_e") != null){
			String msgtime_s = (String)param.get("msgtime_s");
			String msgtime_e = (String)param.get("msgtime_e");
			if(null != msgtime_s.trim()){
				criteria.andGreaterThan("msgtime", msgtime_s);
//				criteria.add(Restrictions.ge("msgtime",msgtime_s));
			}
			if(null != msgtime_e.trim()){
				criteria.andLessThan("msgtime", msgtime_e);
//				criteria.add(Restrictions.le("msgtime",msgtime_e));
			}
			if(null != msgtime_s.trim() && null != msgtime_e.trim()){
				criteria.andBetween("msgtime", msgtime_s, msgtime_e);
//				criteria.add(Restrictions .between("msgtime",msgtime_s,msgtime_e));
			}
		}*/
		if(param.containsKey("msgtime_s") && param.get("msgtime_s") != null){
			String msgtime_s = (String)param.get("msgtime_s");
			if(null != msgtime_s.trim()){
				criteria.andGreaterThan("msgtime", msgtime_s);
//				criteria.add(Restrictions.ge("msgtime",msgtime_s));
			}
		}
		if(param.containsKey("msgtime_e") && param.get("msgtime_e") != null){
			String msgtime_e = (String)param.get("msgtime_e");
			if(null != msgtime_e.trim()){
				criteria.andLessThan("msgtime", msgtime_e);
//				criteria.add(Restrictions.le("msgtime",msgtime_e));
			}
		}
		if(param.containsKey("msgtime_s") && param.get("msgtime_s") != null &&
				param.containsKey("msgtime_e") && param.get("msgtime_e") != null){
			String msgtime_s = (String)param.get("msgtime_s");
			String msgtime_e = (String)param.get("msgtime_e");
			if(null != msgtime_s.trim() && null != msgtime_e.trim()){
				criteria.andBetween("msgtime", msgtime_s, msgtime_e);
//				criteria.add(Restrictions .between("msgtime",msgtime_s,msgtime_e));
			}
		}
		PageHelper.startPage(page, rows);
		List<WhCode> list = this.codeMapper.selectByExample(example);

		// 取分页信息
		PageInfo<WhCode> pageInfo = new PageInfo<WhCode>(list);

		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("total", pageInfo.getTotal());
		rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	}
}
