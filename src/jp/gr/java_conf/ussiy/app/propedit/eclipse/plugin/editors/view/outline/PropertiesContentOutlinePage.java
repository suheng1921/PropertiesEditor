package jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.editors.view.outline;

import java.net.MalformedURLException;
import java.net.URL;

import jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.PropertiesEditorPlugin;
import jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.resources.Messages;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

public class PropertiesContentOutlinePage extends ContentOutlinePage {
	protected static String ALPHA_SORT_BTN_CHECK_KEY = "jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.editors.alphaSortCheckKey"; //$NON-NLS-1$
	
	/**
	 * A segment element.
	 */
	protected static class Segment {
		public String name;
		public Position position;

		public Segment(String name, Position position) {
			this.name= name;
			this.position= position;
		}

		public String toString() {
			return name;
		}
	}

	protected Object fInput;
	protected IDocumentProvider fDocumentProvider;
	protected ITextEditor fTextEditor;
	protected PropertiesOutlineContentProvider contentProvider;

	protected static ImageDescriptor sortImageDescriptor;
	
	/**
	 * Creates a content outline page using the given provider and the given editor.
	 */
	public PropertiesContentOutlinePage(IDocumentProvider provider, ITextEditor editor) {
		super();
		fDocumentProvider= provider;
		fTextEditor= editor;
		fDocumentProvider.getDocument(fTextEditor.getEditorInput()).addDocumentListener(getContentProvider());
	}
	
	protected PropertiesOutlineContentProvider getContentProvider() {
		if (contentProvider == null) {
			contentProvider = new PropertiesOutlineContentProvider(this);
		}
		return contentProvider;
	}
	
	/**
	 * Method declared on ContentOutlinePage
	 */
	public void createControl(Composite parent) {

		super.createControl(parent);

		// create tree viewer
		TreeViewer viewer= getTreeViewer();
		viewer.setContentProvider(getContentProvider());
		viewer.setLabelProvider(new PropertiesOutlineLabelProvider());
		viewer.addSelectionChangedListener(this);
		
		if (fInput != null)
			viewer.setInput(fInput);
		
		// create toolbar action
		PropertiesEditorPlugin.getDefault().getPluginPreferences().setDefault(ALPHA_SORT_BTN_CHECK_KEY, false);
		IToolBarManager toolbar = getSite().getActionBars().getToolBarManager();
		String label = Messages.getString("eclipse.contentoutline.sort.label"); //$NON-NLS-1$
		Action action = new Action(label, Action.AS_CHECK_BOX) {
			public void run() {
				PropertiesEditorPlugin.getDefault().getPluginPreferences().setValue(ALPHA_SORT_BTN_CHECK_KEY, isChecked());
				if (isChecked()) {
					TreeViewer v = getTreeViewer();
					v.setSorter(new ViewerSorter());
					v.setInput(v.getInput());
				} else {
					getTreeViewer().setSorter(null);
					redraw();
				}
			}
		};
		action.setToolTipText(action.getText());
		if (sortImageDescriptor == null) {
			URL url = PropertiesEditorPlugin.getDefault().getBundle().getEntry("/"); //$NON-NLS-1$
			String path = "icons/alphab_sort_co.gif"; //$NON-NLS-1$
			try {
				sortImageDescriptor = ImageDescriptor.createFromURL(new URL(url, path));
			} catch (MalformedURLException e) {
				sortImageDescriptor = ImageDescriptor.getMissingImageDescriptor();
			}
		}
		action.setImageDescriptor(sortImageDescriptor);
		toolbar.add(action);

		action.setChecked(PropertiesEditorPlugin.getDefault().getPluginPreferences().getBoolean(ALPHA_SORT_BTN_CHECK_KEY));
		action.run();
	}
	
	/* (non-Javadoc)
	 * Method declared on ContentOutlinePage
	 */
	public void selectionChanged(SelectionChangedEvent event) {

		super.selectionChanged(event);

		ISelection selection= event.getSelection();
		if (selection.isEmpty())
			fTextEditor.resetHighlightRange();
		else {
			Segment segment= (Segment) ((IStructuredSelection) selection).getFirstElement();
			int start= segment.position.getOffset();
			int length= segment.position.getLength();
			try {
				fTextEditor.setHighlightRange(start, length, true);
			} catch (IllegalArgumentException x) {
				fTextEditor.resetHighlightRange();
			}
		}
	}
	
	/**
	 * Sets the input of the outline page
	 */
	public void setInput(Object input) {
		fInput= input;
		update();
	}
	
	/**
	 * Updates the outline page.
	 */
	public void update() {
		TreeViewer viewer= getTreeViewer();
		if (viewer != null) {
			Control control= viewer.getControl();
			if (control != null && !control.isDisposed()) {
				control.setRedraw(false);
				viewer.setInput(fInput);
				viewer.expandAll();
				control.setRedraw(true);
			}
		}
	}

	/**
	 * Redraw the outline page.
	 */
	public void redraw() {
		TreeViewer viewer= getTreeViewer();

		if (viewer != null) {
			Control control= viewer.getControl();
			if (control != null && !control.isDisposed()) {
				control.setRedraw(false);
				viewer.expandAll();
				control.setRedraw(true);
			}
		}
	}

	/**
	 * @see org.eclipse.ui.part.IPage#dispose()
	 */
	public void dispose() {
		super.dispose();
	}
}
