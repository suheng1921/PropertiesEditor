package jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.checker;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.PropertiesEditorPlugin;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.PlatformUI;

/**
 * @author Administrator
 * 
 * この生成されたコメントの挿入されるテンプレートを変更するため ウィンドウ > 設定 > Java > コード生成 > コードとコメント
 */
public class CheckAndMarkResourceVisitor implements IResourceVisitor {

	private CheckAndMarkDuplicateKey camd;

	public CheckAndMarkResourceVisitor(CheckAndMarkDuplicateKey camd) {

		this.camd = camd;
	}

	public boolean visit(IResource resource) throws CoreException {

		if (resource.getFileExtension() != null && resource.getFileExtension().equals("properties")) { //$NON-NLS-1$
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			try {
				BufferedInputStream in = new BufferedInputStream(((IFile) resource).getContents());
				byte[] tmp = new byte[10240];
				int readCnt = 0;
				while ((readCnt = in.read(tmp)) != -1) {
					out.write(tmp, 0, readCnt);
				}
			} catch (IOException e) {
				IStatus status = new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID, IStatus.OK, e.getMessage(), e);
				ILog log = PropertiesEditorPlugin.getDefault().getLog();
				log.log(status);
				throw new CoreException(status);
			}
			camd.checkAndMarkDuplicateKeyInString(out.toString(), resource);
		}

		return true;
	}

}