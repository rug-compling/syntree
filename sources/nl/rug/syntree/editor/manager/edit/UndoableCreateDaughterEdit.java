/* UndoableCreateDaughterEdit.java */

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

package nl.rug.syntree.editor.manager.edit;

import javax.swing.undo.AbstractUndoableEdit;

import nl.rug.syntree.editor.TreeEditorBridge;
import nl.rug.syntree.tree.component.NodeElement;

/**
 * This class represents an undoable create daughter edit.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class UndoableCreateDaughterEdit extends AbstractUndoableEdit {
	/**
	 * Serial version identifier constant
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Presentation name constant
	 */
	public static final String PRESENTATION_NAME = "Create daughter";

	/**
	 * Mother node
	 */
	protected NodeElement motherNode;

	/**
	 * Daughter node
	 */
	protected NodeElement daughterNode;

	/**
	 * Next daughter node
	 */
	protected NodeElement nextDaughterNode;

	/**
	 * Tree editor bridge
	 */
	protected TreeEditorBridge treeEditorBridge;

	/**
	 * Constructs a new undoable create daughter edit.
	 * 
	 * @param treeEditorBridge
	 *            tree editor bridge
	 * @param motherNode
	 *            mother node
	 * @param daughterNode
	 *            daughter node
	 * @param nextDaughterNode
	 *            next daughter node
	 */
	public UndoableCreateDaughterEdit(TreeEditorBridge treeEditorBridge,
			NodeElement motherNode, NodeElement daughterNode,
			NodeElement nextDaughterNode) {
		this.treeEditorBridge = treeEditorBridge;
		this.motherNode = motherNode;
		this.daughterNode = daughterNode;
		this.nextDaughterNode = nextDaughterNode;
	}

	/**
	 * Undo the last significant create daughter edit.
	 */
	public void undo() {
		super.undo();
		treeEditorBridge.removeDaughter(motherNode, daughterNode);
	}

	/**
	 * Redo the last significant create daughter edit.
	 */
	public void redo() {
		super.redo();
		treeEditorBridge
				.addDaughter(motherNode, daughterNode, nextDaughterNode);
	}

	/**
	 * Returns the presentation name of this undoable edit
	 * 
	 * @return presentation name
	 */
	public String getPresentationName() {
		return PRESENTATION_NAME;
	}
}