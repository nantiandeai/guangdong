package com.creatoo.hn.actions.module.fetch;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.creatoo.hn.mapper.WhFetchFromMapper;
import com.creatoo.hn.mapper.WhFetchStateMapper;
import com.creatoo.hn.model.WhFetchFrom;
import com.creatoo.hn.model.WhFetchState;
import com.creatoo.hn.utils.JdbcUtil;
import com.creatoo.hn.utils.WhConstance;

/**
 * 数据采集服务类
 * @author wangxl
 *
 */
@Service
public class FetchService {
	/**
	 * 日志控制器
	 */
	Logger log = Logger.getLogger(this.getClass());
	
	/**
	 * 数据采集来源
	 */
	@Autowired
	public WhFetchFromMapper whFetchFromMapper; 
	
	/**
	 * 数据采集状态
	 */
	@Autowired
	public WhFetchStateMapper whFetchStateMapper;
	
	/**
	 * 根据采集的类型和来源获取文件名称
	 * @param type 表名
	 * @param fromid 采集源标识
	 * @return 文件名称
	 * @throws Exception
	 */
	public String getFetchMappingFileName(String type, String fromid)throws Exception{
		//1xml文件名
		String xmlName = "fetch_"+fromid+"_"+type.toLowerCase()+".xml";
		return xmlName;
	}
	
	/**
	 * 获得已经生成的采集数据的映射文件
	 * @param xmlName 文件名
	 * @return 采集数据的映射文件
	 * @throws Exception
	 */
	public File getFetchMappingFile(String xmlName)throws Exception{
		File xmlFile = null;//xml文件
		String rootPath = WhConstance.getSysProperty("SYS_UPLOAD_PATH", "C:\\temp\\upload\\");
		String[] rootPaths = rootPath.split(",");
		if(rootPaths != null){
			for(String rp : rootPaths){				
				xmlFile = new File(rp, xmlName);
				if(xmlFile.exists()){
					break;
				}else{
					xmlFile = null;
				}
			}
		}
		return xmlFile;
	}
	
	/**
	 * 获取存放映射文件的相对路径
	 * @param xmlName 映射文件名
	 * @return 存放映射文件的相对路径
	 * @throws Exception
	 */
	public String getFetchMappingFileRootPath()throws Exception{
		String rootPathReal = "";//取最后一个文件存放路径
		String rootPath = WhConstance.getSysProperty("SYS_UPLOAD_PATH", "C:\\temp\\upload\\");
		String[] rootPaths = rootPath.split(",");
		if(rootPaths != null){
			for(String rp : rootPaths){
				rootPathReal = rp;
			}
		}
		return rootPathReal;
	}
	
