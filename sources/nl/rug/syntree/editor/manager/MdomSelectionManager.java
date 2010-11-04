/* MdomSelectionManager.java */

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
import nl.rug.syntree.tree.component.MdomBranchElement;

/**
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * 
 * @author Harm Brouwer <harm.brouwer@rug.nl>
 * @version 1.0
 * @since 1.0
 */
public class MdomSelectionManager {
	/**
	 * Tree editor bridge
	 */
	protected TreeEditorBridge treeEditorBridge;

	/**
	 * Vector containing the selected mdom branch elements
	 */
	protected Vector<MdomBranchElement> selectionVector;

	/**
	 * Prominent mdom branch element
	 */
	protected MdomBranchElement prominentMdomBranch;

	/**
	 * Constructs a mdom branch selection manager.
	 * 
	 * @param treeEditorBridge
	 *            tree editor bridge
	 */
	public MdomSelectionManager(TreeEditorBridge treeEditorBridge) {
		this.treeEditorBridge = treeEditorBridge;
		selectionVector = new Vector<MdomBranchElement>();
	}

	/**
	 * Adds a new mdom branch element to the selection vector.
	 * 
	 * @param mdomBranchElement
	 *            mdom branch element
	 */
	public void add(MdomBranchElement mdomBranchElement) {
		if (!isSelected(mdomBranchElement))
			selectionVector.add(mdomBranchElement);
	}

	/**
	 * Removes the specified mdom branch element from the selection vector.
	 * 
	 * @param mdomBranchElement
	 *            mdom branch element
	 */
	public void remove(MdomBranchElement mdomBranchElement) {
		selectionVector.remove(mdomBranchElement);
	}

	/**
	 * Clears the selection vector.
	 */
	public void clear() {
		selectionVector.clear();
	}

	/**
	 * Verifies whether the specified mdom branch element is on the selection
	 * vector.
	 * 
	 * @param mdomBranchElement
	 *            mdom branch element
	 * 
	 * @return boolean
	 */
	public boolean isSelected(MdomBranchElement mdomBranchElement) {
		return selectionVector.contains(mdomBranchElement);
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
	 * Returns the prominent mdom branch element.
	 * 
	 * @return prominent mdom branch element
	 */
	public MdomBranchElement getProminentMdomBranch() {
		return prominentMdomBranch;
	}

	/**
	 * Sets the prominent mdom branch element to the specified mdom branch
	 * element.
	 * 
	 * @param mdomBranchElement
	 *            mdom branch element
	 */
	public void setProminentMdomBranch(MdomBranchElement mdomBranchElement) {
		this.prominentMdomBranch = mdomBranchElement;
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
	public Vector<MdomBranchElement> getSelectionVector() {
		return selectionVector;
	}
}