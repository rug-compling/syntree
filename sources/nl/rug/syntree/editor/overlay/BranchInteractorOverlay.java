/* BranchInteractorOverlay.java */

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
import java.awt.event.ActionEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Timer;

import nl.rug.syntree.editor.interactor.AbstractBranchInteractor;
import nl.rug.syntree.ui.UIColors;

import org.apache.batik.swing.gvt.Overlay;

/**
 * This class represents an overlay for the branching interactor.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class BranchInteractorOverlay implements Overlay {
	/**
	 * Radius constant for the pendulum bob
	 */
	protected static final float BOB_RADIUS = 7.5f;

	/**
	 * Blink delay constant for the pendulum bob
	 */
	protected static final int BOB_BLINK_DELAY = 5;

	/**
	 * Abstract branch interactor
	 */
	protected AbstractBranchInteractor abstractBranchInteractor;

	/**
	 * Ellipse to represent the pendulum bob
	 */
	protected Ellipse2D bob;

	/**
	 * Line to represent the pendulum string
	 */
	protected Line2D string;

	/**
	 * First x-coordinate of the string
	 */
	protected float x1 = 0.0f;

	/**
	 * First y-coordinate of the string
	 */
	protected float y1 = 0.0f;

	/**
	 * Second x-coordinate of the string
	 */
	protected float x2 = 0.0f;

	/**
	 * Second y-coordinate of the string
	 */
	protected float y2 = 0.0f;

	/**
	 * Current radius of the bob
	 */
	protected float bobRadius = 0.0f;

	/**
	 * Blink action for the bob
	 */
	protected Action bobBlinkAction = new AbstractAction() {
		/**
		 * Serial version identifier constant
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Boolean indicating upwards iteration
		 */
		protected boolean upwards = true;

		public void actionPerformed(ActionEvent ae) {
			if (upwards)
				if (bobRadius < BOB_RADIUS)
					bobRadius += 0.5;
				else
					upwards = false;
			else if (bobRadius > 1)
				bobRadius -= 0.5;
			else
				upwards = true;
			abstractBranchInteractor.getTreeEditorJSVGScrollPane()
					.getTreeEditorJSVGCanvas().repaint();
		}
	};

	/**
	 * Constructs a new overlay for the branch interactor.
	 * 
	 * @param branchInteractor
	 *            branch interactor
	 */
	public BranchInteractorOverlay(
			AbstractBranchInteractor abstractBranchInteractor) {
		this.abstractBranchInteractor = abstractBranchInteractor;
		new Timer(BOB_BLINK_DELAY, bobBlinkAction).start();
	}

	/**
	 * Paints the overlay.
	 * 
	 * @param g
	 *            graphics component
	 */
	public void paint(Graphics g) {
		string = new Line2D.Float(x1, y1, x2, y2);
		Graphics2D g2d = (Graphics2D) g;
		Stroke stringStroke = new BasicStroke(1.5f, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_MITER);
		g2d.setColor(UIColors.EDITOR_PENDULUM_STRING);
		g2d.setStroke(stringStroke);
		g2d.draw(string);
		g2d.setColor(UIColors.EDITOR_PENDULUM_BOB);
		g2d.setPaint(UIColors.EDITOR_PENDULUM_BOB);
		bob = new Ellipse2D.Float(x2 - bobRadius, y2 - bobRadius,
				bobRadius * 2.0f, bobRadius * 2.0f);
		g2d.fill(bob);
		g2d.draw(bob);
	}

	/**
	 * Sets the first x-coordinate to the specified x-coordinate.
	 * 
	 * @param x1
	 *            x-coordinate
	 */
	public void setX1(float x1) {
		this.x1 = x1;
	}

	/**
	 * Sets the first y-coordinate to the specified y-coordinate.
	 * 
	 * @param y1
	 *            y-coordinate
	 */
	public void setY1(float y1) {
		this.y1 = y1;

	}

	/**
	 * Sets the second x-coordinate to the specified x-coordinate.
	 * 
	 * @param x2
	 *            x-coordinate
	 */
	public void setX2(float x2) {
		this.x2 = x2;
	}

	/**
	 * Sets the second y-coordinate to the specified x-coordinate.
	 * 
	 * @param y2
	 *            y-coordinate
	 */
	public void setY2(float y2) {
		this.y2 = y2;
	}

	/**
	 * Returns the first x-coordinate.
	 * 
	 * @return first x-coordinate
	 */
	public float getX1() {
		return x1;
	}

	/**
	 * Returns the first y-coordinate.
	 * 
	 * @return first y-coordinate
	 */
	public float getY1() {
		return y1;
	}

	/**
	 * Returns the second x-coordinate.
	 * 
	 * @return second x-coordinate
	 */
	public float getX2() {
		return x2;
	}

	/**
	 * Returns the second x-coordinate.
	 * 
	 * @return second x-coordinate
	 */
	public float getY2() {
		return y2;
	}
}