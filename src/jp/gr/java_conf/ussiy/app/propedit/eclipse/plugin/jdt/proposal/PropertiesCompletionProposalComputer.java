package jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.jdt.proposal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.resources.Messages;
import jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.util.ProjectProperties;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.ui.text.java.ContentAssistInvocationContext;
import org.eclipse.jdt.ui.text.java.IJavaCompletionProposalComputer;
import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;
import org.eclipse.jface.text.contentassist.CompletionProposal;

public class PropertiesCompletionProposalComputer implements
		IJavaCompletionProposalComputer {

	public List computeCompletionProposals(ContentAssistInvocationContext context,
			IProgressMonitor monitor) {
		if (!(context instanceof JavaContentAssistInvocationContext)) {
			return Collections.EMPTY_LIST;
		}
		
		JavaContentAssistInvocationContext jContext = (JavaContentAssistInvocationContext)context;
		IJavaProject jProject = jContext.getProject();
		IProject project = jProject.getProject();

		Enumeration enu = ProjectProperties.getInstance().getProperty(project).keys();
		List keyList = new ArrayList();
		while (enu.hasMoreElements()) {
			keyList.add(enu.nextElement());
		}
		
		String source = context.getDocument().get();
		int offset = context.getInvocationOffset();
		int idx = source.charAt(offset) == '\"' ? source.lastIndexOf("\"", offset - 1) : source.lastIndexOf("\"", offset); //$NON-NLS-1$ //$NON-NLS-2$
		StringBuffer buf = new StringBuffer();
		for (int i = idx + 1; i < offset; i++) {
			char c = source.charAt(i);
			buf.append(c);
		}
		
		String match = buf.toString();
		
		List list = new ArrayList();
		
		Collections.sort(keyList);
		Iterator ite = keyList.iterator();
		while (ite.hasNext()) {
			String key = (String)ite.next();
			if (key.startsWith(match)) {
				list.add(new CompletionProposal(key, offset - match.length(),
						match.length(), key.length()));
			}
		}
		
		return list;
	}

	public List computeContextInformation(ContentAssistInvocationContext arg0,
			IProgressMonitor arg1) {
		return Collections.emptyList();
	}

	public String getErrorMessage() {
		return Messages.getString("eclipse.propertieseditor.PropertiesCompletionProposalComputer.4"); //$NON-NLS-1$
	}

	public void sessionEnded() {
	}

	public void sessionStarted() {
	}

}
