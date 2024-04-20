package jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.preference;

import jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.PropertiesEditorPlugin;
import jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.editors.ColorManager;
import jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.resources.Messages;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbenchWindow;

public class PropertiesEditorPreference extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public static final String P_COMMENT_COLOR = "commentColor"; //$NON-NLS-1$

	public static final String P_SEPARATOR_COLOR = "separatorColor"; //$NON-NLS-1$

	public static final String P_KEY_COLOR = "keyColor"; //$NON-NLS-1$

	public static final String P_VALUE_COLOR = "valueColor"; //$NON-NLS-1$

	public static final String P_BACKGROUND_COLOR = "backgroundColor"; //$NON-NLS-1$

	public static final String P_COLLAPSE = "collapse"; //$NON-NLS-1$

	public static final String P_INIT_COLLAPSE = "initialCollapse"; //$NON-NLS-1$

	private ColorFieldEditor commentColorFieldEditor;

	private ColorFieldEditor separatorColorFieldEditor;

	private ColorFieldEditor keyColorFieldEditor;

	private ColorFieldEditor valueColorFieldEditor;

	private ColorFieldEditor backgroundColorFieldEditor;

	private BooleanFieldEditor collapseCheckFieldEditor;

	private BooleanFieldEditor initialCollapseCheckFieldEditor;

	public PropertiesEditorPreference() {

		super(GRID);
		setPreferenceStore(PropertiesEditorPlugin.getDefault().getPreferenceStore());
		setDescription(Messages.getString("eclipse.propertieseditor.preference.page.title")); //$NON-NLS-1$
	}

	public void createFieldEditors() {

		Composite parent = getFieldEditorParent();

		final Group collapseGroup = new Group(parent, SWT.NONE);
		GridData collapseGd = new GridData(GridData.FILL_HORIZONTAL);
		collapseGd.horizontalSpan = 2;
		collapseGroup.setLayoutData(collapseGd);
		collapseGroup.setLayout(new GridLayout(1, true));
		collapseGroup.setText(Messages.getString("eclipse.propertieseditor.preference.collapse.group")); //$NON-NLS-1$
		collapseCheckFieldEditor = new BooleanFieldEditor(P_COLLAPSE, Messages.getString("eclipse.propertieseditor.preference.collapse"), collapseGroup) { //$NON-NLS-1$

			/**
			 * @see org.eclipse.jface.preference.BooleanFieldEditor#valueChanged(boolean, boolean)
			 */
			public void valueChanged(boolean oldValue, boolean newValue) {

				initialCollapseCheckFieldEditor.setEnabled(newValue, collapseGroup);
				super.valueChanged(oldValue, newValue);
			}
		};
		initialCollapseCheckFieldEditor = new BooleanFieldEditor(P_INIT_COLLAPSE, Messages.getString("eclipse.propertieseditor.preference.initcollapse"), collapseGroup); //$NON-NLS-1$
		initialCollapseCheckFieldEditor.setEnabled(PropertiesEditorPlugin.getDefault().getPreferenceStore().getBoolean(PropertiesEditorPreference.P_COLLAPSE), collapseGroup);

		final Group colorGroup = new Group(parent, SWT.NONE);
		GridData colorGd = new GridData(GridData.FILL_HORIZONTAL);
		colorGd.horizontalSpan = 2;
		colorGroup.setLayoutData(colorGd);
		colorGroup.setLayout(new GridLayout(1, true));
		colorGroup.setText(Messages.getString("eclipse.propertieseditor.preference.color.option.group")); //$NON-NLS-1$
		commentColorFieldEditor = new ColorFieldEditor(P_COMMENT_COLOR, Messages.getString("eclipse.propertieseditor.preference.color.comment"), colorGroup); //$NON-NLS-1$
		separatorColorFieldEditor = new ColorFieldEditor(P_SEPARATOR_COLOR, Messages.getString("eclipse.propertieseditor.preference.color.separator"), colorGroup); //$NON-NLS-1$
		keyColorFieldEditor = new ColorFieldEditor(P_KEY_COLOR, Messages.getString("eclipse.propertieseditor.preference.color.key"), colorGroup); //$NON-NLS-1$
		valueColorFieldEditor = new ColorFieldEditor(P_VALUE_COLOR, Messages.getString("eclipse.propertieseditor.preference.color.value"), colorGroup); //$NON-NLS-1$
		backgroundColorFieldEditor = new ColorFieldEditor(P_BACKGROUND_COLOR, Messages.getString("eclipse.propertieseditor.preference.color.background"), colorGroup); //$NON-NLS-1$

		addField(collapseCheckFieldEditor);
		addField(initialCollapseCheckFieldEditor);
		addField(commentColorFieldEditor);
		addField(separatorColorFieldEditor);
		addField(keyColorFieldEditor);
		addField(valueColorFieldEditor);
		addField(backgroundColorFieldEditor);
	}

	public void init(IWorkbench workbench) {

	}

	/**
	 * @see org.eclipse.jface.preference.PreferencePage#performApply()
	 */
	protected void performApply() {

		super.performApply();
		apply2Editor();
	}

	private void apply2Editor() {

		IPreferenceStore pStore = getPreferenceStore();
		IWorkbenchWindow[] workbenchWindow = PropertiesEditorPlugin.getDefault().getWorkbench().getWorkbenchWindows();
		for (int i = 0; i < workbenchWindow.length; i++) {
			IWorkbenchPage[] workbenchPage = workbenchWindow[i].getPages();
			for (int j = 0; j < workbenchPage.length; j++) {
				IEditorReference[] editorReferences = workbenchPage[j].getEditorReferences();
				for (int k = 0; k < editorReferences.length; k++) {
					IEditorReference ref = editorReferences[k];
					IEditorPart editorPart = ref.getEditor(false);
					if (editorPart instanceof jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.editors.PropertiesEditor) {
						RGB rgb = PreferenceConverter.getColor(pStore, PropertiesEditorPreference.P_BACKGROUND_COLOR);
						Color color = new ColorManager().getColor(rgb);
						jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.editors.PropertiesEditor editor =
							(jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.editors.PropertiesEditor)editorPart;
						editor.setBackground(color);
					}
				}
			}
		}
	}
}
