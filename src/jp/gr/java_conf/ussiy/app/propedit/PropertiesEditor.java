/*****************************************************
 * This class is main class of application.
 *
 *   @author  Sou Miyazaki
 *
 ****************************************************/
package jp.gr.java_conf.ussiy.app.propedit;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ResourceBundle;

import javax.swing.UIManager;

/**
 * This class is main class of application.
 * 
 * @author Sou Miyazaki
 *  
 */
public class PropertiesEditor {

	/**
	 */
	private static ResourceBundle res = ResourceBundle.getBundle("jp.gr.java_conf.ussiy.app.propedit.lang"); //$NON-NLS-1$

	public static String VERSION = res.getString("version"); //$NON-NLS-1$

	/**
	 */
	public static String COPYRIGHT = res.getString("2004_Sou Miyazaki_All"); //$NON-NLS-1$

	/**
	 * 
	 * @since 1.0.0
	 */
	public PropertiesEditor(String[] args) {

		PropertiesEditorFrame frame = null;
		if (args.length == 0) {
			frame = new PropertiesEditorFrame();
		} else {
			frame = new PropertiesEditorFrame(args[0]);
		}

		frame.validate();
		//The window is arranged in the center.
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame.getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		frame.setVisible(true);
	}

	/**
	 * 
	 * @param args
	 * @since 1.0.0
	 */
	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		new PropertiesEditor(args);
	}

	public static void showCopyRight() {

		System.out.println("/*****************************************"); //$NON-NLS-1$
		System.out.println(res.getString("PropertiesEditor")); //$NON-NLS-1$
		System.out.println(res.getString("Version_") + PropertiesEditor.VERSION); //$NON-NLS-1$
		System.out.println(PropertiesEditor.COPYRIGHT);
		System.out.println("*****************************************/"); //$NON-NLS-1$
	}

	public static String getI18nProperty(String key) {
		return res.getString(key);
	}
}