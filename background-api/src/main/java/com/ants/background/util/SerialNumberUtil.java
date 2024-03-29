package com.ants.background.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 生成反馈编号的根据类
 * @author czd
 * @Date:created 2019/9/24
 * @Version: V1.0
 */
public class SerialNumberUtil {

	/**
	 * 生成商品订单号
	 * @return
	 */
//	public static String getShopIdByUUID() {
////		SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
////		String time=format.format(new Date());
//		int hashCode=UUID.randomUUID().toString().hashCode();
//		if(hashCode<0) {
//			hashCode=-hashCode;
//
//		}
//		return String.format("%011d", hashCode);//time+
//	}



	/**
	 * 生成反馈编号
	 * @return
	 */
	public static String getFeedbackUUID(){
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
		String time=format.format(new Date());
		int num  = (int) (100 + Math.random() * 1000);
		StringBuilder builder  = new StringBuilder();
		builder.append(time);
		builder.append(num);
		return builder.toString();
	}


}
