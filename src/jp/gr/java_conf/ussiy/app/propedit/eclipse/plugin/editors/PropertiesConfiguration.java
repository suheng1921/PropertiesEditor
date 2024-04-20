package jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.editors;

import java.util.ArrayList;
import java.util.List;

import jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.PropertiesEditorPlugin;
import jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.preference.PropertiesEditorPreference;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.reconciler.MonoReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

public class PropertiesConfiguration extends SourceViewerConfiguration {
	
	private static final String EXTENSION_POINT = "jp.gr.java_conf.ussiy.app.propedit.hyperlinkdetectors"; //$NON-NLS-1$

	private PropertiesDoubleClickStrategy doubleClickStrategy;

	private ColorManager colorManager;
	
	private PropertiesEditor editor;

	public PropertiesConfiguration(ColorManager colorManager, PropertiesEditor editor) {

		this.colorManager = colorManager;
		this.editor = editor;
	}

	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {

		return new String[] { IDocument.DEFAULT_CONTENT_TYPE, PropertiesPartitionScanner.PROPERTIES_COMMENT, PropertiesPartitionScanner.PROPERTIES_SEPARATOR, PropertiesPartitionScanner.PROPERTIES_VALUE };
	}

	public ITextDoubleClickStrategy getDoubleClickStrategy(ISourceViewer sourceViewer, String contentType) {

		if (doubleClickStrategy == null) {
			doubleClickStrategy = new PropertiesDoubleClickStrategy();
		}
		return doubleClickStrategy;
	}

	public IPreferenceStore getPreferenceStore() {

		return PropertiesEditorPlugin.getDefault().getPreferenceStore();
	}

	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {

		IPreferenceStore pStore = getPreferenceStore();

		PresentationReconciler reconciler = new PresentationReconciler();

		RGB rgb = PreferenceConverter.getColor(pStore, PropertiesEditorPreference.P_COMMENT_COLOR);
		Color color = colorManager.getColor(rgb);
		TextAttribute attr = new TextAttribute(color);
		NonRuleBasedDamagerRepairer ndr = new NonRuleBasedDamagerRepairer(attr);
		reconciler.setDamager(ndr, PropertiesPartitionScanner.PROPERTIES_COMMENT);
		reconciler.setRepairer(ndr, PropertiesPartitionScanner.PROPERTIES_COMMENT);

		rgb = PreferenceConverter.getColor(pStore, PropertiesEditorPreference.P_SEPARATOR_COLOR);
		color = colorManager.getColor(rgb);
		attr = new TextAttribute(color);
		ndr = new NonRuleBasedDamagerRepairer(attr);
		reconciler.setDamager(ndr, PropertiesPartitionScanner.PROPERTIES_SEPARATOR);
		reconciler.setRepairer(ndr, PropertiesPartitionScanner.PROPERTIES_SEPARATOR);

		rgb = PreferenceConverter.getColor(pStore, PropertiesEditorPreference.P_VALUE_COLOR);
		color = colorManager.getColor(rgb);
		attr = new TextAttribute(color);
		ndr = new NonRuleBasedDamagerRepairer(attr);
		reconciler.setDamager(ndr, PropertiesPartitionScanner.PROPERTIES_VALUE);
		reconciler.setRepairer(ndr, PropertiesPartitionScanner.PROPERTIES_VALUE);

		PropertiesScanner scanner = new PropertiesScanner(colorManager, pStore);
		DefaultDamagerRepairer dr = new DefaultDamagerRepairer(scanner);
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		return reconciler;
	}

	/**
	 * @see org.eclipse.jface.text.source.SourceViewerConfiguration#getReconciler(org.eclipse.jface.text.source.ISourceViewer)
	 */
	public IReconciler getReconciler(ISourceViewer sourceViewer) {
        PropertiesReconcilingStrategy strategy = new PropertiesReconcilingStrategy();
        strategy.setEditor(editor);
        
        MonoReconciler reconciler = new MonoReconciler(strategy,false);
        
        return reconciler;
	}

	/**
	 * @see org.eclipse.jface.text.source.SourceViewerConfiguration#getHyperlinkDetectors(org.eclipse.jface.text.source.ISourceViewer)
	 */
	public IHyperlinkDetector[] getHyperlinkDetectors(ISourceViewer viewer) {
		IHyperlinkDetector[] detectors = super.getHyperlinkDetectors(viewer);
		List list = new ArrayList();
		for (int i = 0; i < detectors.length; i++) {
			if (detectors[i] != null) list.add(detectors[i]);
		}
		list.addAll(computePropertiesHyperlinkDetectors());
		detectors = (IHyperlinkDetector[])list.toArray(new IHyperlinkDetector[0]);
		return detectors;
	}

	protected List computePropertiesHyperlinkDetectors() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = registry.getExtensionPoint(EXTENSION_POINT);
		IExtension[] extensions = extensionPoint.getExtensions();
		ArrayList results = new ArrayList();
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement[] elements = extensions[i].getConfigurationElements();
			for (int j = 0; j < elements.length; j++) {
				try {
					Object detector = elements[j].createExecutableExtension("class"); //$NON-NLS-1$
					if (detector instanceof jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.editors.detector.IHyperlinkDetector) {
						((jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.editors.detector.IHyperlinkDetector)detector).setTextEditor(editor);
						results.add(detector);
					}
				} catch(CoreException e) {
					IStatus status = new Status(IStatus.ERROR, PropertiesEditorPlugin.PLUGIN_ID, IStatus.OK, e.getMessage(), e);
					ILog log = PropertiesEditorPlugin.getDefault().getLog();
					log.log(status);
				}
			}
		}
		
		return results;
	}
	
}