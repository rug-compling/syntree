/* MdomBranchBackgroundPathElement.java */

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
 * This class represents a multidominance branch background path element.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class MdomBranchBackgroundPathElement extends MdomBranchPathElement {
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
	 * Constructs a new multidominance branch background path element.
	 * 
	 * @param treeRepresentation
	 *            the tree representation
	 * @param mdomBranchElement
	 *            the multidominance branch element
	 */
	public MdomBranchBackgroundPathElement(
			TreeRepresentation treeRepresentation,
			MdomBranchElement mdomBranchElement) {
		super(treeRepresentation, mdomBranchElement);
		setId(TreeRepresentation.MDOM_BRANCH_BACKGROUND_PATH_IDENTIFIER_PREFIX
				+ "@" + mdomBranchElement.getId());
		setStyle("fill:none;stroke-width:5;stroke-linecap:round;");
		setHighlightMode(STANDARD);
	}

	/**
	 * Constructs a new multidominance branch background path element from a SVG
	 * element.
	 * 
	 * @param treeRepresentation
	 *            tree representation
	 * @param mdomBranchElement
	 *            the multidominance branch element
	 * @param element
	 *            SVG element
	 */
	public MdomBranchBackgroundPathElement(
			TreeRepresentation treeRepresentation,
			MdomBranchElement mdomBranchElement, AbstractElement element) {
		super(treeRepresentation, mdomBranchElement, element);
		setHighlightMode(STANDARD);
	}

	/**
	 * Sets highlight mode.
	 */
	public void highlightMode() {
		// stroke
		setStroke(UIColors.TREE_BRANCH_RECTANGLE_HIGHLIGHT_STROKE);
		setStrokeOpacity(0.075f);
	}

	/**
	 * Sets standard mode.
	 */
	public void standardMode() {
		// stroke
		setStroke(UIColors.TREE_BRANCH_RECTANGLE_STANDARD_STROKE);
		setStrokeOpacity(0.0f);
	}

	/**
	 * Sets select mode.
	 */
	public void selectMode() {
		// stroke
		setStroke(UIColors.TREE_BRANCH_RECTANGLE_SELECT_FILL);
		setStrokeOpacity(0.075f);
	}

	/**
	 * Sets the highlight mode.
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
	 * Returns the current highlight mode.
	 * 
	 * @return the current highlight mode
	 */
	public int getHighlightMode() {
		return highlightMode;
	}
}