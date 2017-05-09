package com.creatoo.hn.utils;

import com.creatoo.hn.model.WhTyp;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.lang.reflect.Field;

/**
 * 公共的辅助方法
 * @author rbg
 *
 */
@SuppressWarnings("all")
public class CommUtil {

	/**
	 * 将WhTyp的List对象转化成前面EasyUI.tree组件需要的数据
	 * @param list 系统类型列表
	 * @return EasyUI.tree组件需要的数据
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Object> parseTree(List<WhTyp> list)throws Exception{
		List<Object> res = new ArrayList<Object>();
		for (int i=0; i<list.size(); i++) {
			WhTyp whtyp = list.get(i);
			if (whtyp.getTyppid() == null || "".equals(whtyp.getTyppid().trim()) || "0".equals(whtyp.getTyppid().trim())){
				Map item = BeanUtils.describe(whtyp);
				CommUtil.compMenuTree(item, list);
				
				item.put("id", item.get("typid"));
				item.put("text", item.get("typname"));
				res.add(item);
				
				//list.remove(i);
				//--i;
			}
		}
		return res;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void compMenuTree(Map<String,Object> item, List<WhTyp> list){
		if (item.get("children")==null){
			item.put("children", new ArrayList<Object>());
		}
		for(int i=0; i<list.size(); i++){
			WhTyp m = list.get(i);
			if (item.get("typid").equals(m.getTyppid())){
				List children = (List) item.get("children");
				try {
					Map _item = BeanUtils.describe(m);
					CommUtil.compMenuTree(_item, list);
					_item.put("id", _item.get("typid"));
					_item.put("text", _item.get("typname"));
					children.add(_item);
					//list.remove(i);
					//--i;
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}
	}

	/**
	 * 判断是否为整形
	 * added by caiyong
	 * 2017/4/6
	 * */
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}

	/**
	 * 根据类反射对象获取请求参数，如果请求参数不存在该字段则以null为缺失
	 * @param request
	 * @param classObject
	 * @return
	 */
	public static Map<String,String> getRequestParamByClass(HttpServletRequest request,Class classObject){
		Map<String,String> map = new HashMap<String,String>();
		Field[] fields = classObject.getDeclaredFields();
		for(Field field : fields){
			String key = field.getName();
			String value = request.getParameter(key);
			if(null != value){
				map.put(key,value);
			}
		}
		return map;
	}
}
