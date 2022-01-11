package com.joorgeit.movies.util;

public class Util {
	public static boolean isInvalidText(String text) {
		return text == null || text.length() == 0 || "".equals(text) || text.isEmpty() || "".equals(text.trim());
	}

	public static boolean isNumber(String strNum) {
		if (strNum == null) {
			return false;
		}

		try {
			Long.parseLong(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
