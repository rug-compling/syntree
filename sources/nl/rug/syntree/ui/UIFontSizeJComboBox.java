/* UIFontSizeJComboBox.java */

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

package nl.rug.syntree.ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.UIManager;

/**
 * This class represents a font size combo box, listing a number of possible
 * font sizes.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class UIFontSizeJComboBox extends JComboBox {
	/**
	 * Serial version identifier constant
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Array of possible font sizes
	 */
	protected static final String sizes[] = { "8", "9", "10", "11", "12", "14",
			"16", "18", "20", "22", "24", "26", "36", "48", "72" };

	/**
	 * Constructs a new font size combo box, listing a number of possible font
	 * sizes.
	 */
	public UIFontSizeJComboBox() {
		super(sizes);
		this.setBackground(Color.WHITE);
		this.setSelectedItem(Integer.toString(((Font) UIManager
				.get("TextField.font")).getSize()));
	}
}