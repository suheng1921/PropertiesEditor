package jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.editors;

import jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.preference.PropertiesEditorPreference;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;

public class PropertiesScanner extends RuleBasedScanner {

	public PropertiesScanner(ColorManager manager, IPreferenceStore pStore) {

		IToken propertiesDefault = new Token(new TextAttribute(manager.getColor(PreferenceConverter.getColor(pStore, PropertiesEditorPreference.P_KEY_COLOR))));

		setDefaultReturnToken(propertiesDefault);
	}
}