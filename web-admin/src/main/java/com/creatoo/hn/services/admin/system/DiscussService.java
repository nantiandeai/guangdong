package com.creatoo.hn.services.admin.system;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import com.creatoo.hn.actions.home.userCenter.UserCenterAction;
import com.creatoo.hn.mapper.WhCommentMapper;
import com.creatoo.hn.model.WhComment;
import com.creatoo.hn.model.WhMgr;
import com.creatoo.hn.model.WhUserAlerts;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@SuppressWarnings("all")
@Service
public class DiscussService {
	
	@Autowired
	private WhCommentMapper commentMapper;
	@Autowired
	private CommService commService;
	
	@Autowired
	private UserCenterAction useaction;
	/**
	 * 查询评论信息
	 * @return
	 */
//	public Object loaddisList(int page,int rows,WebRequest request){
//		String rmreftyp=request.getParameter("rmreftyp");
//		Map<String, Object> param=new HashMap<String, Object>();
//		//Map<String, Object> param = ReqParamsUtil.parseRequest(request);
//		//String rmreftyp=(String) param.get("rmreftyp");
//		String nrmreftyp=null;
//		if (rmreftyp != null && !"".equals(rmreftyp)) {
//        	if(rmreftyp.equals("培训")){
//        		nrmreftyp="2016101400000051";
//        	}else if(rmreftyp.equals("活动")){
//        		nrmreftyp="2016101400000052";
//        	}else if(rmreftyp.equals("场馆")){
//        		nrmreftyp="2016101400000053";
//        	}else if(rmreftyp.equals("场馆")){
//        		nrmreftyp="2016101400000054";
//        	}else if(rmreftyp.equals("评论")){
//        		nrmreftyp="2016102200000003";
//        	}else{
//        		nrmreftyp="&*!@#S";
//        	}
//        }
//		param.put("rmreftyp", nrmreftyp);
//		PageHelper.startPage(page, rows);
//		List<?> list = this.commentMapper.selectdisList(param);
//		PageInfo pinfo = new PageInfo(list);
//
//		Map<String, Object> res = new HashMap<String, Object>();
//		res.put("total", pinfo.getTotal());
//		res.put("rows", pinfo.getList());
//
//		return res;
//	}
	/**
	 * 删除评论信息操作
	 * 
	 * @return
	 */
	public Object removediscuss(String id,String rmtyp)throws Exception{
			if(rmtyp.equals("0")){
				//查出所有回复 
				Example example=new Example(WhComment.class);
				example.or().andEqualTo("rmtyp",1);
				List<WhComment> list = this.commentMapper.selectByExample(example);
				for(int i=0;i<list.size();i++){
					System.out.println("-------------------->"+id+"=="+list.get(i).getRmrefid());
					if(id.equals(list.get(i).getRmrefid())){
						this.commentMapper.deleteByPrimaryKey(list.get(i).getRmid());
					}
				}
			}
		
		return this.commentMapper.deleteByPrimaryKey(id);
	}
	/**
	 * 查询评论信息
	 * @return
	 */
	/*public Object loaddisList(int page,int rows,WebRequest request){
		//==========条件查找
		String rmreftyp=request.getParameter("rmreftyp");
		String rmtyp=request.getParameter("rmtyp");
		Example example=new Example(WhComment.class);
		Example example2=new Example(WhComment.class);
		Criteria c=example.createCriteria();
		Criteria c2=example2.createCriteria();
		//String nrmreftyp=null;
		if (rmreftyp != null && !"".equals(rmreftyp)) {
			c.andEqualTo("rmreftyp",rmreftyp);
        }
		if (rmtyp != null && !"".equals(rmtyp)) {
			if(rmtyp.equals("0")){
				c.andEqualTo("rmtyp",rmtyp);
				c2.andEqualTo("rmtyp","88");
			}else{
				if (rmreftyp != null && !"".equals(rmreftyp)) {
					c2.andEqualTo("rmreftyp",rmreftyp);
		        }
				c.andEqualTo("rmtyp","88");
				c2.andEqualTo("rmtyp",rmtyp);
			}
        }else{
        	c.andEqualTo("rmtyp",0);
        	c2.andEqualTo("rmtyp",1);
        }
		//=============查询所有评论
		PageHelper.startPage(page, rows);
		List<WhComment> listpl = this.commentMapper.selectByExample(example);
		//===============查询所有回复
		List<WhComment> listhf = this.commentMapper.selectByExample(example2);
		List<WhComment> list=new ArrayList<WhComment>();
		//条件查询是 评论为空 则只输出回复
		if(listhf.size()!=0&&listpl.size()==0){
			list=listhf;
		}
		for(int i=0;i<listpl.size();i++){
			//条件查询是 回复为空 则只输出评论
			if(listhf.size()==0&&listpl.size()!=0){
				list=listpl;
				break;
			}
			for(int j=0;j<listhf.size();j++){
				if(j==0){
					list.add(listpl.get(i));
				}
				System.out.println("=================>"+listpl.get(i).getRmid()+"=="+listhf.get(j).getRmrefid());
				//判断评论是否有回复 有则将回复加入评论后
				if(listpl.get(i).getRmid().equals(listhf.get(j).getRmrefid())){
					list.add(listhf.get(j));
				}
			}
		}
	
		PageInfo pinfo = new PageInfo(list);
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("total", pinfo.getTotal());
		res.put("rows", pinfo.getList());
		return res;
	}*/
	/**
	 * 管理员后台 查询评论
	 * @param request
	 * @return
	 */
	public Object selecthdComment(WebRequest request) throws Exception{
		Map<String, Object> param = ReqParamsUtil.parseRequest(request);
		int page=Integer.parseInt((String)param.get("page"));
		int rows=Integer.parseInt((String)param.get("rows"));
		PageHelper.startPage(page, rows);
		List<Map> list= this.commentMapper.selecthdComment(param);
		
		PageInfo pinfo = new PageInfo(list);
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("total", pinfo.getTotal());
		res.put("rows", pinfo.getList());
		return res;
	}
	/**
	 * 管理员增加回复
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public Object addCommentHF(WebRequest request,HttpSession session) throws Exception {
		String rmrefid = request.getParameter("rmid");
		String rmcontent = request.getParameter("rmcontent");
		String rmpltype = request.getParameter("rmpltype");
		String rmuid = request.getParameter("rmuid");
		WhComment comment = new WhComment();
		comment.setRmcontent(rmcontent);
		comment.setRmrefid(rmrefid);
		comment.setRmdate(new Date());
		comment.setRmreftyp("2016102200000003");
		comment.setRmtyp(1);
		comment.setRmstate(1);
		if(Integer.parseInt(rmpltype) == 1){
			comment.setRmpltype(1);
		}else{
			comment.setRmpltype(0);
		}
		
		/*//修改评论状态
		WhComment comment2 = new WhComment();
		comment2.setRmid(rmrefid);
		comment2.setRmstate(Integer.parseInt(rmpltype));
		this.commentMapper.updateByPrimaryKeySelective(comment2);*/
		WhMgr whmgr=(WhMgr) session.getAttribute("user");
		comment.setRmuid(whmgr.getId());
		comment.setRmid(this.commService.getKey("whcomm"));
		
