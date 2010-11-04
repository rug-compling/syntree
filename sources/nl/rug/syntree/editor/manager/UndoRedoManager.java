/* UndoRedoManager.java */

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

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

/**
 * This class represents a Undo/Redo manager.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class UndoRedoManager extends UndoManager {
	/**
	 * Serial version identifier constant.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new undo/redo manager.
	 */
	public UndoRedoManager() {
		super();
	}

	/**
	 * Redo the last significant edit.
	 */
	public void redo() {
		if (canRedo()) {
			try {
				super.redo();
			} catch (CannotRedoException cre) {
				cre.printStackTrace();
			}
		}
	}

	/**
	 * Undo the last significant edit
	 */
	public void undo() {
		if (canUndo()) {
			try {
				super.undo();
			} catch (CannotUndoException cue) {
				cue.printStackTrace();
			}
		}
	}
}