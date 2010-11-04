/* TextElement.java */

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
import org.w3c.dom.svg.SVGTSpanElement;

/**
 * This class represents a tspan element for a text element.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class TSpanElement extends SVGElement {
	/**
	 * Constant indicating head type tspan element
	 */
	public static final int HEAD_TYPE = 0;

	/**
	 * Constant indicating text type tspan element
	 */
	public static final int TEXT_TYPE = 1;

	/**
	 * Constant indicating tail type tspan element
	 */
	public static final int TAIL_TYPE = 2;

	/**
	 * Cached type of this tspan element
	 */
	protected int cachedType;

	/**
	 * Cached delta x-coordinate
	 */
	protected float cachedDX = 0.0f;

	/**
	 * Cached delta y-coordinate
	 */
	protected float cachedDY = 0.0f;

	/**
	 * Cached height
	 */
	protected float cachedHeight = 0.0f;

	/**
	 * Cached width
	 */
	protected float cachedWidth = 0.0f;

	/**
	 * Cached text
	 */
	protected String cachedText;

	/**
	 * Cached line
	 */
	protected int cachedLine = 0;

	/**
	 * Constructs a new tspan element.
	 * 
	 * @param treeRepresentation
	 *            tree representation
	 * @param text
	 */
	public TSpanElement(TreeRepresentation treeRepresentation, String text,
			String style, int line, int type) {
		super(treeRepresentation, "tspan");

		element.setAttribute("space", "false");

		setTaintMode(TAINTED);
		setStyle(style);
		setDX(cachedDX);
		setDY(cachedDY);
		setLine(line);
		setText(text);
		setType(type);
	}

	/**
	 * Constructs a new tspan element.
	 * 
	 * @param treeRepresentation
	 *            tree representation
	 * @param element
	 *            SVG element
	 */
	public TSpanElement(TreeRepresentation treeRepresentation,
			AbstractElement element) {
		super(treeRepresentation, element);

		// style
		setStyle(element.getAttribute("style"));

		// line
		int line = Integer.parseInt(element.getAttribute("line"));
		setLine(line);

		// type
		int type = Integer.parseInt(element.getAttribute("type"));
		setType(type);
		
		setDX(cachedDX);
		setDY(cachedDY);

		// text
		setText(element.getTextContent());

		// taint
		setTaintMode(TAINTED);
	}

	/**
	 * Duplicates this tspan element for the specified text element
	 * 
	 * @param textElement
	 *            text element element
	 * @reutrn duplicate of this tpsan element
	 */
	public TSpanElement duplicate(TextElement textElement) {
		TSpanElement tspanElement = new TSpanElement(textElement
				.getTreeRepresentation(), this.getText(), this.getStyle(), this
				.getLine(), this.getType());
		return tspanElement;
	}

	/**
	 * Set the delta x-coordinate of this tspan element.
	 * 
	 * @param dx
	 *            dx
	 */
	public void setDX(float dx) {
		element.setAttribute("dx", Float.toString(dx));
		cachedDX = dx;
	}

	/**
	 * Set the delta y-coordinate of this tspan element.
	 * 
	 * @param dy
	 *            dy
	 */
	public void setDY(float dy) {
		element.setAttribute("dy", Float.toString(dy));
		cachedDY = dy;
	}

	/**
	 * Returns the cached delta x-coordinate of this tspan element.
	 * 
	 * @return delta x-coordinate
	 */
	public float getDX() {
		return cachedDX;
	}

	/**
	 * Returns the cached delta y-coordinate of this tspan element.
	 * 
	 * @return delta y-coordinate
	 */
	public float getDY() {
		return cachedDY;
	}

	/**
	 * Returns the (cached) height of this tspan element.
	 * 
	 * @return height
	 */
	public float getHeight() {
		if (getTaintMode() == TAINTED) {
			SVGTSpanElement svgTSpanElement = (SVGTSpanElement) element;
			for (int z = 0; z < getText().length(); z++)
				cachedHeight = Math.max(cachedHeight, svgTSpanElement
						.getExtentOfChar(z).getHeight());
		}
		return cachedHeight;
	}

	/**
	 * Returns the (cached) width of this tspan element.
	 * 
	 * @return width
	 */
	public float getWidth() {
		if (getTaintMode() == TAINTED) {
			SVGTSpanElement svgTSpanElement = (SVGTSpanElement) element;
			cachedWidth = svgTSpanElement.getComputedTextLength();
		}
		return cachedWidth;
	}

	/**
	 * Sets the text of this tspan element.
	 * 
	 * @param text
	 *            text
	 */
	public void setText(String text) {
		element.setTextContent(text);
		cachedText = text;
	}

	/**
	 * Returns the cached text of this tspan element.
	 * 
	 * @return text
	 */
	public String getText() {
		return cachedText;
	}

	/**
	 * Sets the line of this tspan element.
	 * 
	 * @param line
	 *            line
	 */
	public void setLine(int line) {
		element.setAttribute("line", Integer.toString(line));
		cachedLine = line;
	}

	/**
	 * Returns the cached line of this tspan element.
	 * 
	 * @return line
	 */
	public int getLine() {
		return cachedLine;
	}

	/**
	 * Sets the type of this tpsan element to the specified type
	 * 
	 * @param type
	 *            type
	 */
	public void setType(int type) {
		element.setAttribute("type", Integer.toString(type));
		cachedType = type;
	}

	/**
	 * Returns the type of this tspan element
	 * 
	 * @return type
	 */
	public int getType() {
		return cachedType;
	}
}