/* IOSVGXMLFileWriter.java */

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

package nl.rug.syntree.io;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import nl.rug.syntree.tree.TreeRepresentation;

import org.apache.batik.dom.AbstractDocument;

/**
 * This class represents a SVG XML file writer.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class IOSVGXMLFileWriter {
	/**
	 * Reference to the tree representation we want to save
	 */
	TreeRepresentation treeRepresentation;

	/**
	 * The SVG XML file we are going to write the tree representation to
	 */
	File svgXMLFile;

	/**
	 * Constructs a new SVG XML file writer.
	 * 
	 * @param treeRepresentation
	 *            the tree representation to write
	 * @param svgXMLFile
	 *            the svg xml file to write to
	 */
	public IOSVGXMLFileWriter(TreeRepresentation treeRepresentation,
			File svgXMLFile) {
		this.treeRepresentation = treeRepresentation;
		this.svgXMLFile = svgXMLFile;
	}

	/**
	 * Writes the tree representation to the SVG XML file.
	 */
	public int writeFile() {
		int returnCode = 0;
		try {
			AbstractDocument document = treeRepresentation.getSVGDocument()
					.getDocument();
			FileOutputStream fileOutputStream = new FileOutputStream(svgXMLFile);
			Transformer transformer = TransformerFactory.newInstance()
					.newTransformer();
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			// transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			// transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount",
			// "10");
			transformer.transform(new DOMSource(document), new StreamResult(
					fileOutputStream));
		} catch (Exception e) {
			e.printStackTrace();
			returnCode = -1;
		}
		return returnCode;
	}
}