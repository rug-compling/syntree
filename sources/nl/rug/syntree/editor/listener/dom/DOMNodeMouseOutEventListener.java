/* DOMNodeMouseOutEventListener.java */

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
import nl.rug.syntree.editor.manager.NodeSelectionManager;
import nl.rug.syntree.tree.component.NodeElement;
import nl.rug.syntree.tree.component.NodeRectElement;

import org.apache.batik.bridge.UpdateManager;
import org.apache.batik.util.RunnableQueue;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;

/**
 * This class represents a document object mouse out event listener for node
 * elements.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class DOMNodeMouseOutEventListener implements EventListener {
	/**
	 * Tree editor bridge
	 */
	protected TreeEditorBridge treeEditorBridge;

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
	 * Constructs a new dom mouse out event listener.
	 * 
	 * @param treeEditorBridge
	 *            tree editor bridge
	 * @param nodeElement
	 *            node element
	 */
	public DOMNodeMouseOutEventListener(TreeEditorBridge treeEditorBridge,
			NodeElement nodeElement) {
		this.treeEditorBridge = treeEditorBridge;
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
		UpdateManager um = treeEditorBridge.getUpdateManager();
		RunnableQueue rq = um.getUpdateRunnableQueue();
		if (um != null && rq != null) {
			rq.invokeLater(new Runnable() {
				public void run() {					
					if (nodeSelectionManager.isSelected(nodeElement))
						nodeRectElement
								.setHighlightMode(NodeRectElement.SELECT);
					else
						nodeRectElement
								.setHighlightMode(NodeRectElement.STANDARD);
				}
			});
		}
	}
}