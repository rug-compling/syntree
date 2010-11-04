/* TreeElement.java */

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

package nl.rug.syntree.tree.component;

import java.util.Vector;

import nl.rug.syntree.tree.TreeRepresentation;

import org.apache.batik.dom.AbstractElement;
import org.w3c.dom.NodeList;

/**
 * This class represents a tree element for the tree representation.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class TreeElement extends SVGElement {
	/**
	 * Vector containing all the nodes in this tree
	 */
	protected Vector<NodeElement> nodeVector;

	/**
	 * Root node element
	 */
	protected NodeElement rootNode;

	/**
	 * Constructs a new tree element for the specified tree representation.
	 * 
	 * @param treeRepresentation
	 *            tree representation
	 */
	public TreeElement(TreeRepresentation treeRepresentation) {
		super(treeRepresentation, "g");
		nodeVector = new Vector<NodeElement>();

		// id
		Vector<TreeElement> treeVector = treeRepresentation.getTreeVector();
		for (int i = 0; i <= treeVector.size(); i++) {
			boolean isAvailable = true;
			String id = TreeRepresentation.TREE_IDENTIFIER_PREFIX + "-" + i;
			for (int x = 0; x < treeVector.size(); x++)
				if (treeVector.elementAt(x).getId().compareTo(id) == 0) {
					isAvailable = false;
					x = treeVector.size();
				}
			if (isAvailable) {
				setId(id);
				i = treeVector.size() + 1;
			}
		}
	}

	/**
	 * Constructs a new tree element for the specified tree representation.
	 * 
	 * @param treeRepresentation
	 *            tree representation
	 * @param element
	 *            SVG element
	 */
	public TreeElement(TreeRepresentation treeRepresentation,
			AbstractElement element) {
		super(treeRepresentation, element);

		// node vector
		nodeVector = new Vector<NodeElement>();
		NodeList nodeList = element.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i).getNodeName().compareTo("g") == 0) {
				AbstractElement childElement = (AbstractElement) nodeList
						.item(i);
				String id = childElement.getAttribute("id");
				if (id.substring(0,
						TreeRepresentation.NODE_IDENTIFIER_PREFIX.length())
						.compareTo(TreeRepresentation.NODE_IDENTIFIER_PREFIX) == 0) {
					rootNode = new NodeElement(treeRepresentation, this, null,
							childElement);
					nodeVector.add(rootNode);
				}
			}
		}

		setTaintMode(TAINTED);
	}

	/**
	 * Creates a root node for this tree.
	 */
	public void createRootNode() {
		rootNode = new NodeElement(treeRepresentation, this);
		element.appendChild(rootNode.getElement());
		nodeVector.add(rootNode);
	}

	/**
	 * Returns a vector containing all of the nodes of the specified generation.
	 * 
	 * @param depth
	 *            generation depth
	 * 
	 * @return generation vector
	 */
	public Vector<NodeElement> getNodeGenerationVector(int depth) {
		Vector<NodeElement> nodeGenerationVector = new Vector<NodeElement>();
		for (int i = 0; i < nodeVector.size(); i++)
			if (nodeVector.elementAt(i).getDepth() == depth)
				nodeGenerationVector.add(nodeVector.elementAt(i));
		return nodeGenerationVector;
	}

	/**
	 * Returns the number of nodes in this tree.
	 * 
	 * @return number of nodes
	 */
	public int getNumberOfNodes() {
		return getNodeVector().size();
	}

	/**
	 * Returns the cumulative width of the represented tree.
	 * 
	 * @return width
	 */
	public float getWidth() {
		return rootNode.getWidth();
	}

	/**
	 * Returns the cumulative inner width of the represented tree.
	 * 
	 */
	public float getInnerWidth() {
		return rootNode.getInnerWidth();
	}

	/**
	 * Returns the cumulative outer width of the represented tree.
	 * 
	 */
	public float getOuterWidth() {
		return rootNode.getOuterWidth();
	}

	/**
	 * Returns the depth of the represented tree as the maximum number of
	 * downward nodes.
	 * 
	 * @return depth
	 */
	public int getDepth() {
		int depth = 0;
		for (int i = 0; i < nodeVector.size(); i++)
			depth = Math.max(depth, nodeVector.elementAt(i).getDepth());
		return depth;
	}

	/**
	 * Returns the node vector.
	 * 
	 * @return node vector
	 */
	public Vector<NodeElement> getNodeVector() {
		return nodeVector;
	}

	/**
	 * Sets the root node element of this tree.
	 * 
	 * @param nodeElement
	 *            node element
	 */
	public void setRootNode(NodeElement nodeElement) {
		this.rootNode = nodeElement;
	}

	/**
	 * Returns the root node element of this tree.
	 * 
	 * @return root node element
	 */
	public NodeElement getRootNode() {
		return rootNode;
	}
}