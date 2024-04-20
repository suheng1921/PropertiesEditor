package jp.gr.java_conf.ussiy.swing.plaf.basic;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

import jp.gr.java_conf.ussiy.swing.JExtendedPopupComboBox;

public class JExtendedPopupComboBoxUI extends BasicComboBoxUI {

	protected ComboPopup createPopup() {

		BasicComboPopup popup = new BasicComboPopup(comboBox) {

			public void show() {

				JExtendedPopupComboBox sComboBox = (JExtendedPopupComboBox) comboBox;

				ComboBoxModel cModel = sComboBox.getModel();
				JComboBox tmpComboBox = new JComboBox(cModel);
				sComboBox.setPopupWidth(tmpComboBox.getPreferredSize().width);

				Dimension popupSize = sComboBox.getPopupSize();
				popupSize.setSize(popupSize.width, getPopupHeightForRowCount(sComboBox.getMaximumRowCount()));
				if (sComboBox.getWidth() > popupSize.width) {
					popupSize.width = sComboBox.getWidth();
				}
				Rectangle popupBounds = computePopupBounds(0, sComboBox.getBounds().height, popupSize.width, popupSize.height);
				scroller.setMaximumSize(popupBounds.getSize());
				scroller.setPreferredSize(popupBounds.getSize());
				scroller.setMinimumSize(popupBounds.getSize());
				list.invalidate();
				int selectedIndex = sComboBox.getSelectedIndex();
				if (selectedIndex == -1) {
					list.clearSelection();
				} else {
					list.setSelectedIndex(selectedIndex);
				}
				list.ensureIndexIsVisible(list.getSelectedIndex());
				setLightWeightPopupEnabled(sComboBox.isLightWeightPopupEnabled());

				show(sComboBox, popupBounds.x, popupBounds.y);
			}
		};
		popup.getAccessibleContext().setAccessibleParent(comboBox);
		return popup;
	}
}