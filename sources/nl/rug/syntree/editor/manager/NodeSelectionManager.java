/* NodeSelectionManager.java */

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

import java.util.Vector;

import nl.rug.syntree.editor.TreeEditorBridge;
import nl.rug.syntree.tree.component.NodeElement;

/**
 * This class represents a node selection manager.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class NodeSelectionManager {
	/**
	 * Tree editor bridge
	 */
	protected TreeEditorBridge treeEditorBridge;

	/**
	 * Vector containing the selected node elements
	 */
	protected Vector<NodeElement> selectionVector;

	/**
	 * Branch selection manager
	 */
	protected BranchSelectionManager branchSelectionManager;

	/**
	 * Prominent node
	 */
	protected NodeElement prominentNode;

	/**
	 * Constructs a new node selection manager.
	 * 
	 * @param treeEditorBridge
	 *            tree editor bridge
	 */
	public NodeSelectionManager(TreeEditorBridge treeEditorBridge) {
		this.treeEditorBridge = treeEditorBridge;
		selectionVector = new Vector<NodeElement>();
		this.branchSelectionManager = treeEditorBridge
				.getBranchSelectionManager();
	}

	/**
	 * Adds a new node element to the selection vector.
	 * 
	 * @param nodeElement
	 *            node element
	 */
	public void add(NodeElement nodeElement) {
		if (!isSelected(nodeElement))
			selectionVector.add(nodeElement);
	}

	/**
	 * Removes the specified node element from the selection vector.
	 * 
	 * @param nodeElement
	 *            node element
	 */
	public void remove(NodeElement nodeElement) {
		selectionVector.remove(nodeElement);
	}

	/**
	 * Clears the selection vector.
	 */
	public void clear() {
		selectionVector.clear();
	}

	/**
	 * Verifies whether the specified node element is on the selection vector.
	 * 
	 * @param nodeElement
	 *            node element
	 * 
	 * @return boolean
	 */
	public boolean isSelected(NodeElement nodeElement) {
		return selectionVector.contains(nodeElement);
	}

	/**
	 * Returns the size of the selection vector.
	 * 
	 * @return selection vector size
	 */
	public int getSelectionSize() {
		return selectionVector.size();
	}

	/**
	 * Selects a subtree with the prominent node as root node.
	 */
	public void selectSubtree() {
		branchSelectionManager.clear();
		clear();
		add(prominentNode);
		Vector<NodeElement> daughterVector = prominentNode.getDaughterVector();
		for (int i = 0; i < daughterVector.size(); i++)
			selectSubtree(daughterVector.elementAt(i));
	}

	/**
	 * Recursively selected the subtree with the specified node element as root
	 * node.
	 * 
	 * @param nodeElement
	 *            node element
	 */
	protected void selectSubtree(NodeElement nodeElement) {
		Vector<NodeElement> daughterVector = nodeElement.getDaughterVector();
		for (int i = 0; i < daughterVector.size(); i++)
			selectSubtree(daughterVector.elementAt(i));
		add(nodeElement);
		branchSelectionManager.add(nodeElement.getBranchElement());
	}

	/**
	 * Returns the prominent node element.
	 * 
	 * @return prominent node element
	 */
	public NodeElement getProminentNode() {
		return prominentNode;
	}

	/**
	 * Sets the prominent node element to the specified node element.
	 * 
	 * @param nodeElement
	 *            node element
	 */
	public void setProminentNode(NodeElement nodeElement) {
		this.prominentNode = nodeElement;
	}

	/**
	 * Returns the tree editor bridge.
	 * 
	 * @return tree editor bridge
	 */
	public TreeEditorBridge getTreeEditorBridge() {
		return treeEditorBridge;
	}

	/**
	 * Returns the selection vector.
	 * 
	 * @return selection vector
	 */
	public Vector<NodeElement> getSelectionVector() {
		return selectionVector;
	}
}