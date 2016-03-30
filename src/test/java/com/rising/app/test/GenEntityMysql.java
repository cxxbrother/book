package com.rising.app.test;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;

import com.huisa.common.reflection.annotations.huisadb_alias;
import com.huisa.common.reflection.annotations.huisadb_ignore;
import com.mysql.jdbc.Statement;

public class GenEntityMysql {
	
	private String packageOutPath = "main.java.com.book.portal.entity.po.book";//指定实体生成所在包的路径
	private String authorName = "cxx";//作者名字
	private String tablename = "giveback";//表名
	private String[] colnames; // 列名数组
	private String[] colTypes; //列名类型数组
	private String[] colComments; //列名备注数组
	private String[] CHARACTER_MAXIMUM_LENGTHs; //列名长度数组
	private String[] COLUMN_DEFAULTs; //列名默认数组
	private String[] IS_NULLABLEs; //列名是否为空数组
	private String[] autos; //列名自增数组
	
	private int[] colSizes; //列名大小数组
	private boolean f_util = false; // 是否需要导入包java.util.*
	private boolean f_sql = false; // 是否需要导入包java.sql.*
    
    //数据库连接
	private static final String URL ="jdbc:mysql://localhost:3306/book";
	private static final String NAME = "root";
	private static final String PASS = "a2825767";
	private static final String DRIVER ="com.mysql.jdbc.Driver";

