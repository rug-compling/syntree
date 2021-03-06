/* TreeGroupElement.java */

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

/**
 * This class represents a tree group element.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class TreeGroupElement extends SVGElement {
	/**
	 * Constructs a new tree group element.
	 * 
	 * @param treeRepresentation
	 *            tree representation
	 */
	public TreeGroupElement(TreeRepresentation treeRepresentation) {
		super(treeRepresentation, "g");
		setId(TreeRepresentation.TREE_GROUP_IDENTIFIER);
	}

	/**
	 * Constructs a tree group element from an existing document.
	 * 
	 * @param svgDocument
	 *            the existing svg document
	 */
	public TreeGroupElement(TreeRepresentation treeRepresentation,
			SVGDocument svgDocument) {
		super(treeRepresentation, svgDocument
				.getElementById(TreeRepresentation.TREE_GROUP_IDENTIFIER));
	}
}