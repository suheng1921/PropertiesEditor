package jp.gr.java_conf.ussiy.util;

public class StringUtil {

	public static String removeCarriageReturn(String str) {

		StringBuffer buf = new StringBuffer();
		char[] c = str.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] != 0x0D) {
				buf.append(c[i]);
			}
		}
		return buf.toString();
	}
	
	public static String escapeHtml(String str) {
		if (str == null) return null;
		str = str.replaceAll("&", "&amp;");
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		str = str.replaceAll("\"", "&quot;");
		return str;
	}
}