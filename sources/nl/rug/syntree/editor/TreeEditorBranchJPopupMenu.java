/* TreeEditorBranchJPopupMenu.java */

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

import nl.rug.syntree.tree.component.BranchElement;
import nl.rug.syntree.tree.component.NodeElement;
import nl.rug.syntree.ui.UIJMenuItem;

/**
 * This class represents a branch options popup menu.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class TreeEditorBranchJPopupMenu extends JPopupMenu {
	/**
	 * Serial version identifier constant
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Tree editor bridge
	 */
	protected TreeEditorBridge treeEditorBridge;

	/**
	 * Branch element
	 */
	protected BranchElement branchElement;

	/**
	 * Node element
	 */
	protected NodeElement nodeElement;

	/**
	 * Constructs a new branch options popup menu.
	 * 
	 * @param treeEditorBridge
	 *            tree editor bridge
	 * @param nodeElement
	 *            node element
	 */
	public TreeEditorBranchJPopupMenu(TreeEditorBridge treeEditorBridge,
			NodeElement nodeElement) {
		super();
		this.treeEditorBridge = treeEditorBridge;
		this.nodeElement = nodeElement;
		this.branchElement = nodeElement.getBranchElement();

		// switch between line and triangle
		String switchActionIdentifier = "Switch to: ";
		if (branchElement.isLine())
			switchActionIdentifier += "Triangle";
		if (branchElement.isTriangle())
			switchActionIdentifier += "Line";
		this.add(new UIJMenuItem(switchActionIdentifier, KeyEvent.VK_S, 'S',
				new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						invertBranchType();
					}
				}, true));
	}

	/**
	 * Inverts the branch type of the branch element.
	 */
	public void invertBranchType() {
		// switch
		if (branchElement.isLine())
			branchElement.setType(BranchElement.TRIANGLE);
		else
			branchElement.setType(BranchElement.LINE);

		// taint
		nodeElement.setTaintMode(NodeElement.TAINTED);
		nodeElement.getMother().setTaintMode(NodeElement.TAINTED);

		// draw
		treeEditorBridge.getUIJInternalFrame().getTreeEditorJSVGScrollPane()
				.getTreeDrawer().draw();
		treeEditorBridge.getTreeRepresentation().getSVGDocument()
				.computeViewBox();
	}
}