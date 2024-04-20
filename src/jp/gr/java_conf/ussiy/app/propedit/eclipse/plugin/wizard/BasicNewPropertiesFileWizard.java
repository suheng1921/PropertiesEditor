/**
 * 
 */
package jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.wizard;

import jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.resources.Messages;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.ui.wizards.newresource.BasicNewFileResourceWizard;

/**
 *
 */
public class BasicNewPropertiesFileWizard extends BasicNewFileResourceWizard {

	/**
	 * @see org.eclipse.jface.wizard.Wizard#addPage(org.eclipse.jface.wizard.IWizardPage)
	 */
	public void addPage(IWizardPage page) {
		page.setTitle(Messages.getString("eclipse.propertieseditor.BasicNewPropertiesFileWizard.0")); //$NON-NLS-1$
		page.setDescription(Messages.getString("eclipse.propertieseditor.BasicNewPropertiesFileWizard.1")); //$NON-NLS-1$
		super.addPage(page);
	}

}
