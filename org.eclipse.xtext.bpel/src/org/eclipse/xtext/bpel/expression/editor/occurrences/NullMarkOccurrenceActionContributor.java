/**
 * 
 */
package org.eclipse.xtext.bpel.expression.editor.occurrences;

import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.occurrences.MarkOccurrenceActionContributor;

/**
 * Avoids to contribute actions (due to a bug in the bpel editor)
 * 
 * @author bettini
 *
 */
public class NullMarkOccurrenceActionContributor extends MarkOccurrenceActionContributor {

	@Override
	public void contributeActions(XtextEditor editor) {
		// does nothing since it would not work inside a BpelEditor
		// due to a bug in the BpelEditor?
		// see this code in org.eclipse.bpel.common.ui.composite.CompositeEditorActionBars
		/*
		@Override
		public IToolBarManager getToolBarManager() {
			if (toolBarMgr == null) {
				// TODO: The code below does not work anymore in Eclipse 3.2M5.
				// We cannot call this API anymore because we get an exception.
				// See WWinActionBars.getToolBarManager()
	//			toolBarMgr = createSubToolBarManager(getParent().getToolBarManager());
				toolBarMgr = createSubToolBarManager(null);
				toolBarMgr.setVisible(getActive());
			}
			return toolBarMgr;
		}
		 */
	}

}
