package jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.editors.rules;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

public class ValueRule implements IPredicateRule {

	protected IToken fToken;

	protected char fSeparateSequence;

	protected char fEscapeCharacter;

	public ValueRule(char separateSequence, IToken token, char escapeCharacter) {

		fToken = token;
		fSeparateSequence = separateSequence;
		fEscapeCharacter = escapeCharacter;
	}

	public IToken getSuccessToken() {

		return fToken;
	}

	public void disp(int a) {

		disp((char) a);
	}

	public void disp(char a) {

		if (a == '\n') {
			System.out.println("\\n"); //$NON-NLS-1$
		} else if (a == '\r') {
			System.out.println("\\r"); //$NON-NLS-1$
		} else if (a == ' ') {
			System.out.println("\\s"); //$NON-NLS-1$
		} else {
			System.out.println(new char[] { a });
		}
	}

	public IToken evaluate(ICharacterScanner scanner, boolean resume) {

		if (scanner.getColumn() != 0) {
			scanner.unread();
		} else {
			return Token.UNDEFINED;
		}
		int nc = scanner.read();

		// check escape character
		boolean escapeFlg = false;
		int unreadCnt = 0;
		if ((char) nc == fSeparateSequence) {
			int column = scanner.getColumn();
			for (int i = column; i >= 0; i--) {
				scanner.unread();
				scanner.unread();
				unreadCnt++;
				nc = scanner.read();
				if ((char) nc == fEscapeCharacter) {
					if (escapeFlg) {
						escapeFlg = false;
					} else {
						escapeFlg = true;
					}
				} else {
					break;
				}
			}
		} else {
			//            scanner.unread();
			return Token.UNDEFINED;
		}

		for (int j = 0; j < unreadCnt; j++) {
			scanner.read();
		}

		if (escapeFlg) {
			//            scanner.unread();
			return Token.UNDEFINED;
		}

		while (true) {
			nc = scanner.read();
			if (nc == ICharacterScanner.EOF) {
				return getSuccessToken();
			}
			if ((char) nc == fEscapeCharacter) {
				escapeFlg = true;
				while (true) {
					if ((nc = scanner.read()) == fEscapeCharacter) {
						if (escapeFlg) {
							escapeFlg = false;
						} else {
							escapeFlg = true;
						}
						continue;
					} else {
						break;
					}
				}
				if (escapeFlg) {
					if ((char) nc == '\n') {
						continue;
					} else if ((char) nc == ICharacterScanner.EOF) {
						return getSuccessToken();
					} else {
						int nc2 = scanner.read();
						if ((char) nc == '\r' && (char) nc2 == '\n') {
							continue;
						} else if ((char) nc == '\r' && (char) nc2 != '\n') {
							scanner.unread();
							continue;
						} else {
							scanner.unread();
							scanner.unread();
							continue;
						}
					}
				} else {
					if ((char) nc == '\r' || (char) nc == '\n') {
						return getSuccessToken();
					} else {
						continue;
					}
				}
			} else if ((char) nc == '\r' || (char) nc == '\n') {
				return getSuccessToken();
			} else {
				continue;
			}
		}
	}

	public IToken evaluate(ICharacterScanner scanner) {

		return evaluate(scanner, false);
	}

}