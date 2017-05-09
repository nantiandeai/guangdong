package com.creatoo.hn.actions.module.fetch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.creatoo.hn.model.WhFetchFrom;

/**
 * 执行采集任务
 * @author wangxl
 *
 */
public class FetchDataTask extends TimerTask {
	//私有成员变量
	private Timer timer = null;//执行器 
	private WhFetchFrom from = null;//采集源ID
	private List<Map<String, Object>> mapping = null;//元数据映射信息
	
	
	/**
	 * 构造方法,指定采集源
	 * @param fromid 采集源标识
	 */
	public FetchDataTask(WhFetchFrom from, List<Map<String, Object>> mapping){
		this.timer = new Timer();
		this.from = from;
		this.mapping = mapping;
	}
	
	/**
	 * 开始采集入口方法,1秒后开始采集
	 */
	public void start(){
		this.timer.schedule(this, 10000);
	}

	/* 采集数据的主业务逻辑
	 * @see java.util.TimerTask#run()
	 */
	@Override
	public void run() {
		try {
			fetchData();
		} catch (Exception e) {
			Logger.getLogger(FetchDataTask.class.getName()).error(e.getMessage(), e);
		} finally {
			try {
				this.cancel();
				this.timer.cancel();
			} catch (Exception e) {
				Logger.getLogger(FetchDataTask.class.getName()).error(e.getMessage(), e);
			}
		}
		
	}
	
	/**
	 * 数据采集
	 * @throws Exception
	 */
	private void fetchData()throws Exception{
//		DataSourceTransactionManager transactionManager = null;
//		TransactionStatus status = null;
//		try {
//			transactionManager = (DataSourceTransactionManager) SpringContextUtil.getApplicationContext().getBean("transactionManager");
//			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务，这样会比较安全些。
//			status = transactionManager.getTransaction(def); // 获得事务状态
//			
//			//查询数据库中的值
//			WhConfigMapper configMapper = (WhConfigMapper)SpringContextUtil.getApplicationContext().getBean("whConfigMapper");
//			
//	        
//			//逻辑代码，可以写上你的逻辑处理代码
//			transactionManager.commit(status);
//		} catch (Exception e) {
//			//回滚事务
//			transactionManager.rollback(status);
//		}
		
		//列表URL 变量${page}
		String listURL = this.from.getFromlisturl();
		if(listURL.indexOf("${page}") > -1){//解析分页
			//分页必须有“初始值”和“增量值”两个配置
			String urlval_init = from.getFromlistvalinitval();//初始值 
			String urlval_add = from.getFromlistvaladdval();//增量值
			int _urlval_init = Integer.parseInt(urlval_init);//初始值 
			int _urlval_add = Integer.parseInt(urlval_add);//增量值
			
			int curVal = _urlval_init;
			while(true){
				listURL = listURL.replace("${page}", curVal+"");
				boolean isEnd = parseListURL(listURL);
				if(isEnd){
					break;
				}
				curVal += _urlval_add;
			}
		}else{//解析详情页
			parseInfoURL(listURL, null, null);
		}
	}
	
	/**
	 * 解析列表页的数据
	 * @param url 列表页的请求连接
	 * @return true-分页已经结束; false-分页未结束
	 * @throws Exception
	 */
	private boolean parseListURL(String url)throws Exception{
		boolean isEnd = false;
		
		try {
			//采集来源的配置信息
			String type = this.from.getFromfetchtype();//采集的类型
			String fromlistitemmatch = this.from.getFromlistitemmatch();//列表元素选择器
			String frominfoaddrmatch = this.from.getFrominfoaddrmatch();//详情地址元素选择器，一般有href属性
			String fromitemidmatch = this.from.getFromitemidmatch();//第三方唯一主键选择器
			
			//根据连接抓取数据
			Document doc = Jsoup.connect(url).get();
			
			//抓取到的内容
			if(doc != null){
				Elements items = doc.select(fromlistitemmatch);//一个列表页的多项
				if(items != null){
					Iterator<Element> iterator = items.iterator();
					while(iterator.hasNext()){
						//解析列表元素中的数据
						Element item = iterator.next();
						List<Map<String, Object>> fetchData = fetchDataByMapping(item);
						
						//解析唯一主键
						String ID = null;
						Elements idEles = item.select(fromitemidmatch);
						if(idEles != null){
							idEles.text();
							ID = idEles.text();
						}
						
						//详情页的地址-通过元素的href属性
						String itemurl = item.select(frominfoaddrmatch).attr("href");
						parseInfoURL(itemurl, ID, fetchData);
					}
				}
			}
		} catch (Exception e) {
			isEnd = true;
			Logger.getLogger(FetchDataTask.class.getName()).error(e.getMessage(), e);
		}
		
		return isEnd;
	}
	

