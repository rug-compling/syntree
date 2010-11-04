/* TreeEditorJSVGCanvas.java */

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

import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.Vector;

import nl.rug.syntree.editor.manager.NodeTextSelectionManager;
import nl.rug.syntree.tree.component.NodeElement;
import nl.rug.syntree.tree.component.TreeElement;

import org.apache.batik.dom.AbstractElement;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.gvt.event.EventDispatcher;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.gvt.TextSelectionManager;

/**
 * This class represents a Scalable Vector Graphics (SVG) canvas which
 * facilitates the display of SVG images.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class TreeEditorJSVGCanvas extends JSVGCanvas {
	/**
	 * Serial version identifier constant
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Tree editor bridge
	 */
	protected TreeEditorBridge treeEditorBridge;

	/**
	 * Node text selection manager
	 */
	protected NodeTextSelectionManager nodeTextSelectionManager;

	/**
	 * Constructs a new Scalable Vector Graphics canvas.
	 * 
	 * @param treeEditorBridge
	 *            tree editor bridge
	 */
	public TreeEditorJSVGCanvas(TreeEditorBridge treeEditorBridge) {
		super(null, true, true);
		this.setDoubleBuffered(true);
		this.setDoubleBufferedRendering(true);
		this.setDocumentState(JSVGCanvas.ALWAYS_DYNAMIC);
		this.getInputMap().clear();
		this.treeEditorBridge = treeEditorBridge;
	}

	/**
	 * Constructs a new text selection manager for the specified event
	 * dispatcher
	 * 
	 * @param eventDispatcher
	 *            even dispatcher
	 * @return text selection manager
	 */
	protected TextSelectionManager createTextSelectionManager(
			EventDispatcher eventDispatcher) {
		nodeTextSelectionManager = new NodeTextSelectionManager(
				treeEditorBridge, this, eventDispatcher);
		return nodeTextSelectionManager;
	}

	/**
	 * Returns the node element at the specified x-coordinate and y-coordinate.
	 * 
	 * @param x
	 *            x-coordinate
	 * @param y
	 *            y-coordinate
	 */
	public NodeElement nodeElementAt(float x, float y) {
		NodeElement nodeElement = null;
		Point2D point2D = new Point2D.Float(x, y);
		AffineTransform affineTransform = getRenderingTransform();
		try {
			affineTransform = affineTransform.createInverse();
			point2D = affineTransform.transform(point2D, null);
		} catch (NoninvertibleTransformException nte) {
			System.err.println("[MdomInteractor::nodeElementAt()");
			nte.printStackTrace();
		}
		GraphicsNode graphicsNode = getGraphicsNode().nodeHitAt(point2D);
		AbstractElement abstractElement = (AbstractElement) getUpdateManager()
				.getBridgeContext().getElement(graphicsNode);
		if (abstractElement != null)
			while (abstractElement.getTagName().compareTo("g") != 0)
				abstractElement = (AbstractElement) abstractElement
						.getParentNode();
		Vector<TreeElement> treeVector = treeEditorBridge
				.getTreeRepresentation().getTreeVector();
		for (int i = 0; i < treeVector.size(); i++) {
			Vector<NodeElement> nodeVector = treeVector.elementAt(i)
					.getNodeVector();
			for (int z = 0; z < nodeVector.size(); z++) {
				if (nodeVector.elementAt(z).getElement() == abstractElement) {
					nodeElement = nodeVector.elementAt(z);
					z = nodeVector.size();
					i = treeVector.size();
				}
			}
		}
		return nodeElement;
	}

	/**
	 * Returns the node text selection manager.
	 * 
	 * @return node text selection manager
	 */
	public NodeTextSelectionManager getNodeTextSelectionManager() {
		return nodeTextSelectionManager;
	}
}