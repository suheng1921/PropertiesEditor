/**
 * 
 */
package jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.util;

import org.eclipse.core.resources.IProject;

/**
 *
 */
public class ResourceChange {

	public static final int PROJECT_CLOSE = 1;
	public static final int PROJECT_OPEN = 2;
	public static final int PROJECT_ADD = 3;
	public static final int PROJECT_DELETE = 4;
	public static final int PROJECT_MOVE_TO = 5;
	public static final int PROJECT_MOVE_FROM = 6;
	
	public static final int PROPERTIES_CHANGE = 15;
	
	private int type = 0;
	
	private IProject project = null;
	
	public ResourceChange(int type, IProject project) {
		this.type = type;
		this.project = project;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @return the resource
	 */
	public IProject getProject() {
		return project;
	}
	
}
