package jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.editors;

import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

public class ComboFieldEditor extends FieldEditor {

	private Combo combo;

	private String[] items;

	/**
	 * (これを消してコンストラクターの説明を記述)
	 * 
	 * @param name
	 * @param labelText
	 * @param parent
	 */
	public ComboFieldEditor(String name, String labelText, String[] items, Composite parent) {

		super(name, labelText, parent);
		this.items = items;
		setComboItems();
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.FieldEditor#adjustForNumColumns(int)
	 */
	protected void adjustForNumColumns(int numColumns) {

	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.FieldEditor#doFillIntoGrid(org.eclipse.swt.widgets.Composite, int)
	 */
	protected void doFillIntoGrid(Composite parent, int numColumns) {

		getLabelControl(parent);

		combo = new Combo(parent, SWT.NONE);
	}

	protected void setComboItems() {

		for (int i = 0; i < items.length; i++) {
			combo.setItems(items);
		}
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.FieldEditor#doLoad()
	 */
	protected void doLoad() {

		if (combo != null) {
			String value = getPreferenceStore().getString(getPreferenceName());
			combo.setText(value);
		}
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.FieldEditor#doLoadDefault()
	 */
	protected void doLoadDefault() {

		if (combo != null) {
			String value = getPreferenceStore().getDefaultString(getPreferenceName());
			combo.setText(value);
		}
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.FieldEditor#doStore()
	 */
	protected void doStore() {

		getPreferenceStore().setValue(getPreferenceName(), combo.getText());
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.FieldEditor#getNumberOfControls()
	 */
	public int getNumberOfControls() {

		return 2;
	}
}