package com.senhome.shell.common.lang.math;

import com.senhome.shell.common.lang.LangUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NumberUtil<T> {

	public static long computShardNumber(long value, long shardValue) {
		long shardNumber = value / shardValue;
		return shardNumber;
	}

	public static long computeShardNumber(long value) {
		long shardNumber = value % 100;
		return shardNumber;
	}

	public static long getLongQuietly(HttpServletRequest request, String key) {
		try {
			return LangUtil.parseLong(request.getParameter(key));
		} catch (Exception e) {
			return 0L;
		}
	}

	public static long getLongQuietly(HttpServletRequest request, String key, long defaultValue) {
		try {
			return LangUtil.parseLong(request.getParameter(key));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static int getIntQuietly(HttpServletRequest request, String key) {
		try {
			Integer value = LangUtil.parseInt(request.getParameter(key));
			return value == null ? 0 : value.intValue();
		} catch (Exception e) {
			return 0;
		}
	}

	public static int getIntQuietly(HttpServletRequest request, String key, int defaultValue) {
		try {
			Integer value = LangUtil.parseInt(request.getParameter(key));
			return value == null ? defaultValue : value.intValue();
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static int getIntQuietly(Object value) {
		try {
			return LangUtil.parseInt(value);
		} catch (Exception e) {
			return 0;
		}
	}

	public static double getDoubleQuietly(HttpServletRequest request, String key) {
		try {
			return LangUtil.parseDouble(request.getParameter(key));
		} catch (Exception e) {
			return 0;
		}
	}

	public static long getLongQuietly(Map<String, ? extends Object> map, String key) {
		try {
			return LangUtil.parseLong(map.get(key));
		} catch (Exception e) {
			return 0L;
		}
	}

	public static int getIntQuietly(Map<String, ? extends Object> map, String key) {
		try {
			return LangUtil.parseInt(map.get(key));
		} catch (Exception e) {
			return 0;
		}
	}

	public static long getLongQuitely(Object value) {
		try {
			return LangUtil.parseLong(value);
		} catch (Exception e) {
			return 0;
		}
	}

	public static double getDoubleQuitely(Object value) {
		try {
			return LangUtil.parseDouble(value);
		} catch (Exception e) {
			return 0;
		}
	}

	public static boolean doubleEquals(double a, double b) {
		double eps = 1e-6;
		return doubleEquals(a, b, eps);
	}

	public static boolean doubleEquals(double a, double b, double eps) {
		if (eps < 1.0) {
			if (Math.abs(a - b) < eps) {
				return true;
			}
		}
		return false;
	}

	public static double parsePercentNum(String percent) {
		if (StringUtils.isEmpty(percent)) {
			return 0;
		}
		try {
			if (percent.contains("%")) {
				String value = percent.substring(0, percent.length() - 1);
				Double num = LangUtil.parseDouble(value);
				if (num != null) {
					return num / 100;
				}
			}
		} catch (Exception e) {
		}
		return 0;
	}

	/**
	 * 获取n个不同的随机数
	 *
	 * @param n    随机数的个数
	 * @param size 随机数的上限，随机范围为[0,size)
	 * @return
	 */

	public static int[] getRandomArray(int n, int size) {
		if (size < 1) {
			return null;
		}
		if (n >= size) {
			n = size;
			int[] result = new int[n];
			for (int i = 0; i < size; i++)
            {
                result[i] = i;
            }
			return result;
		}
		int count = 0;
		int[] result = new int[n];
		while (count < n) {
			int index = (int) (Math.random() * size + 1);
			boolean flag = true;
			for (int i = 0; i < n; i++) {
				if (index - 1 == result[i]) {
					flag = false;
					break;
				}
			}
			if (flag) {
				result[count] = index - 1;
				count++;
			}
		}
		return result;
	}

	/**
	 * 删除List中的重复元素，需要List中元素重写equals方法
	 *
	 * @param list
	 * @return
	 */

	public static List delDuplicateElementsInList(List list) {
		for (int i = 0; i < list.size(); i++) {
			for (int j = i + 1; j < list.size(); ) {
				if (list.get(i).equals(list.get(j))) {
					list.remove(j);
				} else {
					j++;
				}
			}
		}
		return list;
	}

	/**
	 * 保留两位小数
	 *
	 * @param d
	 * @return
	 */

	public static double getFormatDouble(double d) {
		DecimalFormat df = new DecimalFormat("0.00");
		return Double.valueOf(df.format(d));
	}

	/**
	 * 对一个int类型的数组进行升序快速排序
	 *
	 * @param a
	 * @return
	 */
	public static int[] quickSortNumberArray(int[] a) {
		quickSort(a, 0, a.length - 1);
		return a;
	}

	/**
	 * 对一个int类型的List进行升序快速排序
	 *
	 * @param list
	 * @return
	 */
	public static List<Integer> quickSortNumberList(List<Integer> list) {
		int[] a = new int[list.size()];
		List<Integer> resultList = new ArrayList<Integer>();
		for (int i = 0; i < list.size(); i++) {
			a[i] = list.get(i);
		}
		quickSort(a, 0, a.length - 1);
		for (int anA : a) {
			resultList.add(anA);
		}
		return resultList;
	}

	private static void quickSort(int[] a, int i, int j) {
		if (i > j) {
			return;
		}
		int s = sortArrays(a, i, j);
		if (i < s - 1) {
			quickSort(a, i, s - 1);
		}
		if (s + 1 < j) {
			quickSort(a, s + 1, j);
		}
	}

	private static int sortArrays(int[] a, int i, int j) {
		int x = a[i];
		while (i != j) {
			while (true) {
				if (a[j] <= x) {
					a[i] = a[j];
					break;
				}
				j--;
				if (i == j) {
					a[i] = x;
					return i;
				}
			}
			while (true) {
				if (a[++i] > x) {
					a[j] = a[i];
					break;
				}
				if (i == j) {
					a[i] = x;
					return i;
				}
			}
		}
		return i;
	}
}
