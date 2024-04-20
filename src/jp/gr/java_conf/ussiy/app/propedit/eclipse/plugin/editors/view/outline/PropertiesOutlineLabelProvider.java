package jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.editors.view.outline;

import java.net.MalformedURLException;
import java.net.URL;

import jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.PropertiesEditorPlugin;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class PropertiesOutlineLabelProvider extends LabelProvider {
	protected static Image markerImage;

	/**
	 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
	 */
	public Image getImage(Object element) {
		if (markerImage != null && !markerImage.isDisposed()) {
			return markerImage;
		}
		URL url = PropertiesEditorPlugin.getDefault().getBundle().getEntry("/"); //$NON-NLS-1$
		String path = "icons/outlineMarker.gif"; //$NON-NLS-1$
		ImageDescriptor descriptor = null;
		try {
			descriptor = ImageDescriptor.createFromURL(new URL(url, path));
		} catch (MalformedURLException e) {
			descriptor = ImageDescriptor.getMissingImageDescriptor();
		}
		markerImage = descriptor.createImage();
		return markerImage;
	}

	/**
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
	 */
	public void dispose() {
		super.dispose();
		if (markerImage != null) {
			markerImage.dispose();
		}
	}
}
