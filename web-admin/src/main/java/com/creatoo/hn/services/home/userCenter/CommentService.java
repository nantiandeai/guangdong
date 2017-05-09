package com.creatoo.hn.services.home.userCenter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.creatoo.hn.model.WhCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import com.creatoo.hn.mapper.WhCommentMapper;
import com.creatoo.hn.model.WhComment;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**
 * 个人用户中心--点评业务类
 * @author dzl
 *
 */
@Service
public class CommentService {
   @Autowired
   private WhCommentMapper whcommMapper;
   
   /**
    * 我的活动点评查询
    * @param rmuid
    * @return
    */
   public Object selectMyActComm(String rmuid){
	   return this.whcommMapper.selectMyActComm(rmuid);
   }
   
   /**
    * 我的培训点评查询
    * @param rmuid
    * @return
    */
   public Object selectMyTraitmComm(String rmuid){
	   return this.whcommMapper.selectMyTraitmComm(rmuid);
   }
   
   /**
	 * 添加点评信息
	 * @param whcomm
	 * @return
	 */
  public Object addMyComm(WhComment whcomm){
    return this.whcommMapper.insert(whcomm);
  }
  
	/**
	 * 删除我的点评
	 * @param rmid
	 * @return
	 */
	public Object removeComm(String rmid){
		return this.whcommMapper.deleteByPrimaryKey(rmid);
	}
	
	/**
	 * 查询培训/活动/艺术作品的评论信息
	 * @param reftyp 评论类型（培训/活动/艺术作品）
	 * @param refid 评论类型的标识
	 * @return 评论列表
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List<HashMap> srchComment(String reftyp, String refid)throws Exception{
		/*Example example = new Example(WhComment.class);
		Criteria criteria = example.createCriteria();
		criteria.andNotEqualTo("rmreftyp", reftyp);
		criteria.andNotEqualTo("rmrefid", refid);
		criteria.andNotEqualTo("rmtyp", "0");*/
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("reftyp", reftyp);
		param.put("refid", refid);
		
		return this.whcommMapper.searchComment(param);
	}
	/** 查询回复
	 * @param reftype
	 * @param refid
	 * @return
	 * @throws Exception
	 */
	public List<Object> searchCommentHuifu(String rmids) throws Exception{
		List<String> rmidsList = Arrays.asList( rmids.split(",") );
		return this.whcommMapper.searchCommentHuifu(rmidsList);
	}
	
	/**
	 * 个人中心 评论查询
	 * @param request
	 * @param session
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Object loadcomLoad(WebRequest request,HttpSession session){
		//获取请求参数
		Map<String, Object> param = ReqParamsUtil.parseRequest(request);
		
		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));
		String userid = (String) param.get("rmuid");
		String rmreftyp = (String) param.get("rmreftyp");
		//String rmuid= (String) session.getAttribute(WhConstance.SESS_USER_ID_KEY);
		//带条件的分页查询
		//根据用户id获取所有评论
		PageHelper.startPage(page, rows);
		List<HashMap> commList = this.whcommMapper.searchMyComment(userid,rmreftyp);
		System.out.println("commList============"+commList);
		// 取分页信息
		PageInfo<HashMap> pinfo = new PageInfo<HashMap>(commList);
		commList = pinfo.getList();
		
		//将评论id 装数组
		java.util.ArrayList<String> ids = null;
		if(commList != null && commList.size()>0){
			ids = new java.util.ArrayList<String>();
			for(HashMap m :commList){
				ids.add((String)m.get("rmid"));
			}
		}
		System.out.println("ids==============="+ids);
		List<HashMap> commReList = this.whcommMapper.searchMyCommentRetry(ids);
		
		//将回复  和对应的 放map
		Map<String, HashMap> commRetryMap = new HashMap<String, HashMap>();
		if(commReList != null){
			for(HashMap m :commReList){
				commRetryMap.put((String)m.get("rmrefid"), m);
			}
		}
		
		Map<String, Object> commMapAll = new HashMap<String, Object>();
		commMapAll.put("total", pinfo.getTotal());
		commMapAll.put("commList", commList);
		commMapAll.put("commRetryMap", commRetryMap);
		return commMapAll;
	}
	/**
	 * 删除评论
	 */
	public void removeContent(WebRequest request){
			String rmid=request.getParameter("rmid");

			//查出所有回复 
			Example example=new Example(WhComment.class);
			example.or().andEqualTo("rmrefid",rmid);
			List<WhComment> list = this.whcommMapper.selectByExample(example);
			for(int i=0;i<list.size();i++){
				System.out.println("-------------------->"+rmid+"=="+list.get(i).getRmrefid());
				if(rmid.equals(list.get(i).getRmrefid())){
					this.whcommMapper.deleteByPrimaryKey(list.get(i).getRmid());
				}
			}
		 this.whcommMapper.deleteByPrimaryKey(rmid);
	}
}
