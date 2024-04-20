package jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.editors;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.ITypedRegion;

public class PropertiesDoubleClickStrategy implements ITextDoubleClickStrategy {

	protected ITextViewer fText;

	public void doubleClicked(ITextViewer part) {

		int pos = part.getSelectedRange().x;

		if (pos < 0) {
			return;
		}

		fText = part;

		if (isSelectKey(pos)) {
			selectKey(pos);
		} else {
			selectWord(pos);
		}
	}

	/**
	 * @param pos
	 * @return
	 */
	protected boolean isSelectKey(int pos) {
		IDocument doc = fText.getDocument();

		try {
			int len = doc.getLength();
			if (len == pos) return false;
			ITypedRegion[] partitionType = doc.computePartitioning(pos, 1);
			if (partitionType.length > 0) {
				if (partitionType[0].getType().equals(IDocument.DEFAULT_CONTENT_TYPE)) {
					return true;
				}
			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		return false;
	}

	protected void selectKey(int pos) {

		IDocument doc = fText.getDocument();
		int lineNum = 0;
		int startPos = 0;
		int length = 0;
		String targetLine = null;
		try {
			lineNum = doc.getLineOfOffset(pos);
			startPos = doc.getLineOffset(lineNum);
			length = doc.getLineLength(lineNum);
			targetLine = doc.get(startPos, length);
		} catch (BadLocationException e) {
			e.printStackTrace();
			return;
		}
		if (targetLine.equals("")) { //$NON-NLS-1$
			return;
		}

		String line = targetLine;
		boolean escapeFlg = false;
		boolean flg = true;
		int spcnt = 0;
		int i = 0;
		for ( ; i < line.length(); i++) {
			char achar = line.charAt(i);
			if (flg && (achar == ' ' || achar == '\t')) {
				spcnt++;
				continue;
			} else if (flg) {
				flg = false;
			}
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
					selectRange2(startPos + spcnt, i - spcnt);
					return;
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
		if (targetLine.endsWith("\\\n")) { //$NON-NLS-1$
			selectRange2(startPos + spcnt, i - spcnt - 1);
		} else if (targetLine.endsWith("\\")) { //$NON-NLS-1$
			selectRange2(startPos + spcnt, i - spcnt - 1);
		} else {
			selectRange2(startPos + spcnt, i - spcnt);
		}
	}

	protected boolean selectWord(int caretPos) {

		IDocument doc = fText.getDocument();
		int startPos, endPos;

		try {

			int pos = caretPos;
			char c;

			while (pos >= 0) {
				c = doc.getChar(pos);
				if (!Character.isJavaIdentifierPart(c)) {
					break;
				}
				--pos;
			}

			startPos = pos;

			pos = caretPos;
			int length = doc.getLength();

			while (pos < length) {
				c = doc.getChar(pos);
				if (!Character.isJavaIdentifierPart(c)) {
					break;
				}
				++pos;
			}

			endPos = pos;
			selectRange(startPos, endPos);
			return true;

		} catch (BadLocationException x) {
		}

		return false;
	}

	private void selectRange2(int startPos, int length) {
		fText.setSelectedRange(startPos, length);
	}

	private void selectRange(int startPos, int stopPos) {

		int offset = startPos + 1;
		int length = stopPos - offset;
		fText.setSelectedRange(offset, length);
	}
}
