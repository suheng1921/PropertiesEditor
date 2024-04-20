/*****************************************************
 * It is the frame which displays version information.
 *
 *   @author  Sou Miyazaki
 *
 ****************************************************/
package jp.gr.java_conf.ussiy.app.propedit;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * It is the frame which displays version information.
 * 
 * @author Sou Miyazaki
 *  
 */
public class PropertiesEditorFrame_AboutBox extends JDialog implements ActionListener {

	private JPanel panel1 = new JPanel();

	/**
	 */
	private JPanel panel2 = new JPanel();

	/**
	 */
	private JPanel insetsPanel1 = new JPanel();

	/**
	 */
	private JPanel insetsPanel2 = new JPanel();

	/**
	 */
	private JPanel insetsPanel3 = new JPanel();

	/**
	 */
	private JButton button1 = new JButton();

	/**
	 */
	private JLabel imageLabel = new JLabel();

	/**
	 */
	private JLabel label2 = new JLabel();

	/**
	 */
	private JLabel label3 = new JLabel();

	/**
	 */
	private ImageIcon image1 = new ImageIcon();

	/**
	 */
	private BorderLayout borderLayout1 = new BorderLayout();

	/**
	 */
	private BorderLayout borderLayout2 = new BorderLayout();

	private BorderLayout borderLayout3 = new BorderLayout();

	private BorderLayout borderLayout4 = new BorderLayout();

	JTextField jTextField1 = new JTextField();

	JPanel jPanel1 = new JPanel();

	BorderLayout borderLayout5 = new BorderLayout();

	JLabel jLabel1 = new JLabel();

	/**
	 * 
	 * @param parent
	 * @since 1.0.0
	 */
	public PropertiesEditorFrame_AboutBox(Frame parent) {

		super(parent);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @since 1.0.0
	 */
	private void jbInit() throws Exception {

		image1 = new ImageIcon(jp.gr.java_conf.ussiy.app.propedit.PropertiesEditorFrame.class.getResource("resource/editor.gif")); //$NON-NLS-1$
		imageLabel.setBorder(null);
		imageLabel.setIcon(image1);
		this.setTitle(PropertiesEditor.getI18nProperty("versionMenuItem_Text")); //$NON-NLS-1$
		panel1.setLayout(borderLayout1);
		panel2.setLayout(borderLayout2);
		insetsPanel2.setLayout(borderLayout3);
		insetsPanel2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		label2.setFont(new java.awt.Font("Dialog", 1, 16)); //$NON-NLS-1$
		label2.setText(PropertiesEditor.getI18nProperty("label2_Text") + PropertiesEditor.VERSION); //$NON-NLS-1$
		label3.setFont(new java.awt.Font("Dialog", 1, 15)); //$NON-NLS-1$
		label3.setText(PropertiesEditor.COPYRIGHT);
		label3.setVerticalAlignment(SwingConstants.CENTER);
		insetsPanel3.setLayout(borderLayout4);
		insetsPanel3.setBorder(BorderFactory.createEmptyBorder(10, 60, 10, 10));
		button1.setText("OK"); //$NON-NLS-1$
		button1.addActionListener(this);
		jTextField1.setBorder(null);
		jTextField1.setEditable(false);
		jTextField1.setText(PropertiesEditor.getI18nProperty("jTextField1_Text")); //$NON-NLS-1$
		jPanel1.setLayout(borderLayout5);
		jLabel1.setFont(new java.awt.Font("Dialog", 0, 14)); //$NON-NLS-1$
		jLabel1.setText(PropertiesEditor.getI18nProperty("license_Text")); //$NON-NLS-1$
		insetsPanel2.add(imageLabel, BorderLayout.CENTER);
		panel2.add(insetsPanel2, BorderLayout.WEST);
		this.getContentPane().add(panel1, null);
		insetsPanel3.add(label2, BorderLayout.NORTH);
		insetsPanel3.add(jTextField1, BorderLayout.SOUTH);
		insetsPanel3.add(jPanel1, BorderLayout.CENTER);
		jPanel1.add(label3, BorderLayout.NORTH);
		jPanel1.add(jLabel1, BorderLayout.SOUTH);
		panel2.add(insetsPanel3, BorderLayout.CENTER);
		insetsPanel1.add(button1, null);
		panel1.add(insetsPanel1, BorderLayout.SOUTH);
		panel1.add(panel2, BorderLayout.NORTH);
		setResizable(true);
	}

	/**
	 * 
	 * @param e
	 * @since 1.0.0
	 */
	protected void processWindowEvent(WindowEvent e) {

		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			cancel();
		}
		super.processWindowEvent(e);
	}

	/**
	 * 
	 * @since 1.0.0
	 */
	void cancel() {

		dispose();
	}

	/**
	 * 
	 * @param e
	 * @since 1.0.0
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == button1) {
			cancel();
		}
	}
}