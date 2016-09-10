package cn.keepfight.rebatecalculator;

import java.text.DecimalFormat;

public class PreciseKit {

	static DecimalFormat df = new DecimalFormat("#.##");

	/**
	 * 转换为小数点后两位数的字符串
	 * 
	 * @return 字符串
	 */
	public static String str2(double t) {
		return df.format(t);
	}
	
	public static String str2(String t) {
		double k = 0;
		try {
			k = Double.parseDouble(t);
		} catch (Exception e) {
		}
		return df.format(k);
	}

	
	
	/**
	 * 转换为小数点后两位数的double数
	 * 
	 * @return double数
	 */
	public static double dou2(double t) {
		return Double.parseDouble(df.format(t));
	}
	public static double dou2(String t) {
		double k = 0;
		try {
			k = Double.parseDouble(t);
		} catch (Exception e) {
		}
		return Double.parseDouble(df.format(k));
	}
}
