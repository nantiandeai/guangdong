package com.creatoo.hn.services.admin.user;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.creatoo.hn.mapper.WhUserArtistMapper;
import com.creatoo.hn.model.WhArtExhibition;
import com.creatoo.hn.model.WhUserArtist;
import com.creatoo.hn.model.WhUserTroupe;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.UploadUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * Created by qxk on 2016/10/18.
 */
@Service
public class ActilstServices {

    @Autowired
    private WhUserArtistMapper userArtistMapper;

    @Autowired
    public CommService commservice;
    
    /**
     * 查询
     * @param param
     * @return
     * @throws Exception
     */
    public Map<String, Object> loadActilsList(Map<String, Object> param) throws Exception{
    	//分页信息
    	int page = Integer.parseInt((String)param.get("page"));
    	int rows = Integer.parseInt((String)param.get("rows"));
    					
    	PageHelper.startPage(page, rows);
    	Example example = new Example(WhUserArtist.class);
    	Criteria criteria = example.createCriteria();
		//排序
		if(param.containsKey("sort") && param.get("sort")!= null){
			StringBuffer sb = new StringBuffer((String)param.get("sort"));
			if(param.containsKey("order") && param.get("order") !=  null){
				sb.append(" ").append(param.get("order"));
			}
			example.setOrderByClause(sb.toString());
		}
		//标题
		if(param.containsKey("artistname") && param.get("artistname") != null){
		     String artistname = (String)param.get("artistname");
		       if(!"".equals(artistname.trim())){
			     criteria.andLike("artistname", "%"+artistname.trim()+"%");
		       }
		 }
		//艺术类型
	    if(param.containsKey("artistarttyp") && param.get("artistarttyp") != null){
			String artistarttyp = (String)param.get("artistarttyp");
			   if(!"".equals(artistarttyp.trim())){
				  criteria.andEqualTo("artistarttyp", artistarttyp);
			    }
		}
	    //状态
	    if(param.containsKey("artiststate") && param.get("artiststate") != null){
	 			String artiststate = (String)param.get("artiststate");
	 			   if(!"".equals(artiststate.trim())){
	 				  criteria.andEqualTo("artiststate", artiststate);
	 			    }
	 	}
	    
        List<WhUserArtist> list = this.userArtistMapper.selectByExample(example);
        
        PageInfo<WhUserArtist> pageInfo = new PageInfo<WhUserArtist>(list);
        
	    Map<String, Object> rtnMap = new HashMap<String, Object>();
	    rtnMap.put("total", pageInfo.getTotal());
	    rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
    }

    public void addOrEditActilst(WhUserArtist artist, String uploadPath, MultipartFile file_artistpic) throws Exception{
        try{
            // 保存记录
            String id = null;
            
            //当前日期
			Date now = new Date();
			
            //img
            if(file_artistpic != null && !file_artistpic.isEmpty()){
				UploadUtil.delUploadFile(uploadPath, artist.getArtistpic());
				
				String imgPath_artpic = UploadUtil.getUploadFilePath(file_artistpic.getOriginalFilename(), commservice.getKey("art.picture"), "art", "picture", now);
				file_artistpic.transferTo( UploadUtil.createUploadFile(uploadPath, imgPath_artpic) );
				artist.setArtistpic(UploadUtil.getUploadFileUrl(uploadPath, imgPath_artpic));
			}
            
            if (artist.getArtistid()!=null && !"".equals(artist.getArtistid())){
            	//update
                id = artist.getArtistid();
                this.userArtistMapper.updateByPrimaryKeySelective(artist);
            }else{
            	//add
            	
                id = this.commservice.getKey("WhUserArtist");
                artist.setArtistid(id);
                artist.setArtistregtime(new Date(System.currentTimeMillis()));
                artist.setArtiststate(0);
                this.userArtistMapper.insert(artist);
            }
        }catch (Exception e){
            throw new Exception("保存艺术家记录失败", e);
        }
    }

    private void removeActilstFile(String dbfilepath, String uploadPath) {
        if (dbfilepath != null && !"".equals(dbfilepath)) {
            dbfilepath = dbfilepath.replaceFirst("upload/", "");
            dbfilepath = dbfilepath.replaceAll("/", "\\" + File.separator);

            File _file = new File(uploadPath, dbfilepath);
            if (_file.exists() && _file.isFile()) {
                _file.delete();
            }
        }
    }

    public void removeActilst(String id, String uploadPath) throws Exception{
        if (id==null) return;
        WhUserArtist art = this.userArtistMapper.selectByPrimaryKey(id);
        if (art == null) return;

        String dbimg = art.getArtistpic();

        this.userArtistMapper.deleteByPrimaryKey(id);

        this.removeActilstFile(dbimg, uploadPath);
    }
    /**
     * 艺术家审核
     * @param artistid
     * @param artiststate
     */
	public void checkAct(WhUserArtist art) {
		Date date=new Date();
		art.setArtstime(date);
		this.userArtistMapper.updateByPrimaryKeySelective(art);
	}
    /**
     * 上首页排序
     * @param whus
     */
	public void goHomePage(WhUserArtist whus) {
		this.userArtistMapper.updateByPrimaryKeySelective(whus);
		
	}
    /**
     * 批量状态
     * @param artistid
     * @param fromstate
     * @param tostate
     */
	public void checkeart(String artistid, int fromstate, int tostate) {
		List<String> list = new ArrayList<String>();
		String[] art = artistid.split(",");
		for (int i = 0; i < art.length; i++) {
			list.add(art[i]);
		}
		WhUserArtist whu = new WhUserArtist();
		whu.setArtiststate(tostate);
		whu.setArtstime(new Date());
		Example example = new Example(WhUserArtist.class);
		example.createCriteria().andIn("artistid", list).andEqualTo("artiststate", fromstate);
		this.userArtistMapper.updateByExampleSelective(whu,example);
		
	}
}
