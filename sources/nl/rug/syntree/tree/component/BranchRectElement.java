/* BranchRectElement.java */

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
 * This class represents a branch rectangle element.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class BranchRectElement extends SVGRectElement {
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
	 * Indicator for current highlight mode
	 */
	protected int highlightMode;

	/**
	 * Constructs a new branch rectangle element.
	 * 
	 * @param treeRepresentation
	 *            tree representation
	 * @param branchElement
	 *            branch element
	 */
	public BranchRectElement(TreeRepresentation treeRepresentation,
			BranchElement branchElement) {
		super(treeRepresentation);

		// id
		setId(TreeRepresentation.BRANCH_RECTANGLE_IDENTIFIER_PREFIX + ":"
				+ branchElement.getId().split(":")[1] + ":"
				+ branchElement.getId().split(":")[2]);

		setHighlightMode(STANDARD);
	}

	/**
	 * Constructs a new branch rectangle element.
	 * 
	 * @param treeRepresentation
	 *            tree representation
	 * @param element
	 *            SVG element
	 */
	public BranchRectElement(TreeRepresentation treeRepresentation,
			AbstractElement element) {
		super(treeRepresentation, element);
		setHighlightMode(STANDARD);
	}

	/**
	 * Highlights the branch rectangle element.
	 */
	public void highlightMode() {
		// fill
		setFill(UIColors.TREE_BRANCH_RECTANGLE_HIGHLIGHT_FILL);
		setFillOpacity(0.075f);

		// stroke
		setStroke(UIColors.TREE_BRANCH_RECTANGLE_HIGHLIGHT_STROKE);
		setStrokeOpacity(0.075f);
	}

	/**
	 * Dehighlights the branch rectangle element.
	 */
	public void standardMode() {
		// fill
		setFill(UIColors.TREE_BRANCH_RECTANGLE_STANDARD_FILL);
		setFillOpacity(0.0f);

		// stroke
		setStroke(UIColors.TREE_BRANCH_RECTANGLE_STANDARD_STROKE);
		setStrokeOpacity(0.0f);
	}

	/**
	 * Highlights the branch rectangle element for selection.
	 */
	public void selectMode() {
		// fill
		setFill(UIColors.TREE_BRANCH_RECTANGLE_SELECT_FILL);
		setFillOpacity(0.075f);

		// stroke
		setStroke(UIColors.TREE_BRANCH_RECTANGLE_SELECT_STROKE);
		setStrokeOpacity(0.075f);
	}

	/**
	 * Sets the highlight mode for this branch rectangle to the specified mode.
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
		this.highlightMode = highlightMode;
	}

	/**
	 * Returns the highlight mode for this branch rectangle.
	 */
	public int getHighlightMode() {
		return highlightMode;
	}
}