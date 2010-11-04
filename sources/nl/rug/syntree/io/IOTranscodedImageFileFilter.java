/* IOTranscodedImageFileFilter.java */

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

import javax.swing.filechooser.FileFilter;

/**
 * This class represents a Scalable Vector Graphics XML file filter.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @since 1.0
 * @version 1.0
 */
public class IOTranscodedImageFileFilter extends FileFilter {
	/**
	 * Transcoded image file descriptor
	 */
	public static final String TRANSCODED_IMAGE_FILE_DESCRIPTOR = "Transcoded Image File (*.png *.jpg)";

	/**
	 * Transcoded image png file extension
	 */
	public static final String TRANSCODED_IMAGE_PNG_FILE_EXTENSION = "png";

	/**
	 * Transcoded image jpg file extension
	 */
	public static final String TRANSCODED_IMAGE_JPG_FILE_EXTENSION = "jpg";

	/**
	 * @param file
	 *            the file to filter
	 * @return boolean indicating whether the file is accepted
	 */
	public boolean accept(File file) {
		boolean fileAccepted = false;
		if (file.isDirectory()) {
			fileAccepted = true;
		}
		if (file.getName() != null) {
			if (file.getName().lastIndexOf(".") != -1) {
				if (file.getName().substring(
						file.getName().lastIndexOf(".") + 1,
						file.getName().length()).compareTo(
						TRANSCODED_IMAGE_PNG_FILE_EXTENSION) == 0) {
					fileAccepted = true;
				}
				if (file.getName().substring(
						file.getName().lastIndexOf(".") + 1,
						file.getName().length()).compareTo(
						TRANSCODED_IMAGE_JPG_FILE_EXTENSION) == 0) {
					fileAccepted = true;
				}
			}
		}
		return fileAccepted;
	}

	/**
	 * Returns the decription of this filter.
	 * 
	 * @return description
	 */
	public String getDescription() {
		return TRANSCODED_IMAGE_FILE_DESCRIPTOR;
	}
}