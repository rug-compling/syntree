/* DOMMdomMouseOutEventListener.java */

/*
 Syntree - Syntax visualization and annotation software
 Copyright (C) 2009, 2010  Harm Brouwer

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package nl.rug.syntree.editor.listener.dom;

import nl.rug.syntree.editor.TreeEditorBridge;
import nl.rug.syntree.editor.manager.MdomSelectionManager;
import nl.rug.syntree.tree.component.MdomBranchBackgroundPathElement;
import nl.rug.syntree.tree.component.MdomBranchElement;

import org.apache.batik.bridge.UpdateManager;
import org.apache.batik.util.RunnableQueue;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;

/**
 * This class represents a document object model mouse out event listener for
 * multidominance branch elements.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class DOMMdomMouseOutEventListener implements EventListener {
	/**
	 * Tree editor bridge
	 */
	protected TreeEditorBridge treeEditorBridge;

	/**
	 * Mdom selection manager
	 */
	protected MdomSelectionManager mdomSelectionManager;

	/**
	 * Mdom branch element
	 */
	protected MdomBranchElement mdomBranchElement;

	/**
	 * Mdom branch background path element
	 */
	protected MdomBranchBackgroundPathElement mdomBranchBackgroundPathElement;

	/**
	 * Constructs a new mouse out event listener for multidominance branch
	 * elements.
	 * 
	 * @param treeEditorBridge
	 *            tree editor bridge
	 * @param mdomBranchElement
	 *            mdom branch element
	 */
	public DOMMdomMouseOutEventListener(TreeEditorBridge treeEditorBridge,
			MdomBranchElement mdomBranchElement) {
		this.treeEditorBridge = treeEditorBridge;
		this.mdomBranchElement = mdomBranchElement;
		this.mdomSelectionManager = treeEditorBridge.getMdomSelectionManager();
		this.mdomBranchBackgroundPathElement = mdomBranchElement
				.getMdomBranchBackgroundPathElement();
	}

	/**
	 * Process the specified dom event.
	 * 
	 * @param e
	 *            event
	 */
	public void handleEvent(Event e) {
		UpdateManager um = treeEditorBridge.getUpdateManager();
		RunnableQueue rq = um.getUpdateRunnableQueue();
		if (um != null && rq != null) {
			rq.invokeLater(new Runnable() {
				public void run() {
					if (mdomSelectionManager.isSelected(mdomBranchElement))
						mdomBranchBackgroundPathElement
								.setHighlightMode(MdomBranchBackgroundPathElement.SELECT);
					else
						mdomBranchBackgroundPathElement
								.setHighlightMode(MdomBranchBackgroundPathElement.STANDARD);
				}
			});
		}
	}
}