package com.creatoo.hn.utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.context.request.WebRequest;

/**
 * 解析请求参数帮助类
 * @author rbg
 *
 */
public class ReqParamsUtil {
	/**
	 * 从请求对象中获取分页信息中的第几页
	 * @param request 请求对象
	 * @param defaultPage 默认第几页
	 * @return 分页信息中的第几页
	 */
	public static int getPage(HttpServletRequest request, int defaultPage){
		int page = defaultPage;

		try {
			String pageStr = request.getParameter("page");
			if (pageStr != null && !pageStr.isEmpty()) {
				page = Integer.parseInt(pageStr);
			}
		}catch (Exception e){
			e.printStackTrace();
		}

		return page;
	}

	/**
	 * 从请求对象中获取分页信息中的每页多少行记录
	 * @param request 请求对象
	 * @param defaultRows 默认每页的行数
	 * @return 分页信息中的每页行数
	 */
	public static int getRows(HttpServletRequest request, int defaultRows){
		int rows = defaultRows;

		try {
			String rowsStr = request.getParameter("rows");
			if (rowsStr != null && !rowsStr.isEmpty()) {
				rows = Integer.parseInt(rowsStr);
			}
		}catch (Exception e){
			e.printStackTrace();
		}

		return rows;
	}
	
	public static Map<String, Object> parseRequest(WebRequest request){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (request == null) return paramMap;
		
		Map<String, String[]> reqMap = request.getParameterMap();
		for(Entry<String, String[]> entry : reqMap.entrySet()){
			String[] values = entry.getValue();
			String key = entry.getKey();
			if (values==null || values.length == 0){
				continue;
			}
			if (values.length == 1){
				String value = values[0];
				if (value==null || "".equals(value.trim())) continue;
				paramMap.put(key, parseValue(value, key));
			}
			if (values.length > 1){
				paramMap.put(key, new ArrayList<Object>());
				ArrayList<Object> vlist = (ArrayList<Object>) paramMap.get(key);
				for(String value : values){
					if (value==null || "".equals(value.trim())) continue;
					vlist.add( parseValue(value, key) );
				}
			}
			
		}
		
		return paramMap;
	}
	
	private static Object parseValue(String value, String key){
		Object reObj = value;
		reObj = parseValue4date(value);
		reObj = parseValue4Array(value, key);
		
		return reObj;
	}
	
	private static Object parseValue4date(String value){
		//日期
		if(value != null && value.matches("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}")){
			try {
				return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value);
			} catch (ParseException e) {
				Logger.getLogger(ReqParamsUtil.class).error(e.getMessage(), e);
			}
		}else if(value != null && value.matches("\\d{4}-\\d{2}-\\d{2}")){
			try {
				return new java.text.SimpleDateFormat("yyyy-MM-dd").parse(value);
			} catch (ParseException e) {
				Logger.getLogger(ReqParamsUtil.class).error(e.getMessage(), e);
			}
		}
		return value;
	}
	
	private static Object parseValue4Array(String value, String key){
		//数组
		if(key.endsWith("Array") && value != null){
			value = value.replaceAll("\\s*(,)\\s*", "$1");
			return value.split(",");
		}else{
			return value;
		}
	}
	
	/**解析 请求参数，用于分页查询
	 * @param req HTTP请求对象
	 * @return
	 */
	public static Map<String, Object> parseRequest(HttpServletRequest req){
		//获取请求参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		//日期对象
		java.text.SimpleDateFormat sdf1 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		
		if(req != null){
			Enumeration<String> paraKeys = req.getParameterNames();
			while(paraKeys.hasMoreElements()){
				String key = paraKeys.nextElement();
				String value = req.getParameter(key);
				
				//日期
				if(value != null && value.matches("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}")){
					try {
						paramMap.put(key, sdf1.parse(value));
					} catch (ParseException e) {
						Logger.getLogger(ReqParamsUtil.class).error(e.getMessage(), e);
					}
				}else if(value != null && value.matches("\\d{4}-\\d{2}-\\d{2}")){
					try {
						paramMap.put(key, sdf2.parse(value));
					} catch (ParseException e) {
						Logger.getLogger(ReqParamsUtil.class).error(e.getMessage(), e);
					}
				}
				
				//数组
				else if(key.endsWith("Array") && value != null){
					paramMap.put(key, value.split(","));
				}else{
					paramMap.put(key, value);
				}
			}
		}
		
		return paramMap;
	}
	
	
	/**
	 * 根据请求对象获取客户IP
	 * @param request
	 * @return 客户IP
	 */
	public String gerClientIP(HttpServletRequest request){
	    String ip = request.getHeader("x-forwarded-for");
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getRemoteAddr();
	    }
	    return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
	}
	
	public static void main(String[] args) {
		java.text.SimpleDateFormat sdf1 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		
		String value = "2016-10-13";
		
		if(value != null && value.matches("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}")){
			try {
				System.out.println("1------"+sdf1.format(sdf1.parse(value)));
			} catch (ParseException e) {
				Logger.getLogger(ReqParamsUtil.class).error(e.getMessage(), e);
			}
		}else if(value != null && value.matches("\\d{4}-\\d{2}-\\d{2}")){
			try {
				System.out.println(sdf2.format(sdf2.parse(value)));
			} catch (ParseException e) {
				Logger.getLogger(ReqParamsUtil.class).error(e.getMessage(), e);
			}
		}
	}
}
