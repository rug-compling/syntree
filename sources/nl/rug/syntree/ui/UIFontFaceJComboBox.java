/* UIFontFaceJComboBox.java */

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
import java.awt.Component;
import java.awt.Font;
import java.awt.GraphicsEnvironment;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.UIManager;

/**
 * This class represents a font face combo box, listing all of the available
 * system fonts.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class UIFontFaceJComboBox extends JComboBox {
	/**
	 * Serial version identifier constant
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Cell font size constant
	 */
	public final static int CELL_FONT_SIZE = 14;

	/**
	 * Constructs a new font face combo box, listing all of the available system
	 * fonts.
	 */
	public UIFontFaceJComboBox() {
		super(GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getAvailableFontFamilyNames());
		this.setBackground(Color.WHITE);
		this.setRenderer(new DefaultListCellRenderer() {
			/**
			 * Serial version identifier constant
			 */
			private static final long serialVersionUID = 1L;

			/**
			 * Return a component that has been configured to display the
			 * specified value. That component's paint method is then called to
			 * "render" the cell. If it is necessary to compute the dimensions
			 * of a list because the list cells do not have a fixed size, this
			 * method is called to generate a component on which
			 * getPreferredSize can be invoked.
			 * 
			 * @param list
			 *            The JList we're painting
			 * @param value
			 *            The value returned by
			 *            list.getModel().getElementAt(index)
			 * @param index
			 *            The cells index
			 * @param isSelected
			 *            True if the specified cell was selected
			 * @param cellHasFocus
			 *            True if the specified cell has the focus
			 * @return A component whose paint() method will render the
			 *         specified value
			 */
			public Component getListCellRendererComponent(JList list,
					Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				JLabel cellJLabel = (JLabel) super
						.getListCellRendererComponent(list, value, index,
								isSelected, cellHasFocus);
				Font cellFont = new Font((String) value, Font.PLAIN,
						CELL_FONT_SIZE);
				if (cellFont.canDisplayUpTo((String) value) == -1) {
					cellJLabel.setFont(cellFont);
				} else {
					cellJLabel.setFont(new Font(cellJLabel.getFont()
							.getFontName(), Font.PLAIN, CELL_FONT_SIZE));
				}
				return cellJLabel;
			}
		});
		this
				.setSelectedItem(((Font) UIManager.get("TextField.font"))
						.getName());
	}
}