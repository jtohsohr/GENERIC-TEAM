package com.generic.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	/**
	 * Converts milliseconds to a date (STATIC)
	 * @param milliDate date in milliseconds
	 * @return date in simple format
	 */
	public static String milliToDate(long milliDate) {
		DateFormat simple = new SimpleDateFormat("dd MMMMM yyyy HH:mm:ss");
		Date result = new Date(milliDate);
		return simple.format(result);
	}
}
