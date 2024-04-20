package jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.editors.rules;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

public class SeparatorRule implements IPredicateRule {

	protected IToken fToken;

	protected char[] fSeparateSequences;

	protected char fEscapeCharacter;

	public SeparatorRule(char[] separateSequences, IToken token, char escapeCharacter) {

		fToken = token;
		fSeparateSequences = separateSequences;
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

		int nc = scanner.read();

		// check escape character
		boolean escapeFlg = false;
		boolean isSeparateCharFlg = false;
		for (int i = 0; i < fSeparateSequences.length; i++) {
			if ((char) nc == fSeparateSequences[i]) {
				isSeparateCharFlg = true;
				break;
			}
		}

		int unreadCnt = 0;
		if (isSeparateCharFlg) {
			if (scanner.getColumn() != 0) {
				scanner.unread();
				scanner.unread();
				nc = scanner.read();
				for (int i = 0; i < fSeparateSequences.length; i++) {
					if ((char) nc == fSeparateSequences[i]) {
						int subUnreadCnt = 0;
						int column = scanner.getColumn();
						boolean subEscapeFlg = false;
						for (int j = column; j >= 0; j--) {
							scanner.unread();
							scanner.unread();
							subUnreadCnt++;
							nc = scanner.read();
							if ((char) nc == fEscapeCharacter) {
								if (subEscapeFlg) {
									subEscapeFlg = false;
								} else {
									subEscapeFlg = true;
								}
							} else {
								for (int k = 0; k < subUnreadCnt; k++) {
									scanner.read();
								}
								if (!subEscapeFlg) {
									return Token.UNDEFINED;
								} else {
									break;
								}
							}
						}
					}
				}
				nc = scanner.read();
			}

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
			scanner.unread();
			return Token.UNDEFINED;
		}

		for (int j = 0; j < unreadCnt; j++) {
			scanner.read();
		}

		if (escapeFlg) {
			scanner.unread();
			return Token.UNDEFINED;
		} else {
			return getSuccessToken();
		}
	}

	public IToken evaluate(ICharacterScanner scanner) {

		return evaluate(scanner, false);
	}

}