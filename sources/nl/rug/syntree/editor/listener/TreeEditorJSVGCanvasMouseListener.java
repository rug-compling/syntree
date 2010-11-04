/* TreeEditorJSVGCanvasMouseListener.java */

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

package nl.rug.syntree.editor.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import nl.rug.syntree.editor.TreeEditorJSVGCanvasJPopupMenu;
import nl.rug.syntree.editor.TreeEditorJSVGScrollPane;

/**
 * This class represents a mouse listener for the svg canvas.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class TreeEditorJSVGCanvasMouseListener extends MouseAdapter {
	/**
	 * Tree editor bridge
	 */
	protected TreeEditorJSVGScrollPane treeEditorJSVGScrollPane;

	/**
	 * Constructs a new mouse listener for the svg canvas.
	 */
	public TreeEditorJSVGCanvasMouseListener(
			TreeEditorJSVGScrollPane treeEditorJSVGScrollPane) {
		this.treeEditorJSVGScrollPane = treeEditorJSVGScrollPane;
	}

	/**
	 * Processes a mouse clicked event.
	 * 
	 * @param me
	 *            mouse event
	 */
	public void mouseClicked(MouseEvent me) {
		if ((me.getButton() == MouseEvent.BUTTON3 && (me.getModifiers() & MouseEvent.CTRL_MASK) != 0)
				|| (me.getButton() == MouseEvent.BUTTON2 && (me.getModifiers() & MouseEvent.CTRL_MASK) == 0)) {
			TreeEditorJSVGCanvasJPopupMenu treeEditorJSVGCanvasJPopupMenu;
			treeEditorJSVGCanvasJPopupMenu = new TreeEditorJSVGCanvasJPopupMenu(
					treeEditorJSVGScrollPane.getTreeEditorBridge());
			treeEditorJSVGCanvasJPopupMenu.show(treeEditorJSVGScrollPane
					.getTreeEditorJSVGCanvas(), me.getX(), me.getY());
		}
	}

	/**
	 * Processes a mouse entered event.
	 * 
	 * @param me
	 *            mouse event
	 */
	public void mouseEntered(MouseEvent me) {
		treeEditorJSVGScrollPane.getTreeEditorJSVGCanvas().requestFocus();
	}
}