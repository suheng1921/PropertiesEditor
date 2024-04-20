/*****************************************************
 *
 *   @author  Sou Miyazaki
 *
 ****************************************************/
package jp.gr.java_conf.ussiy.app.propedit;

import java.awt.Component;
import java.awt.HeadlessException;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

/**
 * 
 * @author Sou Miyazaki
 *  
 */
public class JSelectCodeFileChooser extends JFileChooser {

	/**
	 */
	private EncodeSelectPanel scPanel = null;

	/**
	 * 
	 * @since 1.0.0
	 */
	public JSelectCodeFileChooser() {

		super();
	}

	/**
	 * 
	 * @param arg0
	 * @since 1.0.0
	 */
	public JSelectCodeFileChooser(String filepath) {

		super(filepath);
	}

	/**
	 * 
	 * @param arg0
	 * @since 1.0.0
	 */
	public JSelectCodeFileChooser(File file) {

		super(file);
	}

	/**
	 * 
	 * @param arg0
	 * @since 1.0.0
	 */
	public JSelectCodeFileChooser(FileSystemView view) {

		super(view);
	}

	/**
	 * 
	 * @param arg0
	 * @param arg1
	 * @since 1.0.0
	 */
	public JSelectCodeFileChooser(File file, FileSystemView view) {

		super(file, view);
	}

	/**
	 * 
	 * @param arg0
	 * @param arg1
	 * @since 1.0.0
	 */
	public JSelectCodeFileChooser(String filpath, FileSystemView view) {

		super(filpath, view);
	}

	/**
	 * 
	 * @param parent
	 * @since 1.0.0
	 */
	protected JDialog createDialog(Component parent) throws HeadlessException {

		JDialog dialog = super.createDialog(parent);

		if (getDialogType() == JFileChooser.OPEN_DIALOG) {
			scPanel = new EncodeSelectPanel(false);
			this.setAccessory(scPanel);
		}
		return dialog;
	}

	/**
	 * 
	 * @since 1.0.0
	 */
	public String getReadCode() {

		try {
			if (scPanel != null) {
				return scPanel.getSelectedEncode();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}