package jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.editors;

import java.util.ArrayList;
import java.util.List;

import jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.PropertiesEditorPlugin;
import jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.listener.IPropertiesDocumentListener;
import jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.preference.PropertiesPreference;
import jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.property.PropertyUtil;
import jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.resources.Messages;
import jp.gr.java_conf.ussiy.app.propedit.util.EncodeChanger;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.editors.text.FileDocumentProvider;

public class PropertiesDocumentProvider extends FileDocumentProvider {
	private static final String EXTENSION_POINT = "jp.gr.java_conf.ussiy.app.propedit.listeners"; //$NON-NLS-1$

	protected List computePropertiesDocumentListeners() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = registry.getExtensionPoint(EXTENSION_POINT);
		IExtension[] extensions = extensionPoint.getExtensions();
		ArrayList results = new ArrayList();
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement[] elements = extensions[i].getConfigurationElements();
			for (int j = 0; j < elements.length; j++) {
				try {
					Object listener = elements[j].createExecutableExtension("class"); //$NON-NLS-1$
					if (listener instanceof IPropertiesDocumentListener) {
						results.add(listener);
					}
				} catch(CoreException e) {
					e.printStackTrace();
				}
			}
		}
		
		return results;
	}
	
	protected IDocument createDocument(Object element) throws CoreException {

		IDocument document = super.createDocument(element);
		
		List listeners = computePropertiesDocumentListeners();
		for (int i = 0; i < listeners.size(); i++) {
			IPropertiesDocumentListener listener = (IPropertiesDocumentListener)listeners.get(i);
			try {
				listener.beforeConvertAtLoadingDocument(document, element);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		if (document != null) {
			
			try {
				document.set(EncodeChanger.unicodeEsc2Unicode(document.get()));
			} catch (Exception e) {
				IStatus status = new Status(IStatus.ERROR, PropertiesEditorPlugin.PLUGIN_ID, IStatus.OK, e.getMessage(), e);
				ILog log = PropertiesEditorPlugin.getDefault().getLog();
				log.log(status);
				ErrorDialog.openError(null, Messages.getString("eclipse.propertieseditor.convert.error"), Messages.getString("eclipse.propertieseditor.property.get.settings.error"), status); //$NON-NLS-1$ //$NON-NLS-2$
			}

			for (int i = 0; i < listeners.size(); i++) {
				IPropertiesDocumentListener listener = (IPropertiesDocumentListener)listeners.get(i);
				try {
					listener.afterConvertAtLoadingDocument(document, element);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}

			IDocumentPartitioner partitioner = new FastPartitioner(new PropertiesPartitionScanner(), new String[] { IDocument.DEFAULT_CONTENT_TYPE, PropertiesPartitionScanner.PROPERTIES_COMMENT, PropertiesPartitionScanner.PROPERTIES_SEPARATOR, PropertiesPartitionScanner.PROPERTIES_VALUE });
			partitioner.connect(document);
			document.setDocumentPartitioner(partitioner);
		}
		return document;
	}

	protected void doSaveDocument(IProgressMonitor monitor, Object element, IDocument document, boolean overwrite) throws CoreException {

		if (element instanceof IFileEditorInput) {

			IFileEditorInput input = (IFileEditorInput) element;

			IProject project = input.getFile().getProject();

			List listeners = computePropertiesDocumentListeners();
			for (int i = 0; i < listeners.size(); i++) {
				IPropertiesDocumentListener listener = (IPropertiesDocumentListener)listeners.get(i);
				try {
					listener.beforeUnicodeConvertAtSavingDocument(monitor, element, document, overwrite);
				} catch(Exception e) {
					IStatus status = new Status(IStatus.ERROR, PropertiesEditorPlugin.PLUGIN_ID, IStatus.OK, e.getMessage(), e);
					ILog log = PropertiesEditorPlugin.getDefault().getLog();
					log.log(status);
				}
			}
			
			String uniEscStr = null;
			String charcase = PropertyUtil.getCharCase(project, PropertiesEditorPlugin.getDefault().getPreferenceStore().getString(PropertiesPreference.P_CONVERT_CHAR_CASE));
			if (PropertyUtil.getNotAllConvert(project, PropertiesEditorPlugin.getDefault().getPreferenceStore().getBoolean(PropertiesPreference.P_NOT_ALL_CONVERT))) {
				uniEscStr = document.get();
			} else if (PropertyUtil.getNotConvertComment(project, PropertiesEditorPlugin.getDefault().getPreferenceStore().getBoolean(PropertiesPreference.P_NOT_CONVERT_COMMENT))) {
				try {
					if (Messages.getString("eclipse.propertieseditor.preference.convert.char.uppercase").equals(charcase)) { //$NON-NLS-1$
						uniEscStr = EncodeChanger.unicode2UnicodeEscWithoutComment(document.get(), EncodeChanger.UPPERCASE);
					} else {
						uniEscStr = EncodeChanger.unicode2UnicodeEscWithoutComment(document.get(), EncodeChanger.LOWERCASE);
					}
				} catch (Exception e) {
					IStatus status = new Status(IStatus.ERROR, PropertiesEditorPlugin.PLUGIN_ID, IStatus.OK, e.getMessage(), e);
					ILog log = PropertiesEditorPlugin.getDefault().getLog();
					log.log(status);
					ErrorDialog.openError(null, Messages.getString("eclipse.propertieseditor.convert.error"), Messages.getString("eclipse.propertieseditor.property.get.settings.error"), status); //$NON-NLS-1$ //$NON-NLS-2$
				}
			} else {
				try {
					if (Messages.getString("eclipse.propertieseditor.preference.convert.char.uppercase").equals(charcase)) { //$NON-NLS-1$
						uniEscStr = EncodeChanger.unicode2UnicodeEsc(document.get(), EncodeChanger.UPPERCASE);
					} else {
						uniEscStr = EncodeChanger.unicode2UnicodeEsc(document.get(), EncodeChanger.LOWERCASE);
					}
				} catch (Exception e) {
					IStatus status = new Status(IStatus.ERROR, PropertiesEditorPlugin.PLUGIN_ID, IStatus.OK, e.getMessage(), e);
					ILog log = PropertiesEditorPlugin.getDefault().getLog();
					log.log(status);
					ErrorDialog.openError(null, Messages.getString("eclipse.propertieseditor.convert.error"), Messages.getString("eclipse.propertieseditor.property.get.settings.error"), status); //$NON-NLS-1$ //$NON-NLS-2$
				}
			}
			document = new Document(uniEscStr);
			
			for (int i = 0; i < listeners.size(); i++) {
				IPropertiesDocumentListener listener = (IPropertiesDocumentListener)listeners.get(i);
				try {
					listener.afterUnicodeConvertAtSavingDocument(monitor, element, document, overwrite);
				} catch(Exception e) {
					IStatus status = new Status(IStatus.ERROR, PropertiesEditorPlugin.PLUGIN_ID, IStatus.OK, e.getMessage(), e);
					ILog log = PropertiesEditorPlugin.getDefault().getLog();
					log.log(status);
				}
			}

		}
		super.doSaveDocument(monitor, element, document, overwrite);
	}
}