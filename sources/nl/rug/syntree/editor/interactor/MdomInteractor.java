/* MdomInteractor.java */

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
import java.awt.event.MouseEvent;

import nl.rug.syntree.editor.TreeEditorJSVGScrollPane;
import nl.rug.syntree.editor.overlay.MdomInteractorOverlay;
import nl.rug.syntree.tree.component.MdomBranchElement;
import nl.rug.syntree.tree.component.MdomBranchPathElement;

import org.apache.batik.bridge.UpdateManager;
import org.apache.batik.dom.svg.SVGOMPoint;
import org.apache.batik.swing.gvt.InteractorAdapter;
import org.apache.batik.util.RunnableQueue;
import org.w3c.dom.svg.SVGPathElement;

/**
 * This class represents a mdom branch interactor for the tree representation
 * editor.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class MdomInteractor extends InteractorAdapter {
	/**
	 * Identifier for the moveto
	 */
	public static final int MOVETO = 0x00;

	/**
	 * Identifier for the first curveto
	 */
	public static final int FIRST_CURVETO = 0x01;

	/**
	 * Identifier for the first control point of the first curveto
	 */
	public static final int FIRST_CURVETO_CP1 = 0x02;

	/**
	 * Identifier for the second control point of the first curveto
	 */
	public static final int FIRST_CURVETO_CP2 = 0x03;

	/**
	 * Identifier for the second curveto
	 */
	public static final int SECOND_CURVETO = 0x04;

	/**
	 * Identifier for the first control point of the second curveto
	 */
	public static final int SECOND_CURVETO_CP1 = 0x05;

	/**
	 * Identifier for the second control point of the second curveto
	 */
	public static final int SECOND_CURVETO_CP2 = 0x06;

	/**
	 * Identifier for no point
	 */
	public static final int NO_POINT = 0x07;

	/**
	 * Control point padding constant
	 */
	public static final float CONTROL_POINT_PADDING = 10.0f;

	/**
	 * The selected moveto or curveto point
	 */
	protected int selectedPoint;

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
	 * Mdom interactor overlay
	 */
	protected MdomInteractorOverlay mdomInteractorOverlay;

	/**
	 * Mdom branch element
	 */
	protected MdomBranchElement mdomBranchElement;

	/**
	 * Constructs a new mdom branch interactor.
	 * 
	 * @param treeEditorJSVGScrollPane
	 *            tree editor scroll pane
	 */
	public MdomInteractor(TreeEditorJSVGScrollPane treeEditorJSVGScrollPane) {
		this.treeEditorJSVGScrollPane = treeEditorJSVGScrollPane;
		this.startInteraction = false;
		this.interactionFinished = false;
		mdomInteractorOverlay = new MdomInteractorOverlay(this);
		setSelectedPoint(NO_POINT);
	}

	/**
	 * Starts the interaction.
	 */
	@SuppressWarnings("unchecked")
	public void start() {
		mdomBranchElement = treeEditorJSVGScrollPane.getTreeEditorBridge()
				.getMdomSelectionManager().getProminentMdomBranch();
		computeOverlay();
		treeEditorJSVGScrollPane.getTreeEditorJSVGCanvas().getOverlays().add(
				mdomInteractorOverlay);
		treeEditorJSVGScrollPane.getTreeEditorJSVGCanvas().repaint();
		interactionFinished = false;
		startInteraction = true;
	}

	/**
	 * Ends the interaction.
	 */
	public void end() {
		treeEditorJSVGScrollPane.getTreeEditorJSVGCanvas().getOverlays()
				.remove(mdomInteractorOverlay);
		treeEditorJSVGScrollPane.getTreeEditorJSVGCanvas().repaint();
		mdomBranchElement = null;
		startInteraction = false;
		interactionFinished = true;
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

	/**
	 * Processes the specified mouse pressed event.
	 * 
	 * @param me
	 *            mouse event
	 */
	public void mousePressed(MouseEvent me) {
		// moveto
		if (mdomInteractorOverlay.getMoveto().getBounds2D().contains(me.getX(),
				me.getY()))
			setSelectedPoint(MOVETO);

		// first curveto
		if (mdomInteractorOverlay.getFirstCurveto().getBounds2D().contains(
				me.getX(), me.getY()))
			setSelectedPoint(FIRST_CURVETO);
		if (mdomInteractorOverlay.getFirstCurvetoCP1().getBounds2D().contains(
				me.getX(), me.getY()))
			setSelectedPoint(FIRST_CURVETO_CP1);
		if (mdomInteractorOverlay.getFirstCurvetoCP2().getBounds2D().contains(
				me.getX(), me.getY()))
			setSelectedPoint(FIRST_CURVETO_CP2);

		// second curveto
		if (mdomInteractorOverlay.getSecondCurveto().getBounds2D().contains(
				me.getX(), me.getY()))
			setSelectedPoint(SECOND_CURVETO);
		if (mdomInteractorOverlay.getSecondCurvetoCP1().getBounds2D().contains(
				me.getX(), me.getY()))
			setSelectedPoint(SECOND_CURVETO_CP1);
		if (mdomInteractorOverlay.getSecondCurvetoCP2().getBounds2D().contains(
				me.getX(), me.getY()))
			setSelectedPoint(SECOND_CURVETO_CP2);
	}

	/**
	 * Processes the specified mouse dragged event.
	 * 
	 * @param me
	 *            mouse event
	 */
	public void mouseDragged(MouseEvent me) {
		switch (selectedPoint) {
		case MOVETO:
			mdomInteractorOverlay.setMovetoX(me.getX());
			mdomInteractorOverlay.setMovetoY(me.getY());
			break;
		case FIRST_CURVETO:
			mdomInteractorOverlay.setFirstCurvetoX(me.getX());
			mdomInteractorOverlay.setFirstCurvetoY(me.getY());
			break;
		case FIRST_CURVETO_CP1:
			mdomInteractorOverlay.setFirstCurvetoCP1X(me.getX());
			mdomInteractorOverlay.setFirstCurvetoCP1Y(me.getY());
			break;
		case FIRST_CURVETO_CP2:
			mdomInteractorOverlay.setFirstCurvetoCP2X(me.getX());
			mdomInteractorOverlay.setFirstCurvetoCP2Y(me.getY());
			mdomInteractorOverlay.setSecondCurvetoCP1X(mdomInteractorOverlay
					.getFirstCurvetoX()
					+ (mdomInteractorOverlay.getFirstCurvetoX() - me.getX()));
			mdomInteractorOverlay.setSecondCurvetoCP1Y(mdomInteractorOverlay
					.getFirstCurvetoY()
					+ (mdomInteractorOverlay.getFirstCurvetoY() - me.getY()));
			break;
		case SECOND_CURVETO:
			mdomInteractorOverlay.setSecondCurvetoX(me.getX());
			mdomInteractorOverlay.setSecondCurvetoY(me.getY());
			break;
		case SECOND_CURVETO_CP1:
			mdomInteractorOverlay.setSecondCurvetoCP1X(me.getX());
			mdomInteractorOverlay.setSecondCurvetoCP1Y(me.getY());
			mdomInteractorOverlay.setFirstCurvetoCP2X(mdomInteractorOverlay
					.getFirstCurvetoX()
					+ (mdomInteractorOverlay.getFirstCurvetoX() - me.getX()));
			mdomInteractorOverlay.setFirstCurvetoCP2Y(mdomInteractorOverlay
					.getFirstCurvetoY()
					+ (mdomInteractorOverlay.getFirstCurvetoY() - me.getY()));
			break;
		case SECOND_CURVETO_CP2:
			mdomInteractorOverlay.setSecondCurvetoCP2X(me.getX());
			mdomInteractorOverlay.setSecondCurvetoCP2Y(me.getY());
			break;
		}
		treeEditorJSVGScrollPane.getTreeEditorJSVGCanvas().repaint();
	}

	/**
	 * Processes the specified mouse released event.
	 * 
	 * @param me
	 *            mouse event
	 */
	public void mouseReleased(MouseEvent me) {
		final float x = me.getX();
		final float y = me.getY();
		if (getSelectedPoint() != NO_POINT) {
			UpdateManager um = treeEditorJSVGScrollPane
					.getTreeEditorJSVGCanvas().getUpdateManager();
			RunnableQueue rq = um.getUpdateRunnableQueue();
			if (um != null && rq != null) {
				rq.invokeLater(new Runnable() {
					public void run() {
						computeMdomBranchPathElement(x, y);
						treeEditorJSVGScrollPane.getTreeDrawer().draw();
						treeEditorJSVGScrollPane.getTreeEditorBridge()
								.getTreeRepresentation().getSVGDocument()
								.computeViewBox();
						computeOverlay();
						treeEditorJSVGScrollPane.getTreeEditorJSVGCanvas()
								.repaint();
						setSelectedPoint(NO_POINT);
					}
				});
			}
		}
	}

	/**
	 * Processes the specified mouse clicked event.
	 * 
	 * @param me
	 *            mouse event
	 */
	public void mouseClicked(MouseEvent me) {
		if (me.getButton() == 3)
			end();
	}

	/**
	 * Computes the mdom branch path element.
	 * 
	 * @param x
	 *            x-coordinate
	 * @param y
	 *            y-coordinate
	 */
	public void computeMdomBranchPathElement(float x, float y) {
		MdomBranchPathElement mdomBranchPathElement = mdomBranchElement
				.getMdomBranchPathElement();
		SVGPathElement svgPathElement = (SVGPathElement) mdomBranchPathElement
				.getElement();
		SVGOMPoint svgOMPoint = new SVGOMPoint(x, y);
		svgOMPoint = (SVGOMPoint) svgOMPoint.matrixTransform(svgPathElement
				.getScreenCTM().inverse());

		// moveto coordinates
		float movetoX = mdomBranchPathElement.getMovetoX();
		float movetoY = mdomBranchPathElement.getMovetoY();

		// first curveto coordinates
		float firstCurvetoX = mdomBranchPathElement.getFirstCurvetoX();
		float firstCurvetoY = mdomBranchPathElement.getFirstCurvetoY();

		switch (getSelectedPoint()) {
		case MOVETO:
			mdomBranchElement.setMotherNode(treeEditorJSVGScrollPane
					.getTreeEditorJSVGCanvas().nodeElementAt(x, y));
			mdomBranchPathElement.setMovetoX(svgOMPoint.getX());
			mdomBranchPathElement.setMovetoY(svgOMPoint.getY());
			break;
		case FIRST_CURVETO:
			mdomBranchPathElement.setFirstCurvetoX(svgOMPoint.getX() - movetoX);
			mdomBranchPathElement.setFirstCurvetoY(svgOMPoint.getY() - movetoY);
			break;
		case FIRST_CURVETO_CP1:
			mdomBranchPathElement.setFirstCurvetoCP1X(svgOMPoint.getX()
					- movetoX);
			mdomBranchPathElement.setFirstCurvetoCP1Y(svgOMPoint.getY()
					- movetoY);
			break;
		case FIRST_CURVETO_CP2:
			mdomBranchPathElement.setFirstCurvetoCP2X(svgOMPoint.getX()
					- movetoX);
			mdomBranchPathElement.setFirstCurvetoCP2Y(svgOMPoint.getY()
					- movetoY);
			mdomBranchPathElement.setSecondCurvetoCP1X(firstCurvetoX
					- mdomBranchPathElement.getFirstCurvetoCP2X());
			mdomBranchPathElement.setSecondCurvetoCP1Y(firstCurvetoY
					- mdomBranchPathElement.getFirstCurvetoCP2Y());
			break;
		case SECOND_CURVETO:
			mdomBranchElement.setDaughterNode(treeEditorJSVGScrollPane
					.getTreeEditorJSVGCanvas().nodeElementAt(x, y));
			mdomBranchPathElement.setSecondCurvetoX(svgOMPoint.getX()
					- firstCurvetoX - movetoX);
			mdomBranchPathElement.setSecondCurvetoY(svgOMPoint.getY()
					- firstCurvetoY - movetoY);
			break;
		case SECOND_CURVETO_CP1:
			mdomBranchPathElement.setSecondCurvetoCP1X(svgOMPoint.getX()
					- firstCurvetoX - movetoX);
			mdomBranchPathElement.setSecondCurvetoCP1Y(svgOMPoint.getY()
					- firstCurvetoY - movetoY);
			mdomBranchPathElement.setFirstCurvetoCP2X(firstCurvetoX
					- mdomBranchPathElement.getSecondCurvetoCP1X());
			mdomBranchPathElement.setFirstCurvetoCP2Y(firstCurvetoY
					- mdomBranchPathElement.getSecondCurvetoCP1Y());
			break;
		case SECOND_CURVETO_CP2:
			mdomBranchPathElement.setSecondCurvetoCP2X(svgOMPoint.getX()
					- firstCurvetoX - movetoX);
			mdomBranchPathElement.setSecondCurvetoCP2Y(svgOMPoint.getY()
					- firstCurvetoY - movetoY);
			break;
		}
	}

	/**
	 * Computes the mdom interactor overlay.
	 */
	public void computeOverlay() {
		MdomBranchPathElement mdomBranchPathElement = mdomBranchElement
				.getMdomBranchPathElement();
		SVGPathElement svgPathElement = (SVGPathElement) mdomBranchPathElement
				.getElement();
		SVGOMPoint svgOMPoint;

		// make coordinates relative to moveto
		float x = mdomBranchPathElement.getMovetoX();
		float y = mdomBranchPathElement.getMovetoY();

		// moveto
		svgOMPoint = new SVGOMPoint(mdomBranchPathElement.getMovetoX(),
				mdomBranchPathElement.getMovetoY());
		svgOMPoint = (SVGOMPoint) svgOMPoint.matrixTransform(svgPathElement
				.getScreenCTM());
		mdomInteractorOverlay.setMovetoX(svgOMPoint.getX());
		mdomInteractorOverlay.setMovetoY(svgOMPoint.getY());

		// first curveto cp1
		svgOMPoint = new SVGOMPoint(x
				+ mdomBranchPathElement.getFirstCurvetoCP1X(), y
				+ mdomBranchPathElement.getFirstCurvetoCP1Y());
		svgOMPoint = (SVGOMPoint) svgOMPoint.matrixTransform(svgPathElement
				.getScreenCTM());
		mdomInteractorOverlay.setFirstCurvetoCP1X(svgOMPoint.getX()
				- CONTROL_POINT_PADDING);
		mdomInteractorOverlay.setFirstCurvetoCP1Y(svgOMPoint.getY());

		// first curveto
		svgOMPoint = new SVGOMPoint(x
				+ mdomBranchPathElement.getFirstCurvetoX(), y
				+ mdomBranchPathElement.getFirstCurvetoY());
		svgOMPoint = (SVGOMPoint) svgOMPoint.matrixTransform(svgPathElement
				.getScreenCTM());
		mdomInteractorOverlay.setFirstCurvetoX(svgOMPoint.getX());
		mdomInteractorOverlay.setFirstCurvetoY(svgOMPoint.getY());

		// first curveto cp2
		svgOMPoint = new SVGOMPoint(x
				+ mdomBranchPathElement.getFirstCurvetoCP2X(), y
				+ mdomBranchPathElement.getFirstCurvetoCP2Y());
		svgOMPoint = (SVGOMPoint) svgOMPoint.matrixTransform(svgPathElement
				.getScreenCTM());
		mdomInteractorOverlay.setFirstCurvetoCP2X(svgOMPoint.getX()
				- CONTROL_POINT_PADDING);
		mdomInteractorOverlay.setFirstCurvetoCP2Y(svgOMPoint.getY());

		// make coordinates relative to first curveto
		x = x + mdomBranchPathElement.getFirstCurvetoX();
		y = y + mdomBranchPathElement.getFirstCurvetoY();

		// second curveto cp1
		svgOMPoint = new SVGOMPoint(x
				+ mdomBranchPathElement.getSecondCurvetoCP1X(), y
				+ mdomBranchPathElement.getSecondCurvetoCP1Y());
		svgOMPoint = (SVGOMPoint) svgOMPoint.matrixTransform(svgPathElement
				.getScreenCTM());
		mdomInteractorOverlay.setSecondCurvetoCP1X(svgOMPoint.getX()
				+ CONTROL_POINT_PADDING);
		mdomInteractorOverlay.setSecondCurvetoCP1Y(svgOMPoint.getY());

		// second curveto
		svgOMPoint = new SVGOMPoint(x
				+ mdomBranchPathElement.getSecondCurvetoX(), y
				+ mdomBranchPathElement.getSecondCurvetoY());
		svgOMPoint = (SVGOMPoint) svgOMPoint.matrixTransform(svgPathElement
				.getScreenCTM());
		mdomInteractorOverlay.setSecondCurvetoX(svgOMPoint.getX());
		mdomInteractorOverlay.setSecondCurvetoY(svgOMPoint.getY());

		// second curveto cp2
		svgOMPoint = new SVGOMPoint(x
				+ mdomBranchPathElement.getSecondCurvetoCP2X(), y
				+ mdomBranchPathElement.getSecondCurvetoCP2Y());
		svgOMPoint = (SVGOMPoint) svgOMPoint.matrixTransform(svgPathElement
				.getScreenCTM());
		mdomInteractorOverlay.setSecondCurvetoCP2X(svgOMPoint.getX()
				+ CONTROL_POINT_PADDING);
		mdomInteractorOverlay.setSecondCurvetoCP2Y(svgOMPoint.getY());
	}

	/**
	 * Sets the selected point to the specified point.
	 * 
	 * @param point
	 *            point to select
	 */
	public void setSelectedPoint(int point) {
		selectedPoint = point;
	}

	/**
	 * Returns the selected point.
	 * 
	 * @return selected point
	 */
	public int getSelectedPoint() {
		return selectedPoint;
	}
}
