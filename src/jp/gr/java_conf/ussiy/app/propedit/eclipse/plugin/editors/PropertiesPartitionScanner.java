package jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.editors;

import java.util.ArrayList;
import java.util.List;

import jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.editors.rules.CommentLineRule;
import jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.editors.rules.SeparatorRule;
import jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.editors.rules.SkipHeadSpaceRule;
import jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.editors.rules.ValueRule;

import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;

public class PropertiesPartitionScanner extends RuleBasedPartitionScanner {
	public final static String PROPERTIES_COMMENT = "__properties_comment"; //$NON-NLS-1$
	public final static String PROPERTIES_VALUE = "__properties_value"; //$NON-NLS-1$
	public final static String PROPERTIES_SEPARATOR = "__properties_separator"; //$NON-NLS-1$

	public PropertiesPartitionScanner() {

		IToken propertiesComment = new Token(PROPERTIES_COMMENT);
		IToken propertiesValue = new Token(PROPERTIES_VALUE);
		IToken propertiesSeparator = new Token(PROPERTIES_SEPARATOR);

		List ruleList = new ArrayList();
		ruleList.add(new SkipHeadSpaceRule());
		ruleList.add(new CommentLineRule("#", propertiesComment, '\\')); //$NON-NLS-1$
		ruleList.add(new CommentLineRule("!", propertiesComment, '\\')); //$NON-NLS-1$
		ruleList.add(new SeparatorRule(new char[] { '=', ':' }, propertiesSeparator, '\\'));
		ruleList.add(new ValueRule('=', propertiesValue, '\\'));
		ruleList.add(new ValueRule(':', propertiesValue, '\\'));
		ruleList.add(new ValueRule(' ', propertiesValue, '\\'));
		ruleList.add(new ValueRule('\t', propertiesValue, '\\'));
//		ruleList.add(new ValueRuleForWhiteSpace(new char[] { '=', ':', ' ', '\t' }, propertiesValue, '\\'));

		IPredicateRule[] rules = new IPredicateRule[ruleList.size()];
		rules = (IPredicateRule[]) ruleList.toArray(rules);
		setPredicateRules(rules);
	}
}