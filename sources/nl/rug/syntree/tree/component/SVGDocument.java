/* SVGDocument.java */

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

import java.io.File;

import org.apache.batik.dom.AbstractDocument;
import org.apache.batik.dom.AbstractElement;
import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.svg.SVGRect;
import org.w3c.dom.svg.SVGSVGElement;

/**
 * This class represents a scalable vector graphics document object model
 * document.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class SVGDocument {
	/**
	 * Scalable vector graphics aspect preservation ratio
	 */
	protected static final String PRESERVE_ASPECT_RATIO = "xMidYMid meet";

	/**
	 * A document object model document
	 */
	protected AbstractDocument document;

	/**
	 * Document element or root element of the document
	 */
	protected AbstractElement documentElement;

	/**
	 * Namespace of the document
	 */
	protected String namespace;

	/**
	 * Constructs a new document object model document that represents a
	 * scalable vector graphics document.
	 */
	public SVGDocument() {
		// dom implementation
		DOMImplementation svgDOMImplementation;
		svgDOMImplementation = SVGDOMImplementation.getDOMImplementation();

		// namespace
		namespace = SVGDOMImplementation.SVG_NAMESPACE_URI;

		// document
		document = (AbstractDocument) svgDOMImplementation.createDocument(
				namespace, "svg", null);

		// root element
		documentElement = (AbstractElement) document.getDocumentElement();

		// preserve aspect ratio
		documentElement.setAttribute("preserveAspectRatio",
				PRESERVE_ASPECT_RATIO);

		// width
		documentElement.setAttribute("width", "100%");

		// height
		documentElement.setAttribute("height", "100%");

		// crosshair
		documentElement.setAttribute("cursor", "crosshair");

		// documentElement.setAttribute("color-rendering", "optimizeSpeed");
		// documentElement.setAttribute("shape-rendering", "crispEdges");
		// documentElement.setAttribute("text-rendering", "optimizeLegibility");
		// documentElement.setAttribute("font-rendering", "optimizeLegibility");
	}

	/**
	 * Constructs a new document object model that represents the specified
	 * scalable vector graphics document.
	 * 
	 * @param svgXMLFile
	 *            the svg xml file to load
	 */
	public SVGDocument(File svgXMLFile) {
		try {
			String parser = XMLResourceDescriptor.getXMLParserClassName();
			SAXSVGDocumentFactory saxSVGDocumentFactory = new SAXSVGDocumentFactory(
					parser);
			document = (AbstractDocument) saxSVGDocumentFactory
					.createDocument(svgXMLFile.toURI().toString());

			// namespace
			namespace = SVGDOMImplementation.SVG_NAMESPACE_URI; // change this,
			// and be blown
			// into oblivion

			// root element
			documentElement = (AbstractElement) document.getDocumentElement();

			// preserve aspect ratio
			documentElement.setAttribute("preserveAspectRatio",
					PRESERVE_ASPECT_RATIO);

			// width
			documentElement.setAttribute("width", "100%");

			// height
			documentElement.setAttribute("height", "100%");

			// croshair
			documentElement.setAttribute("cursor", "crosshair");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the viewBox of the SVG document element to the geometry of the SVG
	 * bounding box.
	 */
	public void computeViewBox() {
		float x = 0.0f;
		float y = 0.0f;
		float w = 0.0f;
		float h = 0.0f;
		SVGSVGElement svgSVGElement = (SVGSVGElement) documentElement;
		SVGRect svgRect = svgSVGElement.getBBox();
		while (svgRect == null)
			if (svgSVGElement != null)
				svgRect = svgSVGElement.getBBox();
		x = svgRect.getX();
		y = svgRect.getY();
		w = svgRect.getWidth();
		h = svgRect.getHeight();
		svgSVGElement.setAttribute("viewBox", x + " " + y + " " + w + " " + h);
	}

	/**
	 * Returns the element that possesses the specified identifier.
	 * 
	 * @param id
	 *            identifier
	 */
	public AbstractElement getElementById(String id) {
		return (AbstractElement) document.getElementById(id);
	}

	/**
	 * Returns the identifier of the specified element.
	 * 
	 * @param element
	 *            element
	 */
	public String getIdByElement(AbstractElement element) {
		return element.getAttribute("id");
	}

	/**
	 * Returns the document object model document
	 * 
	 * @return document object model document
	 */
	public AbstractDocument getDocument() {
		return document;
	}

	/**
	 * Returns the document element or root element of the document
	 * 
	 * @return document element
	 */
	public AbstractElement getDocumentElement() {
		return documentElement;
	}

	/**
	 * Returns the namespace of the document
	 * 
	 * @return namespace of the document
	 */
	public String getNamespace() {
		return namespace;
	}
}