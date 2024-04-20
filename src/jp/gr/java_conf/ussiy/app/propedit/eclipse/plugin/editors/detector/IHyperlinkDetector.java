package jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.editors.detector;

import org.eclipse.ui.texteditor.ITextEditor;

public interface IHyperlinkDetector extends
		org.eclipse.jface.text.hyperlink.IHyperlinkDetector {

	public void setTextEditor(ITextEditor editor);

}