		this.useaction.addNewAlert(rmuid,"5");
		
		return this.commentMapper.insert(comment);
	}
	
	/***
	 * 审核
	 * @param comment
	 * @return
	 * @throws Exception
	 */
	public Object contentEdit(WhComment comment,String rmcontent2,HttpSession session)throws Exception{
		if(rmcontent2 != null &&!"".equals(rmcontent2)){
			WhComment comment2 = new WhComment();
			comment2.setRmcontent(rmcontent2);
			comment2.setRmrefid(comment.getRmid());
			comment2.setRmdate(new Date());
			comment2.setRmreftyp("2016102200000003");
			comment2.setRmtyp(1);
			comment2.setRmstate(1);
			comment2.setRmpltype(0);
			
			WhMgr whmgr=(WhMgr) session.getAttribute("user");
			comment2.setRmuid(whmgr.getId());
			comment2.setRmid(this.commService.getKey("whcomm"));
			this.commentMapper.insert(comment2);
		}
		comment.setRmcontent(null);
		return this.commentMapper.updateByPrimaryKeySelective(comment);
	}
	
	
	/**
	 * 根据评论id 查找有关的回复
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/loadhfList")
	public Object loadhfList(WebRequest request)throws Exception{
		Map<String, Object> param = ReqParamsUtil.parseRequest(request);
		return this.commentMapper.selecthdCommentHF(param);
	}
}
