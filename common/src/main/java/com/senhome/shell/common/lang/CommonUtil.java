package com.senhome.shell.common.lang;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.*;

public class CommonUtil {
	public static final Charset UTF_8		= Charset.forName("UTF-8");
	public static final String LOG_SPLITER	= "|";
    public static final String PLATFORM_IDENTITY_CODE = "1";

	/**
	 * 格式化统计日志
	 * 
	 * @param args
	 * @return
	 */
	public static String buildStatLog(Object... args) {
		if (isEmpty(args)) {
			return StringUtils.EMPTY;
		}
		StringBuilder builder = new StringBuilder();
		for (Object arg : args) {
			if (builder.length() > 0) {
				builder.append(LOG_SPLITER);
			}
			builder.append(arg);
		}
		return builder.toString();
	}

	/**
	 * 使用UTF8编码转换字符串
	 * 
	 * @param val
	 * @return
	 */
	public static byte[] toBytes(String val) {
		if (StringUtils.isEmpty(val)) {
			return null;
		}
		return val.getBytes(UTF_8);
	}

	/**
	 * 使用UTF-8转换字节序列
	 * 
	 * @param bytes
	 * @return
	 */
	public static String bytesToString(byte[] bytes) {
		if (bytes == null) {
			return null;
		}
		return new String(bytes, UTF_8);
	}

	/**
	 * 如果null，则返回空字符串，否则返回原始字符串
	 * 
	 * @param val
	 * @return
	 */
	public static String emptyIfNull(String val) {
		if (val == null) {
			return "";
		}
		return val;
	}

	/**
	 * 转换为String
	 * 
	 * @param value
	 * @return
	 */
	public static String toString(Object value) {
		return toString(value, null);
	}

	/**
	 * 转换为String
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static String toString(Object value, String defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		return String.valueOf(value);
	}

	/**
	 * 转换为List
	 * 
	 * @param elements
	 * @return
	 */
	@SafeVarargs
	public static <T> List<T> asArrayList(T... elements) {
		if (elements == null) {
			return null;
		}
		List<T> list = new ArrayList<T>(elements.length);
		for (T e : elements) {
			if (e != null) {
				list.add(e);
			}
		}
		return list;
	}

	/**
	 * 转换为Set
	 * 
	 * @param elements
	 * @return
	 */
	@SafeVarargs
	public static <T> Set<T> asHashSet(T... elements) {
		if (elements == null) {
			return null;
		}
		Set<T> set = new HashSet<T>(elements.length);
		for (T e : elements) {
			if (e != null) {
				set.add(e);
			}
		}
		return set;
	}

	/**
	 * 判断集合是否为空
	 * 
	 * @param col
	 * @return
	 */
	public static <T> boolean isEmpty(Collection<T> col) {
		return col == null || col.isEmpty();
	}

	/**
	 * 判断数组是否为空
	 * 
	 * @param arr
	 * @return
	 */
	public static <T> boolean isEmpty(T[] arr) {
		return arr == null || arr.length == 0;
	}

	/**
	 * 判断Map是否为空
	 * 
	 * @param map
	 * @return
	 */
	public static <K, V> boolean isEmpty(Map<K, V> map) {
		return map == null || map.isEmpty();
	}

	/**
	 * 判断两个对象是否相等
	 * 
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static boolean objectEquals(Object o1, Object o2) {
		if (o1 == null || o2 == null) {
			return false;
		}
		return o1 == o2 || o1.equals(o2);
	}

	/**
	 * 生成一个唯一的事务ID
	 *
	 * @return
	 */
	public synchronized static Long generateTransactionId()
	{
		try
		{
			Thread.sleep(1);
		}
		catch (InterruptedException e)
		{
			return null;
		}
		return Long.parseLong(System.currentTimeMillis() + PLATFORM_IDENTITY_CODE);
	}

	/**
	 * 生成用户的16位大写md5密钥
	 *
	 * @param accountId
	 * @return
	 */
	public static String generateAccountSign(int accountId)
		throws Exception
	{
		String signSuffix = "JiaGengDuoQian15088620153";
		String plainText = accountId + signSuffix;

		String sign = plainText;
		for (int i = 0; i < 3; i++)
		{
			sign = strToMD5(sign);
		}
		return sign;
	}

	/**
	 * 把字符串转换成md5
	 *
	 * @param str
	 * @return
	 */
	public static String strToMD5(String str)
		throws UnsupportedEncodingException
	{

		try
		{
			byte[] input;
			input = str.getBytes("UTF-8");
			return bytesToHex(bytesToMD5(input));
		}
		catch (UnsupportedEncodingException e)
		{
			throw e;
		}
	}

	/**
	 * 把字节数组转成16进位制数
	 *
	 * @param bytes
	 * @return
	 */
	public static String bytesToHex(byte[] bytes)
	{
		StringBuffer md5str = new StringBuffer();
		// 把数组每一字节换成16进制连成md5字符串
		int digital;
		for (int i = 4; i < bytes.length - 4; i++)
		{
			digital = bytes[i];
			if (digital < 0)
			{
				digital += 256;
			}
			if (digital < 16)
			{
				md5str.append("0");
			}
			md5str.append(Integer.toHexString(digital));
		}
		return md5str.toString().toUpperCase();
	}

	/**
	 * 把字节数组转换成md5
	 *
	 * @param input
	 * @return
	 */
	public static byte[] bytesToMD5(byte[] input)
	{
		// String md5str = null;
		byte[] buff = null;
		try
		{
			// 创建一个提供信息摘要算法的对象，初始化为md5算法对象
			MessageDigest md = MessageDigest.getInstance("MD5");
			// 计算后获得字节数组
			buff = md.digest(input);
			// 把数组每一字节换成16进制连成md5字符串
			// md5str = bytesToHex(buff);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return buff;
	}
}
