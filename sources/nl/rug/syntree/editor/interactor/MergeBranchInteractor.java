/* MergeBranchInteractor.java */

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

package nl.rug.syntree.editor.interactor;

import nl.rug.syntree.editor.TreeEditorJSVGScrollPane;
import nl.rug.syntree.tree.component.NodeElement;

/**
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class MergeBranchInteractor extends AbstractBranchInteractor {
	/**
	 * 	
	 */
	public MergeBranchInteractor(
			TreeEditorJSVGScrollPane treeEditorJSVGScrollPane) {
		super(treeEditorJSVGScrollPane);
	}

	/**
	 * Adds a daughter node and branch to the mother node at the selected
	 * position.
	 */
	public void performEdit() {
		NodeElement mergeElement = treeEditorJSVGScrollPane
				.getTreeEditorBridge().getNodeCopyCutManager()
				.getProminentNode();
		if (position >= 0 && position < daughterVector.size()
				&& daughterVector.size() > 0)
			treeEditorJSVGScrollPane.getTreeEditorBridge().mergeDaughterBefore(
					mergeElement, nodeElement,
					daughterVector.elementAt(position));
		else
			treeEditorJSVGScrollPane.getTreeEditorBridge().mergeDaughter(
					mergeElement, nodeElement);
	}
}