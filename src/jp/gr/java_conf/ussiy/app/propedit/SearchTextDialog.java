package jp.gr.java_conf.ussiy.app.propedit;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SearchTextDialog extends BaseDialog {

	private String inputString;

	private JPanel panel1 = new JPanel();

	private BorderLayout borderLayout1 = new BorderLayout();

	private JPanel jPanel1 = new JPanel();

	private FlowLayout flowLayout1 = new FlowLayout();

	private JPanel jPanel2 = new JPanel();

	private FlowLayout flowLayout2 = new FlowLayout();

	private JLabel jLabel1 = new JLabel();

	private JButton cancelButton = new JButton();

	private JButton searchButton = new JButton();

	private JTextField inputTextField = new JTextField();

	public SearchTextDialog(Frame frame, boolean modal) {

		this(frame, PropertiesEditor.getI18nProperty("findMenuItem_Text"), modal); //$NON-NLS-1$
	}

	private SearchTextDialog(Frame frame, String title, boolean modal) {

		super(frame, title, modal);
		try {
			jbInit();
			pack();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public SearchTextDialog() {

		this(null, "", false); //$NON-NLS-1$
	}

	private void jbInit() throws Exception {

		panel1.setLayout(borderLayout1);
		jPanel1.setLayout(flowLayout1);
		flowLayout1.setAlignment(FlowLayout.RIGHT);
		jPanel2.setLayout(flowLayout2);
		jLabel1.setFont(new java.awt.Font("Dialog", 1, 13)); //$NON-NLS-1$
		jLabel1.setText(PropertiesEditor.getI18nProperty("jLabel1_Text2")); //$NON-NLS-1$
		inputTextField.setFont(new java.awt.Font("Dialog", 0, 14)); //$NON-NLS-1$
		inputTextField.setPreferredSize(new Dimension(200, 30));
		inputTextField.setText(""); //$NON-NLS-1$
		inputTextField.addKeyListener(new java.awt.event.KeyAdapter() {

			public void keyTyped(KeyEvent e) {

				inputTextField_keyTyped(e);
			}
		});
		cancelButton.setFont(new java.awt.Font("Dialog", 1, 13)); //$NON-NLS-1$
		cancelButton.setText(PropertiesEditor.getI18nProperty("KEY17")); //$NON-NLS-1$
		cancelButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {

				cancelButton_actionPerformed(e);
			}
		});
		searchButton.setFont(new java.awt.Font("Dialog", 1, 13)); //$NON-NLS-1$
		searchButton.setText(PropertiesEditor.getI18nProperty("findMenuItem_Text")); //$NON-NLS-1$
		searchButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {

				searchButton_actionPerformed(e);
			}
		});
		getContentPane().add(panel1);
		panel1.add(jPanel1, BorderLayout.SOUTH);
		jPanel1.add(searchButton, null);
		jPanel1.add(cancelButton, null);
		panel1.add(jPanel2, BorderLayout.CENTER);
		jPanel2.add(jLabel1, null);
		jPanel2.add(inputTextField, null);
	}

	void cancelButton_actionPerformed(ActionEvent e) {

		dispose();
	}

	void searchButton_actionPerformed(ActionEvent e) {

		inputString = inputTextField.getText();
		dispose();
	}

	public String getInputString() {

		return inputString;
	}

	void inputTextField_keyTyped(KeyEvent e) {

		if (e.getKeyChar() == '\n') {
			searchButton_actionPerformed(null);
		}
	}
}