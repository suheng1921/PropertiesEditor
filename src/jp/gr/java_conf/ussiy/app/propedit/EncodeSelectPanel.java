package jp.gr.java_conf.ussiy.app.propedit;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import jp.gr.java_conf.ussiy.app.propedit.bean.Encode;
import jp.gr.java_conf.ussiy.app.propedit.util.EncodeManager;

class EncodeSelectPanel extends JPanel {

	private GridLayout gridLayout1 = new GridLayout();

	private JLabel jLabel1 = new JLabel();

	private ArrayList encodeRadioButton = new ArrayList();

	private ButtonGroup encodeButtonGroup = new ButtonGroup();

	private Vector encodeList;

	private boolean store = true;

	public EncodeSelectPanel() {

		this(true);
	}

	public EncodeSelectPanel(boolean store) {

		this.store = store;
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	void jbInit() throws Exception {

		jLabel1.setText(PropertiesEditor.getI18nProperty("jLabel1_Text")); //$NON-NLS-1$
		this.setLayout(gridLayout1);
		this.setBorder(BorderFactory.createEtchedBorder());
		if (!store) {
			encodeList = EncodeManager.getReadEncodeList();
		}
		for (int i = 0; i < encodeList.size(); i++) {
			Encode enc = (Encode) encodeList.get(i);
			switch (enc.getNo()) {
				case 0:
					JRadioButton radioButton = new JRadioButton(enc.getName());
					radioButton.setSelected(true);
					encodeRadioButton.add(radioButton);
					break;
				default:
					radioButton = new JRadioButton(enc.getName());
					encodeRadioButton.add(radioButton);
			}
		}
		gridLayout1.setColumns(0);
		gridLayout1.setHgap(5);
		gridLayout1.setRows(12);
		gridLayout1.setVgap(5);
		this.add(jLabel1, null);
		for (int i = 0; i < encodeRadioButton.size(); i++) {
			encodeButtonGroup.add((JRadioButton) encodeRadioButton.get(i));
			this.add((JRadioButton) encodeRadioButton.get(i), null);
		}
	}

	public String getSelectedEncode() {

		String name = null;
		Enumeration enu = encodeButtonGroup.getElements();
		while (enu.hasMoreElements()) {
			AbstractButton button = (AbstractButton) enu.nextElement();
			if (button.isSelected()) {
				name = button.getText();
				break;
			}
		}
		for (int i = 0; i < encodeList.size(); i++) {
			Encode enc = (Encode) encodeList.get(i);
			if (enc.getName().equals(name)) {
				return enc.getCode();
			}
		}
		return null;
	}
}