	/*
	 * 构造函数
	 */
	public GenEntityMysql(){
    	//创建连接
    	Connection con;
		//查要生成实体类的表
    	String sql = "select * from " + tablename;
    	String sql2="SELECT	COLUMN_COMMENT,CHARACTER_MAXIMUM_LENGTH,"
    			+ "COLUMN_DEFAULT,IS_NULLABLE,CASE WHEN EXTRA = 'auto_increment' THEN 1 ELSE 0 END AS `auto`"
    			+ " FROM information_schema.COLUMNS WHERE TABLE_NAME='"+tablename+"'";
    	PreparedStatement pStemt = null;
		Statement st =null;
		ResultSet rs =null;	
    	try {
    		try {
				Class.forName(DRIVER);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		con = DriverManager.getConnection(URL,NAME,PASS);
			pStemt = con.prepareStatement(sql);
			
			ResultSetMetaData rsmd = pStemt.getMetaData();
			st =(Statement) con.createStatement();
			rs=st.executeQuery(sql2);
			
			int size = rsmd.getColumnCount();	//统计列
			colnames = new String[size];
			colTypes = new String[size];
			colComments = new String[size];
			CHARACTER_MAXIMUM_LENGTHs = new String[size];
			COLUMN_DEFAULTs = new String[size];
			IS_NULLABLEs = new String[size];
			autos = new String[size];
			colSizes = new int[size];
			for (int i = 0; i < size; i++) {
				colnames[i] = rsmd.getColumnName(i + 1);
				colTypes[i] = rsmd.getColumnTypeName(i + 1);

				if(colTypes[i].equalsIgnoreCase("datetime")){
					f_util = true;
				}
				if(colTypes[i].equalsIgnoreCase("image") || colTypes[i].equalsIgnoreCase("text")){
					f_sql = true;
				}
				colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
			}

			for(int i=0;rs.next()&&i<size;i++) {

				colComments[i]=rs.getString("COLUMN_COMMENT");
				CHARACTER_MAXIMUM_LENGTHs[i]=rs.getString("CHARACTER_MAXIMUM_LENGTH");
				COLUMN_DEFAULTs[i]=rs.getString("COLUMN_DEFAULT");
				IS_NULLABLEs[i]=rs.getString("IS_NULLABLE");
				autos[i]=rs.getString("auto");
			 }	
			String content = parse(colnames,colTypes,colSizes);
			try {
				File directory = new File("");
				//System.out.println("绝对路径："+directory.getAbsolutePath());
				//System.out.println("相对路径："+directory.getCanonicalPath());
				String path=this.getClass().getResource("").getPath();
				
				System.out.println(path);
				System.out.println("src/?/"+path.substring(path.lastIndexOf("/com/", path.length())) );
//				String outputPath = directory.getAbsolutePath()+ "/src/"+path.substring(path.lastIndexOf("/com/", path.length()), path.length()) + initcap(tablename) + ".java";
				String outputPath = directory.getAbsolutePath()+ "/src/"+this.packageOutPath.replace(".", "/")+"/"+initcolname1(tablename) + ".java";
				FileWriter fw = new FileWriter(outputPath);
				PrintWriter pw = new PrintWriter(fw);
				pw.println(content);
				pw.flush();
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
				rs.close();
				st.close();
				con.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
//			try {
//				con.close();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
    }

	/**
	 * 功能：生成实体类主体代码
	 * @param colnames
	 * @param colTypes
	 * @param colSizes
	 * @return
	 */
	private String parse(String[] colnames, String[] colTypes, int[] colSizes) {
		StringBuffer sb = new StringBuffer();
		sb.append("package " + this.packageOutPath.replace("main.java.", "") + ";\r\n");
		//判断是否导入工具包
		if(f_util){
			sb.append("import java.util.Date;\n");
		}
		if(f_sql){
			sb.append("import java.sql.*;\n");
		}
		
		sb.append("import com.huisa.common.reflection.annotations.*;\r\n");
		sb.append("\r\n");
		//注释部分
		sb.append("   /**\r\n");
		sb.append("    * "+tablename+" 实体类\r\n");
		sb.append("    * "+new Date()+" "+this.authorName+"\r\n");
		sb.append("    */ \r\n");
		//实体部分
		sb.append("\r\n\r\n@huisadb_alias(\"" + tablename + "\")");
		sb.append("\rpublic class " + initcolname1(tablename) + "{\r\n");
		processAllAttrs(sb);//属性
		processAllMethod(sb);//get set方法
		sb.append("}\r\n");
		
    	//System.out.println(sb.toString());
		return sb.toString();
	}
	
	/**
	 * 功能：生成所有属性
	 * @param sb
	 */
	private void processAllAttrs(StringBuffer sb) {
		
		for (int i = 0; i < colnames.length; i++) {
			if(colnames[i].equals("update_time")){
				sb.append("\t@huisadb_ignore \r\n");
			}
			if(i==0){
				sb.append("\t@huisadb_ignore \r\n");
			}
			else{
				sb.append("\t@huisadb_alias(\""+colnames[i] +"\")\r\n");
			}
			//sb.append("\t " + initcolname2(colnames[i])  + "; /*remark :"+colnames[i]+"*/ \r\n");
			sb.append("\tprivate " + sqlType2JavaType(colTypes[i]) + " " + initcolname2(colnames[i])  
					+ "; /*"
					+"注释:"+colComments[i]
					+";长度："+CHARACTER_MAXIMUM_LENGTHs[i]
					+";默认值："+COLUMN_DEFAULTs[i]
					+";是否为空："+IS_NULLABLEs[i]
					+";是否自增："+autos[i]
					+ "*/ \r\n");
		}
	}

	/**
	 * 功能：生成所有方法
	 * @param sb
	 */
	private void processAllMethod(StringBuffer sb) {
		
		for (int i = 0; i < colnames.length; i++) {
			sb.append("\tpublic void set" + initcolname1(colnames[i]) + "(" + sqlType2JavaType(colTypes[i]) + " " + 
					initcolname2(colnames[i]) + "){\r\n");
			sb.append("\tthis." + initcolname2(colnames[i]) + "=" + initcolname2(colnames[i]) + ";\r\n");
			sb.append("\t}\r\n");
			sb.append("\tpublic " + sqlType2JavaType(colTypes[i]) + " get" + initcolname1(colnames[i]) + "(){\r\n");
			sb.append("\t\treturn " + initcolname2(colnames[i]) + ";\r\n");
			sb.append("\t}\r\n");
		}
		
	}
	
	private String initcolname1(String str) {
		String[] sp=str.split("_");
		String col="";
		for(int i=0;i<sp.length;i++){
			col+=initcap(sp[i]);
		}
		return col;
	}
	private String initcolname2(String str) {
		String[] sp=str.split("_");
		String col=sp[0];
		for(int i=1;i<sp.length;i++){
			col+=initcap(sp[i]);
		}
		return col;
	}
	/**
	 * 功能：将输入字符串的首字母改成大写
	 * @param str
	 * @return
	 */
	private String initcap(String str) {
		
		char[] ch = str.toCharArray();
		if(ch[0] >= 'a' && ch[0] <= 'z'){
			ch[0] = (char)(ch[0] - 32);
		}
		
		return new String(ch);
	}

	/**
	 * 功能：获得列的数据类型
	 * @param sqlType
	 * @return
	 */
	private String sqlType2JavaType(String sqlType) {
		
		if(sqlType.equalsIgnoreCase("bit")){
			return "boolean";
		}else if(sqlType.equalsIgnoreCase("tinyint")){
			return "java.lang.Short";
		}else if(sqlType.equalsIgnoreCase("smallint")){
			return "java.lang.Short";
		}else if(sqlType.equalsIgnoreCase("int")){
			return "java.lang.Integer";
		}else if(sqlType.equalsIgnoreCase("bigint")){
			return "java.lang.Long";
		}else if(sqlType.equalsIgnoreCase("float")){
			return "java.lang.Float";
		}else if(sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric") 
				|| sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("money") 
				|| sqlType.equalsIgnoreCase("smallmoney")){
			return "java.lang.Double";
		}else if(sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char") 
				|| sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar") 
				|| sqlType.equalsIgnoreCase("text")){
			return "java.lang.String";
		}else if(sqlType.equalsIgnoreCase("datetime")||sqlType.equalsIgnoreCase("timestamp")){
			return "java.util.Date";
		}else if(sqlType.equalsIgnoreCase("image")){
			return "Blod";
		}
		
		return null;
	}
	
	/**
	 * 出口
	 * TODO
	 * @param args
	 */
	public static void main(String[] args) {
		
		new GenEntityMysql();
		
	}

}