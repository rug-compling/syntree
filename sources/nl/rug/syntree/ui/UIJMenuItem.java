/* UIJMenuItem.java */

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

import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 * This class represents a item for a menu in the user interface menu bar.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class UIJMenuItem extends JMenuItem {
	/**
	 * Serial version identifier constant
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new menu item with a name, a mnenomic and an action
	 * listener.
	 * 
	 * @param itemIdentifier
	 *            identifier of the menu item
	 * @param itemMnemonic
	 *            mnemonic for the menu item
	 * @param itemActionListener
	 *            action listener for the menu item
	 * @param itemEnabled
	 *            item enabled
	 */
	public UIJMenuItem(String itemIdentifier, int itemMnemonic,
			ActionListener itemActionListener, boolean itemEnabled) {
		super(itemIdentifier);
		configureMenuItemCommons(itemMnemonic, itemActionListener, itemEnabled);
	}

	/**
	 * Constructs a new menu item with a name, a mnenomic, an accelerator and an
	 * action listener.
	 * 
	 * @param itemIdentifier
	 *            identifier of the menu item
	 * @param itemMnemonic
	 *            mnemonic for the menu item
	 * @param itemAccelerator
	 *            key shortcut for the menu item
	 * @param itemActionListener
	 *            action listener for the menu item
	 * @param itemEnabled
	 *            item enabled
	 */
	public UIJMenuItem(String itemIdentifier, int itemMnemonic,
			char itemAccelerator, ActionListener itemActionListener,
			boolean itemEnabled) {
		super(itemIdentifier);
		configureMenuItemCommons(itemMnemonic, itemActionListener, itemEnabled);
		this.setAccelerator(KeyStroke.getKeyStroke(itemAccelerator, Toolkit
				.getDefaultToolkit().getMenuShortcutKeyMask(), false));
	}

	/**
	 * Constructs a new menu item with a name, an icon, a mnenomic, and an
	 * action listener.
	 * 
	 * @param itemIdentifier
	 *            identifier of the menu item
	 * @param itemIcon
	 *            icon for the menu item
	 * @param itemMnemonic
	 *            mnemonic for the menu item
	 * @param itemActionListener
	 *            action listener for the menu item
	 * @param itemEnabled
	 *            item enabled
	 */
	public UIJMenuItem(String itemIdentifier, ImageIcon itemIcon,
			int itemMnemonic, ActionListener itemActionListener,
			boolean itemEnabled) {
		super(itemIdentifier, itemIcon);
		configureMenuItemCommons(itemMnemonic, itemActionListener, itemEnabled);
	}

	/**
	 * Constructs a new menu item with a name, an icon, a mnenomic, an
	 * accelerator, and an action listener.
	 * 
	 * @param itemIdentifier
	 *            identifier of the menu item
	 * @param itemIcon
	 *            icon for the menu item
	 * @param itemMnemonic
	 *            mnemonic for the menu item
	 * @param itemAccelerator
	 *            key shortcut for the menu item
	 * @param itemActionListener
	 *            action listener for the menu item
	 * @param itemEnabled
	 *            item enabled
	 */
	public UIJMenuItem(String itemIdentifier, ImageIcon itemIcon,
			int itemMnemonic, char itemAccelerator,
			ActionListener itemActionListener, boolean itemEnabled) {
		super(itemIdentifier, itemIcon);
		configureMenuItemCommons(itemMnemonic, itemActionListener, itemEnabled);
		this.setAccelerator(KeyStroke.getKeyStroke(itemAccelerator, Toolkit
				.getDefaultToolkit().getMenuShortcutKeyMask(), false));
	}

	/**
	 * Configures the mnemonic and action listener for a menu item.
	 * 
	 * @param itemMnemonic
	 *            menmonic for the menu item
	 * @param itemActionListener
	 *            action listener for the menu item
	 * @param itemEnabled
	 *            item enabled
	 */
	protected void configureMenuItemCommons(int itemMnemonic,
			ActionListener itemActionListener, boolean itemEnabled) {
		this.setMnemonic(itemMnemonic);
		this.addActionListener(itemActionListener);
		this.setEnabled(itemEnabled);
	}
}