/* AbstractBranchInteractor.java */

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
import nl.rug.syntree.editor.overlay.BranchInteractorOverlay;
import nl.rug.syntree.tree.component.NodeElement;

import org.apache.batik.dom.svg.SVGOMPoint;
import org.apache.batik.dom.svg.SVGOMPolygonElement;
import org.apache.batik.swing.gvt.InteractorAdapter;
import org.w3c.dom.svg.SVGPointList;

/**
 * This class represents an abstract branch interactor for the tree representation editor.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class AbstractBranchInteractor extends InteractorAdapter {
	/**
	 * Tree editor scroll pane
	 */
	protected TreeEditorJSVGScrollPane treeEditorJSVGScrollPane;

	/**
	 * Branch interactor overlay
	 */
	protected BranchInteractorOverlay branchInteractorOverlay;

	/**
	 * Boolean indicating whether the interaction should start
	 */
	protected boolean startInteraction;

	/**
	 * Boolean indicating whether the interaction is finished
	 */
	protected boolean interactionFinished;

	/**
	 * Last x-coordinate of the mouse pointer
	 */
	protected int x = 0;

	/**
	 * Last y-coordinate of the mouse pointer
	 */
	protected int y = 0;

	/**
	 * Position of the branch pendulum in the daughter set
	 */
	protected int position = 0;

	/**
	 * Node element
	 */
	protected NodeElement nodeElement;

	/**
	 * Daughter vector
	 */
	protected Vector<NodeElement> daughterVector;

	/**
	 * Constructs a new abstract branch interactor.
	 * 
	 * @param treeEditorJSVGScrollPane
	 *            tree editor scroll pane
	 */
	public AbstractBranchInteractor(TreeEditorJSVGScrollPane treeEditorJSVGScrollPane) {
		this.treeEditorJSVGScrollPane = treeEditorJSVGScrollPane;
		this.branchInteractorOverlay = new BranchInteractorOverlay(this);
		this.startInteraction = false;
		this.interactionFinished = false;
	}

	/**
	 * Starts the interaction.
	 */
	@SuppressWarnings("unchecked")
	public void start() {
		nodeElement = treeEditorJSVGScrollPane.getTreeEditorBridge()
				.getNodeSelectionManager().getProminentNode();
		daughterVector = nodeElement.getDaughterVector();
		if (daughterVector.size() > 0) {
			position = daughterVector.size() / 2;
			computeOverlay();
			treeEditorJSVGScrollPane.getTreeEditorJSVGCanvas().getOverlays()
					.add(branchInteractorOverlay);
			treeEditorJSVGScrollPane.getTreeEditorJSVGCanvas().repaint();
			interactionFinished = false;
			startInteraction = true;
			startInteraction(null);
		} else {
			performEdit();
			end();
		}
	}
	
	/**
	 * Ends the interaction.
	 */
	public void end() {
		treeEditorJSVGScrollPane.getTreeEditorJSVGCanvas().getOverlays()
				.remove(branchInteractorOverlay);
		treeEditorJSVGScrollPane.getTreeEditorJSVGCanvas().repaint();
		startInteraction = false;
		interactionFinished = true;
	}	
	
	/**
	 * Verifies whether the specified event will start the interaction.
	 * 
	 * Binding: invoked within program
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
	
	/**
	 * Processes the specified mouse moved mouse event.
	 * 
	 * @param me
	 *            mouse event
	 */
	public void mouseMoved(MouseEvent me) {
		if (Math.abs(me.getX() - x) > 1 && daughterVector.size() > 0) {
			SVGOMPolygonElement svgOMPolygonElement = null;
			if (me.getX() < x) {
				if (position == 0)
					svgOMPolygonElement = (SVGOMPolygonElement) daughterVector
							.elementAt(position).getBranchElement()
							.getElement();
				if (position > 0 && position <= daughterVector.size())
					svgOMPolygonElement = (SVGOMPolygonElement) daughterVector
							.elementAt(position - 1).getBranchElement()
							.getElement();
			}
			if (me.getX() > x) {
				if (position == 0)
					svgOMPolygonElement = (SVGOMPolygonElement) daughterVector
							.elementAt(position).getBranchElement()
							.getElement();
				if (position > 0 && position < daughterVector.size())
					svgOMPolygonElement = (SVGOMPolygonElement) daughterVector
							.elementAt(position).getBranchElement()
							.getElement();
				if (position == daughterVector.size())
					svgOMPolygonElement = (SVGOMPolygonElement) daughterVector
							.elementAt(position - 1).getBranchElement()
							.getElement();
			}
			SVGPointList svgPointList = svgOMPolygonElement.getPoints();
			SVGOMPoint svgOMPoint;
			svgOMPoint = new SVGOMPoint(svgPointList.getItem(
					svgPointList.getNumberOfItems() - 1).getX(), svgPointList
					.getItem(svgPointList.getNumberOfItems() - 1).getY());
			svgOMPoint = (SVGOMPoint) svgOMPoint
					.matrixTransform(svgOMPolygonElement.getScreenCTM());
			if (me.getX() < x && me.getX() < svgOMPoint.getX())
				if (position > 0)
					position--;
			if (me.getX() > x && me.getX() > svgOMPoint.getX())
				if (position < daughterVector.size())
					position++;
			x = me.getX();
			y = me.getX();
			computeOverlay();
			treeEditorJSVGScrollPane.getTreeEditorJSVGCanvas().repaint();
		}
	}

	/**
	 * Processes the specified mouse clicked event.
	 * 
	 * @param me
	 *            mouse event
	 */
	public void mouseClicked(MouseEvent me) {
		if (me.getButton() == MouseEvent.BUTTON1)
			performEdit();
		end();
	}

	/**
	 * Processes the specified key event.
	 * 
	 * @param ke
	 *            key event
	 */
	public void keyPressed(KeyEvent ke) {
		if (ke.getKeyCode() == KeyEvent.VK_LEFT)
			if (position > 0) {
				position--;
				computeOverlay();
				treeEditorJSVGScrollPane.getTreeEditorJSVGCanvas().repaint();
			}
		if (ke.getKeyCode() == KeyEvent.VK_RIGHT)
			if (position < daughterVector.size()) {
				position++;
				computeOverlay();
				treeEditorJSVGScrollPane.getTreeEditorJSVGCanvas().repaint();
			}
		if (ke.getKeyCode() == KeyEvent.VK_HOME) {
			position = 0;
			computeOverlay();
			treeEditorJSVGScrollPane.getTreeEditorJSVGCanvas().repaint();
		}
		if (ke.getKeyCode() == KeyEvent.VK_END) {
			position = daughterVector.size();
			computeOverlay();
			treeEditorJSVGScrollPane.getTreeEditorJSVGCanvas().repaint();
		}
		if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
			performEdit();
			end();
		}
		if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
			end();
		}
	}

	/**
	 * Computes the coordinates of the branch interactor pendulum string.
	 */
	protected void computeOverlay() {
		if (daughterVector.size() > 0) {
			float x1 = 0.0f;
			float y1 = 0.0f;
			float x2 = 0.0f;
			float y2 = 0.0f;
			SVGOMPolygonElement svgOMPolygonElement = null;
			if (position == 0)
				svgOMPolygonElement = (SVGOMPolygonElement) daughterVector
						.elementAt(position).getBranchElement().getElement();
			if (position > 0 && position <= daughterVector.size())
				svgOMPolygonElement = (SVGOMPolygonElement) daughterVector
						.elementAt(position - 1).getBranchElement()
						.getElement();
			SVGPointList svgPointList = svgOMPolygonElement.getPoints();
			x1 = svgPointList.getItem(0).getX();
			y1 = svgPointList.getItem(0).getY();
			if (position == 0) {
				x2 = svgPointList.getItem(svgPointList.getNumberOfItems() - 1)
						.getX() - 25.0f;
				y2 = svgPointList.getItem(svgPointList.getNumberOfItems() - 1)
						.getY();
			}
			if (position > 0 && position < daughterVector.size()) {
				SVGOMPolygonElement sisterSVGOMPolygonElement = (SVGOMPolygonElement) daughterVector
						.elementAt(position).getBranchElement().getElement();
				SVGPointList sisterSVGPointList = sisterSVGOMPolygonElement
						.getPoints();
				x2 = svgPointList.getItem(svgPointList.getNumberOfItems() - 1)
						.getX()
						+ ((sisterSVGPointList.getItem(
								sisterSVGPointList.getNumberOfItems() - 1)
								.getX() - svgPointList.getItem(
								svgPointList.getNumberOfItems() - 1).getX()) / 2);
				y2 = svgPointList.getItem(svgPointList.getNumberOfItems() - 1)
						.getY()
						+ ((sisterSVGPointList.getItem(
								sisterSVGPointList.getNumberOfItems() - 1)
								.getY() - svgPointList.getItem(
								svgPointList.getNumberOfItems() - 1).getY()) / 2);
			}
			if (position == daughterVector.size()) {
				x2 = svgPointList.getItem(svgPointList.getNumberOfItems() - 1)
						.getX() + 25.0f;
				y2 = svgPointList.getItem(svgPointList.getNumberOfItems() - 1)
						.getY();
			}
			SVGOMPoint x1y1Point = new SVGOMPoint(x1, y1);
			x1y1Point = (SVGOMPoint) x1y1Point
					.matrixTransform(svgOMPolygonElement.getScreenCTM());
			SVGOMPoint x2y2Point = new SVGOMPoint(x2, y2);
			x2y2Point = (SVGOMPoint) x2y2Point
					.matrixTransform(svgOMPolygonElement.getScreenCTM());
			branchInteractorOverlay.setX1(x1y1Point.getX());
			branchInteractorOverlay.setY1(x1y1Point.getY());
			branchInteractorOverlay.setX2(x2y2Point.getX());
			branchInteractorOverlay.setY2(x2y2Point.getY());
		}
	}
	
	/**
	 * Placeholder.
	 */
	public void performEdit() {	}

	/**
	 * Returns the tree editor scroll pane.
	 * 
	 * @return tree editor scroll pane
	 */
	public TreeEditorJSVGScrollPane getTreeEditorJSVGScrollPane() {
		return treeEditorJSVGScrollPane;
	}
}
