package org.hch.book.util.util;

public class CheckUtil {

	public static boolean stringIsNullOrEmpty(String str) {
		if (null == str || str.trim().isEmpty()) {
			return true;
		}
		return false;
	}
}
