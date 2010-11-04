/* UIColorChooserJButton.java */

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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

/**
 * This class represents a color chooser button for multiple purposes.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class UIColorChooserJButton extends JButton {
	/**
	 * Serial version identifier constant
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Currently selected color
	 */
	protected Color selectedColor;

	/**
	 * A swing default color chooser implementation
	 */
	protected JColorChooser jColorChooser;

	/**
	 * Compound border for a highlighted color chooser
	 */
	protected CompoundBorder highlightedBorder;

	/**
	 * Compound border for a standard color chooser
	 */
	protected CompoundBorder standardBorder;

	/**
	 * Button for the color chooser
	 */
	protected JButton jColorChooserButton;

	/**
	 * Constructs a new color chooser button for multiple purposes.
	 * 
	 * @param jColorChooserTitle
	 *            title for the color chooser
	 */
	public UIColorChooserJButton(final String jColorChooserTitle) {
		super(UIIcons.FORMAT_COLOR);
		selectedColor = Color.BLACK;
		jColorChooser = new JColorChooser(selectedColor);
		jColorChooserButton = this;

		highlightedBorder = new CompoundBorder(new LineBorder(Color.BLACK, 1),
				new EmptyBorder(1, 1, 1, 1));
		standardBorder = new CompoundBorder(new EmptyBorder(2, 2, 0, 2),
				new MatteBorder(0, 0, 2, 0, selectedColor));
		this.setOpaque(false);
		this.setBorder(standardBorder);

		this.addMouseListener(new MouseAdapter() {
			/**
			 * Mouse entered event handler. Sets the color chooser border to a
			 * highlighted border.
			 * 
			 * @param me
			 *            mouse event
			 */
			public void mouseEntered(MouseEvent me) {
				jColorChooserButton
						.setBackground(UIColors.TOOLBAR_BUTTON_MOUSE_OVER);
				jColorChooserButton.setOpaque(true);
				jColorChooserButton.setBorder(highlightedBorder);
			}

			/**
			 * Mouse exited event handler. Sets the color chooser border to a
			 * standard border with the current selected color.
			 * 
			 * @param me
			 *            mouse event
			 */
			public void mouseExited(MouseEvent me) {
				standardBorder = new CompoundBorder(
						new EmptyBorder(2, 2, 0, 2), new MatteBorder(0, 0, 2,
								0, selectedColor));
				jColorChooserButton.setOpaque(false);
				jColorChooserButton.setBorder(standardBorder);
			}
		});

		this.addActionListener(new ActionListener() {
			/**
			 * Button event handler. Constructs and shows a color chooser
			 * dialog.
			 * 
			 * @param ae
			 *            action event
			 */
			public void actionPerformed(ActionEvent ae) {
				JDialog jColorChooserDialog = JColorChooser.createDialog(
						jColorChooserButton, jColorChooserTitle, true,
						jColorChooser, new ActionListener() {
							/**
							 * OK action listener. Sets the user selection to
							 * the current selected color.
							 * 
							 * @param ae
							 *            action event
							 */
							public void actionPerformed(ActionEvent ae) {
								selectedColor = jColorChooser.getColor();
							}
						}, null);
				jColorChooserDialog.setLocationRelativeTo(null);
				jColorChooserDialog.setVisible(true);
			}
		});
	}

	/**
	 * Return the selected color.
	 * 
	 * @return selected color
	 */
	public Color getColor() {
		return selectedColor;
	}

	/**
	 * Return the selected color in hexadecimal rgb color notation.
	 * 
	 * @return selected color in hexadecimal rgb color notation
	 */
	public String getHexadecimalColor() {
		return "#"
				+ (Integer.toHexString(selectedColor.getRGB())).substring(2, 8);
	}

	/**
	 * Set the selected color to the specified color. The specified color is in
	 * hexadecimal rgb notation.
	 * 
	 * @param hexadecimalColor
	 *            color in hexadecimal rgb notation
	 */
	public void setHexadecimalColor(String hexadecimalColor) {
		int r = Integer.parseInt(hexadecimalColor.substring(1, 3), 16);
		int g = Integer.parseInt(hexadecimalColor.substring(3, 5), 16);
		int b = Integer.parseInt(hexadecimalColor.substring(5, 7), 16);
		selectedColor = new Color(r, g, b);
		jColorChooser.setColor(selectedColor);

		standardBorder = new CompoundBorder(new EmptyBorder(2, 2, 0, 2),
				new MatteBorder(0, 0, 2, 0, selectedColor));
		jColorChooserButton.setOpaque(false);
		jColorChooserButton.setBorder(standardBorder);
	}
}