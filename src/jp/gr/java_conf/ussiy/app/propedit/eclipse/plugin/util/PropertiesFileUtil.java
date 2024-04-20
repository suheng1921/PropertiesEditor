package jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.util;

import java.util.ArrayList;
import java.util.List;

import jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.PropertiesEditorPlugin;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class PropertiesFileUtil {

	public static IFile[] findFileExt(IContainer container, IPath excludePath, String extension) {
		IResource[] list = null;
		try {
			list = container.members();
		} catch (CoreException e) {
			IStatus status = new Status(IStatus.ERROR, PropertiesEditorPlugin.PLUGIN_ID, IStatus.OK, e.getMessage(), e);
			ILog log = PropertiesEditorPlugin.getDefault().getLog();
			log.log(status);
			return new IFile[0];
		}
		if (list == null) {
			return new IFile[0];
		}
		List fileList = new ArrayList();
		for (int i = 0; i < list.length; i++) {
			if (list[i] instanceof IFile) {
				if (extension.equals(list[i].getFileExtension())) {
					fileList.add((IFile)list[i]);
				}
			} else 	if (list[i] instanceof IContainer) {
				if (excludePath != null && excludePath.matchingFirstSegments(list[i].getFullPath()) == excludePath.segmentCount()) {
					continue;
				}
				IFile[] files = findFileExt((IContainer)list[i], excludePath, extension);
				for (int j = 0; j < files.length; j++) {
					fileList.add(files[j]);
				}
			}
		}
		
		return (IFile[])fileList.toArray(new IFile[0]);
	}
}
