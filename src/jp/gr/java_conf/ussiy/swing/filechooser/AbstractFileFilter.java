package jp.gr.java_conf.ussiy.swing.filechooser;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public abstract class AbstractFileFilter extends FileFilter {

	private String[] extension;

	private String description;

	public void setDescription(String description) {

		this.description = description;
	}

	public void setExtension(String extension) {

		this.extension = new String[1];
		this.extension[0] = extension.startsWith(".") ? extension : "." + extension; //$NON-NLS-1$ //$NON-NLS-2$
	}

	public void setExtension(String[] extension) {

		this.extension = new String[extension.length];
		for (int i = 0; i < extension.length; i++) {
			this.extension[i] = extension[i].startsWith(".") ? extension[i] : "." + extension[i]; //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	public boolean accept(File file) {

		String name = file.getName();
		if (file.isFile()) {
			for (int i = 0; i < extension.length; i++) {
				if (name.toLowerCase().endsWith(extension[i])) {
					return true;
				}
			}
		} else {
			return true;
		}
		return false;
	}

	public String getDescription() {

		return description;
	}
}