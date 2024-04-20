package jp.gr.java_conf.ussiy.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import jp.gr.java_conf.ussiy.app.propedit.BaseDialog;

public class JFontChooserDialog extends BaseDialog {

	static ResourceBundle res = ResourceBundle.getBundle("jp.gr.java_conf.ussiy.swing.lang"); //$NON-NLS-1$

	public static final int CANCEL_OPTION = 1;

	public static final int ERROR_OPTION = -1;

	public static final int APPROVE_OPTION = 0;

	private int returnValue = ERROR_OPTION;

	private JPanel panel1 = new JPanel();

	private BorderLayout borderLayout1 = new BorderLayout();

	private JPanel jPanel1 = new JPanel();

	private JPanel jPanel2 = new JPanel();

	private BorderLayout borderLayout2 = new BorderLayout();

	private JPanel jPanel3 = new JPanel();

	private JPanel jPanel4 = new JPanel();

	private JLabel jLabel1 = new JLabel();

	private JComboBox fontNameComboBox = new JExtendedPopupComboBox();

	private JLabel jLabel2 = new JLabel();

	private JComboBox styleComboBox = new JExtendedPopupComboBox();

	private JLabel jLabel3 = new JLabel();

	private JComboBox sizeComboBox = new JExtendedPopupComboBox();

	private JLabel charaColorLabel = new JLabel();

	private JButton selectForegroundButton = new JButton();

	private JPanel jPanel5 = new JPanel();

	private GridBagLayout gridBagLayout1 = new GridBagLayout();

	private Border border1;

	private TitledBorder titledBorder1;

	private BorderLayout borderLayout3 = new BorderLayout();

	private String[] fontNameList;

	private static Font defaultFont = new Font("Dialog", Font.PLAIN, 10); //$NON-NLS-1$

	private static Color defaultForegroundColor = Color.BLACK;

	private static Color defaultBackgroundColor = Color.WHITE;

