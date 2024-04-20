/*****************************************************
 *
 *   @author  Sou Miyazaki
 *
 ****************************************************/
package jp.gr.java_conf.ussiy.app.propedit.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import jp.gr.java_conf.ussiy.app.propedit.bean.Encode;

/**
 * 
 * @author Sou Miyazaki
 *  
 */
public class EncodeManager {

	public static Vector readEncList = new Vector();

	public static final String AUTO = "JISAutoDetect"; //$NON-NLS-1$

	public static final String ASCII = "ASCII"; //$NON-NLS-1$

	public static final String SJIS = "Shift_JIS"; //$NON-NLS-1$

	public static final String MS932 = "MS932"; //$NON-NLS-1$

	public static final String EUC_JP = "EUC_JP"; //$NON-NLS-1$

	public static final String JIS = "ISO2022JP"; //$NON-NLS-1$

	public static final String UTF8 = "UTF-8"; //$NON-NLS-1$

	public static final String UTF16 = "UTF-16"; //$NON-NLS-1$

	/**
	 */
	private static List codeList = new ArrayList();

	static {
		readEncList.add(new Encode(0, "Auto", AUTO)); //$NON-NLS-1$
		readEncList.add(new Encode(1, "ASCII", ASCII)); //$NON-NLS-1$
		readEncList.add(new Encode(2, "Shift-JIS", SJIS)); //$NON-NLS-1$
		readEncList.add(new Encode(3, "Shift-JIS(CP932)", MS932)); //$NON-NLS-1$
		readEncList.add(new Encode(4, "EUC-JP", EUC_JP)); //$NON-NLS-1$
		readEncList.add(new Encode(5, "ISO-2022-JP(JIS)", JIS)); //$NON-NLS-1$
		readEncList.add(new Encode(6, "UTF-8", UTF8)); //$NON-NLS-1$
		readEncList.add(new Encode(7, "UTF-16", UTF16)); //$NON-NLS-1$
		codeList.add(ASCII);
		codeList.add(SJIS);
		codeList.add(MS932);
		codeList.add(EUC_JP);
		codeList.add(JIS);
		codeList.add(UTF8);
		codeList.add(UTF16);
	}

	public static Vector getReadEncodeList() {

		return readEncList;
	}
}