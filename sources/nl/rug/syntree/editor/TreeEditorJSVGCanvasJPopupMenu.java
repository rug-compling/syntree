/* TreeEditorJSVGCanvasJPopupMenu.java */

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

package nl.rug.syntree.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JPopupMenu;

import nl.rug.syntree.ui.UIJMenuItem;

/**
 * This class represents a popup menu for the SVG canvas.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class TreeEditorJSVGCanvasJPopupMenu extends JPopupMenu {
	/**
	 * Serial version identifier constant
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Tree editor bridge
	 */
	protected TreeEditorBridge treeEditorBridge;

	/**
	 * Constructs a new SVG canvas popup menu.
	 * 
	 * @param treeEditorBridge
	 *            tree editor bridge
	 */
	public TreeEditorJSVGCanvasJPopupMenu(TreeEditorBridge treeEditorBridge) {
		super();
		this.treeEditorBridge = treeEditorBridge;

		// add a tree
		this.add(new UIJMenuItem("Add a Tree", KeyEvent.VK_A, 'A',
				new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						addTree();
					}
				}, true));
	}

	/**
	 * Adds a tree to the tree representation.
	 */
	public void addTree() {
		treeEditorBridge.createTree();
	}
}