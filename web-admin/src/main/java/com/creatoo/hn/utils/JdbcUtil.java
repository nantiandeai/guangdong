package com.creatoo.hn.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * JDBC辅助方法
 * @author wangxl
 *
 */
public class JdbcUtil {
	/**
	 * 获取连接
	 * @return
	 */
	public static Connection getConn() {
		Connection conn = null; // 创建数据库连接对象
		try {			
			Properties config = new Properties();
			config.load(JdbcUtil.class.getClassLoader().getResourceAsStream("application.properties"));
			String driver = config.getProperty("jdbc.driverClassName");
			String url = config.getProperty("jdbc.url");// 设置连接字符串
			String username = config.getProperty("jdbc.username");// 用户名
			String password = config.getProperty("jdbc.password");// 密码
			
			Properties props = new Properties();
            props.setProperty("user", username);
            props.setProperty("password", password);
            props.setProperty("remarks", "true"); //设置可以获取remarks信息 
            props.setProperty("useInformationSchema", "true");//设置可以获取tables remarks信息
            Class.forName(driver);//注册数据库驱动
            conn = DriverManager.getConnection(url, props);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * 获取表的字段
	 * @param tableName 表名
	 * @return 字段列表
	 */
	public static List<Map<String, String>> getFields(String tableName){
		List<Map<String, String>> fields = new ArrayList<Map<String, String>>();
		Connection conn = null;
		DatabaseMetaData md = null;
		ResultSet rs = null;
		
		try {
			conn = getConn();
			md = conn.getMetaData();
			rs = md.getColumns(null, "%", tableName, "%");
			while(rs.next()){
				String columnName = rs.getString("COLUMN_NAME"); 
				String columnType = rs.getString("TYPE_NAME"); 
				String data_type = rs.getString("DATA_TYPE");
				int datasize = rs.getInt("COLUMN_SIZE"); 
				int digits = rs.getInt("DECIMAL_DIGITS"); 
				int nullable = rs.getInt("NULLABLE"); 
				String remarks = rs.getString("REMARKS"); 
				
				Map<String, String> field = new HashMap<String, String>();
				field.put("fieldname", columnName.toLowerCase());
				field.put("fieldtype", columnType.toUpperCase());
				field.put("fieldjdbctype", data_type);
				field.put("fieldlength", datasize+"");
				field.put("fieldcomment", remarks);
				field.put("fieldrequired", nullable == 0 ? "true" : "false");
				fields.add(field);
				System.out.println(columnName+" "+columnType+" "+datasize+" "+digits+" "+ 	nullable+"  "+remarks); 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(rs != null){
        		try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        	}
        	if(conn != null){
        		try {
        			conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        	}
		}
		return fields;
	}
	
	/**
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static int sqlUpdate(String sql, List<Object> params)throws Exception{
		return 0;
	}
	
	/**
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> sqlQuery(String sql, List<Object> params)throws Exception{
		return null;
	}
	
	/**
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> sqlQueryOne(String sql, List<Object> params)throws Exception{
		return null;
	}
	
	
	
	public static void main(String[] args) {
		List<Map<String, String>> fields = getFields("wh_activity");
		System.out.println( fields );
	}
}
