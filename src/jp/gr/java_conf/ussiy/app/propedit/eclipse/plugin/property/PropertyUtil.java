package jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.property;

import jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.PropertiesEditorPlugin;
import jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.resources.Messages;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;

public class PropertyUtil {

	public static String getCommentChar(IProject project, String preference) {
		String org = null;
		String commentChar = null;
		try {
			org = project.getPersistentProperty(new QualifiedName(PropertiesEditorPlugin.PLUGIN_ID, PropertiesProperty.P_ORIGINAL_SETTINGS));
			commentChar = project.getPersistentProperty(new QualifiedName(PropertiesEditorPlugin.PLUGIN_ID, PropertiesProperty.P_COMMENT_CHARACTER));
		} catch (CoreException e) {
			IStatus status = new Status(IStatus.ERROR, PropertiesEditorPlugin.PLUGIN_ID, IStatus.OK, e.getMessage(), e);
			ILog log = PropertiesEditorPlugin.getDefault().getLog();
			log.log(status);
			ErrorDialog.openError(null, Messages.getString("eclipse.propertieseditor.property.error_title"), Messages.getString("eclipse.propertieseditor.property.get.settings.error"), status); //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (org != null) {
			if (Boolean.valueOf(org).booleanValue()) {
				if (commentChar == null || commentChar.equals("")) { //$NON-NLS-1$
					return preference;
				} else {
					return commentChar;
				}
			}
		}
		return preference;
	}

	public static boolean getNotAllConvert(IProject project, boolean preference) {
		String org = null;
		String notAllConvert = null;
		try {
			org = project.getPersistentProperty(new QualifiedName(PropertiesEditorPlugin.PLUGIN_ID, PropertiesProperty.P_ORIGINAL_SETTINGS));
			notAllConvert = project.getPersistentProperty(new QualifiedName(PropertiesEditorPlugin.PLUGIN_ID, PropertiesProperty.P_NOT_ALL_CONVERT));
		} catch (CoreException e) {
			IStatus status = new Status(IStatus.ERROR, PropertiesEditorPlugin.PLUGIN_ID, IStatus.OK, e.getMessage(), e);
			ILog log = PropertiesEditorPlugin.getDefault().getLog();
			log.log(status);
			ErrorDialog.openError(null, Messages.getString("eclipse.propertieseditor.property.error_title"), Messages.getString("eclipse.propertieseditor.property.get.settings.error"), status); //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (org != null) {
			if (Boolean.valueOf(org).booleanValue()) {
				if (notAllConvert == null || notAllConvert.equals("")) { //$NON-NLS-1$
					return preference;
				} else {
					return Boolean.valueOf(notAllConvert).booleanValue();
				}
			}
		}
		return preference;
	}

	public static boolean getNotConvertComment(IProject project, boolean preference) {
		String org = null;
		String notAllConvert = null;
		String notConvertComment = null;
		try {
			org = project.getPersistentProperty(new QualifiedName(PropertiesEditorPlugin.PLUGIN_ID, PropertiesProperty.P_ORIGINAL_SETTINGS));
			notAllConvert = project.getPersistentProperty(new QualifiedName(PropertiesEditorPlugin.PLUGIN_ID, PropertiesProperty.P_NOT_ALL_CONVERT));
			notConvertComment = project.getPersistentProperty(new QualifiedName(PropertiesEditorPlugin.PLUGIN_ID, PropertiesProperty.P_NOT_CONVERT_COMMENT));
		} catch (CoreException e) {
			IStatus status = new Status(IStatus.ERROR, PropertiesEditorPlugin.PLUGIN_ID, 0, e.getMessage(), e);
			ILog log = PropertiesEditorPlugin.getDefault().getLog();
			log.log(status);
			ErrorDialog.openError(null, Messages.getString("eclipse.propertieseditor.property.error_title"), Messages.getString("eclipse.propertieseditor.property.get.settings.error"), status); //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (org != null) {
			if (Boolean.valueOf(org).booleanValue()) {
				if (notAllConvert != null) {
					if (Boolean.valueOf(notAllConvert).booleanValue()) {
						return true;
					}
				}
				if (notConvertComment == null || notConvertComment.equals("")) { //$NON-NLS-1$
					return preference;
				} else {
					return Boolean.valueOf(notConvertComment).booleanValue();
				}
			}
		}
		return preference;
	}
	
	public static String getCharCase(IProject project, String preference) {
		String org = null;
		String charcase = null;
		try {
			org = project.getPersistentProperty(new QualifiedName(PropertiesEditorPlugin.PLUGIN_ID, PropertiesProperty.P_ORIGINAL_SETTINGS));
			charcase = project.getPersistentProperty(new QualifiedName(PropertiesEditorPlugin.PLUGIN_ID, PropertiesProperty.P_CONVERT_CHAR_CASE));
		} catch (CoreException e) {
			IStatus status = new Status(IStatus.ERROR, PropertiesEditorPlugin.PLUGIN_ID, IStatus.OK, e.getMessage(), e);
			ILog log = PropertiesEditorPlugin.getDefault().getLog();
			log.log(status);
			ErrorDialog.openError(null, Messages.getString("eclipse.propertieseditor.property.error_title"), Messages.getString("eclipse.propertieseditor.property.get.settings.error"), status); //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (org != null) {
			if (Boolean.valueOf(org).booleanValue()) {
				if (charcase == null || charcase.equals("")) { //$NON-NLS-1$
					return preference;
				} else {
					return charcase;
				}
			}
		}
		return preference;
	}
}
