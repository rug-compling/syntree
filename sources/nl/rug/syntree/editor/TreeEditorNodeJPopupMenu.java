/* TreeEditorNodeJPopupMenu.java */

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

import nl.rug.syntree.editor.manager.NodeSelectionManager;
import nl.rug.syntree.tree.component.NodeElement;
import nl.rug.syntree.tree.component.TextElement;
import nl.rug.syntree.ui.UIIcons;
import nl.rug.syntree.ui.UIJMenuItem;

/**
 * This class represents a node options popup menu.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class TreeEditorNodeJPopupMenu extends JPopupMenu {
	/**
	 * Serial version identifier constant
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new node options popup menu.
	 * 
	 * @param treeEditorBridge
	 *            tree editor bridge
	 * @param nodeElement
	 *            node element
	 */
	public TreeEditorNodeJPopupMenu(final TreeEditorBridge treeEditorBridge,
			final NodeElement nodeElement) {
		super();
		final NodeSelectionManager nodeSelectionManager = treeEditorBridge
				.getNodeSelectionManager();
		final TextElement textElement = nodeElement.getTextElement();

		// edit node /////////////////////////////////////////////////////////

		this.add(new UIJMenuItem("Edit Node", UIIcons.NODE_EDIT, KeyEvent.VK_E,
				'E', new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						nodeSelectionManager.setProminentNode(nodeElement);
						if (nodeElement.getTextElement().isVisible())
							treeEditorBridge.getUIJInternalFrame()
									.getTreeEditorJSVGScrollPane()
									.getTextInteractor().start();
					}
				}, textElement.isVisible()));

		// switch between visible and invisible text /////////////////////////

		String switchActionIdentifier = "Set Visibility: ";
		if (textElement.isVisible())
			switchActionIdentifier += "Hidden";
		else
			switchActionIdentifier += "Visible";
		this.add(new UIJMenuItem(switchActionIdentifier, KeyEvent.VK_V, 'V',
				new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						textElement.setVisible(!textElement.isVisible());

						// force redraw
						treeEditorBridge.getUIJInternalFrame()
								.getTreeEditorJSVGScrollPane().getTreeDrawer()
								.draw();
						treeEditorBridge.getTreeRepresentation()
								.getSVGDocument().computeViewBox();

					}
				}, true));

		// separator /////////////////////////////////////////////////////////

		this.addSeparator();

		// add a daughter node ///////////////////////////////////////////////

		this.add(new UIJMenuItem("Add a Daughter", UIIcons.NODE_ADD_DAUGHTER,
				KeyEvent.VK_D, 'D', new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						nodeSelectionManager.setProminentNode(nodeElement);
						treeEditorBridge.getUIJInternalFrame()
								.getTreeEditorJSVGScrollPane()
								.getCreateBranchInteractor().start();
					}
				}, true));

		// remove node ///////////////////////////////////////////////////////

		this.add(new UIJMenuItem("Remove Node", UIIcons.NODE_DELETE,
				KeyEvent.VK_R, 'R', new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						nodeSelectionManager.setProminentNode(nodeElement);
						NodeElement motherNode = nodeElement.getMother();
						treeEditorBridge
								.removeDaughter(motherNode, nodeElement);
					}
				}, true));

		// remove tree ///////////////////////////////////////////////////////

		this.add(new UIJMenuItem("Remove Tree", UIIcons.NODE_DELETE,
				KeyEvent.VK_R, 'R', new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						nodeSelectionManager.setProminentNode(nodeElement);
						treeEditorBridge.removeTree(nodeElement
								.getTreeElement());
					}
				}, (treeEditorBridge.getTreeRepresentation().getTreeVector()
						.size() > 1)));

		// separator /////////////////////////////////////////////////////////

		this.addSeparator();

		// select (sub)tree //////////////////////////////////////////////////

		this.add(new UIJMenuItem("Select (sub)tree",
				UIIcons.NODE_SELECT_SUBTREE, KeyEvent.VK_S, 'S',
				new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						nodeSelectionManager.setProminentNode(nodeElement);
						treeEditorBridge.getNodeSelectionManager()
								.selectSubtree();
						treeEditorBridge.updateSelectionHighlighting();
					}
				}, true));

		// separator /////////////////////////////////////////////////////////

		this.addSeparator();

		// undo //////////////////////////////////////////////////////////////

		this.add(new UIJMenuItem(treeEditorBridge.getUndoRedoManager()
				.getUndoPresentationName(), UIIcons.EDIT_UNDO, KeyEvent.VK_U,
				'U', new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						treeEditorBridge.getUndoRedoManager().undo();
					}
				}, treeEditorBridge.getUndoRedoManager().canUndo()));

		// redo //////////////////////////////////////////////////////////////

		this.add(new UIJMenuItem(treeEditorBridge.getUndoRedoManager()
				.getRedoPresentationName(), UIIcons.EDIT_REDO, KeyEvent.VK_R,
				'R', new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						treeEditorBridge.getUndoRedoManager().redo();

					}
				}, treeEditorBridge.getUndoRedoManager().canRedo()));

		// separator /////////////////////////////////////////////////////////

		this.addSeparator();

		// copy (sub)tree ////////////////////////////////////////////////////

		this.add(new UIJMenuItem("Copy (sub)tree", UIIcons.NODE_COPY,
				KeyEvent.VK_C, 'C', new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						treeEditorBridge.getNodeCopyCutManager().copy(
								nodeElement);
					}
				}, true));

		// cut (sub)tree /////////////////////////////////////////////////////

		this.add(new UIJMenuItem("Cut (sub)tree", UIIcons.NODE_CUT,
				KeyEvent.VK_U, 'U', new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						treeEditorBridge.getNodeCopyCutManager().cut(
								nodeElement);
					}
				}, true));

		// paste /////////////////////////////////////////////////////////////

		this.add(new UIJMenuItem("Paste", UIIcons.NODE_PASTE, KeyEvent.VK_P,
				'P', new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						treeEditorBridge.getNodeCopyCutManager().paste(
								nodeElement);
					}
				}, treeEditorBridge.getNodeCopyCutManager().canPaste()));

		// separator /////////////////////////////////////////////////////////

		this.addSeparator();

		// multidominance relation ///////////////////////////////////////////

		this.add(new UIJMenuItem("Add Mdom Relation", UIIcons.NODE_ADD_MDOM,
				KeyEvent.VK_M, 'M', new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						treeEditorBridge
								.createMultidominanceRelation(nodeElement);
					}
				}, true));
	}
}