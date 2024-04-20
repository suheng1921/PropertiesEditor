/*****************************************************
 * It is the dialog which displays the text changed into Unicode.
 *
 *   @author  Sou Miyazaki
 *
 ****************************************************/
package jp.gr.java_conf.ussiy.app.propedit;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * It is the dialog which displays the text changed into Unicode.
 * 
 * @author Sou Miyazaki
 *  
 */
public class UnicodeDialog extends BaseDialog {

	private JPanel panel1 = new JPanel();

	/**
	 */
	private BorderLayout borderLayout1 = new BorderLayout();

	/**
	 */
	private JScrollPane jScrollPane1 = new JScrollPane();

	/**
	 */
	private JTextArea jTextArea1 = new JTextArea();

	/**
	 */
	private JMenuBar jMenuBar1 = new JMenuBar();

	/**
	 */
	private JMenu jMenu1 = new JMenu();

	/**
	 */
	private JMenuItem jMenuItem1 = new JMenuItem();

	/**
	 * 
	 * @param frame
	 * @param title
	 * @param modal
	 * @since 1.0.0
	 */
	public UnicodeDialog(Frame frame, String title, boolean modal) {

		super(frame, title, modal);
		try {
			jbInit();
			pack();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 
	 * @since 1.0.0
	 */
	private void jbInit() throws Exception {

		panel1.setLayout(borderLayout1);
		jTextArea1.setBackground(new Color(230, 230, 255));
		jTextArea1.setEnabled(true);
		jTextArea1.setEditable(false);
		jMenu1.setText(PropertiesEditor.getI18nProperty("fileMenu_Text")); //$NON-NLS-1$
		jMenuItem1.setText(PropertiesEditor.getI18nProperty("closeMenuItem_Text")); //$NON-NLS-1$
		jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke('U', java.awt.event.KeyEvent.CTRL_MASK, false));
		jMenuItem1.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {

				jMenuItem1_actionPerformed(e);
			}
		});
		getContentPane().add(panel1);
		panel1.add(jScrollPane1, BorderLayout.CENTER);
		jScrollPane1.getViewport().add(jTextArea1, null);
		jMenuBar1.add(jMenu1);
		jMenu1.add(jMenuItem1);
		this.setJMenuBar(jMenuBar1);
	}

	/**
	 * 
	 * @param message
	 * @since 1.0.0
	 */
	public void setMessage(String message) {

		jTextArea1.setText(message);
	}

	/**
	 * 
	 * @param e
	 * @since 1.0.0
	 */
	void jMenuItem1_actionPerformed(ActionEvent e) {

		dispose();
	}
}