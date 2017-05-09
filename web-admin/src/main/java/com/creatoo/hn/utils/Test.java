package com.creatoo.hn.utils;

import java.io.File;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;

/**
 * @author rbg
 *
 */
public class Test {
	/**
	 * 获取mysql连接
	 * @return
	 */
	private static Connection getMySqlConn() {
		//String driver = "com.mysql.jdbc.Driver";
		//String url = "jdbc:mysql://120.77.10.118:3307/szwhg?useUnicode=true&characterEncoding=UTF8";// 设置连接字符串
		String url = "jdbc:mysql://192.168.0.168:3306/szwhg?useUnicode=true&characterEncoding=UTF8";// 设置连接字符串
		String username = "root";// 用户名
		String password = "123456";// 密码
		Connection conn = null; // 创建数据库连接对象
		try {
//			Class.forName(driver);
//			conn = DriverManager.getConnection(url, username, password);
			
			Properties props =new Properties();
            props.setProperty("user", username);
            props.setProperty("password", password);
            props.setProperty("remarks", "true"); //设置可以获取remarks信息 
            props.setProperty("useInformationSchema", "true");//设置可以获取tables remarks信息
            conn = DriverManager.getConnection(url, props);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return conn;
	}
	
	
    /**
     * 获得Oracle连接
     * @return
     */
    private static Connection getOracleConn() {
        //String driver = "oracle.jdbc.driver.OracleDriver";
        //String url = "jdbc:oracle:thin:@localhost:1521:orcl";// 设置连接字符串
    	//String url = "jdbc:oracle:thin:@10.145.192.4:1521:DGSZWHG";// 设置连接字符串
        //String url = "jdbc:oracle:thin:@//localhost:1521/dgszwhg";
        String url = "jdbc:oracle:thin:@//192.168.0.168:1521/pdborcl";
        String username = "szwhdg";//"dgswhg";//"dgszwhg";// 用户名
        String password = "szwhdg";//"ctdgwhg";//"dgszwhg";// 密码
        Connection conn = null; // 创建数据库连接对象
        try {
            //Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }

    public static void query() {
        Connection conn = getOracleConn();
        String sql = "select * from table1";
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            // 建立一个结果集,用来保存查询出来的结果

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String username = rs.getString("column1");
                System.out.println(username);
            }
            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * 从mysql生成ddl脚本
     */
    public static List<String> genOracleDDLFromMysql(){
    	Connection mysqlConn = null;
    	ResultSet rs = null;
    	List<String> sqlList = new ArrayList<String>();
    	try {
    		//获取连接
    		mysqlConn = getMySqlConn();
    		
    		//获取所有表和注释
    		DatabaseMetaData md =  mysqlConn.getMetaData();
    		rs = md.getTables(null, "%", "%", new String[]{"TABLE"});
    		Map<String, String> tableNames = new HashMap<String, String>();
    		while(rs.next()){
    			String tableName = rs.getString("TABLE_NAME");
    			tableNames.put(tableName, rs.getString("REMARKS"));
    		}
    		
    		//所有字段类型
    		Set<String> columnTypeSet = new HashSet<String>();
    		
    		//类型映射
    		@SuppressWarnings("serial")
			Map<String, String> typeMapper = new HashMap<String, String>(){
    			{
    				put("DATE", "DATE");
    				put("DATETIME", "TIMESTAMP(6)");
    				//put("DATETIME", "DATE");
    				put("LONGTEXT", "CLOB");
    				put("VARCHAR", "VARCHAR2");
    				put("TEXT", "CLOB");
    				put("INT", "NUMBER(11)");
    				put("CHAR", "CHAR");
    			}
    		};
    		
    		StringBuffer sb = new StringBuffer("");
    		for(String tableName : tableNames.keySet()){
    			rs.close();
    			rs = md.getColumns(null, "%", tableName, "%");
    			
    			StringBuffer t_sql = new StringBuffer("");
    			List<String> commSQL = new ArrayList<String>();
    			sb.append("\n-- ----------------------------");
    			sb.append("\n-- Table structure for ").append(tableName);
    			sb.append("\n-- ----------------------------");
    			sb.append("\nCREATE TABLE "+tableName+" (");
    			t_sql.append("CREATE TABLE "+tableName+" (");
    			
    			StringBuffer sb_t = new StringBuffer("");
    			sb_t.append("\ncomment on table "+tableName+" is '"+tableNames.get(tableName)+"';");
    			commSQL.add("comment on table "+tableName+" is '"+tableNames.get(tableName)+"'");
    			while(rs.next()){
    				String columnName = rs.getString("COLUMN_NAME"); 
    				String columnType = rs.getString("TYPE_NAME"); 
    				int datasize = rs.getInt("COLUMN_SIZE"); 
    				int digits = rs.getInt("DECIMAL_DIGITS"); 
    				int nullable = rs.getInt("NULLABLE"); 
    				String REMARKS = rs.getString("REMARKS"); 
    				columnTypeSet.add(columnType);
    				//System.out.println(columnName+" "+columnType+" "+datasize+" "+digits+" "+ 	nullable+"  "+REMARKS); 
    				
    				sb.append("\n	").append(columnName).append(" ").append(typeMapper.get(columnType));
    				t_sql.append("\n	").append(columnName).append(" ").append(typeMapper.get(columnType));
    				if("VARCHAR".equals(columnType) || "CHAR".equals(columnType)){
    					if(datasize*3 < 4000){
    						datasize = datasize*3;
    					}else{
    						datasize = 4000;
    					}
    					sb.append(" ").append("("+datasize+")");
    					t_sql.append(" ").append("("+datasize+")");
    				}
    				if(nullable == 0){
    					sb.append(" ").append("NOT NULL");
    					t_sql.append(" ").append("NOT NULL");
    				}
    				sb.append(" ").append(",");
    				t_sql.append(" ").append(",");
    				sb_t.append("\ncomment on column "+tableName+"."+columnName+" is '"+REMARKS+"';");
    				commSQL.add("comment on column "+tableName+"."+columnName+" is '"+REMARKS+"'");
    			}
    			
    			//获取主键
    			rs.close();
    			rs = md.getPrimaryKeys(null, null, tableName); 
    			String pkName = "";
    			String colName = "";
    			while(rs.next()){
    				colName = colName + ("".equals(colName) ? "": ",")+(String)rs.getObject(4); 
    				pkName = (String)rs.getObject(6); 
    			}
    			sb.append("\n	").append("primary key("+colName+")");
    			t_sql.append("\n	").append("primary key("+colName+")");
    			sb.append("\n);");
    			t_sql.append("\n)");
    			sqlList.add(t_sql.toString());
    			sqlList.addAll(commSQL);
    			sb.append(sb_t);
    			sb.append("\n");
    		}
    		
    		//System.out.println( tableNames.size() );
    		System.out.println( columnTypeSet );
    		//System.out.println( sb );

    		//FileUtils.writeStringToFile(new File("E:\\temp\\dgszwhg_ddl_"+new java.text.SimpleDateFormat("yyyyMMddhh").format(new Date())+".sql"), sb.toString(), Charset.forName("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
        	if(rs != null){
        		try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        	}
        	if(mysqlConn != null){
        		try {
					mysqlConn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        	}
        }
    	return sqlList;
    }
    
    /**
     * 在oracle创建DDL
     */
    public static void doDDLtoOracle(List<String> sqls){
    	Connection oracleConn = null;
    	Statement stmt = null;
    	try {
    		//获取连接
    		oracleConn = getOracleConn();
    		oracleConn.setAutoCommit(false);
    		stmt = oracleConn.createStatement(); 
    		for (String sql : sqls) {
    			//System.out.println( sql );
    			stmt.addBatch(sql);
    		} 
    		stmt.executeBatch();  
    		oracleConn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
        	if(stmt != null){
        		try {
        			stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        	}
        	if(oracleConn != null){
        		try {
        			oracleConn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        	}
        }
    }
    
    public static void doInsertToOracle(){
    	Connection oracleConn = null;
    	Connection mysqlConn = null;
    	ResultSet rs_oralce = null;
    	ResultSet rs_mysql = null;
    	PreparedStatement stmt_oracle = null;
    	PreparedStatement stmt_mysql = null;
    	try {
    		//获取连接
    		oracleConn = getOracleConn();
    		mysqlConn = getMySqlConn();
    		
    		//获取所有表和注释
    		DatabaseMetaData md =  mysqlConn.getMetaData();
    		rs_mysql = md.getTables(null, "%", "%", new String[]{"TABLE"});
    		Map<String, String> tableNames = new HashMap<String, String>();
    		while(rs_mysql.next()){
    			String tableName = rs_mysql.getString("TABLE_NAME");
    			tableNames.put(tableName, rs_mysql.getString("REMARKS"));
    		}
    		rs_mysql.close();
    		
    		//循环每张表
    		if(tableNames != null){
    			//删除所有数据
    			for(String tableName : tableNames.keySet()){
    				stmt_oracle = oracleConn.prepareStatement("delete from "+tableName);
    				stmt_oracle.executeUpdate();
    			}
    			stmt_oracle.close();
    			
    			
    			for(String tableName : tableNames.keySet()){
    				System.out.println( tableName+"=====================================" );
    				
    				List<Map<String, Object>> rowDatas = new ArrayList<Map<String, Object>>();
    				String sql = "select * from "+tableName;
    				stmt_mysql = mysqlConn.prepareStatement(sql);
    				rs_mysql = stmt_mysql.executeQuery();
    				ResultSetMetaData rsmd = rs_mysql.getMetaData();
    	            int columnCount = rsmd.getColumnCount();
    	            while (rs_mysql.next()) {
    	            	String fields = "";
    	            	String fieldVals = "";
    	                Map<String, Object> rowData = new HashMap<String, Object>();
    	                for (int i = 1; i <= columnCount; i++) {
    	                    rowData.put(rsmd.getColumnName(i).toUpperCase(), rs_mysql.getObject(i));
    	                    
    	                    fields = fields + ("".equals(fields)?"":",") + rsmd.getColumnName(i);
    	                    fieldVals = fieldVals + ("".equals(fieldVals)?"":",") + "?";
    	                    //System.out.println( "column:"+rsmd.getColumnName(i)+"  type:"+rsmd.getColumnType(i)+"  type:"+rsmd.getColumnTypeName(i)+"   val:"+rs_mysql.getObject(i) );
    	                }
    	                //String insertSql = "insert into "+tableName+"("+fields+") values("+fieldVals+")";
    	                
    	                
    	                rowDatas.add(rowData);
    	                
    	                //System.out.println( insertSql );
    	                //break;
    	                //list.add(rowData);
    	            }
    	            
    	            
    	            insert(oracleConn, tableName, rowDatas);
    	            System.out.println( tableName+"=====================================END-"+java.sql.Types.CLOB );
    	            //break;
    			}
    		}
    		
    		
    		
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
        	if(rs_mysql != null){
        		try {
        			rs_mysql.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        	}
        	if(rs_oralce != null){
        		try {
        			rs_oralce.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        	}
        	if(oracleConn != null){
        		try {
        			oracleConn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        	}
        	if(mysqlConn != null){
        		try {
        			mysqlConn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        	}
        }
    }
    
    
    public static void genInitScriptToOracle(){
    	Connection oracleConn = null;
    	Connection mysqlConn = null;
    	ResultSet rs_oralce = null;
    	ResultSet rs_mysql = null;
    	PreparedStatement stmt_oracle = null;
    	PreparedStatement stmt_mysql = null;
    	try {
    		//获取连接
    		//oracleConn = getOracleConn();
    		mysqlConn = getMySqlConn();
    		
    		//获取所有表和注释
    		DatabaseMetaData md =  mysqlConn.getMetaData();
    		rs_mysql = md.getTables(null, "%", "%", new String[]{"TABLE"});
    		Map<String, String> tableNames = new HashMap<String, String>();
    		while(rs_mysql.next()){
    			String tableName = rs_mysql.getString("TABLE_NAME");
    			tableNames.put(tableName, rs_mysql.getString("REMARKS"));
    		}
    		rs_mysql.close();
    		
    		//需要初始的表
    		Map<String, String> initTables = new HashMap<String, String>(){
				private static final long serialVersionUID = 1L;
				{
    				put("wh_config", "wh_config");
    				put("wh_key", "wh_key");
    				put("wh_menu", "wh_menu");
    				put("wh_mgr", "wh_mgr");
    				put("wh_role", "wh_role");
    				put("wh_rolepms", "wh_rolepms");
    				put("wh_serialno", "wh_serialno");
    				put("wh_tag", "wh_tag");
    				put("wh_typ", "wh_typ");
    			}
    		};
    		
    		Set<String> fieldTypes = new HashSet<String>();
    		Set<String> fieldTypeNames = new HashSet<String>();
    		
    		//循环每张表
    		StringBuffer insertSB = new StringBuffer();
    		if(tableNames != null){
    			for(String tableName : tableNames.keySet()){
    				if(tableName != null && initTables.containsKey(tableName.toLowerCase())){
    					
    					System.out.println( tableName+"=====================================" );
    				
	    				List<Map<String, Object>> rowDatas = new ArrayList<Map<String, Object>>();
	    				String sql = "select * from "+tableName;
	    				stmt_mysql = mysqlConn.prepareStatement(sql);
	    				rs_mysql = stmt_mysql.executeQuery();
	    				ResultSetMetaData rsmd = rs_mysql.getMetaData();
	    	            int columnCount = rsmd.getColumnCount();
	    	            
	    	            while (rs_mysql.next()) {
	    	            	
	    	            	insertSB.append("\ninsert into ").append(tableName).append("(");
	    	            	
	    	            	String fields = "";
	    	            	String fieldVals = "";
	    	                Map<String, Object> rowData = new HashMap<String, Object>();
	    	                for (int i = 1; i <= columnCount; i++) {
	    	                    
	    	                	String _val = "";
	    	                	if(rs_mysql.getObject(i) == null){
	    	                		_val = "null";
	    	                	}else if("DATETIME".equals(rsmd.getColumnTypeName(i))){
	    	                		java.util.Date d = rs_mysql.getDate(i);
	    	                		String datetime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
	    	                		_val = "to_date('"+datetime+"', 'yyyy-mm-dd hh24:mi:ss')";
	    	                	}else if("DATE".equals(rsmd.getColumnTypeName(i))){
	    	                		java.util.Date d = rs_mysql.getDate(i);
	    	                		String datetime = new java.text.SimpleDateFormat("yyyy-MM-dd").format(d);
	    	                		_val = "to_date('"+datetime+"', 'yyyy-mm-dd')";
	    	                	}else if("VARCHAR".equals(rsmd.getColumnTypeName(i))){
	    	                		_val = "'"+rs_mysql.getString(i)+"'";
	    	                	}else if("INT".equals(rsmd.getColumnTypeName(i))){
	    	                		_val = ""+rs_mysql.getInt(i)+"";
	    	                	}
	    	                	
	    	                    fields = fields + ("".equals(fields)?"":",") + rsmd.getColumnName(i);
	    	                    fieldVals = fieldVals + ("".equals(fieldVals)?"":",") + _val;
	    	                    //System.out.println( "column:"+rsmd.getColumnName(i)+"  type:"+rsmd.getColumnType(i)+"  type:"+rsmd.getColumnTypeName(i)+"   val:"+rs_mysql.getObject(i) );
	    	                
	    	                    fieldTypes.add(rsmd.getColumnType(i)+"");
	    	                    fieldTypeNames.add(rsmd.getColumnTypeName(i));
	    	                }
	    	                insertSB.append(fields).append(")").append(" ").append("values(").append(fieldVals).append(");");
	    	                rowDatas.add(rowData);
	    	            }
	    	            insertSB.append("\r\n\r\n");
	    	            
	    	            
	    	            //insert(oracleConn, tableName, rowDatas);
	    	            System.out.println( tableName+"=====================================END-"+java.sql.Types.CLOB );
	    	            //break;
	    			}
    			}
    		}
    		
    		System.out.println( fieldTypes );
    		System.out.println( fieldTypeNames );
    		System.out.println( insertSB );
    		//FileUtils.writeStringToFile(new File("E:\\temp\\dgszwhg_init_"+new java.text.SimpleDateFormat("yyyyMMddhh").format(new Date())+".sql"), insertSB.toString(), Charset.forName("UTF-8"));
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
        	if(rs_mysql != null){
        		try {
        			rs_mysql.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        	}
        	if(rs_oralce != null){
        		try {
        			rs_oralce.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        	}
        	if(oracleConn != null){
        		try {
        			oracleConn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        	}
        	if(mysqlConn != null){
        		try {
        			mysqlConn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        	}
        }
    }
    
    public static void insert(Connection oracleConn, String tableName, List<Map<String, Object>> rowDatas){
    	PreparedStatement stmt_oracle = null;
    	ResultSet rs = null;
    	try {
			
    		stmt_oracle = oracleConn.prepareStatement("select * from "+tableName);
    		rs = stmt_oracle.executeQuery();
    		ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            
            //System.out.println( "=========columnCount:"+columnCount+"========" );
            String insertSql = "";
			String fields = "";
        	String fieldVals = "";
        	for (int i = 1; i <= columnCount; i++) {
                fields = fields + ("".equals(fields)?"":",") + rsmd.getColumnName(i);
                //if(java.sql.Types.CLOB == rsmd.getColumnType(i)){
                	//fieldVals = fieldVals + ("".equals(fieldVals)?"":",") + "EMPTY_CLOB()";
                //}else{
                	fieldVals = fieldVals + ("".equals(fieldVals)?"":",") + "?";
                //}
            }
        	insertSql = "insert into "+tableName+"("+fields+") values("+fieldVals+")";
    		
    		PreparedStatement pst = oracleConn.prepareStatement(insertSql);
    		if(rowDatas != null){
    			for(Map<String, Object> rowData : rowDatas){
	            	for (int i = 1; i <= columnCount; i++) {
                    	String columnName = rsmd.getColumnName(i).toUpperCase();
                    	//pst.setObject(i, rowData.get(columnName));
                    	//System.out.println( i+"  "+columnName+"  "+rsmd.getColumnTypeName(i)+"   "+rowData.get(columnName)  );
                    	setParam(rsmd, pst, i, rowData.get(columnName));
	                }
    				pst.addBatch();
    			}
    		}
    		
    		pst.executeBatch();
    		
    		pst.close();
    		stmt_oracle.close();
    		rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public static void setParam(ResultSetMetaData rsmd, PreparedStatement pst, int column, Object columnVal){
    	try {
			String columnType = rsmd.getColumnTypeName(column);
			if(columnVal == null){
				pst.setObject(column, null);
			}else if("DATE".equals(columnType)){
				pst.setDate(column, (java.sql.Date)columnVal);
			}else if("TIMESTAMP".equals(columnType)){
				pst.setTimestamp(column, (java.sql.Timestamp)columnVal);
			}else if("CLOB".equals(columnType)){
				pst.setString(column, (String)columnVal);
			}else if("VARCHAR2".equals(columnType) || "CHAR".equals(columnType)){
				pst.setString(column, (String)columnVal);
			}else if("INTEGER".equals(columnType)){
				pst.setInt(column, (Integer)columnVal);
			}else if("NUMBER".equals(columnType)){
				pst.setInt(column, (Integer)columnVal);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
//    public static void insertClob(){
//    	
//    	try {
//			Connection oracleConn = getOracleConn();
//			PreparedStatement pst = oracleConn.prepareStatement("insert into wh_activitytpl (actvtplid, actvarttyp, actvdetail, ACTVADDRESS, ACTVISREALNAME, ACTVISFULLDATA, ACTVISATTACH, ACTVCANPERSON, ACTVCANTEAM, ACTVSTATE, ACTVOPTTIME) values (?, ?, ?, 1, 1, 1, 1, 1, 1, 1, sysdate)");
//			pst.setObject(1, "123");
//			pst.setObject(2, "我是clob");
//			pst.setObject(3, "我是clob");
//			pst.execute();
//			
//			pst.close();
//			oracleConn.close();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    }

    
    public static void main(String[] args) {
    	
    	//getOracleConn();
        
        //query();
    	
    	//生成ddl语句
        List<String> sqls = genOracleDDLFromMysql();
        
        //生成初始insert
        genInitScriptToOracle();
        
        //创建表结构
        //doDDLtoOracle(sqls);
        
        //数据移值mysql-oracle
        //doInsertToOracle();
    }
}
