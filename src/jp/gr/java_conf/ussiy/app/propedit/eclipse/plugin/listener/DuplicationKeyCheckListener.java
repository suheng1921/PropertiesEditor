package jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.listener;

import jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.checker.CheckAndMarkDuplicateKey;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IFileEditorInput;

public class DuplicationKeyCheckListener implements IPropertiesDocumentListener {

	/**
	 * @see jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.listener.IPropertiesDocumentListener#afterConvertAtLoadingDocument(org.eclipse.jface.text.IDocument, java.lang.Object)
	 */
	public void afterConvertAtLoadingDocument(IDocument document, Object element) {
		if (element instanceof IFileEditorInput) {
			CheckAndMarkDuplicateKey camd = new CheckAndMarkDuplicateKey();
			try {
				camd.checkAndMarkDuplicateKeyInString(document.get(), (IFileEditorInput)element);
			} catch(CoreException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.listener.IPropertiesDocumentListener#afterUnicodeConvertAtSavingDocument(org.eclipse.core.runtime.IProgressMonitor, java.lang.Object, org.eclipse.jface.text.IDocument, boolean)
	 */
	public void afterUnicodeConvertAtSavingDocument(IProgressMonitor monitor,
			Object element, IDocument document, boolean overwrite) {
	}
	
	/**
	 * @see jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.listener.IPropertiesDocumentListener#beforeConvertAtLoadingDocument(org.eclipse.jface.text.IDocument, java.lang.Object)
	 */
	public void beforeConvertAtLoadingDocument(IDocument document, Object element) {
	}

	/**
	 * @see jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.listener.IPropertiesDocumentListener#beforeUnicodeConvertAtSavingDocument(org.eclipse.core.runtime.IProgressMonitor, java.lang.Object, org.eclipse.jface.text.IDocument, boolean)
	 */
	public void beforeUnicodeConvertAtSavingDocument(IProgressMonitor monitor,
			Object element, IDocument document, boolean overwrite) {
		if (element instanceof IFileEditorInput) {
			CheckAndMarkDuplicateKey camd = new CheckAndMarkDuplicateKey();
			try {
				camd.checkAndMarkDuplicateKeyInString(document.get(), (IFileEditorInput)element);
			} catch(CoreException e) {
				e.printStackTrace();
			}
		}
	}

}
