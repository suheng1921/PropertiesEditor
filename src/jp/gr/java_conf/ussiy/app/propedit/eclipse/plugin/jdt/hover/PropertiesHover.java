/**
 * 
 */
package jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.jdt.hover;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.PropertiesEditorPlugin;
import jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.util.ProjectProperties;
import jp.gr.java_conf.ussiy.util.StringUtil;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.internal.ui.text.java.hover.JavaInformationProvider;
import org.eclipse.jdt.ui.text.java.hover.IJavaEditorTextHover;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextHoverExtension;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;

/**
 * @author miyazaki
 *
 */
public class PropertiesHover implements IJavaEditorTextHover, ITextHoverExtension {
	
	private IEditorPart editorPart;
	
	/**
	 * @see org.eclipse.jdt.ui.text.java.hover.IJavaEditorTextHover#setEditor(org.eclipse.ui.IEditorPart)
	 */
	public void setEditor(IEditorPart editorPart) {
		this.editorPart = editorPart;
	}

	/**
	 * @see org.eclipse.jface.text.ITextHover#getHoverInfo(org.eclipse.jface.text.ITextViewer, org.eclipse.jface.text.IRegion)
	 */
	public String getHoverInfo(ITextViewer textViewer, IRegion region) {
		IDocument document = textViewer.getDocument();
		int offset = region.getOffset();
		try {
			int lineNum = document.getLineOfOffset(offset);
			int lineOffset = document.getLineOffset(lineNum);
			int lineLength = document.getLineLength(lineNum);
			String source = document.get();
			int startIdx = -1;
			int tmp = offset - 1;
			while (tmp >= lineOffset) {
				tmp = source.lastIndexOf("\"", tmp); //$NON-NLS-1$
				if (tmp < lineOffset) {
					startIdx = -1;
					break;
				}
				if (tmp > 0) {
					if (document.getChar(tmp - 1) == '\\') {
						tmp--;
						continue;
					} else {
						startIdx = tmp + 1;
						break;
					}
				} else if (tmp == 0) {
					startIdx = 0 + 1;
					break;
				} else {
					startIdx = -1;
					break;
				}
			}
			tmp = offset + 1;
			int endIdx = -1;
			while (tmp < lineOffset + lineLength) {
				tmp = source.indexOf("\"", tmp); //$NON-NLS-1$
				if (tmp > lineOffset + lineLength) {
					endIdx = -1;
					break;
				}
				if (tmp > 0) {
					if (document.getChar(tmp - 1) == '\\') {
						tmp++;
						continue;
					} else {
						endIdx = tmp;
						break;
					}
				} else if (tmp == 0) {
					endIdx = 0;
					break;
				} else {
					endIdx = -1;
					break;
				}
			}
			
			if (startIdx == -1 || endIdx == -1 || startIdx == endIdx) {
				return null;
			}
			
			String key =  textViewer.getDocument().get(startIdx, endIdx - startIdx);
			
			return getPropertyValue(key);

		} catch (BadLocationException e) {
			IStatus status = new Status(IStatus.ERROR, PropertiesEditorPlugin.PLUGIN_ID, IStatus.OK, e.getMessage(), e);
			ILog log = PropertiesEditorPlugin.getDefault().getLog();
			log.log(status);
			return null;
		}
	}
	
	private String getPropertyValue(String targetKey) {
		if (targetKey == null || targetKey.equals("")) { //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
		
		IEditorInput editorInput = this.editorPart.getEditorInput();
		
		if (editorInput instanceof IFileEditorInput) {
			
			IFileEditorInput fEditorInput = (IFileEditorInput)editorInput;
			IProject project = fEditorInput.getFile().getProject();
	
			Map propertyMap = ProjectProperties.getInstance().getProperty(project, targetKey);
			
			if (propertyMap.isEmpty()) {
				return null;
			}
			
			Iterator ite = propertyMap.keySet().iterator();
			StringBuffer buf = new StringBuffer();
			while (ite.hasNext()) {
				IFile file = (IFile)ite.next();
				String path = StringUtil.escapeHtml((String)file.getFullPath().toPortableString());
				String value = StringUtil.escapeHtml((String)((Properties)propertyMap.get(file)).getProperty(targetKey));
				buf.append("<b>").append(path).append("</b><br/>"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				buf.append(value).append("<br/>"); //$NON-NLS-1$
			}
			return buf.toString();
		} else {
			return null;
		}
	}
	
	/**
	 * @see org.eclipse.jface.text.ITextHover#getHoverRegion(org.eclipse.jface.text.ITextViewer, int)
	 */
	public IRegion getHoverRegion(ITextViewer textViewer, int offset) {
		return null;
	}

	/**
	 * @see org.eclipse.jface.text.ITextHoverExtension#getHoverControlCreator()
	 */
	public IInformationControlCreator getHoverControlCreator() {
		return new JavaInformationProvider(editorPart).getInformationPresenterControlCreator();
	}

}
