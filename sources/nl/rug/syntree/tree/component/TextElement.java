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

import java.util.Vector;

import nl.rug.syntree.tree.TreeRepresentation;

import org.apache.batik.dom.AbstractElement;
import org.w3c.dom.NodeList;
import org.w3c.dom.svg.SVGTextElement;

/**
 * This class represents a text element for a node.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class TextElement extends SVGElement {
	/**
	 * Default text label constant
	 */
	public static final String DEFAULT_TEXT = "x";

	/**
	 * Constant value for left alignment
	 */
	public static final String ALIGN_LEFT = "left";

	/**
	 * Constant value for center alignment
	 */
	public static final String ALIGN_CENTER = "center";

	/**
	 * Constant value for right alignment
	 */
	public static final String ALIGN_RIGHT = "right";

	/**
	 * Constant value for visibility
	 */
	protected static final String VISIBLE = "visible";

	/**
	 * Constant value for invisibility
	 */
	protected static final String INVISIBLE = "hidden";

	/**
	 * Current state of visibility
	 */
	protected boolean isVisible;

	/**
	 * Node element
	 */
	protected NodeElement nodeElement;

	/**
	 * Vector containing all the tspan elements
	 */
	protected Vector<TSpanElement> tspanVector;

	/**
	 * Cached line dimension matrix
	 */
	float cachedLineDimensions[][];

	/**
	 * Cached x-coordinate
	 */
	protected float cachedX = 0.0f;

	/**
	 * Cached y-coordinate
	 */
	protected float cachedY = 0.0f;

	/**
	 * Cached height
	 */
	protected float cachedHeight = 0.0f;

	/**
	 * Cached width
	 */
	protected float cachedWidth = 0.0f;

	/**
	 * Cached text content
	 */
	protected String cachedTextContent;

	/**
	 * Cached alignment
	 */
	protected String cachedAlignment;

	/**
	 * Constructs a new text element for a specified node element
	 * 
	 * @param treeRepresentation
	 *            tree representation
	 * @param nodeElement
	 *            node element
	 * @param text
	 *            text
	 */
	public TextElement(TreeRepresentation treeRepresentation,
			NodeElement nodeElement, String text) {
		super(treeRepresentation, "text");
		this.treeRepresentation = treeRepresentation;
		this.nodeElement = nodeElement;
		tspanVector = new Vector<TSpanElement>();

		// id
		setId(TreeRepresentation.NODE_TEXT_IDENTIFIER_PREFIX + "@"
				+ nodeElement.getId());

		element.setAttribute("style", "text-anchor:start;");
		element.setAttribute("xml:space", "preserve");

		// tspan
		createHeadTSpanElement(0);
		if (text == null)
			text = DEFAULT_TEXT;
		createTextTSpanElement(text, treeRepresentation.getTreeEditorBridge()
				.getUIFormatJToolBar().getFontFormatting(), 0);
		createTailTSpanElement(0);

		// properties
		setAlignment(treeRepresentation.getTreeEditorBridge()
				.getUIFormatJToolBar().getAlignment());
		setVisible(true);
		setTaintMode(TAINTED);
	}

	/**
	 * Constructs a new text element for a specified node element
	 * 
	 * @param treeRepresentation
	 *            tree representation
	 * @param element
	 *            SVG element
	 */
	public TextElement(TreeRepresentation treeRepresentation,
			NodeElement nodeElement, AbstractElement element) {
		super(treeRepresentation, element);
		this.treeRepresentation = treeRepresentation;
		this.nodeElement = nodeElement;

		// text span vector
		tspanVector = new Vector<TSpanElement>();
		NodeList nodeList = element.getElementsByTagName("tspan");
		for (int i = 0; i < nodeList.getLength(); i++) {
			AbstractElement childElement = (AbstractElement) nodeList.item(i);
			tspanVector.add(new TSpanElement(treeRepresentation, childElement));
		}

		// alignment
		String alignment = element.getAttribute("alignment");
		if (alignment.compareTo(ALIGN_LEFT) == 0)
			setAlignment(ALIGN_LEFT);
		if (alignment.compareTo(ALIGN_CENTER) == 0)
			setAlignment(ALIGN_CENTER);
		if (alignment.compareTo(ALIGN_RIGHT) == 0)
			setAlignment(ALIGN_RIGHT);

		// visible
		String visible = element.getAttribute("visibility");
		if (visible.compareTo(VISIBLE) == 0)
			setVisible(true);
		if (visible.compareTo(INVISIBLE) == 0)
			setVisible(false);

		setTaintMode(TAINTED);
	}

	/**
	 * 
	 */
	public TextElement duplicate(NodeElement nodeElement) {
		TextElement textElement = new TextElement(treeRepresentation,
				nodeElement, DEFAULT_TEXT);

		textElement.setVisible(this.isVisible());

		// remove initial tspan element
		while(textElement.getTSpanVector().size() > 0) {
			textElement.getElement().removeChild(
					textElement.getTSpanVector().lastElement().getElement());
			textElement.getTSpanVector().removeElement(
					textElement.getTSpanVector().lastElement());
		}

		// duplicate tspan elements
		for (int i = 0; i < this.getTSpanVector().size(); i++) {
			TSpanElement tspanElement = this.getTSpanVector().elementAt(i)
					.duplicate(textElement);
			textElement.getTSpanVector().add(tspanElement);
			textElement.getElement().appendChild(tspanElement.getElement());
		}

		return textElement;
	}

	/**
	 * Creates a new text tspan element for this text element.
	 * 
	 * @param text
	 *            text
	 * @param style
	 *            style
	 * @param line
	 *            line
	 */
	public TSpanElement createTextTSpanElement(String text, String style, int line) {
		return createTextTSpanElement(null, text, style, line);
	}

	/**
	 * Creates a new text tspan element for this text element before the
	 * specified next tspan element.
	 * 
	 * @param nextTSpanElement
	 *            next tspan element
	 * @param text
	 *            text
	 * @param style
	 *            style
	 * @param line
	 *            line
	 */
	public TSpanElement createTextTSpanElement(TSpanElement nextTSpanElement,
			String text, String style, int line) {
		TSpanElement tspanElement = new TSpanElement(treeRepresentation, text,
				style, line, TSpanElement.TEXT_TYPE);		
		if (nextTSpanElement == null) {
			element.appendChild(tspanElement.getElement());
			tspanVector.add(tspanElement);
		}
		if (nextTSpanElement != null) {
			element.insertBefore(tspanElement.getElement(), nextTSpanElement
					.getElement());
			int index = tspanVector.indexOf(nextTSpanElement);
			tspanVector.insertElementAt(tspanElement, index);
		}
		return tspanElement;
	}

	/**
	 * Creates a new head tpsan element for this text element.
	 * 
	 * @param line
	 *            line number
	 */
	public TSpanElement createHeadTSpanElement(int line) {
		return createHeadTSpanElementBefore(null, line);
	}

	/**
	 * Creates a new head tpsan element for this text element before the
	 * specified next tspan element.
	 * 
	 * @param nextTSpanElement
	 *            next tspan element
	 * @param line
	 *            line number
	 */
	public TSpanElement createHeadTSpanElementBefore(TSpanElement nextTSpanElement,
			int line) {
		TSpanElement tspanElement = new TSpanElement(treeRepresentation, "|",
				"fill:none;", line, TSpanElement.HEAD_TYPE);		
		if (nextTSpanElement == null) {
			element.appendChild(tspanElement.getElement());
			tspanVector.add(tspanElement);
		}
		if (nextTSpanElement != null) {
			element.insertBefore(tspanElement.getElement(), nextTSpanElement
					.getElement());
			int index = tspanVector.indexOf(nextTSpanElement);
			tspanVector.insertElementAt(tspanElement, index);
		}
		return tspanElement;
	}

	/**
	 * Creates a new tail tpsan element for this text element.
	 * 
	 * @param line
	 *            line number
	 */
	public TSpanElement createTailTSpanElement(int line) {
		return createTailTSpanElementBefore(null, line);
	}

	/**
	 * Creates a new tail tpsan element for this text element before the
	 * specified next tspan element.
	 * 
	 * @param nextTSpanElement
	 *            next tspan element
	 * @param line
	 *            line number
	 */
	public TSpanElement createTailTSpanElementBefore(TSpanElement nextTSpanElement,
			int line) {
		TSpanElement tspanElement = new TSpanElement(treeRepresentation, "|",
				"fill:none;", line, TSpanElement.TAIL_TYPE);		
		if (nextTSpanElement == null) {
			element.appendChild(tspanElement.getElement());
			tspanVector.add(tspanElement);
		}
		if (nextTSpanElement != null) {
			element.insertBefore(tspanElement.getElement(), nextTSpanElement
					.getElement());
			int index = tspanVector.indexOf(nextTSpanElement);
			tspanVector.insertElementAt(tspanElement, index);
		}
		return tspanElement;
	}

	public void removeTSpanElement(TSpanElement tspanElement) {
		element.removeChild(tspanElement.getElement());
		tspanVector.removeElement(tspanElement);
	}

	/**
	 * Linearizes the line attributes of the tspan elements in the tspan vector.
	 */
	public void linearizeTSpanVector() {
		boolean linearFromZero = false;
		int currentLine = 0;
		for (int i = 0; i < tspanVector.size(); i++) {
			TSpanElement tspanElement = tspanVector.elementAt(i);
			int nextLine = tspanElement.getLine();
			if (nextLine == 0)
				linearFromZero = true;
			if (nextLine - currentLine == 1)
				if (!linearFromZero)
					tspanElement.setLine(0);
				else
					currentLine = nextLine;
			if (nextLine - currentLine > 1)
				tspanElement.setLine(currentLine + 1);
		}
	}

	/**
	 * Returns the (cached) x-coordinate of this element.
	 * 
	 * @return x-coordinate
	 */
	public float getX() {
		if (isVisible()) {
			if (getText().length() == 0)
				cachedX = 0.0f;
			if (getText().length() > 0 && getTaintMode() == TAINTED)
				cachedX = ((SVGTextElement) element).getBBox().getX();
		} else {
			cachedX = 0.0f;
		}
		return cachedX;
	}

	/**
	 * Returns the (cached) y-coordinate of this element.
	 * 
	 * @return y-coordinate
	 */
	public float getY() {
		if (isVisible()) {
			if (getText().length() == 0)
				cachedY = 0.0f;
			if (getText().length() > 0 && getTaintMode() == TAINTED)
				cachedY = ((SVGTextElement) element).getBBox().getY();
		} else {
			cachedY = 0.0f;
		}
		return cachedY;
	}

	/**
	 * Returns the (cached) height of this element.
	 * 
	 * @return height
	 */
	public float getHeight() {
		if (isVisible()) {
			if (getText().length() == 0)
				cachedHeight = 0.0f;
			if (getText().length() > 0 && getTaintMode() == TAINTED)
				cachedHeight = ((SVGTextElement) element).getBBox().getHeight();
		} else {
			cachedHeight = 0.0f;
		}
		return cachedHeight;
	}

	/**
	 * Returns the (cached) width of this element.
	 * 
	 * @return width
	 */
	public float getWidth() {
		if (isVisible()) {
			if (getText().length() == 0)
				cachedWidth = 0.0f;
			if (getText().length() > 0 && getTaintMode() == TAINTED)
				cachedWidth = ((SVGTextElement) element).getBBox().getWidth();
		} else {
			cachedWidth = 0.0f;
		}
		return cachedWidth;
	}

	/**
	 * Returns the starting x-coordinate of the character at the specified
	 * position.
	 * 
	 * @param position
	 *            position
	 * 
	 * @return x-coordinate
	 */
	public float getCharStartX(int position) {
		float x = 0.0f;
		if (position >= 0 && position < getText().length())
			x = ((SVGTextElement) element).getStartPositionOfChar(position)
					.getX();
		return x;
	}

	/**
	 * Returns the starting y-coordinate of the character at the specified
	 * position.
	 * 
	 * @param position
	 *            position
	 * 
	 * @return y-coordinate
	 */
	public float getCharStartY(int position) {
		float y = 0.0f;
		if (position >= 0 && position < getText().length())
			y = ((SVGTextElement) element).getStartPositionOfChar(position)
					.getY();
		return y;
	}

	/**
	 * Return the ending x-coordinate of the character at the specified
	 * position.
	 * 
	 * @param position
	 *            position
	 * 
	 * @return x-coordinate
	 */
	public float getCharEndX(int position) {
		float x = 0.0f;
		if (position >= 0 && position < getText().length())
			x = ((SVGTextElement) element).getEndPositionOfChar(position)
					.getX();
		return x;
	}

	/**
	 * Returns the ending y-coordinate of the character at the specified
	 * position.
	 * 
	 * @param position
	 *            position
	 * 
	 * @return y-coordinate
	 */
	public float getCharEndY(int position) {
		float y = 0.0f;
		if (position >= 0 && position < getText().length())
			y = ((SVGTextElement) element).getEndPositionOfChar(position)
					.getY();
		return y;
	}

	/**
	 * Returns the height of the character at the specified position.
	 * 
	 * @param position
	 *            position
	 * 
	 * @return height
	 */
	public float getCharHeight(int position) {
		float h = 0.0f;
		if (position >= 0 && position < getText().length()) {
			h = ((SVGTextElement) element).getExtentOfChar(position)
					.getHeight();
		}
		return h;
	}

	/**
	 * Returns the width of the character at the specified position.
	 * 
	 * @param position
	 *            position
	 * 
	 * @return width
	 */
	public float getCharWidth(int position) {
		float w = 0.0f;
		if (position >= 0 && position < getText().length())
			w = ((SVGTextElement) element).getExtentOfChar(position).getWidth();
		return w;
	}

	/**
	 * Returns the height of the generation to which this text element belongs.
	 * 
	 * @return generation height
	 */
	public float getGenerationHeight() {
		float height = 0.0f;
		Vector<NodeElement> generationVector = nodeElement
				.getGenerationVector();
		for (int i = 0; i < generationVector.size(); i++)
			height = Math.max(height, generationVector.elementAt(i)
					.getTextElement().getHeight());
		return height;
	}

	/**
	 * Returns a matrix containing the line dimensions of this text element. The
	 * rows in this matrix represent the lines, the two columns represent
	 * respectively the width and the height of the corresponding line.
	 * 
	 * @return line dimension matrix
	 */
	public float[][] getLineDimensions() {
		float lineDimensions[][] = new float[0][0];
		if (getTaintMode() == TAINTED) {
			int lines = 1;
			if (tspanVector.size() > 0)
				lines = tspanVector.lastElement().getLine() + 1;
			lineDimensions = new float[lines][2];
			lineDimensions[0][0] = 0.0f;
			lineDimensions[0][1] = 0.0f;
			for (int i = 0; i < lines; i++) {
				float h = 0.0f;
				float w = 0.0f;
				for (int x = 0; x < tspanVector.size(); x++) {
					if (tspanVector.elementAt(x).getLine() == i) {
						h = Math.max(h, tspanVector.elementAt(x).getHeight());
						w += tspanVector.elementAt(x).getWidth();
					}
					lineDimensions[i][0] = w;
					lineDimensions[i][1] = 0.5f * h; // bug in batik?!
				}
			}
			cachedLineDimensions = lineDimensions;
		}
		if (getTaintMode() == UNTAINTED)
			lineDimensions = cachedLineDimensions;
		return lineDimensions;
	}

	/**
	 * Returns the tspan vector.
	 * 
	 * @return tspan vector
	 */
	public Vector<TSpanElement> getTSpanVector() {
		return tspanVector;
	}

	/**
	 * Returns the node element.
	 * 
	 * @return node element
	 */
	public NodeElement getNodeElement() {
		return nodeElement;
	}

	/**
	 * Sets the taint mode of the element to the specified taint mode.
	 * 
	 * @param taintMode
	 *            taint mode
	 */
	public void setTaintMode(int taintMode) {
		super.setTaintMode(taintMode);
		if (taintMode == UNTAINTED)
			for (int i = 0; i < tspanVector.size(); i++)
				tspanVector.elementAt(i).setTaintMode(UNTAINTED);
	}

	/**
	 * Returns the (cached) text of this text element.
	 * 
	 * @return text
	 */
	public String getText() {
		if (getTaintMode() == TAINTED)
			cachedTextContent = element.getTextContent();
		return cachedTextContent;
	}

	/**
	 * Returns the cached alignment of this text element.
	 * 
	 * @return alignment
	 */
	public String getAlignment() {
		return cachedAlignment;
	}

	/**
	 * Sets the alignment of this text element to the specified alignment.
	 * 
	 * @param alignment
	 *            alignment
	 */
	public void setAlignment(String alignment) {
		if (alignment.compareTo(ALIGN_LEFT) == 0) {
			element.setAttribute("alignment", ALIGN_LEFT);
			cachedAlignment = alignment;
		}
		if (alignment.compareTo(ALIGN_CENTER) == 0) {
			element.setAttribute("alignment", ALIGN_CENTER);
			cachedAlignment = alignment;
		}
		if (alignment.compareTo(ALIGN_RIGHT) == 0) {
			element.setAttribute("alignment", ALIGN_RIGHT);
			cachedAlignment = alignment;
		}
	}

	/**
	 * Returns a boolean indicating whether this text element is visible.
	 * 
	 * @return boolean
	 */
	public boolean isVisible() {
		return isVisible;
	}

	/**
	 * Sets the visibility of this text element to the specified boolean.
	 * 
	 * @param isVisible
	 *            boolean
	 */
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
		if (isVisible) {
			element.setAttribute("visibility", VISIBLE);
		} else {
			element.setAttribute("visibility", INVISIBLE);
		}

		// taint
		setTaintMode(TextElement.TAINTED);
		for (int i = 0; i < nodeElement.getDaughterVector().size(); i++)
			nodeElement.getDaughterVector().elementAt(i).setTaintMode(
					NodeElement.TAINTED);
		NodeElement iteratorElement = nodeElement;
		while (iteratorElement != null) {
			iteratorElement.setTaintMode(NodeElement.TAINTED);
			iteratorElement = iteratorElement.getMother();
		}
	}
}