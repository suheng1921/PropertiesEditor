/*****************************************************
 * It is a class holding various setup.
 * This class is saved and it is made to read a setup at the time of next starting.
 *
 *   @author  Sou Miyazaki
 *
 ****************************************************/
package jp.gr.java_conf.ussiy.app.propedit.bean;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;

/**
 * It is a class holding various setup. This class is saved and it is made to read a setup at the time of next starting.
 * 
 * @author Sou Miyazaki
 *  
 */
public class AppSetting implements Serializable {

	/**
	 */
	public static final String filepath = "ini"; //$NON-NLS-1$

	/**
	 */
	private static AppSetting setting;

	/**
	 * 
	 * @since 1.0.0
	 */
	private AppSetting() {

	}

	/**
	 * 
	 * @since 1.0.0
	 */
	public static AppSetting getInstance() {

		if (setting == null) {
			setting = new AppSetting();
		}
		return setting;
	}

	/**
	 * 
	 * @since 1.0.0
	 */
	public void saveSetting() throws FileNotFoundException, IOException {

		File file = new File(filepath);
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
		out.writeObject(this);
		out.flush();
		out.close();
	}

	/**
	 * 
	 * @since 1.0.0
	 */
	public void loadSetting() throws FileNotFoundException, ClassNotFoundException, IOException {

		File file = new File(filepath);
		if (!file.exists() || !file.isFile()) {
			return;
		}
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
		AppSetting setting = (AppSetting) in.readObject();
		in.close();
		this.setOpenFileHistory(setting.getOpenFileHistory());
		this.setShowLineNumberFlag(setting.isShowLineNumberFlag());
		this.setShowToolBarFlag(setting.isShowToolBarFlag());
		this.setWordWrapFlag(setting.isWordWrapFlag());
		this.setFramesize(setting.getFramesize());
		this.setFont(setting.getFont());
		this.setForegroundColor(setting.getForegroundColor());
		this.setBackgroundColor(setting.getBackgroundColor());
		this.setLookAndFeelClass(setting.getLookAndFeelClass());
		Vector files = this.getOpenFileHistory();
		if (files.size() >= 10) {
			int rem = files.size() - 10;
			for (int i = 0; i < rem; i++) {
				files.remove(0);
			}
		}
	}

	/**
	 */
	private boolean showLineNumberFlag;

	/**
	 */
	private boolean showToolBarFlag;

	/**
	 */
	private java.util.Vector openFileHistory = new java.util.Vector();

	/**
	 */
	private boolean wordWrapFlag;

	private java.awt.Dimension framesize;

	private Font font;

	private Color backgroundColor;

	private Color foregroundColor;

	private String lookAndFeelClass;

	/**
	 * 
	 * @param ois
	 * @since 1.0.0
	 */
	private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {

		ois.defaultReadObject();
	}

	/**
	 * 
	 * @param oos
	 * @since 1.0.0
	 */
	private void writeObject(ObjectOutputStream oos) throws IOException {

		oos.defaultWriteObject();
	}

	/**
	 * 
	 * @since 1.0.0
	 */
	public boolean isShowLineNumberFlag() {

		return showLineNumberFlag;
	}

	/**
	 * 
	 * @param showLineNumberFlag
	 * @since 1.0.0
	 */
	public void setShowLineNumberFlag(boolean showLineNumberFlag) {

		this.showLineNumberFlag = showLineNumberFlag;
	}

	/**
	 * 
	 * @since 1.0.0
	 */
	public boolean isShowToolBarFlag() {

		return showToolBarFlag;
	}

	/**
	 * 
	 * @param showToolBarFlag
	 * @since 1.0.0
	 */
	public void setShowToolBarFlag(boolean showToolBarFlag) {

		this.showToolBarFlag = showToolBarFlag;
	}

	/**
	 * 
	 * @since 1.0.0
	 */
	public java.util.Vector getOpenFileHistory() {

		return openFileHistory;
	}

	/**
	 * 
	 * @param openFileHistory
	 * @since 1.0.0
	 */
	private void setOpenFileHistory(java.util.Vector openFileHistory) {

		this.openFileHistory = openFileHistory;
		if (openFileHistory.size() == 11) {
			openFileHistory.remove(10);
		}
	}

	/**
	 * 
	 * @param openFile
	 * @since 1.0.0
	 */
	public void setOpenFileHistory(File openFile) {

		this.openFileHistory.add(openFile);
	}

	/**
	 * 
	 * @since 1.0.0
	 */
	public boolean isWordWrapFlag() {

		return wordWrapFlag;
	}

	/**
	 * 
	 * @param wordWrapFlag
	 * @since 1.0.0
	 */
	public void setWordWrapFlag(boolean wordWrapFlag) {

		this.wordWrapFlag = wordWrapFlag;
	}

	public java.awt.Dimension getFramesize() {

		return framesize;
	}

	public void setFramesize(java.awt.Dimension framesize) {

		this.framesize = framesize;
	}

	public void setFont(Font font) {

		this.font = font;
	}

	public Font getFont() {

		return font;
	}

	public void setForegroundColor(Color foreColor) {

		this.foregroundColor = foreColor;
	}

	public Color getForegroundColor() {

		return this.foregroundColor;
	}

	public void setBackgroundColor(Color backColor) {

		this.backgroundColor = backColor;
	}

	public Color getBackgroundColor() {

		return this.backgroundColor;
	}

	public String getLookAndFeelClass() {

		return lookAndFeelClass;
	}

	public void setLookAndFeelClass(String lookAndFeelClass) {

		this.lookAndFeelClass = lookAndFeelClass;
	}
}