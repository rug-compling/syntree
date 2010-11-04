/* TreeRepresentation.java */

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

package nl.rug.syntree.tree;

import java.io.File;
import java.util.Vector;

import nl.rug.syntree.editor.TreeEditorBridge;
import nl.rug.syntree.tree.component.MdomBranchElement;
import nl.rug.syntree.tree.component.MetaGroupElement;
import nl.rug.syntree.tree.component.NodeElement;
import nl.rug.syntree.tree.component.SVGDocument;
import nl.rug.syntree.tree.component.TreeElement;
import nl.rug.syntree.tree.component.TreeGroupElement;

import org.apache.batik.dom.AbstractElement;
import org.w3c.dom.NodeList;

/**
 * This class represents a generic tree representation.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class TreeRepresentation {
	/**
	 * Branch identifier prefix for the dom
	 */
	public static final String BRANCH_IDENTIFIER_PREFIX = "00";

	/**
	 * Branch rectangle identifier prefix for the dom
	 */
	public static final String BRANCH_RECTANGLE_IDENTIFIER_PREFIX = "01";

	/**
	 * Multidominance group identifier
	 */
	public static final String META_GROUP_IDENTIFIER = "M";

	/**
	 * Node identifier prefix for the dom
	 */
	public static final String NODE_IDENTIFIER_PREFIX = "03";

	/**
	 * Node rectangle identifier prefix for the dom
	 */
	public static final String NODE_RECTANGLE_IDENTIFIER_PREFIX = "04";

	/**
	 * Node text identifier prefix for the dom
	 */
	public static final String NODE_TEXT_IDENTIFIER_PREFIX = "05";

	/**
	 * Tree group identifier for the dom
	 */
	public static final String TREE_GROUP_IDENTIFIER = "T";

	/**
	 * Tree identifier prefix for the dom
	 */
	public static final String TREE_IDENTIFIER_PREFIX = "06";

	/**
	 * Multidominance branch identifier prefix for the dom
	 */
	public static final String MDOM_BRANCH_IDENTIFIER_PREFIX = "07";

	/**
	 * Multidominance branch path identifier prefix for the dom
	 */
	public static final String MDOM_BRANCH_PATH_IDENTIFIER_PREFIX = "08";

	/**
	 * Multidominance branch background path identifier prefix for the dom
	 */
	public static final String MDOM_BRANCH_BACKGROUND_PATH_IDENTIFIER_PREFIX = "09";

	/**
	 * A bridge between the graphical and bracket tree structures
	 */
	protected TreeEditorBridge treeEditorBridge;

	/**
	 * A scalable vector graphics dom document
	 */
	protected SVGDocument svgDocument;

	/**
	 * Tree vector for this tree representation
	 */
	protected Vector<TreeElement> treeVector;

	/**
	 * Mdom vector for this tree representation
	 */
	protected Vector<MdomBranchElement> mdomVector;

	/**
	 * Tree group element
	 */
	protected TreeGroupElement treeGroupElement;

	/**
	 * Multidominance relation group element
	 */
	protected MetaGroupElement metaGroupElement;

	/**
	 * Constructs a new generic tree representation.
	 * 
	 * @param treeEditorBridge
	 *            bridge between graphical and bracket structure
	 */
	public TreeRepresentation(TreeEditorBridge treeEditorBridge) {
		this.treeEditorBridge = treeEditorBridge;
		svgDocument = new SVGDocument();

		// tree group element
		treeGroupElement = new TreeGroupElement(this);
		svgDocument.getDocumentElement().appendChild(
				treeGroupElement.getElement());

		// meta group element
		metaGroupElement = new MetaGroupElement(this);
		svgDocument.getDocumentElement().appendChild(
				metaGroupElement.getElement());

		// vectors
		treeVector = new Vector<TreeElement>();
		mdomVector = new Vector<MdomBranchElement>();
	}

	/**
	 * Constructs a new generic tree representation from a SVG XML file
	 * 
	 * @param treeEditorBridge
	 *            bridge between graphical and bracket structure
	 * @param svgXMLFile
	 *            the SVG XML file
	 */
	public TreeRepresentation(TreeEditorBridge treeEditorBridge, File svgXMLFile) {
		this.treeEditorBridge = treeEditorBridge;
		svgDocument = new SVGDocument(svgXMLFile);

		// tree group element
		treeGroupElement = new TreeGroupElement(this, svgDocument);

		// meta group element
		metaGroupElement = new MetaGroupElement(this, svgDocument);

		// node list
		NodeList nodeList;

		// tree vectors
		treeVector = new Vector<TreeElement>();
		nodeList = treeGroupElement.getElement().getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i).getNodeName().compareTo("g") == 0) {
				AbstractElement element = (AbstractElement) nodeList.item(i);
				String id = element.getAttribute("id");
				if (id.substring(0, TREE_IDENTIFIER_PREFIX.length()).compareTo(
						TREE_IDENTIFIER_PREFIX) == 0) {
					treeVector.add(new TreeElement(this, element));
				}
			}
		}

		// mdom vector
		mdomVector = new Vector<MdomBranchElement>();
		nodeList = metaGroupElement.getElement().getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i).getNodeName().compareTo("g") == 0) {
				AbstractElement element = (AbstractElement) nodeList.item(i);
				String id = element.getAttribute("id");
				if (id.substring(0, MDOM_BRANCH_IDENTIFIER_PREFIX.length())
						.compareTo(MDOM_BRANCH_IDENTIFIER_PREFIX) == 0) {
					mdomVector.add(new MdomBranchElement(this, element));
				}
			}
		}
	}

	/**
	 * Creates a new tree for this tree representation.
	 * 
	 * @return the created tree element
	 */
	public TreeElement createTree() {
		TreeElement treeElement = new TreeElement(this);
		treeGroupElement.getElement().appendChild(treeElement.getElement());
		treeElement.createRootNode();
		treeVector.add(treeElement);
		return treeElement;
	}

	/**
	 * Adds the specified tree to the this tree representation.
	 * 
	 * @param treeElement
	 *            tree element
	 */
	public void addTree(TreeElement treeElement) {
		treeGroupElement.getElement().appendChild(treeElement.getElement());
		treeVector.add(treeElement);
	}

	/**
	 * Removes the specified tree from this tree representation.
	 * 
	 * @param treeElement
	 *            tree element
	 */
	public void removeTree(TreeElement treeElement) {
		if (treeVector.size() > 1) {
			treeGroupElement.getElement().removeChild(treeElement.getElement());
			treeVector.remove(treeElement);
		}					
	}

	/**
	 * Returns the number of trees in this representation.
	 * 
	 * @return number of trees
	 */
	public int getNumberOfTrees() {
		return getTreeVector().size();
	}

	/**
	 * Creates a new multidominance relation for the specified mother node.
	 * 
	 * @param motherNode
	 *            mother node
	 */
	public void createMultidominanceRelation(NodeElement motherNode) {
		createMultidominanceRelation(motherNode, null);
	}

	/**
	 * Creates a new multidominance relation for the spcified mother node and
	 * daughter node.
	 * 
	 * @param motherNode
	 *            mother node
	 * @param daughterNode
	 *            daughter node
	 */
	public void createMultidominanceRelation(NodeElement motherNode,
			NodeElement daughterNode) {
		MdomBranchElement mdomBranchElement = new MdomBranchElement(this,
				motherNode);
		metaGroupElement.getElement().appendChild(
				mdomBranchElement.getElement());
		mdomVector.add(mdomBranchElement);
	}

	/**
	 * Returns the tree group element.
	 * 
	 * @return tree group element
	 */
	public TreeGroupElement getTreeGroupElement() {
		return treeGroupElement;
	}

	/**
	 * Returns the meta group element.
	 * 
	 * @return meta group element
	 */
	public MetaGroupElement getMetaGroupElement() {
		return metaGroupElement;
	}

	/**
	 * Returns the tree vector for this representation.
	 * 
	 * @return tree vector
	 */
	public Vector<TreeElement> getTreeVector() {
		return treeVector;
	}

	/**
	 * Returns the mdom vector for this representation.
	 * 
	 * @return mdom vector
	 */
	public Vector<MdomBranchElement> getMdomVector() {
		return mdomVector;
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
	 * Returns the svg document.
	 * 
	 * @return svg document
	 */
	public SVGDocument getSVGDocument() {
		return svgDocument;
	}
}