	/**
	 * 根据采集映射文件名，解析xml文档,如果文件不存在，生成新的
	 * @param xmlName xml文件名
	 * @param type 表名
	 * @return 解析xml文档以列表数据
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getFetchMappingData(String xmlName, String type)throws Exception{
		List<Map<String, Object>> rtnList = new ArrayList<Map<String, Object>>();
		
		//2xml是否存在
		File xmlFile = getFetchMappingFile(xmlName);//xml文件
		
		//3如果文件不存在，生成一个文件
		if(xmlFile == null){
			FileOutputStream fos = null;
			XMLWriter xmlWriter = null;
			try {
				//fields为根元素
				Element root = DocumentHelper.createElement("fields");  
				Document document = DocumentHelper.createDocument(root);
				
				//表名
				String tableName = type;
				
				//生成元素
				List<Map<String, String>> fields = JdbcUtil.getFields(tableName);
				if(fields != null){
					for(Map<String, String> fieldMap : fields){
						
						//一个字段 
						Element field = root.addElement("field"); 
						
						Element fieldname = field.addElement("fieldname");//字段名称,对应表字段名称 
						fieldname.addText(fieldMap.get("fieldname"));
						
						Element fieldtype = field.addElement("fieldtype");//字段类型 
						fieldtype.addText(fieldMap.get("fieldtype"));
						
						Element fieldjdbctype = field.addElement("fieldjdbctype");//字段类型 
						fieldjdbctype.addText(fieldMap.get("fieldjdbctype"));
						
						Element fieldlength = field.addElement("fieldlength");//字段长度
						fieldlength.addText(fieldMap.get("fieldlength"));
						
						Element fieldrequired = field.addElement("fieldrequired");//是否必须
						fieldrequired.addText(fieldMap.get("fieldrequired"));
						
						Element fieldcomment = field.addElement("fieldcomment");//字段描述 
						fieldcomment.addCDATA(fieldMap.get("fieldcomment"));
						
						Element fieldformat = field.addElement("fieldformat");//日期格式
						fieldformat.addCDATA("");
						
						Element fieldregex = field.addElement("fieldregex"); //正则匹配
						fieldregex.addCDATA("regex");
						
						Element defaultVal = field.addElement("defaultVal");//默认值
						defaultVal.addCDATA("");
						
						Element valMapping = field.addElement("valMapping"); //值映射多个用逗号分隔
						valMapping.addCDATA("");  
					}
				}
				
				//把生成的xml文档存放在硬盘上  true代表是否换行  
				OutputFormat format = new OutputFormat("    ",true);  
				format.setEncoding("UTF-8");//设置编码格式  
				String rootPathReal = getFetchMappingFileRootPath();//取最后一个文件存放路径
				fos = new FileOutputStream(rootPathReal+File.separator+xmlName);
				xmlWriter = new XMLWriter(fos, format);  
				xmlWriter.write(document);  
				
				//确定好文件名
				xmlFile = new File(rootPathReal, xmlName);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			} finally{
				if(fos != null){
					fos.close();
				}
				if(xmlWriter != null){
					xmlWriter.close();
				}
			}
		}
		
		//4解析xml文件
		SAXReader reader = new SAXReader();
		Document document = reader.read(xmlFile);
		List<Element> fields = (List<Element>)document.getRootElement().elements("field");
		if(fields != null){
			for(Element field : fields){
				Element fieldname = field.element("fieldname");//字段名称,对应表字段名称 
				Element fieldtype = field.element("fieldtype");//字段类型 
				Element fieldjdbctype = field.element("fieldjdbctype");//字段类型 
				Element fieldlength = field.element("fieldlength");//字段长度
				Element fieldrequired = field.element("fieldrequired");//是否必须
				Element fieldcomment = field.element("fieldcomment");//字段描述 
				Element fieldformat = field.element("fieldformat");//日期格式
				Element fieldregex = field.element("fieldregex"); //正则匹配
				Element defaultVal = field.element("defaultVal");//默认值
				Element valMapping = field.element("valMapping"); //值映射多个用逗号分隔
				
				Map<String, Object> tmap = new HashMap<String, Object>();
				tmap.put("fieldname", fieldname.getTextTrim());
				tmap.put("fieldtype", fieldtype.getTextTrim());
				tmap.put("fieldjdbctype", fieldjdbctype.getTextTrim());
				tmap.put("fieldlength", fieldlength.getTextTrim());
				tmap.put("fieldrequired", fieldrequired.getTextTrim());
				tmap.put("fieldcomment", fieldcomment.getTextTrim());
				tmap.put("fieldformat", fieldformat.getTextTrim());
				tmap.put("fieldregex", fieldregex.getTextTrim());
				tmap.put("defaultVal", defaultVal.getTextTrim());
				tmap.put("valMapping", valMapping.getTextTrim());
				rtnList.add(tmap);
			}
		}
		
		return rtnList;
	}

	
	/**
	 * 根据type和fromid获得数据采集时数据映射信息，用于前端生成编辑表单
	 * @param type 表名
	 * @param fromid 数据采集来源标识
	 * @return 数据映射信息
	 * @throws Exception
	 */
	public List<Map<String, Object>> getFetchMapping(String type, String fromid)throws Exception{
		//1xml文件名
		String xmlName = getFetchMappingFileName(type, fromid);
		
		return getFetchMappingData(xmlName, type);
	}	
	
