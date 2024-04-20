package jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.action;

import java.util.Iterator;

import jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.editors.PropertiesEditor;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.projection.ProjectionAnnotationModel;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;

public class ExpandAllFoldingAction implements IEditorActionDelegate {
	private PropertiesEditor textEditor;

	/**
	 * @see org.eclipse.ui.IEditorActionDelegate#setActiveEditor(org.eclipse.jface.action.IAction, org.eclipse.ui.IEditorPart)
	 */
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		if (targetEditor instanceof PropertiesEditor) {
			textEditor = (PropertiesEditor)targetEditor;
		}
	}

	/**
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		if (textEditor == null) {
			return;
		}
		
		ProjectionAnnotationModel model = textEditor.getAnnotationModel();
		Iterator annotationIterator = model.getAnnotationIterator();
		while(annotationIterator.hasNext()) {
			model.expand((Annotation)annotationIterator.next());
		}
	}

	/**
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

}