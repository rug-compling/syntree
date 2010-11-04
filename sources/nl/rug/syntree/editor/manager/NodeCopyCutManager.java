/* NodeCopyCutManager.java */

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

package nl.rug.syntree.editor.manager;

import nl.rug.syntree.editor.TreeEditorBridge;
import nl.rug.syntree.tree.component.NodeElement;

// import java.util.Vector;

/**
 * This class represents a Copy/Cut manager.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class NodeCopyCutManager {
	/**
	 * Tree editor bridge
	 */
	protected TreeEditorBridge treeEditorBridge;

	/**
	 * The prominent node
	 */
	protected NodeElement prominentNode;

	/**
	 * Constructs a new Copy/Cut manager.
	 */
	public NodeCopyCutManager(TreeEditorBridge treeEditorBridge) {
		this.treeEditorBridge = treeEditorBridge;
	}

	/**
	 * Copies the specified node.
	 * 
	 * @param nodeElement
	 *            node element to copy
	 */
	public void copy(NodeElement nodeElement) {
		setProminentNode(nodeElement);
		treeEditorBridge.getNodeSelectionManager().setProminentNode(nodeElement);
		treeEditorBridge.getNodeSelectionManager().selectSubtree();
		treeEditorBridge.updateSelectionHighlighting();
	}

	/**
	 * Cuts the specified node.
	 * 
	 * @param nodeElement
	 *            node element to cut
	 */
	public void cut(NodeElement nodeElement) {
		setProminentNode(nodeElement);
		treeEditorBridge.removeDaughter(nodeElement.getMother(), nodeElement);
	}

	/**
	 * Pastes the prominent node.
	 * 
	 * @param nodeElement
	 *            node element to paste the prominent node to
	 */
	public void paste(NodeElement nodeElement) {
		if(canPaste()) {
			treeEditorBridge.getNodeSelectionManager().setProminentNode(nodeElement);
			treeEditorBridge.getUIJInternalFrame().getTreeEditorJSVGScrollPane().getMergeBranchInteractor().start();			
		}
	}

	/**
	 * Returns whether there is something to paste
	 * 
	 * @return boolean
	 */
	public boolean canPaste() {
		return (prominentNode != null);
	}

	/**
	 * Sets the prominent node.
	 * 
	 * @param prominentNode
	 *            node to make prominent
	 */
	public void setProminentNode(NodeElement prominentNode) {
		this.prominentNode = prominentNode;
	}

	/**
	 * Returns the prominent node.
	 * 
	 * @return prominent node
	 */
	public NodeElement getProminentNode() {
		return prominentNode;
	}
}