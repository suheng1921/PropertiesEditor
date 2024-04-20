package jp.gr.java_conf.ussiy.swing.filechooser;

import java.util.ResourceBundle;

public class PropertiesFileFilter extends AbstractFileFilter {

	static ResourceBundle res = ResourceBundle.getBundle("jp.gr.java_conf.ussiy.swing.lang"); //$NON-NLS-1$

	public PropertiesFileFilter() {

		super.setExtension(".properties"); //$NON-NLS-1$
		super.setDescription(res.getString("file_description_text")); //$NON-NLS-1$
	}
}