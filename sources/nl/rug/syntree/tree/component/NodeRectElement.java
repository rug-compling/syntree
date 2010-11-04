/* NodeRectElement.java */

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
import nl.rug.syntree.ui.UIColors;

import org.apache.batik.dom.AbstractElement;

/**
 * This class represents a node rectangle element.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class NodeRectElement extends SVGRectElement {
	/**
	 * Edit mode identifier constant
	 */
	public static final int EDIT = 0x00;

	/**
	 * Highlight mode identifier constant
	 */
	public static final int HIGHLIGHT = 0x01;

	/**
	 * Standard mode identifier constant
	 */
	public static final int STANDARD = 0x02;

	/**
	 * Select mode identifier constant
	 */
	public static final int SELECT = 0x03;

	/**
	 * Indicator for current highlighting mode
	 */
	protected int highlightMode;

	/**
	 * Constructs a new node rectangle element.
	 * 
	 * @param treeRepresentation
	 *            tree representation
	 * @param nodeElement
	 *            node element
	 */
	public NodeRectElement(TreeRepresentation treeRepresentation,
			NodeElement nodeElement) {
		super(treeRepresentation);

		// id
		setId(TreeRepresentation.NODE_RECTANGLE_IDENTIFIER_PREFIX + "@"
				+ nodeElement.getId());

		setHighlightMode(STANDARD);
	}

	/**
	 * Constructs a new node rectangle element.
	 * 
	 * @param treeRepresentation
	 *            tree representation
	 * @param element
	 *            SVG element
	 */
	public NodeRectElement(TreeRepresentation treeRepresentation,
			AbstractElement element) {
		super(treeRepresentation, element);

		setHighlightMode(STANDARD);
	}

	/**
	 * Highlights the node rectangle element for editing.
	 */
	protected void editMode() {
		// fill
		setFill(UIColors.TREE_NODE_RECTANGLE_EDIT_FILL);
		setFillOpacity(0.075f);

		// stroke
		setStroke(UIColors.TREE_NODE_RECTANGLE_EDIT_STROKE);
		setStrokeOpacity(0.075f);
	}

	/**
	 * Highlights the node rectangle element.
	 */
	public void highlightMode() {
		// fill
		setFill(UIColors.TREE_NODE_RECTANGLE_HIGHLIGHT_FILL);
		setFillOpacity(0.075f);

		// stroke
		setStroke(UIColors.TREE_NODE_RECTANGLE_HIGHLIGHT_STROKE);
		setStrokeOpacity(0.075f);
	}

	/**
	 * Dehighlights the node rectangle element.
	 */
	protected void standardMode() {
		// fill
		setFill(UIColors.TREE_NODE_RECTANGLE_STANDARD_FILL);
		setFillOpacity(0.0f);

		// stroke
		setStroke(UIColors.TREE_NODE_RECTANGLE_STANDARD_STROKE);
		setStrokeOpacity(0.0f);
	}

	/**
	 * Highlights the node rectangle element for selection.
	 */
	protected void selectMode() {
		// fill
		setFill(UIColors.TREE_NODE_RECTANGLE_SELECT_FILL);
		setFillOpacity(0.075f);

		// stroke
		setStroke(UIColors.TREE_NODE_RECTANGLE_SELECT_STROKE);
		setStrokeOpacity(0.075f);
	}

	/**
	 * Sets the highlight mode for this node rectangle to the specified mode.
	 * 
	 * @param highlightMode
	 *            highlight mode
	 */
	public void setHighlightMode(int highlightMode) {
		if (highlightMode != this.highlightMode && highlightMode == STANDARD)
			standardMode();
		if (highlightMode != this.highlightMode && highlightMode == HIGHLIGHT)
			highlightMode();
		if (highlightMode != this.highlightMode && highlightMode == SELECT)
			selectMode();
		if (highlightMode != this.highlightMode && highlightMode == EDIT)
			editMode();
		this.highlightMode = highlightMode;
	}

	/**
	 * Returns the highlight mode for this node rectangle.
	 */
	public int getHighlightMode() {
		return highlightMode;
	}
}