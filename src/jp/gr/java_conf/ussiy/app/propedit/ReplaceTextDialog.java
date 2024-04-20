package jp.gr.java_conf.ussiy.app.propedit;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ReplaceTextDialog extends BaseDialog {

	private String sourceText;

	private String exchangeText;

	private JPanel panel1 = new JPanel();

	private BorderLayout borderLayout1 = new BorderLayout();

	private JPanel jPanel1 = new JPanel();

	private JButton replaceButton = new JButton();

	private JButton cancelButton = new JButton();

	private FlowLayout flowLayout1 = new FlowLayout();

	private JPanel jPanel2 = new JPanel();

	private GridBagLayout gridBagLayout1 = new GridBagLayout();

	private JLabel jLabel1 = new JLabel();

	private JTextField sourceTextField = new JTextField();

	private JLabel jLabel2 = new JLabel();

	private JTextField exchangeTextField = new JTextField();

	public ReplaceTextDialog(Frame frame, boolean modal) {

		this(frame, PropertiesEditor.getI18nProperty("replaceMenuItem_Text"), modal); //$NON-NLS-1$
	}

	private ReplaceTextDialog(Frame frame, String title, boolean modal) {

		super(frame, title, modal);
		try {
			jbInit();
			pack();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public ReplaceTextDialog() {

		this(null, "", false); //$NON-NLS-1$
	}

	private void jbInit() throws Exception {

		panel1.setLayout(borderLayout1);
		replaceButton.setFont(new java.awt.Font("Dialog", 1, 13)); //$NON-NLS-1$
		replaceButton.setText(PropertiesEditor.getI18nProperty("replaceMenuItem_Text")); //$NON-NLS-1$
		replaceButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {

				replaceButton_actionPerformed(e);
			}
		});
		cancelButton.setFont(new java.awt.Font("Dialog", 1, 13)); //$NON-NLS-1$
		cancelButton.setText(PropertiesEditor.getI18nProperty("KEY17")); //$NON-NLS-1$
		cancelButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {

				cancelButton_actionPerformed(e);
			}
		});
		jPanel1.setLayout(flowLayout1);
		flowLayout1.setAlignment(FlowLayout.RIGHT);
		jPanel2.setLayout(gridBagLayout1);
		jLabel1.setFont(new java.awt.Font("Dialog", 1, 13)); //$NON-NLS-1$
		jLabel1.setText(PropertiesEditor.getI18nProperty("jLabel1_Text1")); //$NON-NLS-1$
		sourceTextField.setFont(new java.awt.Font("Dialog", 0, 14)); //$NON-NLS-1$
		sourceTextField.setPreferredSize(new Dimension(200, 30));
		sourceTextField.setText(""); //$NON-NLS-1$
		jLabel2.setFont(new java.awt.Font("Dialog", 1, 13)); //$NON-NLS-1$
		jLabel2.setText(PropertiesEditor.getI18nProperty("jLabel2_Text")); //$NON-NLS-1$
		exchangeTextField.setFont(new java.awt.Font("Dialog", 0, 14)); //$NON-NLS-1$
		exchangeTextField.setPreferredSize(new Dimension(200, 30));
		exchangeTextField.setText(""); //$NON-NLS-1$
		getContentPane().add(panel1);
		panel1.add(jPanel1, BorderLayout.SOUTH);
		jPanel1.add(replaceButton, null);
		jPanel1.add(cancelButton, null);
		panel1.add(jPanel2, BorderLayout.CENTER);
		jPanel2.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		jPanel2.add(sourceTextField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 3, 0, 0), 0, 0));
		jPanel2.add(jLabel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 0, 0, 0), 0, 0));
		jPanel2.add(exchangeTextField, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(2, 3, 0, 0), 0, 0));
	}

	void replaceButton_actionPerformed(ActionEvent e) {

		sourceText = sourceTextField.getText();
		exchangeText = exchangeTextField.getText();
		dispose();
	}

	void cancelButton_actionPerformed(ActionEvent e) {

		dispose();
	}

	public String getSourceText() {

		return sourceText;
	}

	public String getExchangeText() {

		return exchangeText;
	}
}