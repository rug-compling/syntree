/* MdomBranchPathElement.java */

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

package nl.rug.syntree.tree.component;

import nl.rug.syntree.tree.TreeRepresentation;

import org.apache.batik.dom.AbstractElement;

/**
 * This class represents a multidominance branch path element.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class MdomBranchPathElement extends SVGElement {
	/**
	 * Moveto svg constant
	 */
	public static final String MOVETO = "M";

	/**
	 * Curveto svg constant
	 */
	public static final String CURVETO = "c";

	/**
	 * Multidominance branch element
	 */
	protected MdomBranchElement mdomBranchElement;

	/**
	 * Moveto x-coordinate
	 */
	protected float cachedMovetoX = 0.0f;

	/**
	 * Moveto y-coordinate
	 */
	protected float cachedMovetoY = 0.0f;

	/**
	 * First curveto first control point x-coordinate
	 */
	protected float cachedFirstCurvetoCP1X = 0.0f;

	/**
	 * First curveto first control point y-coordinate
	 */
	protected float cachedFirstCurvetoCP1Y = 0.0f;

	/**
	 * First curveto second control point x-coordinate
	 */
	protected float cachedFirstCurvetoCP2X = 0.0f;

	/**
	 * First curveto second control point y-coordinate
	 */
	protected float cachedFirstCurvetoCP2Y = 10.0f;

	/**
	 * First curveto x-coordinate
	 */
	protected float cachedFirstCurvetoX = 0.0f;

	/**
	 * First curveto y-coordinate
	 */
	protected float cachedFirstCurvetoY = 10.0f;

	/**
	 * Second curveto first control point x-coordinate
	 */
	protected float cachedSecondCurvetoCP1X = 0.0f;

	/**
	 * Second curveto first control point y-coordinate
	 */
	protected float cachedSecondCurvetoCP1Y = 0.0f;

	/**
	 * Second curveto second control point x-coordinate
	 */
	protected float cachedSecondCurvetoCP2X = 0.0f;

	/**
	 * Second curveto second control point y-coordinate
	 */
	protected float cachedSecondCurvetoCP2Y = 10.0f;

	/**
	 * Second curveto x-coordinate
	 */
	protected float cachedSecondCurvetoX = 0.0f;

	/**
	 * Second curveto y-coordinate
	 */
	protected float cachedSecondCurvetoY = 10.0f;

	/**
	 * Constructs a new multidominance branch path element.
	 * 
	 * @param treeRepresentation
	 *            tree representation
	 * @param mdomBranchElement
	 *            the multidominance branch element
	 */
	public MdomBranchPathElement(TreeRepresentation treeRepresentation,
			MdomBranchElement mdomBranchElement) {
		super(treeRepresentation, "path");
		setId(TreeRepresentation.MDOM_BRANCH_PATH_IDENTIFIER_PREFIX + "@"
				+ mdomBranchElement.getId());
		this.mdomBranchElement = mdomBranchElement;

		element
				.setAttribute(
						"style",
						"fill:none;stroke:black;stroke-width:.75;stroke-linecap:round;stroke-linejoin:round;");

		// moveto
		setMoveto(cachedMovetoX, cachedMovetoY);

		// first curveto
		setFirstCurveto(cachedFirstCurvetoCP1X, cachedFirstCurvetoCP1Y,
				cachedFirstCurvetoCP2X, cachedFirstCurvetoCP2Y,
				cachedFirstCurvetoX, cachedFirstCurvetoY);

		// second curveto
		setSecondCurveto(cachedSecondCurvetoCP1X, cachedSecondCurvetoCP1Y,
				cachedSecondCurvetoCP2X, cachedSecondCurvetoCP2Y,
				cachedSecondCurvetoX, cachedSecondCurvetoY);
	}

	/**
	 * Constructs a new multidominance branch path element from a SVG element.
	 * 
	 * @param treeRepresentation
	 *            tree representation
	 * @param mdomBranchElement
	 *            the multidominance branch element
	 * @param element
	 *            SVG element
	 */
	public MdomBranchPathElement(TreeRepresentation treeRepresentation,
			MdomBranchElement mdomBranchElement, AbstractElement element) {
		super(treeRepresentation, element);
		this.mdomBranchElement = mdomBranchElement;

		String pathData[] = element.getAttribute("d").replaceAll("M|c", "")
				.split(" ");

		// moveto
		cachedMovetoX = Float.parseFloat(pathData[0].split(",")[0]);
		cachedMovetoY = Float.parseFloat(pathData[0].split(",")[1]);

		// first curveto first control point
		cachedFirstCurvetoCP1X = Float.parseFloat(pathData[1].split(",")[0]);
		cachedFirstCurvetoCP1Y = Float.parseFloat(pathData[1].split(",")[1]);

		// first curveto second control point
		cachedFirstCurvetoCP2X = Float.parseFloat(pathData[2].split(",")[0]);
		cachedFirstCurvetoCP2Y = Float.parseFloat(pathData[2].split(",")[1]);

		// first curveto
		cachedFirstCurvetoX = Float.parseFloat(pathData[3].split(",")[0]);
		cachedFirstCurvetoY = Float.parseFloat(pathData[3].split(",")[1]);

		// second curveto first control point
		cachedSecondCurvetoCP1X = Float.parseFloat(pathData[4].split(",")[0]);
		cachedSecondCurvetoCP1Y = Float.parseFloat(pathData[4].split(",")[1]);

		// second curveto second control point
		cachedSecondCurvetoCP2X = Float.parseFloat(pathData[5].split(",")[0]);
		cachedSecondCurvetoCP2Y = Float.parseFloat(pathData[5].split(",")[1]);

		// second curveto second control point
		cachedSecondCurvetoX = Float.parseFloat(pathData[6].split(",")[0]);
		cachedSecondCurvetoY = Float.parseFloat(pathData[6].split(",")[1]);
	}

	/**
	 * Sets the x-coordinate and y-coordinate of the moveto to the specified
	 * x-coordinate and y-coordinate.
	 * 
	 * @param x
	 *            x-coordinate
	 * @param y
	 *            y-coordinate
	 */
	public void setMoveto(float x, float y) {
		setMovetoX(x);
		setMovetoY(y);
	}

	/**
	 * Sets the x-coordinate of the moveto to the specified x-coordinate.
	 * 
	 * @param x
	 *            x-coordinate
	 */
	public void setMovetoX(float x) {
		// moveto
		String moveto = MOVETO;
		moveto += x + "," + getMovetoY();

		// first curveto
		String firstCurveto = CURVETO;
		firstCurveto += getFirstCurvetoCP1X() + "," + getFirstCurvetoCP1Y()
				+ " ";
		firstCurveto += getFirstCurvetoCP2X() + "," + getFirstCurvetoCP2Y()
				+ " ";
		firstCurveto += getFirstCurvetoX() + "," + getFirstCurvetoY();

		// second curveto
		String secondCurveto = CURVETO;
		secondCurveto += getSecondCurvetoCP1X() + "," + getSecondCurvetoCP1Y()
				+ " ";
		secondCurveto += getSecondCurvetoCP2X() + "," + getSecondCurvetoCP2Y()
				+ " ";
		secondCurveto += getSecondCurvetoX() + "," + getSecondCurvetoY();

		// path data
		String pathData = moveto + " " + firstCurveto + " " + secondCurveto;
		element.setAttribute("d", pathData);

		cachedMovetoX = x;
	}

	/**
	 * Sets the y-coordinate of the moveto to the specified y-coordinate.
	 * 
	 * @param y
	 *            y-coordinate
	 */
	public void setMovetoY(float y) {
		// moveto
		String moveto = MOVETO;
		moveto += getMovetoX() + "," + y;

		// first curveto
		String firstCurveto = CURVETO;
		firstCurveto += getFirstCurvetoCP1X() + "," + getFirstCurvetoCP1Y()
				+ " ";
		firstCurveto += getFirstCurvetoCP2X() + "," + getFirstCurvetoCP2Y()
				+ " ";
		firstCurveto += getFirstCurvetoX() + "," + getFirstCurvetoY();

		// second curveto
		String secondCurveto = CURVETO;
		secondCurveto += getSecondCurvetoCP1X() + "," + getSecondCurvetoCP1Y()
				+ " ";
		secondCurveto += getSecondCurvetoCP2X() + "," + getSecondCurvetoCP2Y()
				+ " ";
		secondCurveto += getSecondCurvetoX() + "," + getSecondCurvetoY();

		// path data
		String pathData = moveto + " " + firstCurveto + " " + secondCurveto;
		element.setAttribute("d", pathData);

		cachedMovetoY = y;
	}

	/**
	 * Returns the moveto x-coordinate.
	 * 
	 * @return moveto x-coordinate
	 */
	public float getMovetoX() {
		return cachedMovetoX;
	}

	/**
	 * Returns the moveto y-coordinate.
	 * 
	 * @return moveto y-coordinate
	 */
	public float getMovetoY() {
		return cachedMovetoY;
	}

	/**
	 * Sets the curveto coordinates and the control point coordinates to the
	 * specified values.
	 * 
	 * @param x1
	 *            x-coordinate
	 * @param y1
	 *            y-coordinate
	 * @param x2
	 *            x-coordinate
	 * @param y2
	 *            y-coordinate
	 * @param x
	 *            x-coordinate
	 * @param y
	 *            y-coordinate
	 */
	public void setFirstCurveto(float x1, float y1, float x2, float y2,
			float x, float y) {
		// first control point
		setFirstCurvetoCP1X(x1);
		setFirstCurvetoCP1Y(y1);

		// second control point
		setFirstCurvetoCP2X(x2);
		setFirstCurvetoCP2Y(y2);

		// set curveto
		setFirstCurvetoX(x);
		setFirstCurvetoY(y);
	}

	/**
	 * Sets the x-coordinate of the first control point of the curveto.
	 * 
	 * @param x1
	 *            x-coordinate
	 */
	public void setFirstCurvetoCP1X(float x1) {
		// moveto
		String moveto = MOVETO;
		moveto += getMovetoX() + "," + getMovetoY();

		// first curveto
		String firstCurveto = CURVETO;
		firstCurveto += x1 + "," + getFirstCurvetoCP1Y() + " ";
		firstCurveto += getFirstCurvetoCP2X() + "," + getFirstCurvetoCP2Y()
				+ " ";
		firstCurveto += getFirstCurvetoX() + "," + getFirstCurvetoY();

		// second curveto
		String secondCurveto = CURVETO;
		secondCurveto += getSecondCurvetoCP1X() + "," + getSecondCurvetoCP1Y()
				+ " ";
		secondCurveto += getSecondCurvetoCP2X() + "," + getSecondCurvetoCP2Y()
				+ " ";
		secondCurveto += getSecondCurvetoX() + "," + getSecondCurvetoY();

		// path data
		String pathData = moveto + " " + firstCurveto + " " + secondCurveto;
		element.setAttribute("d", pathData);

		cachedFirstCurvetoCP1X = x1;
	}

	/**
	 * Sets the y-coordinate of the first control point of the curveto.
	 * 
	 * @param y1
	 *            y-coordinate
	 */
	public void setFirstCurvetoCP1Y(float y1) {
		// moveto
		String moveto = MOVETO;
		moveto += getMovetoX() + "," + getMovetoY();

		// first curveto
		String firstCurveto = CURVETO;
		firstCurveto += getFirstCurvetoCP1X() + "," + y1 + " ";
		firstCurveto += getFirstCurvetoCP2X() + "," + getFirstCurvetoCP2Y()
				+ " ";
		firstCurveto += getFirstCurvetoX() + "," + getFirstCurvetoY();

		// second curveto
		String secondCurveto = CURVETO;
		secondCurveto += getSecondCurvetoCP1X() + "," + getSecondCurvetoCP1Y()
				+ " ";
		secondCurveto += getSecondCurvetoCP2X() + "," + getSecondCurvetoCP2Y()
				+ " ";
		secondCurveto += getSecondCurvetoX() + "," + getSecondCurvetoY();

		// path data
		String pathData = moveto + " " + firstCurveto + " " + secondCurveto;
		element.setAttribute("d", pathData);

		cachedFirstCurvetoCP1Y = y1;
	}

	/**
	 * Returns the first curveto control point x-coordinate of the first
	 * curveto.
	 * 
	 * @return first curveto control point x-coordinate
	 */
	public float getFirstCurvetoCP1X() {
		return cachedFirstCurvetoCP1X;
	}

	/**
	 * Returns the first curveto control point y-coordinate of the first
	 * curveto.
	 * 
	 * @return first curveto control point y-coordinate
	 */
	public float getFirstCurvetoCP1Y() {
		return cachedFirstCurvetoCP1Y;
	}

	/**
	 * Sets the x-coordinate of the second control point of the curveto.
	 * 
	 * @param x2
	 *            x-coordinate
	 */
	public void setFirstCurvetoCP2X(float x2) {
		// moveto
		String moveto = MOVETO;
		moveto += getMovetoX() + "," + getMovetoY();

		// first curveto
		String firstCurveto = CURVETO;
		firstCurveto += getFirstCurvetoCP1X() + "," + getFirstCurvetoCP1Y()
				+ " ";
		firstCurveto += x2 + "," + getFirstCurvetoCP2Y() + " ";
		firstCurveto += getFirstCurvetoX() + "," + getFirstCurvetoY();

		// second curveto
		String secondCurveto = CURVETO;
		secondCurveto += getSecondCurvetoCP1X() + "," + getSecondCurvetoCP1Y()
				+ " ";
		secondCurveto += getSecondCurvetoCP2X() + "," + getSecondCurvetoCP2Y()
				+ " ";
		secondCurveto += getSecondCurvetoX() + "," + getSecondCurvetoY();

		// path data
		String pathData = moveto + " " + firstCurveto + " " + secondCurveto;
		element.setAttribute("d", pathData);

		cachedFirstCurvetoCP2X = x2;
	}

	/**
	 * Sets the y-coordinate of the second control point of the curveto.
	 * 
	 * @param y2
	 *            y-coordinate
	 */
	public void setFirstCurvetoCP2Y(float y2) {
		// moveto
		String moveto = MOVETO;
		moveto += getMovetoX() + "," + getMovetoY();

		// first curveto
		String firstCurveto = CURVETO;
		firstCurveto += getFirstCurvetoCP1X() + "," + getFirstCurvetoCP1Y()
				+ " ";
		firstCurveto += getFirstCurvetoCP2X() + "," + y2 + " ";
		firstCurveto += getFirstCurvetoX() + "," + getFirstCurvetoY();

		// second curveto
		String secondCurveto = CURVETO;
		secondCurveto += getSecondCurvetoCP1X() + "," + getSecondCurvetoCP1Y()
				+ " ";
		secondCurveto += getSecondCurvetoCP2X() + "," + getSecondCurvetoCP2Y()
				+ " ";
		secondCurveto += getSecondCurvetoX() + "," + getSecondCurvetoY();

		// path data
		String pathData = moveto + " " + firstCurveto + " " + secondCurveto;
		element.setAttribute("d", pathData);

		cachedFirstCurvetoCP2Y = y2;
	}

	/**
	 * Returns the second curveto control point x-coordinate of the first
	 * curveto.
	 * 
	 * @return second curveto control point x-coordinate
	 */
	public float getFirstCurvetoCP2X() {
		return cachedFirstCurvetoCP2X;
	}

	/**
	 * Returns the second curveto control point y-coordinate of the first
	 * curveto.
	 * 
	 * @return second curveto control point y-coordinate
	 */
	public float getFirstCurvetoCP2Y() {
		return cachedFirstCurvetoCP2Y;
	}

	/**
	 * Sets the x-coordinate of the curveto.
	 * 
	 * @param x
	 *            x-coordinate
	 */
	public void setFirstCurvetoX(float x) {
		// moveto
		String moveto = MOVETO;
		moveto += getMovetoX() + "," + getMovetoY();

		// first curveto
		String firstCurveto = CURVETO;
		firstCurveto += getFirstCurvetoCP1X() + "," + getFirstCurvetoCP1Y()
				+ " ";
		firstCurveto += getFirstCurvetoCP2X() + "," + getFirstCurvetoCP2Y()
				+ " ";
		firstCurveto += x + "," + getFirstCurvetoY();

		// second curveto
		String secondCurveto = CURVETO;
		secondCurveto += getSecondCurvetoCP1X() + "," + getSecondCurvetoCP1Y()
				+ " ";
		secondCurveto += getSecondCurvetoCP2X() + "," + getSecondCurvetoCP2Y()
				+ " ";
		secondCurveto += getSecondCurvetoX() + "," + getSecondCurvetoY();

		// path data
		String pathData = moveto + " " + firstCurveto + " " + secondCurveto;
		element.setAttribute("d", pathData);

		cachedFirstCurvetoX = x;
	}

	/**
	 * Sets the y-coordinate of the curveto.
	 * 
	 * @param y
	 *            y-coordinate
	 */
	public void setFirstCurvetoY(float y) {
		// moveto
		String moveto = MOVETO;
		moveto += getMovetoX() + "," + getMovetoY();

		// first curveto
		String firstCurveto = CURVETO;
		firstCurveto += getFirstCurvetoCP1X() + "," + getFirstCurvetoCP1Y()
				+ " ";
		firstCurveto += getFirstCurvetoCP2X() + "," + getFirstCurvetoCP2Y()
				+ " ";
		firstCurveto += getFirstCurvetoX() + "," + y;

		// second curveto
		String secondCurveto = CURVETO;
		secondCurveto += getSecondCurvetoCP1X() + "," + getSecondCurvetoCP1Y()
				+ " ";
		secondCurveto += getSecondCurvetoCP2X() + "," + getSecondCurvetoCP2Y()
				+ " ";
		secondCurveto += getSecondCurvetoX() + "," + getSecondCurvetoY();

		// path data
		String pathData = moveto + " " + firstCurveto + " " + secondCurveto;
		element.setAttribute("d", pathData);

		cachedFirstCurvetoY = y;
	}

	/**
	 * Returns the first curveto x-coordinate.
	 * 
	 * @return first curveto x-coordinate
	 */
	public float getFirstCurvetoX() {
		return cachedFirstCurvetoX;
	}

	/**
	 * Returns the first curveto y-coordinate.
	 * 
	 * @return first curveto y-coordinate
	 */
	public float getFirstCurvetoY() {
		return cachedFirstCurvetoY;
	}

	/**
	 * Sets the curveto coordinates and the control point coordinates to the
	 * specified values.
	 * 
	 * @param x1
	 *            x-coordinate
	 * @param y1
	 *            y-coordinate
	 * @param x2
	 *            x-coordinate
	 * @param y2
	 *            y-coordinate
	 * @param x
	 *            x-coordinate
	 * @param y
	 *            y-coordinate
	 */
	public void setSecondCurveto(float x1, float y1, float x2, float y2,
			float x, float y) {
		// first control point
		setSecondCurvetoCP1X(x1);
		setSecondCurvetoCP1Y(y1);

		// second control point
		setSecondCurvetoCP2X(x2);
		setSecondCurvetoCP2Y(y2);

		// set curveto
		setSecondCurvetoX(x);
		setSecondCurvetoY(y);
	}

	/**
	 * Sets the x-coordinate of the first control point of the curveto.
	 * 
	 * @param x1
	 *            x-coordinate
	 */
	public void setSecondCurvetoCP1X(float x1) {
		// moveto
		String moveto = MOVETO;
		moveto += getMovetoX() + "," + getMovetoY();

		// first curveto
		String firstCurveto = CURVETO;
		firstCurveto += getFirstCurvetoCP1X() + "," + getFirstCurvetoCP1Y()
				+ " ";
		firstCurveto += getFirstCurvetoCP2X() + "," + getFirstCurvetoCP2Y()
				+ " ";
		firstCurveto += getFirstCurvetoX() + "," + getFirstCurvetoY();

		// second curveto
		String secondCurveto = CURVETO;
		secondCurveto += x1 + "," + getSecondCurvetoCP1Y() + " ";
		secondCurveto += getSecondCurvetoCP2X() + "," + getSecondCurvetoCP2Y()
				+ " ";
		secondCurveto += getSecondCurvetoX() + "," + getSecondCurvetoY();

		// path data
		String pathData = moveto + " " + firstCurveto + " " + secondCurveto;
		element.setAttribute("d", pathData);

		cachedSecondCurvetoCP1X = x1;
	}

	/**
	 * Sets the y-coordinate of the first control point of the curveto.
	 * 
	 * @param y1
	 *            y-coordinate
	 */
	public void setSecondCurvetoCP1Y(float y1) {
		// moveto
		String moveto = MOVETO;
		moveto += getMovetoX() + "," + getMovetoY();

		// first curveto
		String firstCurveto = CURVETO;
		firstCurveto += getFirstCurvetoCP1X() + "," + getFirstCurvetoCP1Y()
				+ " ";
		firstCurveto += getFirstCurvetoCP2X() + "," + getFirstCurvetoCP2Y()
				+ " ";
		firstCurveto += getFirstCurvetoX() + "," + getFirstCurvetoY();

		// second curveto
		String secondCurveto = CURVETO;
		secondCurveto += getSecondCurvetoCP1X() + "," + y1 + " ";
		secondCurveto += getSecondCurvetoCP2X() + "," + getSecondCurvetoCP2Y()
				+ " ";
		secondCurveto += getSecondCurvetoX() + "," + getSecondCurvetoY();

		// path data
		String pathData = moveto + " " + firstCurveto + " " + secondCurveto;
		element.setAttribute("d", pathData);

		cachedSecondCurvetoCP1Y = y1;
	}

	/**
	 * Returns the first curveto control point x-coordinate of the second
	 * curveto.
	 * 
	 * @return first curveto control point x-coordinate
	 */
	public float getSecondCurvetoCP1X() {
		return cachedSecondCurvetoCP1X;
	}

	/**
	 * Returns the first curveto control point y-coordinate of the second
	 * curveto.
	 * 
	 * @return first curveto control point y-coordinate
	 */
	public float getSecondCurvetoCP1Y() {
		return cachedSecondCurvetoCP1Y;
	}

	/**
	 * Sets the x-coordinate of the second control point of the curveto.
	 * 
	 * @param x2
	 *            x-coordinate
	 */
	public void setSecondCurvetoCP2X(float x2) {
		// moveto
		String moveto = MOVETO;
		moveto += getMovetoX() + "," + getMovetoY();

		// first curveto
		String firstCurveto = CURVETO;
		firstCurveto += getFirstCurvetoCP1X() + "," + getFirstCurvetoCP1Y()
				+ " ";
		firstCurveto += getFirstCurvetoCP2X() + "," + getFirstCurvetoCP2Y()
				+ " ";
		firstCurveto += getFirstCurvetoX() + "," + getFirstCurvetoY();

		// second curveto
		String secondCurveto = CURVETO;
		secondCurveto += getSecondCurvetoCP1X() + "," + getSecondCurvetoCP1Y()
				+ " ";
		secondCurveto += x2 + "," + getSecondCurvetoCP2Y() + " ";
		secondCurveto += getSecondCurvetoX() + "," + getSecondCurvetoY();

		// path data
		String pathData = moveto + " " + firstCurveto + " " + secondCurveto;
		element.setAttribute("d", pathData);

		cachedSecondCurvetoCP2X = x2;
	}

	/**
	 * Sets the y-coordinate of the second control point of the curveto.
	 * 
	 * @param y2
	 *            y-coordinate
	 */
	public void setSecondCurvetoCP2Y(float y2) {
		// moveto
		String moveto = MOVETO;
		moveto += getMovetoX() + "," + getMovetoY();

		// first curveto
		String firstCurveto = CURVETO;
		firstCurveto += getFirstCurvetoCP1X() + "," + getFirstCurvetoCP1Y()
				+ " ";
		firstCurveto += getFirstCurvetoCP2X() + "," + getFirstCurvetoCP2Y()
				+ " ";
		firstCurveto += getFirstCurvetoX() + "," + getFirstCurvetoY();

		// second curveto
		String secondCurveto = CURVETO;
		secondCurveto += getSecondCurvetoCP1X() + "," + getSecondCurvetoCP1Y()
				+ " ";
		secondCurveto += getSecondCurvetoCP2X() + "," + y2 + " ";
		secondCurveto += getSecondCurvetoX() + "," + getSecondCurvetoY();

		// path data
		String pathData = moveto + " " + firstCurveto + " " + secondCurveto;
		element.setAttribute("d", pathData);

		cachedSecondCurvetoCP2Y = y2;
	}

	/**
	 * Returns the second curveto control point x-coordinate of the second
	 * curveto.
	 * 
	 * @return second curveto control point x-coordinate
	 */
	public float getSecondCurvetoCP2X() {
		return cachedSecondCurvetoCP2X;
	}

	/**
	 * Returns the second curveto control point y-coordinate of the second
	 * curveto.
	 * 
	 * @return second curveto control point y-coordinate
	 */
	public float getSecondCurvetoCP2Y() {
		return cachedSecondCurvetoCP2Y;
	}

	/**
	 * Sets the x-coordinate of the curveto.
	 * 
	 * @param x
	 *            x-coordinate
	 */
	public void setSecondCurvetoX(float x) {
		// moveto
		String moveto = MOVETO;
		moveto += getMovetoX() + "," + getMovetoY();

		// first curveto
		String firstCurveto = CURVETO;
		firstCurveto += getFirstCurvetoCP1X() + "," + getFirstCurvetoCP1Y()
				+ " ";
		firstCurveto += getFirstCurvetoCP2X() + "," + getFirstCurvetoCP2Y()
				+ " ";
		firstCurveto += getFirstCurvetoX() + "," + getFirstCurvetoY();

		// second curveto
		String secondCurveto = CURVETO;
		secondCurveto += getSecondCurvetoCP1X() + "," + getSecondCurvetoCP1Y()
				+ " ";
		secondCurveto += getSecondCurvetoCP2X() + "," + getSecondCurvetoCP2Y()
				+ " ";
		secondCurveto += x + "," + getSecondCurvetoY();

		// path data
		String pathData = moveto + " " + firstCurveto + " " + secondCurveto;
		element.setAttribute("d", pathData);

		cachedSecondCurvetoX = x;
	}

	/**
	 * Sets the y-coordinate of the curveto.
	 * 
	 * @param y
	 *            y-coordinate
	 */
	public void setSecondCurvetoY(float y) {
		// moveto
		String moveto = MOVETO;
		moveto += getMovetoX() + "," + getMovetoY();

		// first curveto
		String firstCurveto = CURVETO;
		firstCurveto += getFirstCurvetoCP1X() + "," + getFirstCurvetoCP1Y()
				+ " ";
		firstCurveto += getFirstCurvetoCP2X() + "," + getFirstCurvetoCP2Y()
				+ " ";
		firstCurveto += getFirstCurvetoX() + "," + getFirstCurvetoY();

		// second curveto
		String secondCurveto = CURVETO;
		secondCurveto += getSecondCurvetoCP1X() + "," + getSecondCurvetoCP1Y()
				+ " ";
		secondCurveto += getSecondCurvetoCP2X() + "," + getSecondCurvetoCP2Y()
				+ " ";
		secondCurveto += getSecondCurvetoX() + "," + y;

		// path data
		String pathData = moveto + " " + firstCurveto + " " + secondCurveto;
		element.setAttribute("d", pathData);

		cachedSecondCurvetoY = y;
	}

	/**
	 * Returns the second curveto x-coordinate.
	 * 
	 * @return second curveto x-coordinate
	 */
	public float getSecondCurvetoX() {
		return cachedSecondCurvetoX;
	}

	/**
	 * Returns the second curveto y-coordinate.
	 * 
	 * @return second curveto y-coordinate
	 */
	public float getSecondCurvetoY() {
		return cachedSecondCurvetoY;
	}

	/**
	 * Returns the multidominance branch element.
	 * 
	 * @return mdom branch element
	 */
	public MdomBranchElement getMdomBranchElement() {
		return mdomBranchElement;
	}
}