package com.rising.app.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huisa.common.database.BaseDao;
import com.huisa.common.exception.ServiceException;

import freemarker.template.utility.StringUtil;

public class Test {
	private static Logger logger = LoggerFactory.getLogger(Test.class);

	public static void main(String[] args) throws Exception {
		// 生成javabean
/*		try {
			new BaseDao()
					.generateJavaBean(
							"portal_user_role",
							"E:/LGH/mintour-portal/src/main/java/com/mintour/portal/entity/po",
							"com.mintour.portal.entity.po");
		} catch (ServiceException e) {
		}*/

		
/*		String json="小强\"的强项。\"";
		logger.info(json);
		logger.info(json.replaceAll("\"", "”"));*/
		String s="a_bbb_cccc";
		System.out.println(initcolname(s));
		
	}
	private static String initcolname(String str) {
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
	private static String initcap(String str) {
		
		char[] ch = str.toCharArray();
		if(ch[0] >= 'a' && ch[0] <= 'z'){
			ch[0] = (char)(ch[0] - 32);
		}
		
		return new String(ch);
	}

}
