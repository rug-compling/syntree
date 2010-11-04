/* TextInteractor.java */

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

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Vector;

import nl.rug.syntree.editor.TreeEditorJSVGScrollPane;
import nl.rug.syntree.editor.overlay.TextInteractorCursorOverlay;
import nl.rug.syntree.editor.text.TextEditor;
import nl.rug.syntree.tree.component.NodeElement;
import nl.rug.syntree.tree.component.NodeRectElement;
import nl.rug.syntree.tree.component.TSpanElement;
import nl.rug.syntree.tree.component.TextElement;

import org.apache.batik.bridge.UpdateManager;
import org.apache.batik.dom.svg.SVGOMPoint;
import org.apache.batik.swing.gvt.InteractorAdapter;
import org.apache.batik.util.RunnableQueue;
import org.w3c.dom.svg.SVGTextElement;

/**
 * This class represents a text interactor for the tree representation editor.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class TextInteractor extends InteractorAdapter {
	/**
	 * Text node character update interval
	 */
	protected static final int CHARACTER_UPDATE_INTERVAL = 3;

	/**
	 * Tree editor scroll pane
	 */
	protected TreeEditorJSVGScrollPane treeEditorJSVGScrollPane;

	/**
	 * Boolean indicating whether the interaction should start
	 */
	protected boolean startInteraction;

	/**
	 * Boolean indicating whether the interaction is finished
	 */
	protected boolean interactionFinished;

	/**
	 * Text interactor cursor overlay
	 */
	protected TextInteractorCursorOverlay textInteractorCursorOverlay;

	/**
	 * Boolean indicating whether a function key is pressed
	 */
	protected boolean functionKeyPressed = false;

	/**
	 * Node element
	 */
	protected NodeElement nodeElement;

	/**
	 * Text element
	 */
	protected TextElement textElement;

	/**
	 * Cursor position in the text content
	 */
	protected int position;

	/**
	 * Current amount of characters in buffer
	 */
	protected int characterUpdateBuffer = 0;

	/**
	 * Constructs a new text interactor.
	 * 
	 * @param treeEditorJSVGScrollPane
	 *            tree editor scroll pane
	 */
	public TextInteractor(TreeEditorJSVGScrollPane treeEditorJSVGScrollPane) {
		this.treeEditorJSVGScrollPane = treeEditorJSVGScrollPane;
		this.textInteractorCursorOverlay = new TextInteractorCursorOverlay(this);
	}

	// start and end interaction /////////////////////////////////////////////

	/**
	 * Starts the interaction.
	 */
	@SuppressWarnings("unchecked")
	public void start() {
		// disable the text selection mouse listener
		treeEditorJSVGScrollPane.getTreeEditorJSVGCanvas()
				.getNodeTextSelectionManager().disableMouseListener();

		// node and text element
		nodeElement = treeEditorJSVGScrollPane.getTreeEditorBridge()
				.getNodeSelectionManager().getProminentNode();
		textElement = nodeElement.getTextElement();

		// enter editing mode
		if (textElement.isVisible()) {

			// set edit highlighting
			setHighlightingMode(NodeRectElement.EDIT);

			// preprocess new nodes when necessary
			if (nodeElement.getIsNewNode()) {
				preprocessNewNode();
				invokeRedraw(false);
			}

			// set cursor position, and add cursor
			setPosition(textElement.getText().length() - 1);
			treeEditorJSVGScrollPane.getTreeEditorJSVGCanvas().getOverlays()
					.add(textInteractorCursorOverlay);
			updateCursor();

			// disable recenter on resize
			treeEditorJSVGScrollPane.getTreeEditorJSVGCanvas()
					.setRecenterOnResize(false);

			// reset character update buffer
			characterUpdateBuffer = 0;

			// start interaction
			interactionFinished = false;
			startInteraction = true;
			startInteraction(null);
		}
	}

	/**
	 * Ends the interaction.
	 */
	public void end() {
		// redraw the tree.
		// redrawTree();

		
		// FIX THIS...
		UpdateManager um = treeEditorJSVGScrollPane.getTreeEditorJSVGCanvas()
				.getUpdateManager();
		RunnableQueue rq = um.getUpdateRunnableQueue();
		if (um != null && rq != null)
			rq.invokeLater(new Runnable() {
				public void run() {
					Vector<NodeElement> nodeVector = nodeElement
							.getTreeElement().getNodeVector();
					for (int i = 0; i < nodeVector.size(); i++) {
						nodeVector.elementAt(i).setTaintMode(
								NodeElement.TAINTED);
					}

					invokeRedraw(true);
				}

			});

		// re-enable recenter on resize
		treeEditorJSVGScrollPane.getTreeEditorJSVGCanvas().setRecenterOnResize(
				true);

		// remove cursor overlay
		treeEditorJSVGScrollPane.getTreeEditorJSVGCanvas().getOverlays()
				.remove(textInteractorCursorOverlay);

		// repaint the canvas
		treeEditorJSVGScrollPane.getTreeEditorJSVGCanvas().repaint();

		// reset node highlight mode
		setHighlightingMode(NodeRectElement.SELECT);

		// re-enable text selection mouse listener
		treeEditorJSVGScrollPane.getTreeEditorJSVGCanvas()
				.getNodeTextSelectionManager().enableMouseListener();

		// end interaction
		interactionFinished = true;
		startInteraction = false;
	}

	/**
	 * Verifies whether the specified event will start the interaction.
	 * 
	 * Binding: invoked programmatically
	 * 
	 * @param ie
	 *            input event
	 */
	public boolean startInteraction(InputEvent ie) {
		return startInteraction;
	}

	/**
	 * Verifies whether the interaction will be ended.
	 * 
	 * @return boolean
	 */
	public boolean endInteraction() {
		return interactionFinished;
	}

	// event handlers ////////////////////////////////////////////////////////

	/**
	 * Processes the specified mouse clicked event.
	 * 
	 * @param me
	 *            mouse event
	 */
	public void mouseClicked(MouseEvent me) {
		end();
	}

	/**
	 * Processes the specified key typed event.
	 * 
	 * @param ke
	 *            key event
	 */
	public void keyTyped(KeyEvent ke) {
		if (!functionKeyPressed)
			addChar(ke.getKeyChar());
	}

	/**
	 * Processes the specified key pressed event.
	 * 
	 * @param ke
	 *            key event
	 */
	public void keyPressed(KeyEvent ke) {
		functionKeyPressed = false;

		// enter
		if (ke.getKeyCode() == KeyEvent.VK_ENTER
				&& ke.getModifiers() != KeyEvent.SHIFT_MASK) {
			end();
			functionKeyPressed = true;
		}

		// enter + shift
		if (ke.getKeyCode() == KeyEvent.VK_ENTER
				&& ke.getModifiers() == KeyEvent.SHIFT_MASK) {
			addLine();
			functionKeyPressed = true;
		}

		// escape
		if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
			end();
			functionKeyPressed = true;
		}

		// home
		if (ke.getKeyCode() == KeyEvent.VK_HOME) {
			setPosition(1);
			functionKeyPressed = true;
			updateCursor();
		}

		// end
		if (ke.getKeyCode() == KeyEvent.VK_END) {
			setPosition(textElement.getText().length() - 1);
			functionKeyPressed = true;
			updateCursor();
		}

		// left arrow
		if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
			setPosition(position - 1);
			functionKeyPressed = true;
			updateCursor();
		}

		// right arrow
		if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
			setPosition(position + 1);
			functionKeyPressed = true;
			updateCursor();
		}

		// back space
		if (ke.getKeyCode() == KeyEvent.VK_BACK_SPACE
				&& ke.getModifiers() != KeyEvent.SHIFT_MASK) {
			removeChar(position - 1);
			functionKeyPressed = true;
		}

		// back space + shift
		if (ke.getKeyCode() == KeyEvent.VK_BACK_SPACE
				&& ke.getModifiers() == KeyEvent.SHIFT_MASK) {
			removeLine();
			functionKeyPressed = true;
		}

		// delete
		if (ke.getKeyCode() == KeyEvent.VK_DELETE) {
			removeChar(position);
			functionKeyPressed = true;
		}
	}

	// helper methods ////////////////////////////////////////////////////////

	/**
	 * Computes the coordinates of the text interactor cursor string overlay.
	 */
	public void computeOverlay() {
		float x1 = 0.0f;
		float y1 = 0.0f;
		float x2 = 0.0f;
		float y2 = 0.0f;

		// get character coordinates
		if (position == 0) {
			x1 = textElement.getCharStartX(position);
			y1 = -(0.5f * textElement.getCharHeight(position));
			x2 = x1;
			y2 = textElement.getCharStartY(position);
		} else {
			x1 = textElement.getCharEndX(position - 1);
			y1 = -(0.5f * textElement.getCharHeight(position - 1));
			x2 = x1;
			y2 = textElement.getCharEndY(position - 1);
		}

		// take care of whitespace
		// if (y1 == y2)

		// quick and dirty cursor fix
		y1 = y2 - 8.0f;

		// construct SVG points
		SVGTextElement svgTextElement = (SVGTextElement) textElement
				.getElement();
		SVGOMPoint x1y1Point = new SVGOMPoint(x1, y1);
		x1y1Point = (SVGOMPoint) x1y1Point.matrixTransform(svgTextElement
				.getScreenCTM());
		SVGOMPoint x2y2Point = new SVGOMPoint(x2, y2);
		x2y2Point = (SVGOMPoint) x2y2Point.matrixTransform(svgTextElement
				.getScreenCTM());

		// set cursor coordinates
		textInteractorCursorOverlay.setX1(x1y1Point.getX());
		textInteractorCursorOverlay.setY1(x1y1Point.getY());
		textInteractorCursorOverlay.setX2(x2y2Point.getX());
		textInteractorCursorOverlay.setY2(x2y2Point.getY());
	}

	/**
	 * Adds the specified key character to the node text.
	 * 
	 * @param keyChar
	 *            key character to add
	 */
	public void addChar(final char keyChar) {
		UpdateManager um = treeEditorJSVGScrollPane.getTreeEditorJSVGCanvas()
				.getUpdateManager();
		RunnableQueue rq = um.getUpdateRunnableQueue();
		if (um != null && rq != null)
			rq.invokeLater(new Runnable() {
				public void run() {
					boolean requiresRedraw = false;

					// add the character
					requiresRedraw = TextEditor.addChar(nodeElement
							.getTreeRepresentation(), nodeElement, keyChar,
							position);

					// process redraw
					invokeRedraw(requiresRedraw);

					// update cursor
					setPosition(position + 1);
					updateCursor();
				}
			});
	}

	/**
	 * Removes the character at the specified position.
	 * 
	 * @param charPosition
	 *            position of character to remove
	 */
	public void removeChar(final int charPosition) {
		UpdateManager um = treeEditorJSVGScrollPane.getTreeEditorJSVGCanvas()
				.getUpdateManager();
		RunnableQueue rq = um.getUpdateRunnableQueue();
		if (um != null && rq != null)
			rq.invokeLater(new Runnable() {
				public void run() {
					boolean requiresRedraw = false;

					// remove character
					requiresRedraw = TextEditor.removeChar(
							treeEditorJSVGScrollPane.getTreeEditorBridge()
									.getTreeRepresentation(), nodeElement,
							charPosition);

					// process redraw
					invokeRedraw(requiresRedraw);

					// update position
					setPosition(charPosition);
					updateCursor();
				}
			});
	}

	/**
	 * ??
	 */
	public void addLine() {
		UpdateManager um = treeEditorJSVGScrollPane.getTreeEditorJSVGCanvas()
				.getUpdateManager();
		RunnableQueue rq = um.getUpdateRunnableQueue();
		if (um != null && rq != null)
			rq.invokeLater(new Runnable() {
				public void run() {
					boolean requiresRedraw = false;

					// add line delta
					requiresRedraw = TextEditor.addLine(
							treeEditorJSVGScrollPane.getTreeEditorBridge()
									.getTreeRepresentation(), nodeElement,
							position);

					// process redraw
					invokeRedraw(requiresRedraw);
					// invokeRedraw(requiresRedraw);

					// update position
					setPosition(position + 1);
					updateCursor();
				}
			});
	}

	/**
	 * 
	 */
	public void removeLine() {
		UpdateManager um = treeEditorJSVGScrollPane.getTreeEditorJSVGCanvas()
				.getUpdateManager();
		RunnableQueue rq = um.getUpdateRunnableQueue();
		if (um != null && rq != null)
			rq.invokeLater(new Runnable() {
				public void run() {
					boolean requiresRedraw = false;

					// add line delta
					requiresRedraw = TextEditor.removeLine(
							treeEditorJSVGScrollPane.getTreeEditorBridge()
									.getTreeRepresentation(), nodeElement,
							position);

					// process redraw
					invokeRedraw(requiresRedraw);
					// invokeRedraw(requiresRedraw);

					// update position
					setPosition(textElement.getText().length() - 1);
					updateCursor();
				}
			});
	}

	/**
	 * Invokes an appropriate redraw of the modified tree representation.
	 * 
	 * @param fullRedrawRequired
	 *            full redraw required or not
	 */
	public void invokeRedraw(boolean fullRedrawRequired) {
		if (fullRedrawRequired) {
			treeEditorJSVGScrollPane.getTreeDrawer().draw();
			treeEditorJSVGScrollPane.getTreeEditorBridge()
					.getTreeRepresentation().getSVGDocument().computeViewBox();
		} else {
			// redraw node
			if (characterUpdateBuffer < CHARACTER_UPDATE_INTERVAL)
				treeEditorJSVGScrollPane.getTreeDrawer().draw(nodeElement);

			// redraw tree
			if (characterUpdateBuffer == CHARACTER_UPDATE_INTERVAL)
				treeEditorJSVGScrollPane.getTreeDrawer().draw();

			// compute viewBox
			treeEditorJSVGScrollPane.getTreeEditorBridge()
					.getTreeRepresentation().getSVGDocument().computeViewBox();

			// update character update buffer
			characterUpdateBuffer++;
			if (characterUpdateBuffer > CHARACTER_UPDATE_INTERVAL)
				characterUpdateBuffer = 0;
		}
	}

	/**
	 * Empties a node that is marked as being new.
	 */
	public void preprocessNewNode() {
		String text = textElement.getText();
		for (int i = 0; i < text.length(); i++)
			TextEditor.removeChar(nodeElement.getTreeRepresentation(),
					nodeElement, i);
		nodeElement.setIsNewNode(false);
	}

	/**
	 * Preempts a redraw action in the update manager's runnable queue.
	 */
	public void redrawTree() {
		UpdateManager um = treeEditorJSVGScrollPane.getTreeEditorJSVGCanvas()
				.getUpdateManager();
		RunnableQueue rq = um.getUpdateRunnableQueue();
		if (um != null && rq != null)
			rq.preemptLater(new Runnable() {
				public void run() {
					treeEditorJSVGScrollPane.getTreeDrawer().draw();
					treeEditorJSVGScrollPane.getTreeEditorBridge()
							.getTreeRepresentation().getSVGDocument()
							.computeViewBox();
				}
			});
	}

	/**
	 * Preempts a change in highlight mode.
	 */
	public void setHighlightingMode(final int highlightingMode) {
		UpdateManager um = treeEditorJSVGScrollPane.getTreeEditorJSVGCanvas()
				.getUpdateManager();
		RunnableQueue rq = um.getUpdateRunnableQueue();
		if (um != null && rq != null)
			rq.preemptLater(new Runnable() {
				public void run() {
					nodeElement.getNodeRectElement().setHighlightMode(
							highlightingMode);
				}
			});
	}

	/**
	 * Recomputes and repaints the cursor overlay.
	 */
	public void updateCursor() {
		computeOverlay();
		treeEditorJSVGScrollPane.getTreeEditorJSVGCanvas().repaint();
	}

	// accessors and mutators ////////////////////////////////////////////////

	/**
	 * Sets the cursor position to the specified position.
	 * 
	 * @param position
	 *            cursor position
	 */
	public void setPosition(int position) {
		if (position > 0 && position < textElement.getText().length()) {
			int length = 0;
			for (int i = 0; i < textElement.getTSpanVector().size(); i++) {
				TSpanElement tspanElement = textElement.getTSpanVector()
						.elementAt(i);
				length += tspanElement.getText().length();
				if (position <= length) {
					// head type
					if (tspanElement.getType() == TSpanElement.HEAD_TYPE) {
						if (this.position > position) {
							setPosition(position - 1);
						}
						this.position = position;

					}

					// text
					if (tspanElement.getType() == TSpanElement.TEXT_TYPE) {
						this.position = position;
					}

					// tail type
					if (tspanElement.getType() == TSpanElement.TAIL_TYPE) {
						if (this.position < position)
							setPosition(position + 1);
						else if (this.position > position)
							setPosition(position - 1);
					}
					i = textElement.getTSpanVector().size();
				}
			}
		}
	}

	/**
	 * Returns the current cursor position.
	 * 
	 * @return position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * Returns the node element that is being edited.
	 * 
	 * @return node element
	 */
	public NodeElement getNodeElement() {
		return nodeElement;
	}

	/**
	 * Returns the text element of the node element that is being edited.
	 * 
	 * @return text element
	 */
	public TextElement getTextElement() {
		return textElement;
	}

	/**
	 * Returns the tree editor scroll pane.
	 * 
	 * @return tree editor scroll pane
	 */
	public TreeEditorJSVGScrollPane getTreeEditorJSVGScrollPane() {
		return treeEditorJSVGScrollPane;
	}
}