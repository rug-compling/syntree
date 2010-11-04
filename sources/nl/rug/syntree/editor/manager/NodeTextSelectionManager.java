/* NodeTextSelectionManager.java */

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

import java.awt.Cursor;

import nl.rug.syntree.editor.TreeEditorBridge;
import nl.rug.syntree.tree.component.NodeElement;

import org.apache.batik.gvt.Selectable;
import org.apache.batik.gvt.event.EventDispatcher;
import org.apache.batik.gvt.event.GraphicsNodeMouseEvent;
import org.apache.batik.gvt.event.GraphicsNodeMouseListener;
import org.apache.batik.gvt.event.SelectionListener;
import org.apache.batik.swing.gvt.AbstractJGVTComponent;
import org.apache.batik.swing.gvt.TextSelectionManager;

/**
 * This class represents a node text selection manager.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class NodeTextSelectionManager extends TextSelectionManager {
	/**
	 * 
	 */
	protected TreeEditorBridge treeEditorBridge;

	/**
	 * 
	 */
	protected MouseListener mouseListener;

	/**
	 * 
	 */
	protected EventDispatcher eventDispatcher;

	/**
     * 
     */
	protected SelectionListener nodeTextSelectionListener;

	/**
	 * Constructs a new node text selection manager.
	 */
	public NodeTextSelectionManager(TreeEditorBridge treeEditorBridge,
			AbstractJGVTComponent component, EventDispatcher eventDispatcher) {
		super(component, eventDispatcher);

		this.treeEditorBridge = treeEditorBridge;
		this.eventDispatcher = eventDispatcher;

		eventDispatcher.removeGraphicsNodeMouseListener(super.mouseListener);
		mouseListener = new MouseListener();
		eventDispatcher.addGraphicsNodeMouseListener(mouseListener);

		this.setSelectionOverlayXORMode(true);
	}

	/**
	 * To implement a GraphicsNodeMouseListener.
	 */
	protected class MouseListener implements GraphicsNodeMouseListener {
		public void mouseClicked(GraphicsNodeMouseEvent evt) {

			if (evt.getSource() instanceof Selectable) {
				textSelector.mouseClicked(evt);
			}

		}

		public void mousePressed(GraphicsNodeMouseEvent evt) {
			float x = evt.getX();
			float y = evt.getY();
			NodeElement nodeElement = treeEditorBridge.getUIJInternalFrame()
					.getTreeEditorJSVGScrollPane().getTreeEditorJSVGCanvas()
					.nodeElementAt(x, y);
			if (nodeElement != null) {
				if (nodeElement.equals(treeEditorBridge
						.getNodeSelectionManager().getProminentNode())) {
					if (evt.getSource() instanceof Selectable) {
						textSelector.mousePressed(evt);
					} else if (selectionHighlight != null) {
						textSelector.clearSelection();
					}
				}
			}
		}

		public void mouseReleased(GraphicsNodeMouseEvent evt) {
			float x = evt.getX();
			float y = evt.getY();
			NodeElement nodeElement = treeEditorBridge.getUIJInternalFrame()
					.getTreeEditorJSVGScrollPane().getTreeEditorJSVGCanvas()
					.nodeElementAt(x, y);
			if (nodeElement != null) {
				if (nodeElement.equals(treeEditorBridge
						.getNodeSelectionManager().getProminentNode())) {
					textSelector.mouseReleased(evt);
				}
			}
		}

		public void mouseEntered(GraphicsNodeMouseEvent evt) {
			float x = evt.getX();
			float y = evt.getY();
			NodeElement nodeElement = treeEditorBridge.getUIJInternalFrame()
					.getTreeEditorJSVGScrollPane().getTreeEditorJSVGCanvas()
					.nodeElementAt(x, y);
			if (nodeElement != null) {
				if (nodeElement.equals(treeEditorBridge
						.getNodeSelectionManager().getProminentNode())) {
					if (evt.getSource() instanceof Selectable) {
						textSelector.mouseEntered(evt);
						previousCursor = component.getCursor();
						if (previousCursor.getType() == Cursor.DEFAULT_CURSOR) {
							component.setCursor(TEXT_CURSOR);
						}
					}
				}
			}
		}

		public void mouseExited(GraphicsNodeMouseEvent evt) {

			if (evt.getSource() instanceof Selectable) {
				textSelector.mouseExited(evt);
				if (component.getCursor() == TEXT_CURSOR) {
					component.setCursor(previousCursor);
				}
			}
		}

		public void mouseDragged(GraphicsNodeMouseEvent evt) {
			float x = evt.getX();
			float y = evt.getY();
			NodeElement nodeElement = treeEditorBridge.getUIJInternalFrame()
					.getTreeEditorJSVGScrollPane().getTreeEditorJSVGCanvas()
					.nodeElementAt(x, y);
			if (nodeElement != null) {
				if (nodeElement.equals(treeEditorBridge
						.getNodeSelectionManager().getProminentNode())) {
					textSelector.mouseDragged(evt);
				}
			}
		}

		public void mouseMoved(GraphicsNodeMouseEvent evt) {
		}
	}

	/**
	 */
	public void disableMouseListener() {
		clearSelection();
		eventDispatcher.removeGraphicsNodeMouseListener(mouseListener);
	}

	/**
	 */
	public void enableMouseListener() {
		eventDispatcher.addGraphicsNodeMouseListener(mouseListener);
	}
}