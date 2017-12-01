package cn.xlink.cassandra.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatTools {

    private DateFormatTools(){
        throw new IllegalStateException("Utility class");
    }

	public static final String funcGetDateWithFormat(Date date, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	public static final Date funcGetDateWithFormat(String date, String format) {
		if(null == date) {
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * yyyy-MM-dd'T'HH:mm:ss.SS'Z'
	 * @param date
	 * @return
	 */
	public static final String funcGetDate(Date date) {
		return funcGetDateWithFormat(date, "yyyy-MM-dd'T'HH:mm:ss.SS'Z'");
	}
	
	/**
	 * yyyy-MM-dd'T'HH:mm:ss.SS'Z'
	 * 
	 * @param date
	 * @return
	 */
	public static final Date funcGetDate(String date) {
		return funcGetDateWithFormat(date,"yyyy-MM-dd'T'HH:mm:ss.SS'Z'");
	}
	
	/**
	 * yyyy-MM-dd
	 * @param date
	 * @return
	 */
	public static final String funcGetDay(Date date) {
		return funcGetDateWithFormat(date, "yyyy-MM-dd");
	}
	
	/**
	 * yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static final Date funcGetDay(String date) {
		return funcGetDateWithFormat(date,"yyyy-MM-dd");
	}
}