	/**
	 * 解析详情页的数据
	 * @param url 连接地址
	 * @param fromID 外部数据的唯一标识
	 * @param fetchData 映射好的一部分数据
	 * @throws Exception
	 */
	private void parseInfoURL(String url, String fromID, List<Map<String, Object>> fetchData)throws Exception{
		try {
			//采集来源的配置信息
			String type = this.from.getFromfetchtype();//采集的类型
			String fromlistitemmatch = this.from.getFromlistitemmatch();//列表元素选择器
			String frominfoaddrmatch = this.from.getFrominfoaddrmatch();//详情地址元素选择器，一般有href属性
			String fromitemidmatch = this.from.getFromitemidmatch();//第三方唯一主键选择器
			
			//根据连接抓取数据
			Document doc = Jsoup.connect(url).get();
			
			//抓取到的内容
			if(doc != null){
				//fromID为null
				if(fromID == null){
					fromID = doc.select(fromitemidmatch).text();
				}
				
				//
				fetchDataByMapping(doc, fetchData);
				
				//将数据写入数据库
				saveData(type, fetchData, fromID);
			}
		} catch (Exception e) {
			Logger.getLogger(FetchDataTask.class.getName()).error(e.getMessage(), e);
		}
	}
	
	
	/**
	 * 保存采集到数据
	 * @param fetchData 采集到的数据
	 * @param ID 唯一标识
	 * @throws Exception
	 */
	private void saveData(String type, List<Map<String, Object>> fetchData, String ID)throws Exception{
		//是否已经采集过该数据 0-活动;1-培训;2-资讯
//		if(0 == type){
//			saveData4act(fetchData, ID);
//		}else if(1 == type){
//			saveData4tra(fetchData, ID);
//		}else if(2 == type){
//			saveData4zx(fetchData, ID);
//		}
	}
	
