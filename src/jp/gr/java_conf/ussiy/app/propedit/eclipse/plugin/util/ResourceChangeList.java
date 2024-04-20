/**
 * 
 */
package jp.gr.java_conf.ussiy.app.propedit.eclipse.plugin.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 *
 */
public class ResourceChangeList {
	
	private List list = new ArrayList();

	public void add(ResourceChange rc) {
		boolean addDoneFlg = false;
		Iterator ite = list.iterator();
		List newList = new ArrayList();
		while (ite.hasNext()) {
			ResourceChange rcInList = (ResourceChange)ite.next();
			if (rc.getType() == ResourceChange.PROPERTIES_CHANGE) {
				if (rcInList.getProject().getFullPath().toPortableString().equals(rc.getProject().getFullPath().toPortableString())) {
					newList.add(rcInList);
					addDoneFlg = true;
					continue;
				}
			} else {
				if (rcInList.getProject().getFullPath().toPortableString().equals(rc.getProject().getFullPath().toPortableString())) {
					if (rcInList.getType() == ResourceChange.PROPERTIES_CHANGE) {
						newList.remove(rcInList);
						newList.add(rc);
						addDoneFlg = true;
						continue;
					} else {
						newList.add(rcInList);
						addDoneFlg = true;
						continue;
					}
				}
			}
			newList.add(rcInList);
		}
		if (!addDoneFlg) newList.add(rc);
		list = newList;
	}

	public void addAll(ResourceChangeList list) {
		Iterator ite = list.iterator();
		while (ite.hasNext()) {
			ResourceChange rc = (ResourceChange)ite.next();
			add(rc);
		}
	}
	
	public Iterator iterator() {
		return list.iterator();
	}
	
	public int size() {
		return list.size();
	}

}
