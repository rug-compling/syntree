/* SVGRectElement.java */

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
 * This class represents an abstract svg rectangle element.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public abstract class SVGRectElement extends SVGElement {
	/**
	 * Constant x-radius value for rectangle rounding
	 */
	protected static final float ROUNDING_X_RADIUS = 1.5f;

	/**
	 * Constant y-radius value for rectangle rounding
	 */
	protected static final float ROUNDING_Y_RADIUS = 1.5f;

	/**
	 * Cached x-coordinate in floating point precision
	 */
	protected float cachedX = 0.0f;

	/**
	 * Cached y-coordinate in floating point precision
	 */
	protected float cachedY = 0.0f;

	/**
	 * Cached height in floating point precision
	 */
	protected float cachedHeight = 0.0f;

	/**
	 * Cached width in floating point precision
	 */
	protected float cachedWidth = 0.0f;

	/**
	 * Constructs a new abstract SVG rectangle element.
	 * 
	 * @param treeRepresentation
	 *            tree representation
	 */
	public SVGRectElement(TreeRepresentation treeRepresentation) {
		super(treeRepresentation, "rect");

		// rounding
		element.setAttribute("rx", Float.toString(ROUNDING_X_RADIUS));
		element.setAttribute("ry", Float.toString(ROUNDING_Y_RADIUS));

		// stroke
		element.setAttribute("stroke-width", "1");
		element.setAttribute("stroke-linecap", "round");
		element.setAttribute("stroke-linejoin", "round");

		// properties
		setFillOpacity(cachedFillOpacity);
		setStrokeOpacity(cachedStrokeOpacity);
		setX(cachedX);
		setY(cachedY);
		setWidth(cachedWidth);
		setHeight(cachedHeight);
	}

	/**
	 * Constructs a new abstract SVG rectangle element.
	 * 
	 * @param treeRepresentation
	 *            tree representation
	 * @param element
	 *            SVG element
	 */

	public SVGRectElement(TreeRepresentation treeRepresentation,
			AbstractElement element) {
		super(treeRepresentation, element);
	}

	/**
	 * Sets the x-coordinate of the rectangle.
	 * 
	 * @param x
	 *            x-coordinate
	 */
	public void setX(float x) {
		element.setAttribute("x", Float.toString(x));
		cachedX = x;
	}

	/**
	 * Sets the y-coordinate of the rectangle.
	 * 
	 * @param y
	 *            y-coordinate
	 */
	public void setY(float y) {
		element.setAttribute("y", Float.toString(y));
		cachedY = y;
	}

	/**
	 * Returns the x-coordinate of the rectangle.
	 * 
	 * @return x-coordinate
	 */
	public float getX() {
		return cachedX;
	}

	/**
	 * Returns the y-coordinate of the rectangle.
	 * 
	 * @return y-coordinate
	 */
	public float getY() {
		return cachedY;
	}

	/**
	 * Sets the height of the rectangle.
	 * 
	 * @param height
	 *            height
	 */
	public void setHeight(float height) {
		element.setAttribute("height", Float.toString(height));
		cachedHeight = height;
	}

	/**
	 * Sets the width of the rectangle.
	 * 
	 * @param width
	 *            width
	 */
	public void setWidth(float width) {
		element.setAttribute("width", Float.toString(width));
		cachedWidth = width;
	}

	/**
	 * Returns the height of the rectangle.
	 * 
	 * @return height
	 */
	public float getHeight() {
		return cachedHeight;
	}

	/**
	 * Returns the width of the rectangle.
	 * 
	 * @return width
	 */
	public float getWidth() {
		return cachedWidth;
	}
}