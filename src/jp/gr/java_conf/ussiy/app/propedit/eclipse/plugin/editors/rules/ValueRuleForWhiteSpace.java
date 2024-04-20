package jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.editors.rules;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

public class ValueRuleForWhiteSpace implements IPredicateRule {

	protected IToken fToken;

	protected char[] fSeparateSequences = new char[] { ' ', '\t' };

	protected char fEscapeCharacter;

	protected char[] fOtherSeparateSequences;

	public ValueRuleForWhiteSpace(char[] otherSeparateSequences, IToken token, char escapeCharacter) {

		fToken = token;
		fOtherSeparateSequences = otherSeparateSequences;
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
		boolean otherEscapeFlg = false;
		int unreadCnt = 0;
		boolean isSeparateCharFlg = false;
		for (int i = 0; i < fSeparateSequences.length; i++) {
			if ((char) nc == fSeparateSequences[i]) {
				isSeparateCharFlg = true;
				break;
			}
		}
		if (isSeparateCharFlg) {
			int readCnt = 0;
			LOOP : while (true) {
				nc = scanner.read();
				readCnt++;
				if ((char) nc == '\n' || (char) nc == '\r' || nc == ICharacterScanner.EOF) {
					break;
				} else if ((char) nc == fEscapeCharacter) {
					break LOOP;
					//        			if (otherEscapeFlg) {
					//        				otherEscapeFlg = false;
					//        			} else {
					//        				otherEscapeFlg = true;
					//        			}
					//        			continue;
				}
				for (int i = 0; i < fOtherSeparateSequences.length; i++) {
					if ((char) nc == fOtherSeparateSequences[i]) {
						if (!otherEscapeFlg) {
							for (int j = 0; j < readCnt; j++) {
								scanner.unread();
							}
							return Token.UNDEFINED;
						} else {
							break LOOP;
						}
					}
				}
				otherEscapeFlg = false;
			}
			for (int i = 0; i < readCnt; i++) {
				scanner.unread();
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
			return Token.UNDEFINED;
		}

		for (int j = 0; j < unreadCnt; j++) {
			scanner.read();
		}

		if (escapeFlg) {
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