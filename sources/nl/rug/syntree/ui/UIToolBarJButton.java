/* UIToolBarJButton.java */

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
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * This class represents a button for a toolbar.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class UIToolBarJButton extends JButton {
	/**
	 * Serial version identifier constant
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Boolean that indicates whether this button is enabled
	 */
	protected boolean isEnablable;

	/**
	 * Boolean that indicates whether this button is enabled
	 */
	protected boolean isEnabled = false;

	/**
	 * Compound border for highlighted buttons
	 */
	protected CompoundBorder highlightedBorder;

	/**
	 * Compound border for standard buttons
	 */
	protected CompoundBorder standardBorder;

	/**
	 * Reference to this button
	 */
	protected JButton jButton;

	/**
	 * The default background color for this button
	 */
	protected Color jButtonBackgroundColor;

	/**
	 * Constructs a new toolbar button with an icon, tooltip text and an action
	 * listener. This button can be configured as an enablable button.
	 * 
	 * @param buttonIcon
	 *            icon for the button
	 * @param buttonToolTipText
	 *            tooltip text for the button
	 * @param buttonActionListener
	 *            action listener for the button
	 * @param buttonIsEnablable
	 *            true when the button is enablable
	 */
	public UIToolBarJButton(ImageIcon buttonIcon, String buttonToolTipText,
			ActionListener buttonActionListener, boolean buttonIsEnablable) {
		super(buttonIcon);
		this.setToolTipText(buttonToolTipText);
		this.addActionListener(buttonActionListener);
		this.isEnablable = buttonIsEnablable;
		this.jButton = this;
		this.jButtonBackgroundColor = this.getBackground();
		highlightedBorder = new CompoundBorder(new LineBorder(Color.BLACK, 1),
				new EmptyBorder(1, 1, 1, 1));
		standardBorder = new CompoundBorder(new EmptyBorder(1, 1, 1, 1),
				new EmptyBorder(1, 1, 1, 1));
		this.setBorder(standardBorder);
		this.setOpaque(false);
		this.addMouseListener(new MouseAdapter() {
			/**
			 * Mouse entered event handler. Changes the button border from
			 * standard to highlighted.
			 * 
			 * @param me
			 *            mouse event
			 */
			public void mouseEntered(MouseEvent me) {
				jButton.setBackground(UIColors.TOOLBAR_BUTTON_MOUSE_OVER);
				jButton.setBorder(highlightedBorder);
				jButton.setOpaque(true);
			}

			/**
			 * Mouse exited event handler. If the button is enablable and
			 * enabled, this handler highlights the border, otherwise it changes
			 * the highlighted border back to the standard border.
			 * 
			 * @param me
			 *            mouse event
			 */
			public void mouseExited(MouseEvent me) {
				if (isEnablable && isEnabled) {
					jButton.setBackground(UIColors.TOOLBAR_BUTTON_ENABLED);
					jButton.setBorder(highlightedBorder);
					jButton.setOpaque(true);
				} else {
					jButton.setBackground(jButtonBackgroundColor);
					jButton.setBorder(standardBorder);
					jButton.setOpaque(false);
				}
				jButton.setRequestFocusEnabled(false);
			}
		});
	}

	/**
	 * Returns true if this button is enabled and false when it is disabled.
	 * 
	 * @return boolean that indicates whether this button is enabled
	 */
	public boolean getIsEnabled() {
		return isEnabled;
	}

	/**
	 * Sets the button to enabled or disabled if it is enablable.
	 * 
	 * @param isEnabled
	 *            boolean that indicates whether this button should be enabled
	 *            or disabled
	 */
	public void setIsEnabled(boolean isEnabled) {
		if (isEnablable) {
			this.isEnabled = isEnabled;
			if (this.isEnabled) {
				this.setBackground(UIColors.TOOLBAR_BUTTON_ENABLED);
				this.setBorder(highlightedBorder);
				this.setOpaque(true);
			} else {
				this.setBackground(jButtonBackgroundColor);
				this.setBorder(standardBorder);
				this.setOpaque(false);
			}
		}
	}
}