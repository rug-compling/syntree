/* MdomInteractorOverlay.java */

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

package nl.rug.syntree.editor.overlay;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import nl.rug.syntree.editor.interactor.MdomInteractor;
import nl.rug.syntree.ui.UIColors;

import org.apache.batik.swing.gvt.Overlay;

/**
 * This class represents an overlay for the mdom interactor.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class MdomInteractorOverlay implements Overlay {
	/** */
	protected static final float POINT_RADIUS = 3.5f;

	/** Mdom branch interactor */
	protected MdomInteractor mdomInteractor;

	/** */
	protected Ellipse2D moveto;

	/** */
	protected Ellipse2D firstCurvetoCP1;

	/** */
	protected Ellipse2D firstCurvetoCP2;

	/** */
	protected Ellipse2D firstCurveto;

	/** */
	protected Ellipse2D secondCurvetoCP1;

	/** */
	protected Ellipse2D secondCurvetoCP2;

	/** */
	protected Ellipse2D secondCurveto;

	/** */
	protected Line2D firstCurvetoCP1Line;

	/** */
	protected Line2D firstCurvetoCP2Line;

	/** */
	protected Line2D firstCurvetoCPLine;

	/** */
	protected Line2D secondCurvetoCP1Line;

	/** */
	protected Line2D secondCurvetoCP2Line;

	/** */
	protected Line2D secondCurvetoCPLine;

	/** */
	protected float movetoX = 0.0f;

	/** */
	protected float movetoY = 0.0f;

	/** */
	protected float firstCurvetoCP1X = 0.0f;

	/** */
	protected float firstCurvetoCP1Y = 0.0f;

	/** */
	protected float firstCurvetoCP2X = 0.0f;

	/** */
	protected float firstCurvetoCP2Y = 0.0f;

	/** */
	protected float firstCurvetoX = 0.0f;

	/** */
	protected float firstCurvetoY = 0.0f;

	/** */
	protected float secondCurvetoCP1X = 0.0f;

	/** */
	protected float secondCurvetoCP1Y = 0.0f;

	/** */
	protected float secondCurvetoCP2X = 0.0f;

	/** */
	protected float secondCurvetoCP2Y = 0.0f;

	/** */
	protected float secondCurvetoX = 0.0f;

	/** */
	protected float secondCurvetoY = 0.0f;

	/**
	 * Constructs a new overlay for the mdom interactor.
	 * 
	 * @param mdomInteractor
	 *            mdom interactor
	 */
	public MdomInteractorOverlay(MdomInteractor mdomInteractor) {
		this.mdomInteractor = mdomInteractor;
	}

	/**
	 * Paints the overlay.
	 * 
	 * @param g
	 *            graphics component
	 */
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		float dash[] = { 5.0f, 5.0f };
		Stroke lineStroke = new BasicStroke(2.0f, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);

		Stroke ellipseStroke = new BasicStroke(2.0f, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_MITER);

		// first curveto cp line
		g2d.setColor(UIColors.EDITOR_MDOM_INTERACTOR_LINE);
		g2d.setStroke(lineStroke);
		firstCurvetoCPLine = new Line2D.Float(firstCurvetoCP1X,
				firstCurvetoCP1Y, firstCurvetoCP2X, firstCurvetoCP2Y);
		g2d.draw(firstCurvetoCPLine);

		// first curveto cp1 line
		g2d.setColor(UIColors.EDITOR_MDOM_INTERACTOR_LINE);
		g2d.setStroke(lineStroke);
		firstCurvetoCP1Line = new Line2D.Float(movetoX, movetoY,
				firstCurvetoCP1X, firstCurvetoCP1Y);
		g2d.draw(firstCurvetoCP1Line);

		// moveto
		g2d.setColor(UIColors.EDITOR_MDOM_INTERACTOR_POINT);
		g2d.setStroke(ellipseStroke);
		moveto = new Ellipse2D.Float(movetoX - POINT_RADIUS, movetoY
				- POINT_RADIUS, POINT_RADIUS * 2.0f, POINT_RADIUS * 2.0f);
		g2d.fill(moveto);
		g2d.draw(moveto);

		// first curveto cp1
		g2d.setColor(UIColors.EDITOR_MDOM_INTERACTOR_CONTROL_POINT);
		g2d.setStroke(ellipseStroke);
		firstCurvetoCP1 = new Ellipse2D.Float(
				(firstCurvetoCP1X - POINT_RADIUS), firstCurvetoCP1Y
						- POINT_RADIUS, POINT_RADIUS * 2.0f,
				POINT_RADIUS * 2.0f);
		g2d.fill(firstCurvetoCP1);
		g2d.draw(firstCurvetoCP1);

		// first curveto cp2 line
		g2d.setColor(UIColors.EDITOR_MDOM_INTERACTOR_LINE);
		g2d.setStroke(lineStroke);
		firstCurvetoCP2Line = new Line2D.Float(firstCurvetoX, firstCurvetoY,
				firstCurvetoCP2X, firstCurvetoCP2Y);
		g2d.draw(firstCurvetoCP2Line);

		// second curveto cp line
		g2d.setColor(UIColors.EDITOR_MDOM_INTERACTOR_LINE);
		g2d.setStroke(lineStroke);
		secondCurvetoCPLine = new Line2D.Float(secondCurvetoCP1X,
				secondCurvetoCP1Y, secondCurvetoCP2X, secondCurvetoCP2Y);
		g2d.draw(secondCurvetoCPLine);

		// second curveto cp1 line
		g2d.setColor(UIColors.EDITOR_MDOM_INTERACTOR_LINE);
		g2d.setStroke(lineStroke);
		secondCurvetoCP1Line = new Line2D.Float(firstCurvetoX, firstCurvetoY,
				secondCurvetoCP1X, secondCurvetoCP1Y);
		g2d.draw(secondCurvetoCP1Line);

		// first curveto
		g2d.setColor(UIColors.EDITOR_MDOM_INTERACTOR_POINT);
		g2d.setStroke(ellipseStroke);
		firstCurveto = new Ellipse2D.Float(firstCurvetoX - POINT_RADIUS,
				firstCurvetoY - POINT_RADIUS, POINT_RADIUS * 2.0f,
				POINT_RADIUS * 2.0f);
		g2d.fill(firstCurveto);
		g2d.draw(firstCurveto);

		// first curveto cp2
		g2d.setColor(UIColors.EDITOR_MDOM_INTERACTOR_CONTROL_POINT);
		g2d.setStroke(ellipseStroke);
		firstCurvetoCP2 = new Ellipse2D.Float(
				(firstCurvetoCP2X - POINT_RADIUS), firstCurvetoCP2Y
						- POINT_RADIUS, POINT_RADIUS * 2.0f,
				POINT_RADIUS * 2.0f);
		g2d.fill(firstCurvetoCP2);
		g2d.draw(firstCurvetoCP2);

		// second curveto cp1
		g2d.setColor(UIColors.EDITOR_MDOM_INTERACTOR_CONTROL_POINT);
		g2d.setStroke(ellipseStroke);
		secondCurvetoCP1 = new Ellipse2D.Float(
				(secondCurvetoCP1X - POINT_RADIUS), secondCurvetoCP1Y
						- POINT_RADIUS, POINT_RADIUS * 2.0f,
				POINT_RADIUS * 2.0f);
		g2d.fill(secondCurvetoCP1);
		g2d.draw(secondCurvetoCP1);

		// second curveto cp2 line
		g2d.setColor(UIColors.EDITOR_MDOM_INTERACTOR_LINE);
		g2d.setStroke(lineStroke);
		secondCurvetoCP2Line = new Line2D.Float(secondCurvetoX, secondCurvetoY,
				secondCurvetoCP2X, secondCurvetoCP2Y);
		g2d.draw(secondCurvetoCP2Line);

		// second curveto
		g2d.setColor(UIColors.EDITOR_MDOM_INTERACTOR_POINT);
		g2d.setStroke(ellipseStroke);
		secondCurveto = new Ellipse2D.Float(secondCurvetoX - POINT_RADIUS,
				secondCurvetoY - POINT_RADIUS, POINT_RADIUS * 2.0f,
				POINT_RADIUS * 2.0f);
		g2d.fill(secondCurveto);
		g2d.draw(secondCurveto);

		// second curveto cp2
		g2d.setColor(UIColors.EDITOR_MDOM_INTERACTOR_CONTROL_POINT);
		g2d.setStroke(ellipseStroke);
		secondCurvetoCP2 = new Ellipse2D.Float(
				(secondCurvetoCP2X - POINT_RADIUS), secondCurvetoCP2Y
						- POINT_RADIUS, POINT_RADIUS * 2.0f,
				POINT_RADIUS * 2.0f);
		g2d.fill(secondCurvetoCP2);
		g2d.draw(secondCurvetoCP2);
	}

	/**
     *
     *
     */
	public void setMovetoX(float x) {
		this.movetoX = x;
	}

	/**
     *
     *
     */
	public void setMovetoY(float y) {
		this.movetoY = y;
	}

	/**
     *
     *
     */
	public void setFirstCurvetoCP1X(float x) {
		this.firstCurvetoCP1X = x;
	}

	/**
     *
     *
     */
	public void setFirstCurvetoCP1Y(float y) {
		this.firstCurvetoCP1Y = y;
	}

	/**
     *
     *
     */
	public void setFirstCurvetoCP2X(float x) {
		this.firstCurvetoCP2X = x;
	}

	/**
     *
     *
     */
	public void setFirstCurvetoCP2Y(float y) {
		this.firstCurvetoCP2Y = y;
	}

	/**
     *
     *
     */
	public void setFirstCurvetoX(float x) {
		this.firstCurvetoX = x;
	}

	/**
     *
     *
     */
	public void setFirstCurvetoY(float y) {
		this.firstCurvetoY = y;
	}

	/**
     *
     *
     */
	public void setSecondCurvetoCP1X(float x) {
		this.secondCurvetoCP1X = x;
	}

	/**
     *
     *
     */
	public void setSecondCurvetoCP1Y(float y) {
		this.secondCurvetoCP1Y = y;
	}

	/**
     *
     *
     */
	public void setSecondCurvetoCP2X(float x) {
		this.secondCurvetoCP2X = x;
	}

	/**
     *
     *
     */
	public void setSecondCurvetoCP2Y(float y) {
		this.secondCurvetoCP2Y = y;
	}

	/**
     *
     *
     */
	public void setSecondCurvetoX(float x) {
		this.secondCurvetoX = x;
	}

	/**
     *
     *
     */
	public void setSecondCurvetoY(float y) {
		this.secondCurvetoY = y;
	}

	/**
     *
     */
	public float getMovetoX() {
		return movetoX;
	}

	/**
     *
     */
	public float getMovetoY() {
		return movetoY;
	}

	/**
     *
     */
	public float getFirstCurvetoX() {
		return firstCurvetoX;
	}

	/**
     *
     */
	public float getFirstCurvetoY() {
		return firstCurvetoY;
	}

	/**
     *
     */
	public float getFirstCurvetoCP1X() {
		return firstCurvetoCP1X;
	}

	/**
     *
     */
	public float getFirstCurvetoCP1Y() {
		return firstCurvetoCP1Y;
	}

	/**
     *
     */
	public float getFirstCurvetoCP2X() {
		return firstCurvetoCP2X;
	}

	/**
     *
     */
	public float getFirstCurvetoCP2Y() {
		return firstCurvetoCP2Y;
	}

	/**
     *
     */
	public float getSecondCurvetoX() {
		return secondCurvetoX;
	}

	/**
     *
     */
	public float getSecondCurvetoY() {
		return secondCurvetoY;
	}

	/**
     *
     */
	public float getSecondCurvetoCP1X() {
		return secondCurvetoCP1X;
	}

	/**
     *
     */
	public float getSecondCurvetoCP1Y() {
		return secondCurvetoCP1Y;
	}

	/**
     *
     */
	public float getSecondCurvetoCP2X() {
		return secondCurvetoX;
	}

	/**
     *
     */
	public float getSecondCurvetoCP2Y() {
		return secondCurvetoY;
	}

	/**
     *
     */
	public Ellipse2D getMoveto() {
		return moveto;
	}

	/**
     *
     */
	public Ellipse2D getFirstCurveto() {
		return firstCurveto;
	}

	/**
     *
     */
	public Ellipse2D getFirstCurvetoCP1() {
		return firstCurvetoCP1;
	}

	/**
     *
     */
	public Ellipse2D getFirstCurvetoCP2() {
		return firstCurvetoCP2;
	}

	/**
     *
     */
	public Ellipse2D getSecondCurveto() {
		return secondCurveto;
	}

	/**
     *
     */
	public Ellipse2D getSecondCurvetoCP1() {
		return secondCurvetoCP1;
	}

	/**
     *
     */
	public Ellipse2D getSecondCurvetoCP2() {
		return secondCurvetoCP2;
	}
}
