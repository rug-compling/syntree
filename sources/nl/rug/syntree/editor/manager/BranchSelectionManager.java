/* BranchSelectionManager.java */

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
import nl.rug.syntree.tree.component.BranchElement;

/**
 * This class represents a branch selection manager.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class BranchSelectionManager {
	/**
	 * Tree editor bridge
	 */
	protected TreeEditorBridge treeEditorBridge;

	/**
	 * Vector containing the selected branch elements
	 */
	protected Vector<BranchElement> selectionVector;

	/**
	 * Constructs a new branch selection manager.
	 */
	public BranchSelectionManager(TreeEditorBridge treeEditorBridge) {
		this.treeEditorBridge = treeEditorBridge;
		selectionVector = new Vector<BranchElement>();
	}

	/**
	 * Adds a new branch element to the selection vector.
	 * 
	 * @param branchElement
	 *            branch element
	 */
	public void add(BranchElement branchElement) {
		if (!isSelected(branchElement))
			selectionVector.add(branchElement);
	}

	/**
	 * Removes the specified branch element from the selection vector.
	 * 
	 * @param branchElement
	 *            branch element
	 */
	public void remove(BranchElement branchElement) {
		selectionVector.remove(branchElement);
	}

	/**
	 * Clears the selection vector.
	 */
	public void clear() {
		selectionVector.clear();
	}

	/**
	 * Verifies whether the specified branch element is on the selecion vector.
	 * 
	 * @param branchElement
	 *            branch element
	 * 
	 * @return boolean
	 */
	public boolean isSelected(BranchElement branchElement) {
		return selectionVector.contains(branchElement);
	}

	/**
	 * Returns the size of the selection vector
	 * 
	 * @return selection vector size
	 */
	public int getSelectionSize() {
		return selectionVector.size();
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
	public Vector<BranchElement> getSelectionVector() {
		return selectionVector;
	}
}