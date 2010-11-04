/* DOMNodeMouseClickEventListener.java */

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
import nl.rug.syntree.editor.TreeEditorJSVGCanvas;
import nl.rug.syntree.editor.TreeEditorNodeJPopupMenu;
import nl.rug.syntree.editor.manager.BranchSelectionManager;
import nl.rug.syntree.editor.manager.MdomSelectionManager;
import nl.rug.syntree.editor.manager.NodeSelectionManager;
import nl.rug.syntree.tree.component.NodeElement;
import nl.rug.syntree.tree.component.NodeRectElement;

import org.apache.batik.bridge.UpdateManager;
import org.apache.batik.dom.events.DOMMouseEvent;
import org.apache.batik.util.RunnableQueue;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;

/**
 * This class represents a document object model mouse click event listener for
 * node elements.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class DOMNodeMouseClickEventListener implements EventListener {
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
	 * Node element
	 */
	protected NodeElement nodeElement;

	/**
	 * Node rectangle element
	 */
	protected NodeRectElement nodeRectElement;

	/**
	 * Constructs a new dom mouse click event listener.
	 * 
	 * @param treeEditorBridge
	 *            tree editor bridge
	 * @param nodeElement
	 *            node element
	 */
	public DOMNodeMouseClickEventListener(TreeEditorBridge treeEditorBridge,
			NodeElement nodeElement) {
		this.treeEditorBridge = treeEditorBridge;
		this.branchSelectionManager = treeEditorBridge
				.getBranchSelectionManager();
		this.mdomSelectionManager = treeEditorBridge.getMdomSelectionManager();
		this.nodeSelectionManager = treeEditorBridge.getNodeSelectionManager();
		this.nodeElement = nodeElement;
		this.nodeRectElement = nodeElement.getNodeRectElement();
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
					if (dme.getButton() == 0 && dme.getDetail() == 2)
						doubleLeftClick(dme);
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
	protected void singleLeftClick(DOMMouseEvent dme) {
		nodeSelectionManager.setProminentNode(nodeElement);
		if (!dme.getShiftKey()) {
			if (!dme.getCtrlKey()
					&& nodeSelectionManager.getSelectionSize() >= 0) {
				branchSelectionManager.clear();
				mdomSelectionManager.clear();
				nodeSelectionManager.clear();
			}
			if ((!dme.getCtrlKey() && nodeSelectionManager.getSelectionSize() == 0)
					|| (dme.getCtrlKey() && nodeSelectionManager
							.getSelectionSize() >= 0))
				if (!nodeSelectionManager.isSelected(nodeElement))
					nodeSelectionManager.add(nodeElement);
				else
					nodeSelectionManager.remove(nodeElement);
			treeEditorBridge.updateSelectionHighlighting();
		}
		if (dme.getShiftKey()) {
			treeEditorBridge.getUIJInternalFrame()
					.getTreeEditorJSVGScrollPane().getCreateBranchInteractor()
					.start();
		}
	}

	/**
	 * Process a single right click dom mouse event.
	 * 
	 * @param dme
	 *            dom mouse event
	 */
	protected void singleRightClick(DOMMouseEvent dme) {
		if (!dme.getCtrlKey()) {
			TreeEditorJSVGCanvas treeEditorJSVGCanvas;
			treeEditorJSVGCanvas = treeEditorBridge.getUIJInternalFrame()
					.getTreeEditorJSVGScrollPane().getTreeEditorJSVGCanvas();

			// node popup menu
			TreeEditorNodeJPopupMenu treeEditorNodeJPopupMenu;
			treeEditorNodeJPopupMenu = new TreeEditorNodeJPopupMenu(
					treeEditorBridge, nodeElement);
			treeEditorNodeJPopupMenu.show(treeEditorJSVGCanvas, dme
					.getClientX(), dme.getClientY());
		}
	}

	/**
	 * Process a double left click dom mouse event.
	 * 
	 * @param dme
	 *            dom mouse event
	 */
	protected void doubleLeftClick(DOMMouseEvent dme) {
		nodeSelectionManager.setProminentNode(nodeElement);
		treeEditorBridge.getUIJInternalFrame().getTreeEditorJSVGScrollPane()
				.getTextInteractor().start();
	}
}