package jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.editors.view.outline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.editors.view.outline.PropertiesContentOutlinePage.Segment;

import org.eclipse.jface.text.BadPositionCategoryException;
import org.eclipse.jface.text.DefaultPositionUpdater;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.IPositionUpdater;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * Divides the editor's document into ten segments and provides elements for
 * them.
 */
class PropertiesOutlineContentProvider implements ITreeContentProvider, IDocumentListener {

	private final PropertiesContentOutlinePage fContentOutlinePage;

	/**
	 * @param page
	 */
	PropertiesOutlineContentProvider(PropertiesContentOutlinePage page) {
		this.fContentOutlinePage = page;
	}

	protected final static String SEGMENTS = "__properties_segments"; //$NON-NLS-1$

	protected IPositionUpdater fPositionUpdater = new DefaultPositionUpdater(
			SEGMENTS);

	protected List fContent = new ArrayList(10);

	protected void parse(IDocument document) {
		String line = null;
		int cntLine = 0;
		boolean multipleValueFlg = false;

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new StringReader(document.get()));
			while ((line = reader.readLine()) != null) {
				cntLine++;
				if (multipleValueFlg) {
					if (line.endsWith("\\")) { //$NON-NLS-1$
						multipleValueFlg = true;
					} else {
						multipleValueFlg = false;
					}
					continue;
				} else {
					if (line.trim().equals("") || line.trim().startsWith("#") || line.trim().startsWith("!")) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						continue;
					}
				}
				line = line.trim();
				boolean escapeFlg = false;
				boolean nonSeparate = true;
				for (int i = 0; i < line.length(); i++) {
					char achar = line.charAt(i);
					if (achar == '\\') {
						if (escapeFlg) {
							String tmp = line;
							line = ""; //$NON-NLS-1$
							line = tmp.substring(0, i);
							line += tmp.substring(i + 1, tmp.length());
							escapeFlg = false;
							i--;
						} else {
							escapeFlg = true;
						}
					} else if (achar == '=' || achar == '\t' || achar == ':' || achar == ' ') {
						if (escapeFlg) {
							String tmp = line;
							line = ""; //$NON-NLS-1$
							line = tmp.substring(0, i - 1);
							line += tmp.substring(i, tmp.length());
							escapeFlg = false;
							i--;
							continue;
						} else {
							int offset = document.getLineOffset(cntLine - 1);
							Position p = new Position(offset, i);
							document.addPosition(SEGMENTS, p);

							fContent.add(new Segment(line.substring(0, i).trim(), p)); //$NON-NLS-1$

							nonSeparate = false;
							escapeFlg = false;
							break;
						}
					} else {
						if (escapeFlg) {
							String tmp = line;
							line = ""; //$NON-NLS-1$
							line = tmp.substring(0, i - 1);
							line += tmp.substring(i, tmp.length());
							i--;
						}
						escapeFlg = false;
					}
				}
				if (nonSeparate) {
					if (line.endsWith("\\")) { //$NON-NLS-1$
						continue;
					}
				}
				if (line.endsWith("\\")) { //$NON-NLS-1$
					multipleValueFlg = true;
				} else {
					multipleValueFlg = false;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
			}
		}

	}

	/*
	 * @see IContentProvider#inputChanged(Viewer, Object, Object)
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (oldInput != null) {
			IDocument document = this.fContentOutlinePage.fDocumentProvider
					.getDocument(oldInput);
			if (document != null) {
				try {
					document.removePositionCategory(SEGMENTS);
				} catch (BadPositionCategoryException x) {
				}
				document.removePositionUpdater(fPositionUpdater);
			}
		}

		fContent.clear();

		if (newInput != null) {
			IDocument document = this.fContentOutlinePage.fDocumentProvider
					.getDocument(newInput);
			if (document != null) {
				document.addPositionCategory(SEGMENTS);
				document.addPositionUpdater(fPositionUpdater);

				parse(document);
			}
		}
	}

	/*
	 * @see IContentProvider#dispose
	 */
	public void dispose() {
		if (fContent != null) {
			fContent.clear();
			fContent = null;
		}
	}

	/*
	 * @see IContentProvider#isDeleted(Object)
	 */
	public boolean isDeleted(Object element) {
		return false;
	}

	/*
	 * @see IStructuredContentProvider#getElements(Object)
	 */
	public Object[] getElements(Object element) {
		return fContent.toArray();
	}

	/*
	 * @see ITreeContentProvider#hasChildren(Object)
	 */
	public boolean hasChildren(Object element) {
		return element == this.fContentOutlinePage.fInput;
	}

	/*
	 * @see ITreeContentProvider#getParent(Object)
	 */
	public Object getParent(Object element) {
		if (element instanceof Segment)
			return this.fContentOutlinePage.fInput;
		return null;
	}

	/*
	 * @see ITreeContentProvider#getChildren(Object)
	 */
	public Object[] getChildren(Object element) {
		if (element == this.fContentOutlinePage.fInput)
			return fContent.toArray();
		return new Object[0];
	}

	/**
	 * @see org.eclipse.jface.text.IDocumentListener#documentAboutToBeChanged(org.eclipse.jface.text.DocumentEvent)
	 */
	public void documentAboutToBeChanged(DocumentEvent event) {
	}

	/**
	 * @see org.eclipse.jface.text.IDocumentListener#documentChanged(org.eclipse.jface.text.DocumentEvent)
	 */
	public void documentChanged(DocumentEvent event) {
//		try {
//			event.getDocument().removePositionCategory(SEGMENTS);
//		} catch (BadPositionCategoryException x) {
//		}
//		event.getDocument().removePositionUpdater(fPositionUpdater);
//		fContent.clear();
//		event.getDocument().addPositionCategory(SEGMENTS);
//		event.getDocument().addPositionUpdater(fPositionUpdater);
//		parse(event.getDocument());
//		fContentOutlinePage.update();
	}

}