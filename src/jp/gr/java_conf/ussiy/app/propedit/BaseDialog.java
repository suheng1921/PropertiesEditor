package jp.gr.java_conf.ussiy.app.propedit;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;

import javax.swing.JDialog;

public class BaseDialog extends JDialog {

	public BaseDialog(Frame frame, String title, boolean modal) {

		super(frame, title, modal);
	}

	private void setInitScreen() {

		// The window is arranged in the center.
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = this.getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	}

	public void setVisible(boolean b) {

		// Setup the display position of a dialog
		setInitScreen();
		super.setVisible(b);
	}
}