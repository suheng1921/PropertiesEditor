/*****************************************************
 *
 *   @author  Sou Miyazaki
 *
 ****************************************************/
package jp.gr.java_conf.ussiy.app.propedit.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import jp.gr.java_conf.ussiy.io.LockableFileOutputStream;
import jp.gr.java_conf.ussiy.util.StringUtil;

/**
 * 
 * @author Sou Miyazaki
 *  
 */
public class FileOpener extends File {

	/**
	 */
	private StringBuffer txt = null;

	/**
	 * 
	 * @param arg0
	 * @since 1.0.0
	 */
	public FileOpener(String filepath) {

		super(filepath);
	}

	/**
	 * 
	 * @param arg0
	 * @since 1.0.0
	 */
	public FileOpener(File file) {

		super(file.getPath());
	}

	/**
	 * 
	 * @param Code
	 * @since 1.0.0
	 */
	public void read(String code) throws IOException {

		byte[] buffer = null;
		BufferedInputStream bis = null;
		ByteArrayOutputStream bin = null;
		int getLength = 0;

		if (this.isFile()) {
			// In the case of a file (nothing is carried out when it is not a
			// file)
			try {
				bis = new BufferedInputStream(new FileInputStream(this));
				bin = new ByteArrayOutputStream();
				buffer = new byte[1024];
				// Reading of a file
				while ((getLength = bis.read(buffer)) != -1) {
					bin.write(buffer, 0, getLength);
				}
				txt = new StringBuffer(StringUtil.removeCarriageReturn(new String(bin.toByteArray(), code)));

				return;
			} catch (IOException e) {
				throw e;
			} finally {
				try {
					if (bis != null) {
						bis.close();
					}
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * 
	 * @param code
	 * @since 1.0.0
	 */
	public void write(String code) throws AlreadyFileLockException, IOException {

		LockableFileOutputStream out = null;
		try {
			out = new LockableFileOutputStream(new FileOutputStream(this));
			if (!out.tryLock()) {
				throw new AlreadyFileLockException(Messages.getString("FileOpener.0")); //$NON-NLS-1$
			}
			out.write(this.getText().getBytes(code));
		} catch (IOException e) {
			throw e;
		} finally {
			if (out != null) {
				out.unlock();
				out.flush();
				out.close();
				out = null;
			}
		}
	}

	/**
	 * 
	 * @since 1.0.0
	 */
	public String getText() {

		if (txt == null) {
			return null;
		} else {
			return txt.toString();
		}
	}

	/**
	 * 
	 * @param buffer
	 * @since 1.0.0
	 */
	public void setText(String buffer) {

		txt = new StringBuffer();
		if (buffer != null) {
			txt.append(buffer);
		}
	}
}