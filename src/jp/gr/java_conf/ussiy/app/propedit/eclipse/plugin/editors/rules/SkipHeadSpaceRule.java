package jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.editors.rules;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

public class SkipHeadSpaceRule implements IPredicateRule {

	public IToken getSuccessToken() {

		return Token.UNDEFINED;
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

		if (scanner.getColumn() == 0) {
			int nc = 0;
			while (true) {
				nc = scanner.read();
				if (nc == ICharacterScanner.EOF) {
					return getSuccessToken();
				}
				if ((char) nc != ' ' && (char) nc != '\t') {
					scanner.unread();
					return getSuccessToken();
				}
			}
		}
		return getSuccessToken();
	}

	public IToken evaluate(ICharacterScanner scanner) {

		return evaluate(scanner, false);
	}
}