	/**
	 * 保存数据映射信息
	 * @param type 表名
	 * @param fromid 来源标识
	 * @param request 请求对象
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void saveFetchMapping(String type, String fromid, HttpServletRequest request)throws Exception{
		FileOutputStream fos = null;
		XMLWriter xmlWriter = null;
		SAXReader reader = null;
		try {
			//1xml文件名
			String xmlName = getFetchMappingFileName(type, fromid);
			
			//xml文件,不存在，生成xml文件
			File xmlFile = this.getFetchMappingFile(xmlName);
			if(!xmlFile.exists()){
				this.getFetchMappingData(xmlName, type);
			}
			
			//解析XML
			reader = new SAXReader();
			Document document = reader.read(xmlFile);
			List<Element> fields = (List<Element>)document.getRootElement().elements("field");
			if(fields != null){
				for(Element field : fields){
					Element fieldname = field.element("fieldname");//字段名称,对应表字段名称 
					Element fieldformat = field.element("fieldformat");//日期格式
					Element fieldregex = field.element("fieldregex"); //正则匹配
					Element defaultVal = field.element("defaultVal");//默认值
					Element valMapping = field.element("valMapping"); //值映射多个用逗号分隔
					
					//表单元素规则 
					String _fieldformat = request.getParameter("fieldformat_"+fieldname);
					String _fieldregex = request.getParameter("fieldregex_"+fieldname);
					String _defaultVal = request.getParameter("defaultVal_"+fieldname);
					String _valMapping = request.getParameter("valMapping_"+fieldname);
					
					fieldformat.clearContent();
					fieldformat.addCDATA(_fieldformat);
					fieldregex.clearContent();
					fieldregex.addCDATA(_fieldregex);
					defaultVal.clearContent();
					defaultVal.addCDATA(_defaultVal);
					valMapping.clearContent();
					valMapping.addCDATA(_valMapping);
				}
			}
			
			//保存
			OutputFormat format = new OutputFormat("    ",true);  
			format.setEncoding("UTF-8");//设置编码格式  
			String rootPathReal = getFetchMappingFileRootPath();//取最后一个文件存放路径
			fos = new FileOutputStream(rootPathReal+File.separator+xmlName);
			xmlWriter = new XMLWriter(fos, format);  
			xmlWriter.write(document);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		} finally{
			if(fos != null){
				fos.close();
			}
			if(xmlWriter != null){
				xmlWriter.close();
			}
		}
		
	}
	
	/**
	 * 根据指定的来源采集数据,把来源中的活动/培训/资讯采集到本系统中
	 * @param fromid 采集来源标识
	 * @throws Exception
	 */
	public void fetchData(String fromid, String uid)throws Exception{
		try {
			//是否正在采集中
			boolean isRuning = isRuning(fromid);
			if(isRuning){
				throw new Exception("此数据源正在采集中.");
			}
			
			//修改为正在采集中,防止并发采集
			WhFetchState record = new WhFetchState();
			record.setFsfromid(fromid);
			record.setFsstate(0);
			int rows = this.whFetchStateMapper.updateByPrimaryKeySelective(record);
			if(rows < 1){
				record.setFsstime(new Date());
				record.setFsoptuid(uid);
				rows = this.whFetchStateMapper.insert(record);
				if(rows > 0){
					record.setFsoptuid(null);
					this.whFetchStateMapper.updateByPrimaryKeySelective(record);
				}
			}
			
			//开始采集数据
			WhFetchFrom from = this.whFetchFromMapper.selectByPrimaryKey(fromid);
			List<Map<String, Object>> mapping = this.getFetchMapping(from.getFromfetchtype()+"", fromid);
			FetchDataTask myTask = new FetchDataTask(from, mapping);  
			myTask.start();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}
	
	/**
	 * 获取当前的采集状态
	 * @param fromid 来源标识
	 * @throws Exception
	 */
	public boolean isRuning(String fromid)throws Exception{
		boolean isRuning = false;
		WhFetchState record = new WhFetchState();
		record.setFsfromid(fromid);
		record = this.whFetchStateMapper.selectOne(record);
		if(record != null && record.getFsstate() == 0){
			isRuning = true;
		}
		return isRuning;
	}
	
	
	
	public static void main(String[] args)throws Exception {
		FetchService fetch = new FetchService();
		//fetch.getFetchMapping("a.xml");
		
		
		List<Map<String, Object>> rtnList = fetch.getFetchMappingData("e.xml", "wh_fetch_data");
		System.out.println( rtnList );
	}
}
