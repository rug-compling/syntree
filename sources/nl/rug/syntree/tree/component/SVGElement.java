/* SVGElement.java */

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
import org.apache.batik.dom.svg.SVGOMPoint;
import org.w3c.dom.svg.SVGLocatable;
import org.w3c.dom.svg.SVGPoint;
import org.w3c.dom.svg.SVGRect;
import org.w3c.dom.svg.SVGSVGElement;

/**
 * This class represents an abstract svg dom element.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public abstract class SVGElement {
	/**
	 * Constant attribute value for tainted mode
	 */
	public static final int TAINTED = 0x00;

	/**
	 * Constant attribute value of untainted mode
	 */
	public static final int UNTAINTED = 0x01;

	/**
	 * Tree represenation
	 */
	protected TreeRepresentation treeRepresentation;

	/**
	 * Document object model element
	 */
	protected AbstractElement element;

	/**
	 * Identifier
	 */
	protected String id;

	/**
	 * Taint mode for this element
	 */
	protected int taintMode;

	/**
	 * Cached x-translation
	 */
	protected float cachedXTranslate = 0.0f;

	/**
	 * Cached y-translation
	 */
	protected float cachedYTranslate = 0.0f;

	/**
	 * Cached rotation angle
	 */
	protected float cachedARotate = 0.0f;

	/**
	 * Cached rotation x-coordinate
	 */
	protected float cachedXRotate = 0.0f;

	/**
	 * Cached rotation y-coordinate
	 */
	protected float cachedYRotate = 0.0f;

	/**
	 * Cached style
	 */
	protected String cachedStyle;

	/**
	 * Cached fill color
	 */
	protected String cachedFill;

	/**
	 * Cached stroke color
	 */
	protected String cachedStroke;

	/**
	 * Cached fill opacity
	 */
	protected float cachedFillOpacity = 0.0f;

	/**
	 * Cached stroke opacity
	 */
	protected float cachedStrokeOpacity = 0.0f;

	/**
	 * Constructs a new scalable vector graphics dom element for the specified
	 * tree representation.
	 * 
	 * @param treeRepresentation
	 *            tree representation
	 */
	public SVGElement(TreeRepresentation treeRepresentation, String tag) {
		this.treeRepresentation = treeRepresentation;
		SVGDocument svgDocument = treeRepresentation.getSVGDocument();
		this.element = (AbstractElement) svgDocument.getDocument()
				.createElementNS(svgDocument.getNamespace(), tag);
		setTranslate(cachedXTranslate, cachedYTranslate);
		setRotate(cachedARotate, cachedXRotate, cachedYRotate);
		setTaintMode(TAINTED);
	}

	/**
	 * Constructs a new scalable vector graphics DOM element for the specified
	 * tree representation from a SVG element.
	 * 
	 * @param treeRepresentation
	 *            tree representation
	 * @param element
	 *            SVG element
	 */
	public SVGElement(TreeRepresentation treeRepresentation,
			AbstractElement element) {
		this.treeRepresentation = treeRepresentation;
		this.element = element;
		id = element.getAttribute("id");
		setTranslate(cachedXTranslate, cachedYTranslate);
		setRotate(cachedARotate, cachedXRotate, cachedYRotate);
		setTaintMode(TAINTED);
	}

	/**
	 * Return the SVG point for this element.
	 * 
	 * @return SVG point
	 */
	public SVGPoint getSVGPoint() {
		SVGLocatable svgLocatable = (SVGLocatable) element;
		SVGRect svgRect = svgLocatable.getBBox();
		while (svgRect == null)
			svgRect = svgLocatable.getBBox();
		SVGOMPoint svgOMPoint = new SVGOMPoint(svgRect.getX(), svgRect.getY());
		SVGSVGElement svgElement = (SVGSVGElement) treeRepresentation
				.getSVGDocument().getDocumentElement();
		return svgOMPoint.matrixTransform(svgLocatable
				.getTransformToElement(svgElement));
	}

	/**
	 * Sets the transform attribute of the element to translate to the specified
	 * x-coordinate and y-coordinate.
	 * 
	 * @param x
	 *            x-coordinate
	 * @param y
	 *            y-coordinate
	 */
	public void setTranslate(float x, float y) {
		setXTranslate(x);
		setYTranslate(y);
	}

	/**
	 * Sets the transform attribute of the element to translate to the specified
	 * x-coordinate.
	 * 
	 * @param x
	 *            x-coordinate
	 */
	public void setXTranslate(float x) {
		String rotate = "rotate(" + getARotate() + "," + getXRotate() + ","
				+ getYRotate() + ")";
		element.setAttribute("transform", "translate(" + x + ","
				+ getYTranslate() + ") " + rotate);
		cachedXTranslate = x;
	}

	/**
	 * Sets the transform attribute of the element to translate to the specified
	 * y-coordinate.
	 * 
	 * @param y
	 *            y-coordinate
	 */
	public void setYTranslate(float y) {
		String rotate = "rotate(" + getARotate() + "," + getXRotate() + ","
				+ getYRotate() + ")";
		element.setAttribute("transform", "translate(" + getXTranslate() + ","
				+ y + ") " + rotate);
		cachedYTranslate = y;
	}

	/**
	 * Returns the x-coordinate to which the transform attribute translates.
	 * 
	 * @return x-coordinate
	 */
	public float getXTranslate() {
		return cachedXTranslate;
	}

	/**
	 * Returns the y-coordinate to which the transform attribute translates.
	 * 
	 * @return y-coordinate
	 */
	public float getYTranslate() {
		return cachedYTranslate;
	}

	/**
	 * Sets the transform attribute of the element to rotate by the specified
	 * angle in the specified x-coordinate and y-coordinate.
	 * 
	 * @param a
	 *            rotation angle
	 * @param x
	 *            x-coordinate
	 * @param y
	 *            y-coordinate
	 */
	public void setRotate(float a, float x, float y) {
		setARotate(a);
		setXRotate(x);
		setYRotate(y);
	}

	/**
	 * Sets the transform attribute of the element to rotate by the specified
	 * angle.
	 * 
	 * @param a
	 *            rotation angle
	 */
	public void setARotate(float a) {
		String transform = "translate(" + getXTranslate() + ","
				+ getYTranslate() + ")";
		element.setAttribute("transform", transform + " rotate(" + a + ","
				+ getXRotate() + "," + getYRotate() + ")");
		cachedARotate = a;
	}

	/**
	 * Sets the transform attribute of the element to rotate in the specified
	 * x-coordinate.
	 * 
	 * @param x
	 *            x-coordinate
	 */
	public void setXRotate(float x) {
		String transform = "translate(" + getXTranslate() + ","
				+ getYTranslate() + ")";
		element.setAttribute("transform", transform + " rotate(" + getARotate()
				+ "," + x + "," + getYRotate() + ")");
		cachedXRotate = x;
	}

	/**
	 * Sets the transform attribute of the element to rotate in the specified
	 * x-coordinate.
	 * 
	 * @param y
	 *            y-coordinate
	 */
	public void setYRotate(float y) {
		String transform = "translate(" + getXTranslate() + ","
				+ getYTranslate() + ")";
		element.setAttribute("transform", transform + " rotate(" + getARotate()
				+ "," + getXRotate() + "," + y + ")");
		cachedYRotate = y;
	}

	/**
	 * Returns the angle with which the transform attribute rotates.
	 * 
	 * @return rotation angle
	 */
	public float getARotate() {
		return cachedARotate;
	}

	/**
	 * Returns the x-coordinate in which the transform attribute rotates.
	 * 
	 * @return x-coordinate
	 */
	public float getXRotate() {
		return cachedXRotate;
	}

	/**
	 * Returns the y-coordinate in which the transform attribute rotates.
	 * 
	 * @return y-coordinate
	 */
	public float getYRotate() {
		return cachedYRotate;
	}

	/**
	 * Sets the taint mode of the element to the specified taint mode.
	 * 
	 * @param taintMode
	 *            taint mode
	 */
	public void setTaintMode(int taintMode) {
		if (taintMode == TAINTED)
			this.taintMode = TAINTED;
		if (taintMode == UNTAINTED)
			this.taintMode = UNTAINTED;
	}

	/**
	 * Returns the taint mode of the element.
	 * 
	 * @return taint mode
	 */
	public int getTaintMode() {
		return taintMode;
	}

	/**
	 * Sets the style of this element.
	 * 
	 * @param style
	 *            style
	 */
	public void setStyle(String style) {
		element.setAttribute("style", style);
		cachedStyle = style;
	}

	/**
	 * Returns the style of this element.
	 * 
	 * @return style
	 */
	public String getStyle() {
		return cachedStyle;
	}

	/**
	 * Sets the fill color of the rectangle.
	 * 
	 * @param color
	 *            fill color
	 */
	public void setFill(String color) {
		element.setAttribute("fill", color);
		cachedFill = color;
	}

	/**
	 * Returns the fill color of the rectangle.
	 * 
	 * @return fill color
	 */
	public String getFill() {
		return cachedFill;
	}

	/**
	 * Sets the stroke color of the rectangle.
	 * 
	 * @param color
	 *            stroke color
	 */
	public void setStroke(String color) {
		element.setAttribute("stroke", color);
		cachedStroke = color;
	}

	/**
	 * Returns the stroke color of the rectangle.
	 * 
	 * @return stroke color
	 */
	public String getStroke() {
		return cachedStroke;
	}

	/**
	 * Sets the fill opacity of the rectangle.
	 * 
	 * @param opacity
	 *            fill opacity
	 */
	public void setFillOpacity(float opacity) {
		element.setAttribute("fill-opacity", Float.toString(opacity));
		cachedFillOpacity = opacity;
	}

	/**
	 * Returns the fill opacity of the rectangle.
	 * 
	 * @return fill opacity
	 */
	public float getFillOpacity() {
		return cachedFillOpacity;
	}

	/**
	 * Sets the stroke opacity of the rectangle.
	 * 
	 * @param opacity
	 *            stroke opacity
	 */
	public void setStrokeOpacity(float opacity) {
		element.setAttribute("stroke-opacity", Float.toString(opacity));
		cachedStrokeOpacity = opacity;
	}

	/**
	 * Returns the stroke opacity of the rectangle.
	 * 
	 * @return stroke opacity
	 */
	public float getStrokeOpacity() {
		return cachedStrokeOpacity;
	}

	/**
	 * Sets the dom element of this abstract element to the specified element.
	 * 
	 * @param element
	 *            element
	 */
	public void setElement(AbstractElement element) {
		this.element = element;
	}

	/**
	 * Returns the document object model element.
	 * 
	 * @return element
	 */
	public AbstractElement getElement() {
		return element;
	}

	/**
	 * Sets the identifier of this element to the specified identifier.
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
		element.setAttribute("id", id);
	}

	/**
	 * Returns the identifier of this element.
	 * 
	 * @return identifier
	 */
	public String getId() {
		return id;
	}

	/**
	 * Returns the tree representation.
	 * 
	 * @return tree representation
	 */
	public TreeRepresentation getTreeRepresentation() {
		return treeRepresentation;
	}
}
