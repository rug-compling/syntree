/* BranchElement.java */

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

import nl.rug.syntree.tree.TreeRepresentation;

import org.apache.batik.dom.AbstractElement;
import org.w3c.dom.NodeList;

/**
 * This class represents a branch element for the tree representation.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class BranchElement extends SVGElement {
	/**
	 * Constant value for line type branches
	 */
	public static final String LINE = "line";

	/**
	 * Constant value for triangle type branches
	 */
	public static final String TRIANGLE = "triangle";

	/**
	 * Branch rectangle element
	 */
	protected BranchRectElement branchRectElement;

	/**
	 * Cached first x-coordinate
	 */
	protected float cachedX1 = 0.0f;

	/**
	 * Cached first y-coordinate
	 */
	protected float cachedY1 = 0.0f;

	/**
	 * Cached second x-coordinate
	 */
	protected float cachedX2 = 0.0f;

	/**
	 * Cached second y-coordinate
	 */
	protected float cachedY2 = 0.0f;

	/**
	 * Cached third x-coordinate
	 */
	protected float cachedX3 = 0.0f;

	/**
	 * Cached third y-coordinate
	 */
	protected float cachedY3 = 0.0f;

	/**
	 * Type of this branch element
	 */
	protected String type;

	/**
	 * Style of this branch element
	 */
	protected String style;

	/**
	 * Constructs a new branch element between the specified mother node and
	 * daughter node.
	 * 
	 * @param treeRepresentation
	 *            tree representation
	 * @param motherNode
	 *            mother node
	 * @param daughterNode
	 *            daughter node
	 */
	public BranchElement(TreeRepresentation treeRepresentation,
			NodeElement motherNode, NodeElement daughterNode) {
		super(treeRepresentation, "polygon");
		setType(LINE);
		setStyle("stroke:black;stroke-width:.75;stroke-linejoin:round;fill:white;");

		// id
		setId(TreeRepresentation.BRANCH_IDENTIFIER_PREFIX + ":"
				+ motherNode.getId() + ":" + daughterNode.getId());

		// branch rectangle element
		branchRectElement = new BranchRectElement(treeRepresentation, this);
	}

	/**
	 * Constructs a new branch element between the specified mother node and
	 * daughter node.
	 * 
	 * @param treeRepresentation
	 *            tree representation
	 * @param element
	 *            SVG element
	 */
	public BranchElement(TreeRepresentation treeRepresentation,
			AbstractElement element) {
		super(treeRepresentation, element);

		NodeList nodeList = element.getParentNode().getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i).getNodeName().compareTo("rect") == 0) {
				AbstractElement siblingElement = (AbstractElement) nodeList
						.item(i);
				String id = siblingElement.getAttribute("id");
				if (id.substring(
						0,
						TreeRepresentation.BRANCH_RECTANGLE_IDENTIFIER_PREFIX
								.length()).compareTo(
						TreeRepresentation.BRANCH_RECTANGLE_IDENTIFIER_PREFIX) == 0) {
					branchRectElement = new BranchRectElement(
							treeRepresentation, siblingElement);
				}
			}
		}

		// type
		String type = element.getAttribute("type");
		if (type.compareTo(LINE) == 0)
			setType(LINE);
		if (type.compareTo(TRIANGLE) == 0)
			setType(TRIANGLE);
	}

	/**
	 * Sets the first x-coordinate of this branch element.
	 * 
	 * @param x1
	 *            x-coordinate
	 */
	public void setX1(float x1) {
		if (isLine())
			element.setAttribute("points", x1 + "," + this.getY1() + " "
					+ this.getX2() + "," + this.getY2());
		if (isTriangle())
			element.setAttribute("points", x1 + "," + this.getY1() + " "
					+ this.getX2() + "," + this.getY2() + " " + this.getX3()
					+ "," + this.getY3());
		this.cachedX1 = x1;
	}

	/**
	 * Sets the first y-coordinate of this branch element.
	 * 
	 * @param y1
	 *            y-coordinate
	 */
	public void setY1(float y1) {
		if (isLine())
			element.setAttribute("points", this.getX1() + "," + y1 + " "
					+ this.getX2() + "," + this.getY2());
		if (isTriangle())
			element.setAttribute("points", this.getX1() + "," + y1 + " "
					+ this.getX2() + "," + this.getY2() + " " + this.getX3()
					+ "," + this.getY3());
		this.cachedY1 = y1;
	}

	/**
	 * Sets the second x-coordinate of this branch element.
	 * 
	 * @param x2
	 *            x-coordinate
	 */
	public void setX2(float x2) {
		if (isLine())
			element.setAttribute("points", this.getX1() + "," + this.getY1()
					+ " " + x2 + "," + this.getY2());
		if (isTriangle())
			element.setAttribute("points", this.getX1() + "," + this.getY1()
					+ " " + x2 + "," + this.getY2() + " " + this.getX3() + ","
					+ this.getY3());
		this.cachedX2 = x2;
	}

	/**
	 * Sets the second y-coordinate of this branch element.
	 * 
	 * @param y2
	 *            y-coordinate
	 */
	public void setY2(float y2) {
		if (isLine())
			element.setAttribute("points", this.getX1() + "," + this.getY1()
					+ " " + this.getX2() + "," + y2);
		if (isTriangle())
			element.setAttribute("points", this.getX1() + "," + this.getY1()
					+ " " + this.getX2() + "," + y2 + " " + this.getX3() + ","
					+ this.getY3());
		this.cachedY2 = y2;
	}

	/**
	 * Sets the third x-coordinate of this branch element.
	 * 
	 * @param x3
	 *            x-coordinate
	 */
	public void setX3(float x3) {
		if (isTriangle())
			element.setAttribute("points", this.getX1() + "," + this.getY1()
					+ " " + this.getX2() + "," + this.getY2() + " " + x3 + ","
					+ this.getY3());
		this.cachedX3 = x3;
	}

	/**
	 * Sets the third y-coordinate of this branch element.
	 * 
	 * @param y3
	 *            y-coordinate
	 */
	public void setY3(float y3) {
		if (isTriangle())
			element.setAttribute("points", this.getX1() + "," + this.getY1()
					+ " " + this.getX2() + "," + this.getY2() + " "
					+ this.getX3() + "," + y3);
		this.cachedY3 = y3;
	}

	/**
	 * Returns the first x-coordinate of this branch element.
	 * 
	 * @return x-coordinate
	 */
	public float getX1() {
		return cachedX1;
	}

	/**
	 * Returns the first y-coordinate of this branch element.
	 * 
	 * @return y-coordinate
	 */
	public float getY1() {
		return cachedY1;
	}

	/**
	 * Returns the second x-coordinate of this branch element.
	 * 
	 * @return x-coordinate
	 */
	public float getX2() {
		return cachedX2;
	}

	/**
	 * Returns the second y-coordinate of this branch element.
	 * 
	 * @return y-coordinate
	 */
	public float getY2() {
		return cachedY2;
	}

	/**
	 * Returns the third x-coordinate of this branch element.
	 * 
	 * @return x-coordinate
	 */
	public float getX3() {
		return cachedX3;
	}

	/**
	 * Returns the third y-coordinate of this branch element.
	 * 
	 * @return y-coordinate
	 */
	public float getY3() {
		return cachedY3;
	}

	/**
	 * Sets the type of the branch element to the specified type.
	 * 
	 * @param type
	 *            branch type
	 */
	public void setType(String type) {
		this.type = type;
		if (type.compareTo(LINE) == 0) {
			element.setAttribute("type", LINE);
			element.setAttribute("points", "0.0,0.0 0.0,0.0");
		}
		if (type.compareTo(TRIANGLE) == 0) {
			element.setAttribute("type", TRIANGLE);
			element.setAttribute("points", "0.0,0.0 0.0,0.0 0.0,0.0");
		}
	}

	/**
	 * Returns the type of the branch element.
	 * 
	 * @return branch type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Returns a boolean indicating whether this branch is a line.
	 * 
	 * @return boolean
	 */
	public boolean isLine() {
		boolean isLine = false;
		if (type.compareTo(LINE) == 0)
			isLine = true;
		return isLine;
	}

	/**
	 * Returns a boolean indicating whether this branch is a triangle.
	 * 
	 * @return boolean
	 */
	public boolean isTriangle() {
		boolean isTriangle = false;
		if (type.compareTo(TRIANGLE) == 0)
			isTriangle = true;
		return isTriangle;
	}

	/**
	 * Sets the style of this element to the specified style.
	 * 
	 * @param style
	 *            style
	 */
	public void setStyle(String style) {
		element.setAttribute("style", style);
		this.style = style;
	}

	/**
	 * Returns the style of this element.
	 * 
	 * @return style
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * Returns the branch rectangle element.
	 * 
	 * @return branch rectangle element
	 */
	public BranchRectElement getBranchRectElement() {
		return branchRectElement;
	}
}