/* TreeDrawer.java */

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

package nl.rug.syntree.drawer;

import nl.rug.syntree.drawer.type.PlainBinaryTree;
import nl.rug.syntree.tree.TreeRepresentation;
import nl.rug.syntree.tree.component.MdomBranchElement;
import nl.rug.syntree.tree.component.NodeElement;
import nl.rug.syntree.tree.component.TreeElement;

/**
 * This class represents a tree drawer routing point for multiple tree types.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class TreeDrawer {
	/**
	 * Plain binary tree type identifier constant
	 */
	public static final int PLAIN_BINARY_TREE = 0x00;

	/**
	 * Tree representation
	 */
	protected TreeRepresentation treeRepresentation;

	/**
	 * The current tree type
	 */
	protected int treeType;

	/**
	 * Constructs a new tree drawer for the specified tree representation.
	 * 
	 * @param treeRepresentation
	 *            tree representation
	 */
	public TreeDrawer(TreeRepresentation treeRepresentation) {
		this.treeRepresentation = treeRepresentation;
		this.treeType = PLAIN_BINARY_TREE;
	}

	/**
	 * Draws all of the trees in the tree representation.
	 */
	public void draw() {
		switch (treeType) {
		case PLAIN_BINARY_TREE:
			PlainBinaryTree.draw(treeRepresentation);
			break;
		}
	}

	/**
	 * Draws the specified tree in the tree representation.
	 * 
	 * @param treeElement
	 *            tree element
	 */
	public void draw(TreeElement treeElement) {
		switch (treeType) {
		case PLAIN_BINARY_TREE:
			PlainBinaryTree.draw(treeRepresentation, treeElement);
			break;
		}
	}

	/**
	 * Draws the specified node in the tree representation.
	 * 
	 * @param nodeElement
	 *            node element
	 */
	public void draw(NodeElement nodeElement) {
		switch (treeType) {
		case PLAIN_BINARY_TREE:
			PlainBinaryTree.drawNodeText(treeRepresentation, nodeElement);
			break;
		}
	}

	/**
	 * Draws the specified mdom branch in the tree representation.
	 * 
	 * @param mdomBranchElement
	 *            mdom branch element
	 */
	public void draw(MdomBranchElement mdomBranchElement) {
		switch (treeType) {
		case PLAIN_BINARY_TREE:
			PlainBinaryTree.drawMdomBranch(treeRepresentation,
					mdomBranchElement);
		}
	}

	/**
	 * Returns the tree representation.
	 * 
	 * @return tree representation
	 */
	public TreeRepresentation getTreeRepresentation() {
		return treeRepresentation;
	}

	/**
	 * Returns the current tree type.
	 * 
	 * @return current tree type
	 */
	public int getTreeType() {
		return treeType;
	}
}