package cn.xlink.cassandra.core.utils;

/**
 * 字符串工具类
 * 
 * @author shenweiran
 */
public final class StringTools {

	public static final String _blank = "";
	public static final String s_blank_regrex = "\\s*";
	public static final String en_blank_regrex = " +";
	public static final String cn_blank_regrex = "　+";

    private StringTools(){
        throw new IllegalStateException("Utility class");
    }

	public static final String getString(Object obj) {
		if (obj == null) {
			return "";
		}
		return obj.toString();
	}

	public static final long getLong(Object str) {
		if (str == null) {
			return 0;
		}
		return getLong(str.toString());
	}

	public static final int getInt(Object str) {
		if (str == null) {
			return 0;
		}
		return getInt(str.toString());
	}

	public static final float getFloat(Object str) {
		if (str == null) {
			return 0;
		}
		return getFloat(str.toString());
	}

	public static final double getDouble(Object str) {
		if (str == null) {
			return 0;
		}
		return getDouble(str.toString());
	}

	public static final boolean getBoolean(Object str) {
		if (str == null) {
			return false;
		}
		return getBoolean(str.toString());
	}

	public static final short getShort(Object str) {
		return getShort(str.toString());
	}

	public static final long getLong(String str) {
		return (long) getDouble(str);
	}

	public static final int getInt(String str) {
		return (int) getDouble(str);
	}

	public static final float getFloat(String str) {
		return (float) getDouble(str);
	}

	public static final double getDouble(String str) {
		try {
			return Double.parseDouble(str);
		} catch (Exception e) {
			return 0;
		}
	}

	public static final boolean getBoolean(String str) {
		return Boolean.valueOf(str);
	}

	public static final short getShort(String str) {
		return Short.parseShort(str);
	}

	public static final boolean isEmpty(String str) {
		return (null == str) ? true : ((str.trim().length() == 0) ? true : false);
	}

	/**
	 * 判断两个字符串是否相等，兼容比较字符为null的情况。
	 *
	 * @author linsida
	 * @date 2016年8月4日 下午2:29:55
	 */
	public static final boolean equals(String str1, String str2) {
		return (str1 == null) ? (str2 == null) : ((str2 == null) ? false : str1.equals(str2));
	}

	/**
	 * 空白
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

	public static final String removeBlank(String expression) {
		if(null != expression){
			expression = expression.replaceAll(en_blank_regrex, _blank);
			expression = expression.replaceAll(cn_blank_regrex, _blank);
			expression = expression.replaceAll(s_blank_regrex, _blank);
		}
		return expression;
	}
}
