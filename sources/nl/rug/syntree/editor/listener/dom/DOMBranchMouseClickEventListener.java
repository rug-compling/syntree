/* DOMBranchMouseClickEventListener.java */

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

import nl.rug.syntree.editor.TreeEditorBranchJPopupMenu;
import nl.rug.syntree.editor.TreeEditorBridge;
import nl.rug.syntree.editor.TreeEditorJSVGCanvas;
import nl.rug.syntree.editor.manager.BranchSelectionManager;
import nl.rug.syntree.editor.manager.MdomSelectionManager;
import nl.rug.syntree.editor.manager.NodeSelectionManager;
import nl.rug.syntree.tree.component.BranchElement;
import nl.rug.syntree.tree.component.BranchRectElement;
import nl.rug.syntree.tree.component.NodeElement;

import org.apache.batik.bridge.UpdateManager;
import org.apache.batik.dom.events.DOMMouseEvent;
import org.apache.batik.util.RunnableQueue;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;

/**
 * This class represents a document object model mouse click event listener for
 * branch elements.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class DOMBranchMouseClickEventListener implements EventListener {
	/**
	 * Tree editor bridge
	 */
	protected TreeEditorBridge treeEditorBridge;

	/**
	 * Branch selection manager
	 */
	protected BranchSelectionManager branchSelectionManager;

	/**
	 * Mdom selection manager
	 */
	protected MdomSelectionManager mdomSelectionManager;

	/**
	 * Node selection manager
	 */
	protected NodeSelectionManager nodeSelectionManager;

	/**
	 * Branch element
	 */
	protected BranchElement branchElement;

	/**
	 * Node element
	 */
	protected NodeElement nodeElement;

	/**
	 * Branch rectangle element
	 */
	protected BranchRectElement branchRectElement;

	/**
	 * Constructs a new dom click event listener for branch elements.
	 * 
	 * @param treeEditorBridge
	 *            tree editor bridge
	 * @param nodeElement
	 *            node element
	 */
	public DOMBranchMouseClickEventListener(TreeEditorBridge treeEditorBridge,
			NodeElement nodeElement) {
		this.treeEditorBridge = treeEditorBridge;
		this.branchSelectionManager = treeEditorBridge
				.getBranchSelectionManager();
		this.mdomSelectionManager = treeEditorBridge.getMdomSelectionManager();
		this.nodeSelectionManager = treeEditorBridge.getNodeSelectionManager();
		this.nodeElement = nodeElement;
		this.branchElement = nodeElement.getBranchElement();
		this.branchRectElement = nodeElement.getBranchElement()
				.getBranchRectElement();
	}

	/**
	 * Process the specified dom event.
	 * 
	 * @param e
	 *            event
	 */
	public void handleEvent(Event e) {
		final DOMMouseEvent dme = (DOMMouseEvent) e;
		UpdateManager um = treeEditorBridge.getUpdateManager();
		RunnableQueue rq = um.getUpdateRunnableQueue();
		if (um != null && rq != null) {
			rq.invokeLater(new Runnable() {
				public void run() {
					if (dme.getButton() == 0 && dme.getDetail() == 1)
						singleLeftClick(dme);
					if (dme.getButton() == 2 && dme.getDetail() == 1)
						singleRightClick(dme);
				}
			});
		}
	}

	/**
	 * Process a single left click dom mouse event.
	 * 
	 * @param dme
	 *            dom mouse event
	 */
	public void singleLeftClick(DOMMouseEvent dme) {
		if (!dme.getCtrlKey() && branchSelectionManager.getSelectionSize() >= 0) {
			branchSelectionManager.clear();
			mdomSelectionManager.clear();
			nodeSelectionManager.clear();
		}
		if ((!dme.getCtrlKey() && branchSelectionManager.getSelectionSize() == 0)
				|| (dme.getCtrlKey() && branchSelectionManager
						.getSelectionSize() >= 0))
			if (!branchSelectionManager.isSelected(branchElement))
				branchSelectionManager.add(branchElement);
			else
				branchSelectionManager.remove(branchElement);
		treeEditorBridge.updateSelectionHighlighting();
	}

	/**
	 * Process a single right click dom mouse event.
	 * 
	 * @param dme
	 *            dom mouse event
	 */
	public void singleRightClick(DOMMouseEvent dme) {
		if (!dme.getCtrlKey()) {
			TreeEditorJSVGCanvas treeEditorJSVGCanvas;
			treeEditorJSVGCanvas = treeEditorBridge.getUIJInternalFrame()
					.getTreeEditorJSVGScrollPane().getTreeEditorJSVGCanvas();

			// branch popup menu
			TreeEditorBranchJPopupMenu treeEditorBranchJPopupMenu;
			treeEditorBranchJPopupMenu = new TreeEditorBranchJPopupMenu(
					treeEditorBridge, nodeElement);
			treeEditorBranchJPopupMenu.show(treeEditorJSVGCanvas, dme
					.getClientX(), dme.getClientY());
		}
	}
}