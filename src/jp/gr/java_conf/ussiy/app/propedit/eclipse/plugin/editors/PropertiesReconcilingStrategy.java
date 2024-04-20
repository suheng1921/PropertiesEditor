package jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.editors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.reconciler.DirtyRegion;
import org.eclipse.jface.text.reconciler.IReconcilingStrategy;
import org.eclipse.jface.text.reconciler.IReconcilingStrategyExtension;
import org.eclipse.swt.widgets.Display;

public class PropertiesReconcilingStrategy implements IReconcilingStrategy,
		IReconcilingStrategyExtension {

	private PropertiesEditor editor;

	private IDocument fDocument;
	protected final ArrayList fPositions = new ArrayList();

	/**
	 * @return Returns the editor.
	 */
	public PropertiesEditor getEditor() {
		return editor;
	}

	public void setEditor(PropertiesEditor editor) {
		this.editor = editor;
	}

	/*
	 * @see org.eclipse.jface.text.reconciler.IReconcilingStrategy#setDocument(org.eclipse.jface.text.IDocument)
	 */
	public void setDocument(IDocument document) {
		this.fDocument = document;

	}

	/*
	 * @see org.eclipse.jface.text.reconciler.IReconcilingStrategy#reconcile(org.eclipse.jface.text.reconciler.DirtyRegion,
	 *      org.eclipse.jface.text.IRegion)
	 */
	public void reconcile(DirtyRegion dirtyRegion, IRegion subRegion) {
		initialReconcile();
	}

	/*
	 * @see org.eclipse.jface.text.reconciler.IReconcilingStrategy#reconcile(org.eclipse.jface.text.IRegion)
	 */
	public void reconcile(IRegion partition) {
		initialReconcile();
	}

	/*
	 * @see org.eclipse.jface.text.reconciler.IReconcilingStrategyExtension#setProgressMonitor(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void setProgressMonitor(IProgressMonitor monitor) {

	}

	/*
	 * @see org.eclipse.jface.text.reconciler.IReconcilingStrategyExtension#initialReconcile()
	 */
	public void initialReconcile() {
		calculatePositions();
	}

	/**
	 * uses {@link #fDocument},{@link #fOffset}and {@link #fRangeEnd}to
	 * calculate {@link #fPositions}. About syntax errors: this method is not a
	 * validator, it is useful.
	 */
	protected void calculatePositions() {
		fPositions.clear();

		try {
			recursiveTokens();
		} catch (CoreException e) {
			e.printStackTrace();
		}
		// Collections.sort(fPositions, new RangeTokenComparator());

		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				editor.updateFoldingStructure(fPositions);
			}

		});
	}

	/**
	 * emits tokens to {@link #fPositions}.
	 * 
	 * @throws BadLocationException
	 */
	protected void recursiveTokens() throws CoreException {
		String line = null;
		int cntLine = 0;
		boolean multipleValueFlg = false;
		int startLine = 0;
		int endLine = 0;
		int startPos = 0;
		int endPos = 0;

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new StringReader(fDocument.get()));
			while ((line = reader.readLine()) != null) {
				cntLine++;
				if (multipleValueFlg) {
					if (line.endsWith("\\")) { //$NON-NLS-1$
						multipleValueFlg = true;
					} else {
						multipleValueFlg = false;
						endLine = cntLine - 1;
						IRegion region = fDocument.getLineInformation(endLine);
						String lineDelim = fDocument.getLineDelimiter(endLine);
						int delimiterLength = 0;
						if (lineDelim != null) {
							delimiterLength = fDocument.getLineDelimiter(endLine).length();
						}
						endPos = region.getOffset() + region.getLength() + delimiterLength - 1;
						startPos = fDocument.getLineOffset(startLine);
						emitPosition(startPos, endPos - startPos + 1);
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
					startLine = cntLine - 1;
				} else {
					multipleValueFlg = false;
				}
			}
			if (multipleValueFlg) {
				IRegion region = fDocument.getLineInformation(cntLine - 1);
				endPos = region.getOffset() + region.getLength();
				startPos = fDocument.getLineOffset(startLine);
				emitPosition(startPos, endPos - startPos);
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

	protected void emitPosition(int startOffset, int length) {
		fPositions.add(new Position(startOffset, length));
	}

}