	/**
	 * 保存采集活动
	 * @param fetchData
	 * @param ID
	 * @throws Exception
	 */
	private void saveData4act(List<Map<String, Object>> fetchData, String ID)throws Exception{
		//WhFetchActMapper whFetchActMapper = (WhFetchActMapper)SpringContextUtil.getApplicationContext().getBean("whFetchActMapper");
		
//		Example example = new Example(WhFetchAct.class);
//		example.createCriteria().andEqualTo("fafromid", fromid).andEqualTo("fafromdataid", ID);
//		int count = whFetchActMapper.selectCountByExample(example);
//		if(count < 1){
//			WhFetchAct act = new WhFetchAct();
//		
//			if(fetchData != null){
//				for(Map<String, Object> map : fetchData){
//					try {
//						String fieldname = (String)map.get("fieldname");
//						String fieldtype = (String)map.get("fieldtype");
//						String fieldjdbctype = (String)map.get("fieldjdbctype");
//						String fieldlength = (String)map.get("fieldlength");
//						String fieldrequired = (String)map.get("fieldrequired");
//						String fieldcomment = (String)map.get("fieldcomment");
//						String fieldformat = (String)map.get("fieldformat");
//						String fieldregex = (String)map.get("fieldregex");
//						String defaultVal = (String)map.get("defaultVal");//默认值
//						String valMapping = (String)map.get("valMapping");
//						String fieldvalue = (String)map.get("fieldvalue");
//						
//						//确定最后的值
//						String lastVal = null;
//						if(fieldvalue != null){
//							lastVal = fieldvalue;
//						}else if(defaultVal != null && "".equals(defaultVal)){
//							lastVal = defaultVal;
//						}
//						
//						//采集到了值
//						if(lastVal != null){
//							//映射处理
//							Map<String, String> _valMapping = new HashMap<String, String>();
//							if(valMapping != null){//a=b;c=d;
//								String[] mappingArr = valMapping.split(";");
//								for(String valMap : mappingArr){
//									if(valMap != null && !valMap.isEmpty()){
//										String[] arr2 = valMap.split("=");
//										if(arr2.length == 2){
//											if(!arr2[0].isEmpty()){
//												_valMapping.put(arr2[0], arr2[1]);
//											}
//										}
//									}
//								}
//								if(_valMapping.containsKey(lastVal)){
//									lastVal = _valMapping.get(lastVal);
//								}
//							}
//							
//							
//							//属性类型
//							Field clazzField = act.getClass().getDeclaredField(fieldname);
//							String methodName = "set"+fieldname.substring(0,1).toUpperCase()+fieldname.substring(1);
//							Method m = act.getClass().getMethod(methodName, clazzField.getType());
//							
//							if(String.class.equals(clazzField.getType())){//字符串类型
//								m.invoke(act, lastVal);
//							}else if(java.util.Date.class.equals(clazzField.getType())){//日期类型
//								if(fieldformat.isEmpty()){
//									java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(fieldformat);
//									Date date =  sdf.parse(lastVal);
//									m.invoke(act, date);
//								}
//							}else if(java.lang.Integer.class.equals(clazzField.getType())){//int类型
//								m.invoke(act, Integer.parseInt(lastVal));
//							}
//						}
//					} catch (Exception e) {
//						Logger.getLogger(FetchDataTask.class.getName()).error(e.getMessage(), e);
//					}
//				}
//			}
//			whFetchActMapper.insert(act);
//		}
		
	}
	
	
	/**
	 * 根据模板和抓取的元素，解析数据
	 * @param ele
	 * @return
	 * @throws Exception
	 */
	private List<Map<String, Object>> fetchDataByMapping(Element ele)throws Exception{
		List<Map<String, Object>> fetchData = new ArrayList<Map<String, Object>>();
		if(this.mapping != null && ele != null){
			for(Map<String, Object> map : this.mapping){
				String fieldregex = (String)map.get("fieldregex");
				if(fieldregex != null && !"".equals(fieldregex) && !"null".equals(fieldregex.toLowerCase())){
					String fieldvalue = ele.select(fieldregex).text();
					
					Map<String, Object> _map = new HashMap<String, Object>();
					_map.putAll(map);
					_map.put("fieldvalue", fieldvalue);
					fetchData.add(_map);
				}
			}
		}
		return fetchData;
	}
	
	
	/**
	 * 根据模板和抓取的元素，解析数据
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	private void fetchDataByMapping(Document doc, List<Map<String, Object>> fetchData)throws Exception{
		if(this.mapping != null && doc != null){
			for(Map<String, Object> map : this.mapping){
				String fieldregex = (String)map.get("fieldregex");
				if(fieldregex != null && !"".equals(fieldregex) && !"null".equals(fieldregex.toLowerCase())){
					String fieldvalue = doc.select(fieldregex).text();
					
					Map<String, Object> _map = new HashMap<String, Object>();
					_map.putAll(map);
					_map.put("fieldvalue", fieldvalue);
					fetchData.add(_map);
				}
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args)throws Exception {
		FetchDataTask myTask = new FetchDataTask(null, null);  
		myTask.start();
		System.out.println( "main" );
		
		//WhFetchAct act = new WhFetchAct();
		
		//属性类型
//		Field clazzField = act.getClass().getDeclaredField("actvid");
//		System.out.println( clazzField.getType() );
//		
//		if(clazzField.getType().equals(String.class)){
//			System.out.println( "is String" );
//		}
	}
}
