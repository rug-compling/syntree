/* MdomBranchElement.java */

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
 * This class represents a multidominance branch element.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class MdomBranchElement extends SVGElement {
	/**
	 * Multidominance branch path element
	 */
	protected MdomBranchPathElement mdomBranchPathElement;

	/**
	 * Multidominance branch background path element
	 */
	protected MdomBranchBackgroundPathElement mdomBranchBackgroundPathElement;

	/**
	 * Mother node element
	 */
	protected NodeElement motherNode;

	/**
	 * Daughter node element
	 */
	protected NodeElement daughterNode;

	/**
	 * Constructs a new multidominance branch element for the specified mother
	 * node.
	 * 
	 * @param treeRepresentation
	 *            tree representationcreateTree();createTree();
	 * @param motherNode
	 *            mother node
	 */
	public MdomBranchElement(TreeRepresentation treeRepresentation,
			NodeElement motherNode) {
		super(treeRepresentation, "g");
		setMotherNode(motherNode);

		// id
		Vector<MdomBranchElement> mdomVector = treeRepresentation
				.getMdomVector();
		for (int i = 0; i <= mdomVector.size(); i++) {
			boolean isAvailable = true;
			String id = TreeRepresentation.MDOM_BRANCH_IDENTIFIER_PREFIX + "-"
					+ i;
			for (int x = 0; x < mdomVector.size(); x++)
				if (mdomVector.elementAt(x).getId().compareTo(id) == 0) {
					isAvailable = false;
					x = mdomVector.size();
				}
			if (isAvailable) {
				setId(id);
				i = mdomVector.size() + 1;
			}
		}

		// background path
		mdomBranchBackgroundPathElement = new MdomBranchBackgroundPathElement(
				treeRepresentation, this);
		element.appendChild(mdomBranchBackgroundPathElement.getElement());

		// path
		mdomBranchPathElement = new MdomBranchPathElement(treeRepresentation,
				this);
		element.appendChild(mdomBranchPathElement.getElement());

		// event listeners
		treeRepresentation.getTreeEditorBridge()
				.addMdomBranchBackgroundPathElementEventListeners(this);
		treeRepresentation.getTreeEditorBridge()
				.addMdomBranchPathElementEventListeners(this);
	}

	/**
	 * Constructs a new multidominance branch element for the specified mother
	 * node.
	 * 
	 * @param treeRepresentation
	 *            tree representation
	 * @param element
	 *            SVG element
	 */
	public MdomBranchElement(TreeRepresentation treeRepresentation,
			AbstractElement element) {
		super(treeRepresentation, element);

		// mother node
		String motherNodeId = element.getAttribute("mother");
		if (motherNodeId != null) {
			Vector<TreeElement> treeVector = treeRepresentation.getTreeVector();
			for (int i = 0; i < treeVector.size(); i++) {
				Vector<NodeElement> nodeVector = treeVector.elementAt(i)
						.getNodeVector();
				for (int x = 0; x < nodeVector.size(); x++) {
					if (nodeVector.elementAt(x).getId().compareTo(motherNodeId) == 0) {
						motherNode = nodeVector.elementAt(x);
						x = nodeVector.size();
						i = treeVector.size();
					}
				}
			}
		}

		// daughter node
		String daughterNodeId = element.getAttribute("daughter");
		if (daughterNodeId != null) {
			Vector<TreeElement> treeVector = treeRepresentation.getTreeVector();
			for (int i = 0; i < treeVector.size(); i++) {
				Vector<NodeElement> nodeVector = treeVector.elementAt(i)
						.getNodeVector();
				for (int x = 0; x < nodeVector.size(); x++) {
					if (nodeVector.elementAt(x).getId().compareTo(
							daughterNodeId) == 0) {
						daughterNode = nodeVector.elementAt(x);
						x = nodeVector.size();
						i = treeVector.size();
					}
				}
			}
		}

		// path elements
		NodeList nodeList = element.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			AbstractElement childElement = (AbstractElement) nodeList.item(i);
			String id = childElement.getAttribute("id");
			if (id
					.substring(
							0,
							TreeRepresentation.MDOM_BRANCH_BACKGROUND_PATH_IDENTIFIER_PREFIX
									.length())
					.compareTo(
							TreeRepresentation.MDOM_BRANCH_BACKGROUND_PATH_IDENTIFIER_PREFIX) == 0) {
				mdomBranchBackgroundPathElement = new MdomBranchBackgroundPathElement(
						treeRepresentation, this, childElement);
				treeRepresentation.getTreeEditorBridge()
						.addMdomBranchBackgroundPathElementEventListeners(this);
			}
			if (id.substring(
					0,
					TreeRepresentation.MDOM_BRANCH_PATH_IDENTIFIER_PREFIX
							.length()).compareTo(
					TreeRepresentation.MDOM_BRANCH_PATH_IDENTIFIER_PREFIX) == 0) {
				mdomBranchPathElement = new MdomBranchPathElement(
						treeRepresentation, this, childElement);
				treeRepresentation.getTreeEditorBridge()
						.addMdomBranchPathElementEventListeners(this);
			}
		}
	}

	/**
	 * Sets the mother node of this mdom branch element.
	 * 
	 * @param motherNode
	 *            mother node
	 */
	public void setMotherNode(NodeElement motherNode) {
		if (motherNode != null)
			element.setAttribute("mother", motherNode.getId());
		else
			element.removeAttribute("mother");
		this.motherNode = motherNode;
	}

	/**
	 * Returns the mother node of this mdom branch element.
	 * 
	 * @return mother node
	 */
	public NodeElement getMotherNode() {
		return motherNode;
	}

	/**
	 * Sets the daughter node of this mdom branch element.
	 * 
	 * @param daughterNode
	 *            daughter node
	 */
	public void setDaughterNode(NodeElement daughterNode) {
		if (daughterNode != null)
			element.setAttribute("daughter", daughterNode.getId());
		else
			element.removeAttribute("daughter");
		this.daughterNode = daughterNode;
	}

	/**
	 * Returns the daughter node of this mdom branch element.
	 * 
	 * @return daughter node
	 */
	public NodeElement getDaughterNode() {
		return daughterNode;
	}

	/**
	 * Returns the multidominance branch path element.
	 * 
	 * @return mdom branch path element
	 */
	public MdomBranchPathElement getMdomBranchPathElement() {
		return mdomBranchPathElement;
	}

	/**
	 * Returns the multidominance branch background path element.
	 * 
	 * @return mdom branch background path element
	 */
	public MdomBranchBackgroundPathElement getMdomBranchBackgroundPathElement() {
		return mdomBranchBackgroundPathElement;
	}
}