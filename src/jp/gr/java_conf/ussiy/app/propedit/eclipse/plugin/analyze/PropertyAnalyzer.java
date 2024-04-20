package jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.analyze;

import org.eclipse.jface.text.IDocument;

public class PropertyAnalyzer {

	public static PropertyElement[] analyze(IDocument document) throws Exception {
		return null;
	}
	
}

class ElementListCreator {
	
	void addElement(int keyStartPos, int keyLength, String key, int valueStartPos,
			int valueLength, String value, int elementStartLine, int elementLineCount) {
		PropertyElement element = new PropertyElement();
		element.setKeyStartPos(keyStartPos);
		element.setKeyLength(keyLength);
		element.setKey(key);
		element.setValueStartPos(valueStartPos);
		element.setValueLength(valueLength);
		element.setValue(value);
		element.setElementStartLine(elementStartLine);
		element.setElementLineCount(elementLineCount);
	}
}
