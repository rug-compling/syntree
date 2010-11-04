/* UIStandardJToolBar.java */

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

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JToolBar;

/**
 * This class represents a standard tool bar for the user interface.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class UIStandardJToolBar extends JToolBar {
	/**
	 * Serial version identifier constant
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a standard tool bar for the user interface.
	 * 
	 * @param uiMainJFrame
	 *            reference to the user interface main frame
	 */
	public UIStandardJToolBar(final UIMainJFrame uiMainJFrame) {
		super();
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));

		// new treespace
		this.add(new UIToolBarJButton(UIIcons.FILE_NEW_TREESPACE,
				"New treespace", new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						uiMainJFrame.getUIJDesktopPane().addUIJInternalFrame();
					}
				}, false));

		// open treespace
		this.add(new UIToolBarJButton(UIIcons.FILE_OPEN_TREESPACE,
				"Open treespace", new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						uiMainJFrame.getUIJDesktopPane().openTreespace();
					}
				}, false));

		// save treespace
		this.add(new UIToolBarJButton(UIIcons.FILE_SAVE_TREESPACE,
				"Save treespace", new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						uiMainJFrame.getUIJDesktopPane()
								.getSelectedUIJInternalFrame().saveTreespace();
					}
				}, false));

		// save treespace as
		this.add(new UIToolBarJButton(UIIcons.FILE_SAVEAS_TREESPACE,
				"Save treespace", new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						uiMainJFrame.getUIJDesktopPane()
								.getSelectedUIJInternalFrame()
								.saveTreespaceAs();
					}
				}, false));

		// close treespace
		this.add(new UIToolBarJButton(UIIcons.FILE_CLOSE_TREESPACE,
				"Close treespace", new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						uiMainJFrame.getUIJDesktopPane()
								.getSelectedUIJInternalFrame().closeTreespace();
					}
				}, false));

		// print treespace
		this.add(new UIToolBarJButton(UIIcons.FILE_PRINT_TREESPACE,
				"Print treespace", new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
					}
				}, false));

		// export treespace
		this.add(new UIToolBarJButton(UIIcons.FILE_EXPORT_TREESPACE,
				"Export treespace", new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
					}
				}, false));

		// help
		this.add(new UIToolBarJButton(UIIcons.HELP_HELP, "Help",
				new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
					}
				}, false));
	}
}