	private static final String[] fontSizeList = { "7", "8", "9", "10", "11", "12", "14", "16", "18", "20", "22", "24", "26", "28", "36", "48", "72" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$ //$NON-NLS-13$ //$NON-NLS-14$ //$NON-NLS-15$ //$NON-NLS-16$ //$NON-NLS-17$

	private static final int[] fontStyleCodeList = { Font.PLAIN, Font.BOLD, Font.ITALIC, Font.BOLD | Font.ITALIC };

	private JLabel backgroundColorLabel = new JLabel();

	private JButton selectBackgroundButton = new JButton();

	private JPanel buttonPanel = new JPanel();

	private JButton cancelButton = new JButton();

	private JButton setButton = new JButton();

	private FlowLayout flowLayout1 = new FlowLayout();

	JTextField sampleTextField = new JTextField();

	public JFontChooserDialog(Frame frame, boolean modal) {

		this(frame, defaultFont, defaultForegroundColor, defaultBackgroundColor, modal);
	}

	public JFontChooserDialog(Frame frame, Font selectFont, Color selectForegroundColor, Color selectBackgroundColor, boolean modal) {

		super(frame, res.getString("fontchooser_title"), modal); //$NON-NLS-1$
		defaultFont = selectFont;
		defaultForegroundColor = selectForegroundColor;
		defaultBackgroundColor = selectBackgroundColor;
		try {
			jbInit();
			initialize();
			pack();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void jbInit() throws Exception {

		border1 = BorderFactory.createEtchedBorder(Color.white, new Color(142, 142, 142));
		titledBorder1 = new TitledBorder(border1, res.getString("sample_titled_border")); //$NON-NLS-1$
		panel1.setLayout(borderLayout1);
		jPanel2.setLayout(borderLayout2);
		jLabel1.setText(res.getString("font_name")); //$NON-NLS-1$
		jLabel2.setText(res.getString("style_name")); //$NON-NLS-1$
		jLabel3.setText(res.getString("font_size")); //$NON-NLS-1$
		charaColorLabel.setOpaque(true);
		charaColorLabel.setPreferredSize(new Dimension(50, 26));
		charaColorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		charaColorLabel.setText(res.getString("font_color")); //$NON-NLS-1$
		selectForegroundButton.setText(res.getString("selectForegroundButton_text")); //$NON-NLS-1$
		selectForegroundButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {

				selectForegroundButton_actionPerformed(e);
			}
		});
		jPanel1.setLayout(gridBagLayout1);
		jPanel5.setBorder(titledBorder1);
		jPanel5.setLayout(borderLayout3);
		sampleTextField.setText(res.getString("sample_character")); //$NON-NLS-1$
		panel1.setMinimumSize(new Dimension(300, 200));
		jPanel1.setPreferredSize(new Dimension(300, 120));
		fontNameComboBox.setPreferredSize(new Dimension(100, 18));
		fontNameComboBox.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {

				fontNameComboBox_actionPerformed(e);
			}
		});
		styleComboBox.setMinimumSize(new Dimension(23, 18));
		styleComboBox.setPreferredSize(new Dimension(70, 18));
		styleComboBox.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {

				styleComboBox_actionPerformed(e);
			}
		});
		sizeComboBox.setMinimumSize(new Dimension(23, 18));
		sizeComboBox.setPreferredSize(new Dimension(50, 18));
		sizeComboBox.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {

				sizeComboBox_actionPerformed(e);
			}
		});
		backgroundColorLabel.setOpaque(true);
		backgroundColorLabel.setPreferredSize(new Dimension(50, 26));
		backgroundColorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		backgroundColorLabel.setHorizontalTextPosition(SwingConstants.TRAILING);
		backgroundColorLabel.setText(res.getString("backgroundColorLabel_bgcolor")); //$NON-NLS-1$
		selectBackgroundButton.setText(res.getString("selectBackgroundButton_text")); //$NON-NLS-1$
		selectBackgroundButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {

				selectBackgroundButton_actionPerformed(e);
			}
		});
		cancelButton.setText(res.getString("cancelButton_text")); //$NON-NLS-1$
		cancelButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {

				cancelButton_actionPerformed(e);
			}
		});
		setButton.setText(res.getString("setButton_text")); //$NON-NLS-1$
		setButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {

				setButton_actionPerformed(e);
			}
		});
		buttonPanel.setLayout(flowLayout1);
		flowLayout1.setAlignment(FlowLayout.RIGHT);
		flowLayout1.setHgap(5);
		jPanel4.setBorder(BorderFactory.createEtchedBorder());
		jPanel3.setBorder(null);
		getContentPane().add(panel1);
		panel1.add(jPanel1, BorderLayout.CENTER);
		jPanel1.add(jPanel5, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 4, 4, 4), 390, 219));
		//    jPanel5.add(sampleTextArea, BorderLayout.CENTER);
		jPanel5.add(sampleTextField, BorderLayout.CENTER);
		panel1.add(jPanel2, BorderLayout.NORTH);
		jPanel2.add(jPanel3, BorderLayout.SOUTH);
		jPanel3.add(charaColorLabel, null);
		jPanel3.add(selectForegroundButton, null);
		jPanel3.add(backgroundColorLabel, null);
		jPanel3.add(selectBackgroundButton, null);
		jPanel2.add(jPanel4, BorderLayout.NORTH);
		jPanel4.add(jLabel1, null);
		jPanel4.add(fontNameComboBox, null);
		jPanel4.add(jLabel2, null);
		jPanel4.add(styleComboBox, null);
		jPanel4.add(jLabel3, null);
		jPanel4.add(sizeComboBox, null);
		panel1.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.add(setButton, null);
		buttonPanel.add(cancelButton, null);
	}

	private String getFontStyleStringByFontStyleCode(int style) {

		switch (style) {
			case Font.PLAIN:
				return "Plain"; //$NON-NLS-1$
			case Font.BOLD:
				return "Bold"; //$NON-NLS-1$
			case Font.ITALIC:
				return "Italic"; //$NON-NLS-1$
			case Font.BOLD | Font.ITALIC:
				return "Bold Italic"; //$NON-NLS-1$
		}
		return null;
	}

	private int getFontStyleCodeByFontStyleString(String style) {

		if (style == null) {
			return -1;
		} else if (style.equals("Plain")) { //$NON-NLS-1$
			return Font.PLAIN;
		} else if (style.equals("Bold")) { //$NON-NLS-1$
			return Font.BOLD;
		} else if (style.equals("Italic")) { //$NON-NLS-1$
			return Font.ITALIC;
		} else if (style.equals("Bold Italic")) { //$NON-NLS-1$
			return Font.BOLD | Font.ITALIC;
		}
		return -1;
	}

	private void initialize() {

		// フォント名の初期化
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		fontNameList = env.getAvailableFontFamilyNames();
		for (int i = 0; i < fontNameList.length; i++) {
			fontNameComboBox.addItem(fontNameList[i]);
		}
		// スタイルの初期化
		for (int i = 0; i < fontStyleCodeList.length; i++) {
			styleComboBox.addItem(this.getFontStyleStringByFontStyleCode(fontStyleCodeList[i]));
		}
		// サイズの初期化
		for (int i = 0; i < fontSizeList.length; i++) {
			sizeComboBox.addItem(fontSizeList[i]);
		}
		fontNameComboBox.setSelectedItem(defaultFont.getFamily());
		styleComboBox.setSelectedItem(this.getFontStyleStringByFontStyleCode(defaultFont.getStyle()));
		sizeComboBox.setSelectedItem(Integer.toString(defaultFont.getSize()));
		sampleTextField.setFont(defaultFont);
		sampleTextField.setForeground(defaultForegroundColor);
		sampleTextField.setBackground(defaultBackgroundColor);
		backgroundColorLabel.setBackground(defaultBackgroundColor);
		charaColorLabel.setForeground(defaultForegroundColor);
	}

	public Font getSelectedFont() {

		String fontName = (String) fontNameComboBox.getSelectedItem();
		int fontStyle = this.getFontStyleCodeByFontStyleString((String) styleComboBox.getSelectedItem());
		int fontSize = Integer.parseInt((String) sizeComboBox.getSelectedItem());
		return new Font(fontName, fontStyle, fontSize);
	}

	private void fontNameComboBox_actionPerformed(ActionEvent e) {

		Font f = sampleTextField.getFont();
		Font newFont = new Font((String) fontNameComboBox.getSelectedItem(), f.getStyle(), f.getSize());
		sampleTextField.setFont(newFont);
	}

	private void styleComboBox_actionPerformed(ActionEvent e) {

		Font f = sampleTextField.getFont();
		Font newFont = new Font(f.getFontName(), getFontStyleCodeByFontStyleString((String) styleComboBox.getSelectedItem()), f.getSize());
		sampleTextField.setFont(newFont);
	}

	private void sizeComboBox_actionPerformed(ActionEvent e) {

		Font f = sampleTextField.getFont();
		Font newFont = new Font(f.getFontName(), f.getStyle(), Integer.parseInt((String) sizeComboBox.getSelectedItem()));
		sampleTextField.setFont(newFont);
	}

	private void selectForegroundButton_actionPerformed(ActionEvent e) {

		Color selectedColor = JColorChooser.showDialog(this, res.getString("colorchooser_title"), defaultForegroundColor); //$NON-NLS-1$
		if (selectedColor == null) {
			return;
		}
		defaultForegroundColor = selectedColor;
		charaColorLabel.setForeground(defaultForegroundColor);
		sampleTextField.setForeground(defaultForegroundColor);
	}

	private void selectBackgroundButton_actionPerformed(ActionEvent e) {

		Color selectedColor = JColorChooser.showDialog(this, res.getString("colorchooser_title"), defaultBackgroundColor); //$NON-NLS-1$
		if (selectedColor == null) {
			return;
		}
		defaultBackgroundColor = selectedColor;
		backgroundColorLabel.setBackground(defaultBackgroundColor);
		sampleTextField.setBackground(defaultBackgroundColor);
	}

	public Color getForgroundColor() {

		return defaultForegroundColor;
	}

	public Color getBackgroundColor() {

		return defaultBackgroundColor;
	}

	private void cancelButton_actionPerformed(ActionEvent e) {

		returnValue = CANCEL_OPTION;
		this.dispose();
	}

	private void setButton_actionPerformed(ActionEvent e) {

		returnValue = APPROVE_OPTION;
		this.dispose();
	}

	public int getReturnValue() {

		return returnValue;
	}
}