package jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.property;

import jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.PropertiesEditorPlugin;
import jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.resources.Messages;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PropertyPage;

public class PropertiesProperty extends PropertyPage {

	public static final String P_NOT_CONVERT_COMMENT = "notConvertComment"; //$NON-NLS-1$

	public static final String P_NOT_ALL_CONVERT = "notConvert"; //$NON-NLS-1$

	public static final String P_COMMENT_CHARACTER = "commentCharacter"; //$NON-NLS-1$
	
	public static final String P_CONVERT_CHAR_CASE = "convertCharCase"; //$NON-NLS-1$
	
	public static final String P_ORIGINAL_SETTINGS = "originalSettings"; //$NON-NLS-1$
	
	private String[] charCaseItems = new String[] { Messages.getString("eclipse.propertieseditor.preference.convert.char.uppercase"), Messages.getString("eclipse.propertieseditor.preference.convert.char.lowercase") }; //$NON-NLS-1$ //$NON-NLS-2$
	
	private Button orgCheckBox = null;
	
	private Text commentText = null;
	
	private Combo convertCharCaseCombo = null;
	
	private Button notAllConvertCheckBox = null;
	
	private Button notConvertCommentCheckBox = null;
	
	protected Control createContents(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		orgCheckBox = new Button(composite, SWT.CHECK);
		orgCheckBox.setText(Messages.getString("eclipse.propertieseditor.property.project_org_settings")); //$NON-NLS-1$
		GridData gd = new GridData();
		gd.horizontalSpan = 2;
		orgCheckBox.setLayoutData(gd);
		orgCheckBox.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (orgCheckBox.getSelection()) {
					commentText.setEnabled(true);
					convertCharCaseCombo.setEnabled(true);
					notAllConvertCheckBox.setEnabled(true);
					if (notAllConvertCheckBox.getSelection()) {
						notConvertCommentCheckBox.setEnabled(false);
					} else {
						notConvertCommentCheckBox.setEnabled(true);
					}
				} else {
					commentText.setEnabled(false);
					convertCharCaseCombo.setEnabled(false);
					notAllConvertCheckBox.setEnabled(false);
					notConvertCommentCheckBox.setEnabled(false);
				}
			}
		});

		Label separator = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		separator.setLayoutData(gd);
		
		Label label = new Label(composite, SWT.NONE);
		label.setText(Messages.getString("eclipse.propertieseditor.preference.comment.character")); //$NON-NLS-1$
		
		commentText = new Text(composite, SWT.BORDER);
		commentText.setTextLimit(1);
		
		label= new Label(composite, SWT.NONE);
		label.setText(Messages.getString("eclipse.propertieseditor.preference.convert.char.case")); //$NON-NLS-1$
		
		convertCharCaseCombo = new Combo(composite, SWT.READ_ONLY);
		convertCharCaseCombo.setItems(charCaseItems);
		
		Group group = new Group(composite, SWT.NONE);
		group.setLayout(new GridLayout());
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		group.setLayoutData(gd);
		group.setText(Messages.getString("eclipse.propertieseditor.preference.convert.option.group")); //$NON=NLS-1$ //$NON-NLS-1$
		
		notAllConvertCheckBox = new Button(group, SWT.CHECK);
		notAllConvertCheckBox.setText(Messages.getString("eclipse.propertieseditor.preference.convert")); //$NON-NLS-1$
		notAllConvertCheckBox.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (notAllConvertCheckBox.getSelection()) {
					notConvertCommentCheckBox.setEnabled(false);
				} else {
					notConvertCommentCheckBox.setEnabled(true);
				}
			}
		});

		notConvertCommentCheckBox = new Button(group, SWT.CHECK);
		notConvertCommentCheckBox.setText(Messages.getString("eclipse.propertieseditor.preference.convert.comment")); //$NON-NLS-1$

		initialize();
		
		return composite;
	}
	
	private void initialize() {
		String org = null;
		String commentChar = null;
		String charcase = null;
		String notAllConvert = null;
		String notConvertComment = null;
		try {
			IProject project = null;
			IAdaptable adaptable  = getElement();
			if (adaptable instanceof IJavaProject) {
				project = ((IJavaProject)adaptable).getProject();
			} else {
				project = (IProject)adaptable;
			}
			org = project.getPersistentProperty(new QualifiedName(PropertiesEditorPlugin.PLUGIN_ID, P_ORIGINAL_SETTINGS));
			commentChar = project.getPersistentProperty(new QualifiedName(PropertiesEditorPlugin.PLUGIN_ID, P_COMMENT_CHARACTER));
			charcase = project.getPersistentProperty(new QualifiedName(PropertiesEditorPlugin.PLUGIN_ID, P_CONVERT_CHAR_CASE));
			notAllConvert = project.getPersistentProperty(new QualifiedName(PropertiesEditorPlugin.PLUGIN_ID, P_NOT_ALL_CONVERT));
			notConvertComment = project.getPersistentProperty(new QualifiedName(PropertiesEditorPlugin.PLUGIN_ID, P_NOT_CONVERT_COMMENT));
		} catch (CoreException e) {
			IStatus status = new Status(IStatus.ERROR, PropertiesEditorPlugin.PLUGIN_ID, IStatus.OK, e.getMessage(), e);
			ILog log = PropertiesEditorPlugin.getDefault().getLog();
			log.log(status);
			ErrorDialog.openError(null, Messages.getString("eclipse.propertieseditor.property.error_title"), Messages.getString("eclipse.propertieseditor.property.get.settings.error"), status); //$NON-NLS-1$ //$NON-NLS-2$
		}
		
		if (commentChar != null) {
			commentText.setText(commentChar);
		} else {
			commentText.setText("#"); //$NON-NLS-1$
		}
		if (charcase != null) {
			convertCharCaseCombo.setText(charcase);
		} else {
			convertCharCaseCombo.setText(Messages.getString("eclipse.propertieseditor.preference.convert.char.lowercase")); //$NON-NLS-1$
		}
		if (notAllConvert != null) {
			notAllConvertCheckBox.setSelection(Boolean.valueOf(notAllConvert).booleanValue());
			if (notAllConvertCheckBox.getSelection()) {
				notConvertCommentCheckBox.setEnabled(false);
			} else {
				notConvertCommentCheckBox.setEnabled(true);
			}
		} else {
			notAllConvertCheckBox.setSelection(false);
			notConvertCommentCheckBox.setEnabled(false);
		}
		if (notConvertComment != null) {
			notConvertCommentCheckBox.setSelection(Boolean.valueOf(notConvertComment).booleanValue());
		} else {
			notConvertCommentCheckBox.setSelection(false);
		}
		
		if (org != null) {
			orgCheckBox.setSelection(Boolean.valueOf(org).booleanValue());
			if (orgCheckBox.getSelection()) {
				commentText.setEnabled(true);
				convertCharCaseCombo.setEnabled(true);
				notAllConvertCheckBox.setEnabled(true);
				if (notAllConvertCheckBox.getSelection()) {
					notConvertCommentCheckBox.setEnabled(false);
				} else {
					notConvertCommentCheckBox.setEnabled(true);
				}
			} else {
				commentText.setEnabled(false);
				convertCharCaseCombo.setEnabled(false);
				notAllConvertCheckBox.setEnabled(false);
				notConvertCommentCheckBox.setEnabled(false);
			}
		} else {
			orgCheckBox.setSelection(false);
			commentText.setEnabled(false);
			convertCharCaseCombo.setEnabled(false);
			notAllConvertCheckBox.setEnabled(false);
			notConvertCommentCheckBox.setEnabled(false);
		}

	}

	/**
	 * @see org.eclipse.jface.preference.PreferencePage#performApply()
	 */
	protected void performApply() {
		try {
			IAdaptable adapter = getElement();
			IProject project = null;
			if (adapter instanceof IJavaProject) {
				IJavaProject jp = (IJavaProject)adapter;
				project = jp.getProject();
			} else {
				project = (IProject)adapter;
			}
			project.setPersistentProperty(new QualifiedName(PropertiesEditorPlugin.PLUGIN_ID, P_ORIGINAL_SETTINGS), Boolean.toString(orgCheckBox.getSelection()));
			project.setPersistentProperty(new QualifiedName(PropertiesEditorPlugin.PLUGIN_ID, P_COMMENT_CHARACTER), commentText.getText());
			project.setPersistentProperty(new QualifiedName(PropertiesEditorPlugin.PLUGIN_ID, P_CONVERT_CHAR_CASE), convertCharCaseCombo.getText());
			project.setPersistentProperty(new QualifiedName(PropertiesEditorPlugin.PLUGIN_ID, P_NOT_ALL_CONVERT), Boolean.toString(notAllConvertCheckBox.getSelection()));
			project.setPersistentProperty(new QualifiedName(PropertiesEditorPlugin.PLUGIN_ID, P_NOT_CONVERT_COMMENT), Boolean.toString(notConvertCommentCheckBox.getSelection()));
		} catch (CoreException e) {
			IStatus status = new Status(IStatus.ERROR, PropertiesEditorPlugin.PLUGIN_ID, IStatus.OK, e.getMessage(), e);
			ILog log = PropertiesEditorPlugin.getDefault().getLog();
			log.log(status);
			ErrorDialog.openError(getShell(), Messages.getString("eclipse.propertieseditor.property.error_title"), Messages.getString("eclipse.propertieseditor.property.save.settings.error"), status); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	protected void performDefaults() {
		commentText.setText("#"); //$NON-NLS-1$
		convertCharCaseCombo.setText(Messages.getString("eclipse.propertieseditor.preference.convert.char.lowercase")); //$NON-NLS-1$
		notAllConvertCheckBox.setSelection(false);
		notConvertCommentCheckBox.setEnabled(false);
		notConvertCommentCheckBox.setSelection(false);
		orgCheckBox.setSelection(false);
		commentText.setEnabled(false);
		convertCharCaseCombo.setEnabled(false);
		notAllConvertCheckBox.setEnabled(false);
		notConvertCommentCheckBox.setEnabled(false);
	}

	public boolean performOk() {
		performApply();
		return true;
	}

}