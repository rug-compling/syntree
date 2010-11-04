/* NodeElement.java */

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
 * This class represents a node in the tree representation.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class NodeElement extends SVGElement {
	/**
	 * The tree element to which this node belongs
	 */
	protected TreeElement treeElement;

	/**
	 * A rectangle element for node highlighting
	 */
	protected NodeRectElement nodeRectElement;

	/**
	 * Text element containing the text label of this node
	 */
	protected TextElement textElement;

	/**
	 * Branch element that connects this node to its mother
	 */
	protected BranchElement branchElement;

	/**
	 * Mother node element
	 */
	protected NodeElement motherNodeElement;

	/**
	 * Cached inner (sub)tree width in floating point precision
	 */
	protected float cachedInnerWidth = 0.0f;

	/**
	 * Cached outer (sub)tree width in floating point precision
	 */
	protected float cachedOuterWidth = 0.0f;

	/**
	 * Boolean indicating whether this node is new
	 */
	protected boolean isNewNode = true;

	/**
	 * Vector containing all of this nodes daughters
	 */
	protected Vector<NodeElement> daughterVector;

	/**
	 * Constructs a new node element within the specified tree representation
	 * and for the specified tree element.
	 * 
	 * @param treeRepresentation
	 *            tree representation
	 * @param treeElement
	 *            tree element
	 */
	public NodeElement(TreeRepresentation treeRepresentation,
			TreeElement treeElement) {
		super(treeRepresentation, "g");
		this.treeElement = treeElement;

		// id
		Vector<NodeElement> nodeVector = treeElement.getNodeVector();
		for (int i = 0; i <= nodeVector.size(); i++) {
			boolean isAvailable = true;
			String id = TreeRepresentation.NODE_IDENTIFIER_PREFIX + "-" + i
					+ "@" + treeElement.getId();
			for (int x = 0; x < nodeVector.size(); x++)
				if (nodeVector.elementAt(x).getId().compareTo(id) == 0) {
					isAvailable = false;
					x = nodeVector.size();
				}
			if (isAvailable) {
				setId(id);
				i = nodeVector.size() + 1;
			}
		}

		// daughter vector
		daughterVector = new Vector<NodeElement>();

		// node rectangle element
		nodeRectElement = new NodeRectElement(treeRepresentation, this);
		element.appendChild(nodeRectElement.getElement());

		// text element
		textElement = new TextElement(treeRepresentation, this, "x");
		element.appendChild(textElement.getElement());

		// dom event listeners
		treeRepresentation.getTreeEditorBridge()
				.addNodeRectElementEventListeners(this);
		treeRepresentation.getTreeEditorBridge().addTextElementEventListeners(
				this);
	}

	/**
	 * Constructs a new node element within the specified tree representation
	 * and for the specified tree element.
	 * 
	 * @param treeRepresentation
	 *            tree representation
	 * @param treeElement
	 *            tree element
	 * @param motherNodeElement
	 *            mother node element
	 * @param element
	 *            SVG element
	 */
	public NodeElement(TreeRepresentation treeRepresentation,
			TreeElement treeElement, NodeElement motherNodeElement,
			AbstractElement element) {
		super(treeRepresentation, element);

		// tree element
		this.treeElement = treeElement;

		// mother node element
		this.motherNodeElement = motherNodeElement;
		if (motherNodeElement != null)
			motherNodeElement.getDaughterVector().add(this);

		// daughter vector
		daughterVector = new Vector<NodeElement>();

		// daughter nodes, node text, node rectangle, and branches
		String relevantTags[] = { "g", "text", "rect", "polygon" };
		NodeList nodeList = element.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			boolean relevantTag = false;
			for (int x = 0; x < relevantTags.length; x++) {
				if (nodeList.item(i).getNodeName().compareTo(relevantTags[x]) == 0) {
					relevantTag = true;
					x = relevantTags.length;
				}
			}
			if (relevantTag) {
				AbstractElement childElement = (AbstractElement) nodeList
						.item(i);
				String id = childElement.getAttribute("id");

				// node rectangle element
				if (id.length() >= TreeRepresentation.NODE_RECTANGLE_IDENTIFIER_PREFIX
						.length()) {
					String idSubstring = id.substring(0,
							TreeRepresentation.NODE_RECTANGLE_IDENTIFIER_PREFIX
									.length());
					if (idSubstring
							.compareTo(TreeRepresentation.NODE_RECTANGLE_IDENTIFIER_PREFIX) == 0) {
						nodeRectElement = new NodeRectElement(
								treeRepresentation, childElement);
						treeRepresentation.getTreeEditorBridge()
								.addNodeRectElementEventListeners(this);
					}
				}

				// node text element
				if (id.length() >= TreeRepresentation.NODE_TEXT_IDENTIFIER_PREFIX
						.length()) {
					String idSubstring = id.substring(0,
							TreeRepresentation.NODE_TEXT_IDENTIFIER_PREFIX
									.length());
					if (idSubstring
							.compareTo(TreeRepresentation.NODE_TEXT_IDENTIFIER_PREFIX) == 0) {
						textElement = new TextElement(treeRepresentation, this,
								childElement);
						treeRepresentation.getTreeEditorBridge()
								.addTextElementEventListeners(this);						
					}
				}

				// branch element
				if (id.length() >= TreeRepresentation.BRANCH_IDENTIFIER_PREFIX
						.length()) {
					String idSubstring = id.substring(0,
							TreeRepresentation.BRANCH_IDENTIFIER_PREFIX
									.length());
					if (idSubstring
							.compareTo(TreeRepresentation.BRANCH_IDENTIFIER_PREFIX) == 0) {
						branchElement = new BranchElement(treeRepresentation,
								childElement);
						treeRepresentation.getTreeEditorBridge()
								.addBranchElementEventListeners(this);
						treeRepresentation.getTreeEditorBridge()
								.addBranchRectElementEventListeners(this);
					}
				}

				// node element
				if (id.length() >= TreeRepresentation.NODE_IDENTIFIER_PREFIX
						.length()) {
					String idSubstring = id.substring(0,
							TreeRepresentation.NODE_IDENTIFIER_PREFIX.length());
					if (idSubstring
							.compareTo(TreeRepresentation.NODE_IDENTIFIER_PREFIX) == 0) {
						treeElement.getNodeVector().add(
								new NodeElement(treeRepresentation,
										treeElement, this, childElement));
					}
				}
			}
		}
		isNewNode = false;
	}

	/**
	 * Creates a new daughter for this node.
	 * 
	 * @return daughter node
	 */
	public NodeElement createDaughter() {
		return createDaughterBefore(null);
	}

	/**
	 * Creates a new daughter and inserts it before the next daughter in case it
	 * is specified. Otherwise, the new daughter will be appended to the set of
	 * existing daughters.
	 * 
	 * @param nextDaughterNode
	 *            the daughter that should follow the new daughter
	 * @return daughter node
	 */
	public NodeElement createDaughterBefore(NodeElement nextDaughterNode) {
		NodeElement daughterNode = new NodeElement(treeRepresentation,
				treeElement);
		daughterNode.setMother(this);

		// insert in DOM and vector
		if (nextDaughterNode != null) {
			element.insertBefore(daughterNode.getElement(), nextDaughterNode
					.getElement());
			daughterVector.insertElementAt(daughterNode, daughterVector
					.indexOf(nextDaughterNode));
		} else {
			element.appendChild(daughterNode.getElement());
			daughterVector.add(daughterNode);
		}
		treeElement.getNodeVector().add(daughterNode);

		// branch
		daughterNode.createBranch(this);

		// taint
		NodeElement iteratorElement = this;
		while (iteratorElement != null) {
			iteratorElement.setTaintMode(NodeElement.TAINTED);
			iteratorElement = iteratorElement.getMother();
		}

		return daughterNode;
	}

	/**
	 * 
	 */
	public NodeElement mergeDaughter(NodeElement mergeNode) {
		return mergeDaughterBefore(mergeNode, null);
	}

	/**
	 * 
	 */
	public NodeElement mergeDaughterBefore(NodeElement mergeNode,
			NodeElement nextDaughterNode) {

		NodeElement daughterNode = mergeNode.duplicate(treeElement);
		daughterNode.setMother(this);
		daughterNode.setIsNewNode(false);

		for (int i = 0; i < mergeNode.getDaughterVector().size(); i++) {
			daughterNode.mergeDaughter(mergeNode.getDaughterVector().elementAt(
					i));
		}

		// insert in DOM and vector
		if (nextDaughterNode != null) {
			element.insertBefore(daughterNode.getElement(), nextDaughterNode
					.getElement());
			daughterVector.insertElementAt(daughterNode, daughterVector
					.indexOf(nextDaughterNode));
		} else {
			element.appendChild(daughterNode.getElement());
			daughterVector.add(daughterNode);
		}
		treeElement.getNodeVector().add(daughterNode);

		// branch
		daughterNode.createBranch(this);

		// taint
		NodeElement iteratorElement = this;
		while (iteratorElement != null) {
			iteratorElement.setTaintMode(NodeElement.TAINTED);
			iteratorElement = iteratorElement.getMother();
		}

		treeRepresentation.getTreeEditorBridge()
				.addNodeRectElementEventListeners(daughterNode);		
		treeRepresentation.getTreeEditorBridge().addTextElementEventListeners(
				daughterNode);

		return daughterNode;
	}

	/**
	 * 
	 */
	public NodeElement duplicate(TreeElement treeElement) {
		NodeElement nodeElement = new NodeElement(treeRepresentation,
				treeElement);

		nodeElement
				.setTextElement(this.getTextElement().duplicate(nodeElement));

		return nodeElement;
	}

	/**
	 * Adds an existing daughter to this node.
	 * 
	 * @param daughterNode
	 *            daughter node
	 * @param nextDaughterNode
	 *            next daughter node
	 * @return the added daughter
	 */
	public NodeElement addDaughter(NodeElement daughterNode,
			NodeElement nextDaughterNode) {
		// insert in DOM and vector
		if (nextDaughterNode != null) {
			element.insertBefore(daughterNode.getElement(), nextDaughterNode
					.getElement());
			daughterVector.insertElementAt(daughterNode, daughterVector
					.indexOf(nextDaughterNode));
		} else {
			element.appendChild(daughterNode.getElement());
			daughterVector.add(daughterNode);
		}
		treeElement.getNodeVector().add(daughterNode);

		// taint
		NodeElement iteratorElement = this;
		while (iteratorElement != null) {
			iteratorElement.setTaintMode(NodeElement.TAINTED);
			iteratorElement = iteratorElement.getMother();
		}

		return daughterNode;
	}

	/**
	 * 
	 * @param daughterNode
	 */
	protected void removeNodeFromNodeVector(NodeElement daughterNode) {
		for (int i = 0; i < daughterNode.getDaughterVector().size(); i++)
			removeNodeFromNodeVector(daughterNode.getDaughterVector()
					.elementAt(i));
		treeElement.getNodeVector().removeElement(daughterNode);
	}

	/**
	 * Remove a daughter node from this node
	 * 
	 * @param daughterNode
	 *            daughter node
	 */
	public void removeDaughter(NodeElement daughterNode) {
		removeNodeFromNodeVector(daughterNode);

		element.removeChild(daughterNode.getElement());
		daughterVector.remove(daughterNode);

		// taint
		NodeElement iteratorElement = this;
		while (iteratorElement != null) {
			iteratorElement.setTaintMode(NodeElement.TAINTED);
			iteratorElement = iteratorElement.getMother();
		}
	}

	/**
	 * Creates a new branch element between this node and its mother node.
	 * 
	 * @param motherNode
	 *            mother node
	 */
	public void createBranch(NodeElement motherNode) {
		branchElement = new BranchElement(treeRepresentation, motherNode, this);
		element.appendChild(branchElement.getElement());
		element.insertBefore(branchElement.getBranchRectElement().getElement(),
				(AbstractElement) element.getFirstChild());

		// dom event listeners
		treeRepresentation.getTreeEditorBridge()
				.addBranchElementEventListeners(this);
		treeRepresentation.getTreeEditorBridge()
				.addBranchRectElementEventListeners(this);
	}

	/**
	 * Returns the mother of this node.
	 * 
	 * @return mother node element
	 */
	public NodeElement getMother() {
		return motherNodeElement;
	}

	/**
	 * Sets the mother of this node to the specified node element.
	 * 
	 * @param motherNodeElement
	 *            mother node element
	 */
	public void setMother(NodeElement motherNodeElement) {
		this.motherNodeElement = motherNodeElement;
	}

	/**
	 * Returns a vector containing all of this nodes daughters.
	 * 
	 * @return daughter vector
	 */
	public Vector<NodeElement> getDaughterVector() {
		return daughterVector;
	}

	/**
	 * Returns a vector containing all of the nodes of the same generation of
	 * this node.
	 * 
	 * @return generation vector
	 */
	public Vector<NodeElement> getGenerationVector() {
		Vector<NodeElement> generationVector = new Vector<NodeElement>();
		int depth = this.getDepth();
		for (int i = 0; i < treeElement.getNodeVector().size(); i++)
			if (treeElement.getNodeVector().elementAt(i).getDepth() == depth)
				generationVector.add(treeElement.getNodeVector().elementAt(i));
		return generationVector;
	}

	/**
	 * Returns the depth of this tree relative to the root node of the tree.
	 * 
	 * @return depth
	 */
	public int getDepth() {
		int depth = 0;
		NodeElement motherNode = this.getMother();
		if (motherNode != null)
			depth = motherNode.getDepth() + 1;
		return depth;
	}

	/**
	 * Computes the cumulative (sub)tree width of all generations below this
	 * node.
	 */
	public void computeWidth() {
		computeInnerWidth();
		computeOuterWidth();
	}

	/**
	 * Computes the cumulative inner (sub)tree width of all generations below
	 * this node.
	 */
	public void computeInnerWidth() {
		float width = 0.0f;
		if (daughterVector.size() >= 1) {
			NodeElement daughterNodeElement = daughterVector.firstElement();
			width = Math.abs(daughterNodeElement.getInnerWidth());
			width += Math.abs(daughterNodeElement.getXTranslate());
		}
		float textElementWidth = textElement.getWidth();
		if (width < textElementWidth / 2)
			width += textElementWidth / 2;
		cachedInnerWidth = width;
	}

	/**
	 * Computes the cumulative outer (sub)tree width of all generations below
	 * this node.
	 */
	public void computeOuterWidth() {
		float width = 0.0f;
		if (daughterVector.size() >= 1) {
			NodeElement daughterNodeElement = daughterVector.lastElement();
			width = Math.abs(daughterNodeElement.getOuterWidth());
			width += Math.abs(daughterNodeElement.getXTranslate());
		}
		float textElementWidth = textElement.getWidth();
		if (width < textElementWidth / 2)
			width += textElementWidth / 2;
		cachedOuterWidth = width;

	}

	/**
	 * Returns the cumulative (sub)tree width of all generations below this
	 * node.
	 * 
	 * @return width
	 */
	public float getWidth() {
		return getInnerWidth() + getOuterWidth();
	}

	/**
	 * Returns the cumulative inner (sub)tree width of all generations below
	 * this node.
	 * 
	 * @return inner width
	 */
	public float getInnerWidth() {
		return cachedInnerWidth;
	}

	/**
	 * Returns the cumulative outer (sub)tree width of all generations below
	 * this node.
	 * 
	 * @return outer width
	 */
	public float getOuterWidth() {
		return cachedOuterWidth;
	}

	/**
	 * Returns a boolean indicating whether this node is new.
	 * 
	 * @return boolean
	 */
	public boolean getIsNewNode() {
		return isNewNode;
	}

	/**
	 * Sets the boolean indicating whether this node is new.
	 * 
	 * @param isNewNode
	 *            boolean
	 */
	public void setIsNewNode(boolean isNewNode) {
		this.isNewNode = isNewNode;
	}

	/**
	 * Returns the branch element that connects this node to its mother.
	 * 
	 * @return branch element
	 */
	public BranchElement getBranchElement() {
		return branchElement;
	}

	/**
	 * Returns the tree element to which this node is attached.
	 * 
	 * @return tree element
	 */
	public TreeElement getTreeElement() {
		return treeElement;
	}

	/**
	 * Sets the text element of this node to the specified text element.
	 * 
	 * @param textElement
	 *            text element
	 */
	public void setTextElement(TextElement textElement) {
		element.replaceChild(textElement.getElement(), this.textElement
				.getElement());
		this.textElement = textElement;
	}

	/**
	 * Returns the text element that contains the text of this node.
	 * 
	 * @return text element
	 */
	public TextElement getTextElement() {
		return textElement;
	}

	/**
	 * Returns the rectangle that facilates highlighting for tis node.
	 * 
	 * @return node rectangle element
	 */
	public NodeRectElement getNodeRectElement() {
		return nodeRectElement;
	}
}
