package jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.listener;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;

public interface IPropertiesDocumentListener {

	public void beforeUnicodeConvertAtSavingDocument(IProgressMonitor monitor, Object element, IDocument document, boolean overwrite);
	
	public void afterUnicodeConvertAtSavingDocument(IProgressMonitor monitor, Object element, IDocument document, boolean overwrite);
	
	public void beforeConvertAtLoadingDocument(IDocument document, Object element);
	
	public void afterConvertAtLoadingDocument(IDocument document, Object element);
	
}
