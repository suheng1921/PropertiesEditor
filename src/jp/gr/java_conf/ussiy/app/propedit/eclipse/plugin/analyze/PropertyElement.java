package jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.analyze;

public class PropertyElement {

	private int keyStartPos;
	private int keyLength;
	private int valueStartPos;
	private int valueLength;
	private String key;
	private String value;
	private int elementStartLine;
	private int elementLineCount;
	
	public int getElementStartLine() {
		return elementStartLine;
	}
	
	public void setElementStartLine(int elementStartLine) {
		this.elementStartLine = elementStartLine;
	}
	
	public String getKey() {
		return key;
	}
	
	public int getElementLineCount() {
		return elementLineCount;
	}
	
	public void setElementLineCount(int elementLineCount) {
		this.elementLineCount = elementLineCount;
	}
	
	public int getKeyLength() {
		return keyLength;
	}
	
	public void setKeyLength(int keyLength) {
		this.keyLength = keyLength;
	}
	
	public int getValueLength() {
		return valueLength;
	}
	
	public void setValueLength(int valueLength) {
		this.valueLength = valueLength;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public int getKeyStartPos() {
		return keyStartPos;
	}
	
	public void setKeyStartPos(int keyStartPos) {
		this.keyStartPos = keyStartPos;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public int getValueStartPos() {
		return valueStartPos;
	}
	
	public void setValueStartPos(int valueStartPos) {
		this.valueStartPos = valueStartPos;
	}
	
}
