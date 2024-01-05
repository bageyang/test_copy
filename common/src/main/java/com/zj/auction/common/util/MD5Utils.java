package com.zj.auction.common.util;
import com.zj.auction.common.constant.SystemConstant;
import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;


/**
 * @Title MD5Utils
 * @Package com.duoqio.boot.util
 * @Description ${Description}
 * @Author Mao Qi
 * @Date 2021/3/5 15:51
 * @Copyright 重庆多企源科技有限公司
 * @Website {http://www.duoqio.com/index.asp?source=code}
 */
public class MD5Utils {

    public static void main(String[] args) {
//        String encryption = encryption("TmpVME16SXhNREE9DQowNjUxYTdhMjc2ZDQ0MGYxOWY0YzI0N2FmNjQ3MTJhMw==", "0651a7a276d440f19f4c247af64712a3");
//        System.out.println("encryption = " + encryption);
            String a =    encryption("y4209232","a(");
		System.out.println(a);
    }


	/**
	 * 加密
	 * @param param
	 * @return  0:密码   1：盐
	 */
	public static String[] encryption(String param) {
		String salt = PasswordUtils.salt();
		String pwdMd5 = PasswordUtils.encryptPassword(salt, param);
		
		
		
//		PubFun.isNull(param,SystemConstant.DATA_ILLEGALITY_CODE);
//		String uuid =  UUID.randomUUID().toString().replaceAll("\\-", "");
//		//加密  
//        byte[] b1 = Base64.encodeBase64(param.getBytes(), true);  
//        String base1 = new String(b1);  
//        System.out.println(base1);
//		byte[] b2 = Base64.encodeBase64((base1+uuid).getBytes(), true);  
//        String base2 = new String(b2);
//        String[] result = {base2.substring(0,base2.length()-2),uuid};
		String[] result = {pwdMd5,salt};
        return result;
	}
	/**
	 * 验证加密
	 * @param param
	 * @return
	 */
	public static String isEncryption(String param,String salt) {
		return PasswordUtils.encryptPassword(salt, param);
	}
	/**
	 * 解密
	 * @param param 加密加盐后的值
	 * @param salt 盐
	 * @return 解密后的密码
	 */
	public static String encryption(String param,String salt) {
		PubFun.isNull(param, SystemConstant.DATA_ILLEGALITY_CODE);
		PubFun.isNull(param, SystemConstant.DATA_ILLEGALITY_CODE);
		byte[] b1 = Base64.decodeBase64(param);
		String jie = new String(b1).toString();
		byte[] b2 = Base64.decodeBase64(jie.substring(0, jie.indexOf(jie.substring(jie.length()-32))));
		String password = new String(b2).toString();
		return password;
	 }


	/**
	 * @Description 第三方供应链加密方法
	 * @Title stringToMD5
	 * @Author Mao Qi
	 * @Date 2021/3/5 15:49
	 * @param plainText
	 * @return	java.lang.String
	 */
	public static String stringToMD5(String plainText) {
		char hexDigits[] = {
				'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
		};
		try {
			byte[] btInput = plainText.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

}
