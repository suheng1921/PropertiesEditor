package jp.gr.java_conf.ussiy.swing;

import java.awt.Dimension;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

import jp.gr.java_conf.ussiy.swing.plaf.basic.JExtendedPopupComboBoxUI;

public class JExtendedPopupComboBox extends JComboBox {

	protected int popupWidth;

	public JExtendedPopupComboBox() {

		initialize();
	}

	public JExtendedPopupComboBox(ComboBoxModel aModel) {

		super(aModel);
		initialize();
	}

	public JExtendedPopupComboBox(Object[] items) {

		super(items);
		initialize();
	}

	public JExtendedPopupComboBox(Vector items) {

		super(items);
		initialize();
	}

	public void initialize() {

		setUI(new JExtendedPopupComboBoxUI());
		popupWidth = 0;
	}

	public void setPopupWidth(int width) {

		popupWidth = width;
	}

	public Dimension getPopupSize() {

		Dimension size = getSize();
		if (popupWidth < 1) {
			popupWidth = size.width;
		}
		return new Dimension(popupWidth, size.height);
	}

	public void updateUI() {

		super.updateUI();
		initialize();
	}
}