/* UIMainJFrame.java */

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

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import nl.rug.syntree.Main;

/**
 * This class represents the main user interface frame. This user interface
 * frame will contain a menu bar, a standard tool bar, a formatting tool bar and
 * a desktop pane.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class UIMainJFrame extends JFrame {
	/**
	 * Serial version identifier constant
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Width constant for the user interface frame
	 */
	protected static final int FRAME_WIDTH = 1024;

	/**
	 * Height constant for the user interface frame
	 */
	protected static final int FRAME_HEIGHT = 768;

	/**
	 * Desktop pane for this user interface
	 */
	protected UIJDesktopPane uiJDesktopPane;

	/**
	 * Format toolbar for this user interface
	 */
	protected UIFormatJToolBar uiFormatJToolBar;

	/**
	 * Constructs the main user interface frame and adds a menu bar, a standard
	 * tool bar, a formatting tool bar and desktop pane.
	 */
	public UIMainJFrame() {
		super(Main.APPLICATION_IDENTIFIER);
		this.setIconImage(UIIcons.APPLICATION_ICON.getImage());
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);

		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	
		uiFormatJToolBar = new UIFormatJToolBar(this);
		JPanel uiToolBarPanel = new JPanel(new BorderLayout(), true);
		uiToolBarPanel.add(new UIStandardJToolBar(this), BorderLayout.NORTH);
		uiToolBarPanel.add(uiFormatJToolBar, BorderLayout.SOUTH);
		this.add(uiToolBarPanel, BorderLayout.NORTH);

		uiJDesktopPane = new UIJDesktopPane(this);
		this.getContentPane().add(uiJDesktopPane, BorderLayout.CENTER);

		this.addWindowListener(new WindowAdapter() {
			/**
			 * Event handler for the closing of the user interface.
			 * 
			 * @param we
			 *            window event
			 */
			public void windowClosing(WindowEvent we) {
				cleanExit();
			}
		});

		this.setVisible(true);

		uiJDesktopPane.addUIJInternalFrame();
		uiJDesktopPane.getSelectedUIJInternalFrame().setMaximum(true);
		
		this.setJMenuBar(new UIJMenuBar(this));
	}

	/**
	 * Clean program termination
	 */
	public void cleanExit() {
		UIJInternalFrame uiJInternalFrames[] = uiJDesktopPane
				.getUIJInternalFrames();
		boolean modifiedFiles = false;
		for (int i = 0; i < uiJInternalFrames.length; i++) {
			if (uiJInternalFrames[i].isModified()) {
				modifiedFiles = true;
				i = uiJInternalFrames.length;
			}
		}
		if (modifiedFiles) {
			Object[] options = { "Save and quit", "Quit without saving",
					"Cancel" };
			int choice = JOptionPane
					.showOptionDialog(
							null,
							"You have unsaved modified file(s). Do you want to save them now and quit?",
							"Warning", JOptionPane.DEFAULT_OPTION,
							JOptionPane.WARNING_MESSAGE, null, options,
							options[0]);
			// save and quit
			if (choice == 0) {
				for (int i = 0; i < uiJInternalFrames.length; i++) {
					uiJInternalFrames[i].setSelected(true);
					uiJInternalFrames[i].saveTreespace();
				}
				Main.exit(Main.EXIT_SUCCESS);
			}
			// quit without saving
			if (choice == 1) {
				Main.exit(Main.EXIT_SUCCESS);
			}
		} else {
			Main.exit(Main.EXIT_SUCCESS);
		}
	}

	/**
	 * Return a reference to the desktop pane of the user interface.
	 * 
	 * @return desktop pane
	 */
	public UIJDesktopPane getUIJDesktopPane() {
		return uiJDesktopPane;
	}

	/**
	 * Return a reference to the formatting tool bar of the user interface.
	 * 
	 * @return formatting tool bar
	 */
	public UIFormatJToolBar getUIFormatJToolBar() {
		return uiFormatJToolBar;
	}
}