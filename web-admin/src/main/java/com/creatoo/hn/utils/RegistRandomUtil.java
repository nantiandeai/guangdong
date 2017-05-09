package com.creatoo.hn.utils;

import java.util.Random;

/**
 * 随机验证码生成辅助方法
 * @author dzl
 *
 */
public class RegistRandomUtil {
	/**
	 * 生成随机验证码
	 * 
	 * @return
	 */
	public static String random() {
		java.util.Random r = new java.util.Random();
		int x = r.nextInt(899999);
		x = x + 100000;
		String s = x + "";
		return s;
	}
   
}
