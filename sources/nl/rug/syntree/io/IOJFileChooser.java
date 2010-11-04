/* Main.java */

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

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

/**
 * This class represents a file chooser for input and output.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class IOJFileChooser extends JFileChooser {
	/**
	 * Serial version identifier constant
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Scalable Vector Graphics XML file identifier
	 */
	public static final int SVG_XML = 0x00;

	/**
	 * Transcoded image format files identifier
	 */
	public static final int TRANSCODED_IMAGE = 0x01;

	/**
	 * Constructs a new file chooser for input and output.
	 * 
	 * @param fileType
	 *            file type identifier
	 */
	public IOJFileChooser(int fileType) {
		super();

		FileSystemView fileSystemView = this.getFileSystemView();
		this.setCurrentDirectory(fileSystemView.getDefaultDirectory());

		switch (fileType) {
		case SVG_XML:
			setFileFilter(new IOSVGXMLFileFilter());
			try {
				File svgXMLFile = new File("."
						+ IOSVGXMLFileFilter.SVG_XML_FILE_EXTENSION);
				setSelectedFile(svgXMLFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case TRANSCODED_IMAGE:
			setFileFilter(new IOTranscodedImageFileFilter());
			break;
		}
	}
}