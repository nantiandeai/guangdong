package com.creatoo.hn.services.admin.system;

import com.creatoo.hn.mapper.WhEntsourceMapper;
import com.creatoo.hn.model.WhAct;
import com.creatoo.hn.model.WhEntsource;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.creatoo.hn.utils.UploadUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by qxk on 2016/10/19.
 */

@Service
public class EntsrcService {

    @Autowired
    private CommService commService;

    @Autowired
    private WhEntsourceMapper entsourceMapper;

    public Object selectEntsrcList(WhEntsource ent, WebRequest request)throws Exception{
        Map<String,Object> params = ReqParamsUtil.parseRequest(request);
        int page = Integer.valueOf( params.get("page").toString() );
        int rows = Integer.valueOf( params.get("rows").toString() );
        String sort = (String) params.get("sort");
        String order = (String) params.get("order");
        String refid = (String) params.get("refid");
        String reftype = (String) params.get("reftype");
        String enttype = (String) params.get("enttype");
        String entsuorpro = (String) params.get("entsuorpro");
        Example example = new Example(WhEntsource.class);
        Example.Criteria c = example.createCriteria();
        if (sort != null && !"".equals(sort)) {
            example.setOrderByClause(sort + " " + order);
        }
        if (refid != null && !"".equals(refid)) {
        	c .andEqualTo("refid", refid);
        }
        if (reftype != null && !"".equals(reftype)) {
        	c .andEqualTo("reftype", reftype);
        }
        if (enttype != null && !"".equals(enttype)) {
        	c .andEqualTo("enttype", enttype);
        }
        if (entsuorpro != null && !"".equals(entsuorpro)) {
        	c .andEqualTo("entsuorpro", entsuorpro);
        }
        PageHelper.startPage(page, rows);
        List<?> list = this.entsourceMapper.selectByExample(example);
        PageInfo pinfo = new PageInfo(list);

        Map<String, Object> res = new HashMap<String, Object>();
        res.put("total", pinfo.getTotal());
        res.put("rows", pinfo.getList());
        return res;
    }
    
	public void addOrEditActInfo(WhEntsource ent,HttpServletRequest req,MultipartFile file_enturl,MultipartFile file_deourl) throws Exception {
		try {
			//处理设置的阿里云视频路径
			String video_enturl = req.getParameter("video_enturl");
			if (video_enturl!=null && !video_enturl.isEmpty()){
				ent.setEnturl(video_enturl);
			}

			// 保存记录
			String id = null;
			if (ent.getEntid()!= null && !"".equals(ent.getEntid())) {
				id = ent.getEntid();
				this.entsourceMapper.updateByPrimaryKeySelective(ent);
			} else {
				id=this.commService.getKey("WhEntsource");
				ent.setEntid(id);
				if (ent.getEnturl()==null) ent.setEnturl("");
				ent.setEntoptimt(new Date());
				this.entsourceMapper.insert(ent);
			}
			// 处理图片
			WhEntsource entsrc = this.entsourceMapper.selectByPrimaryKey(id);
			this.actInfoFile(entsrc,req,file_enturl,file_deourl);
			// 再次保存
			this.entsourceMapper.updateByPrimaryKeySelective(entsrc);
		} catch (Exception e) {
			throw new Exception("保存资源记录失败", e);
		}
	}
	
	// 保存上传的文件，写入文件路径到持久对像
		private void actInfoFile(WhEntsource ent, HttpServletRequest req,MultipartFile mfile,MultipartFile mfile1)
				throws Exception {
			// 当前日期
			Date now = new Date();
			//图片或者文件获取根目录
			String uploadPath = UploadUtil.getUploadPath(req);
			//图片处理
			if(mfile != null && !mfile.isEmpty()){
				//删除图片
				UploadUtil.delUploadFile(uploadPath, ent.getEnturl());
				//获取保存到db的路径
				String imgPath = UploadUtil.getUploadFilePath(mfile.getOriginalFilename(), commService.getKey("activity.picture"), "activity", "picture", now);
				//上传到本地
				mfile.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath) );
				//将\转/保存
				ent.setEnturl(UploadUtil.getUploadFileUrl(uploadPath, imgPath));
			}
			//图片处理
			if(mfile1 != null && !mfile1.isEmpty()){
				//删除图片
				UploadUtil.delUploadFile(uploadPath, ent.getDeourl());
				//获取保存到db的路径
				String imgPath = UploadUtil.getUploadFilePath(mfile1.getOriginalFilename(), commService.getKey("activity.picture"), "activity", "picture", now);
				//上传到本地
				mfile1.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath) );
				//将\转/保存
				ent.setDeourl(UploadUtil.getUploadFileUrl(uploadPath, imgPath));
			}
		}
		/**
		 * 删除资源信息操作
		 * 
		 * @return
		 */
		public void entRomv(String id,HttpServletRequest req) throws Exception{
			// 获取数据库中存放的图片路径,资料路径
			WhEntsource whent = this.entsourceMapper.selectByPrimaryKey(id);
			String uploadPath = UploadUtil.getUploadPath(req);
			String enturl=whent.getEnturl();
			String deourl=whent.getDeourl();
			// 删除记录
			this.entsourceMapper.deleteByPrimaryKey(id);
			
			UploadUtil.delUploadFile(uploadPath, enturl);
			UploadUtil.delUploadFile(uploadPath, deourl);
		}
}
