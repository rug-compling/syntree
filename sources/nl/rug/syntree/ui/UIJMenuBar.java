/* UIJMenuBar.java */

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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

/**
 * This class represents a menu bar for the user interface.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class UIJMenuBar extends JMenuBar {
	/**
	 * Serial version identifier constant
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new menu bar for the user interface.
	 * 
	 * @param uiMainJFrame
	 *            the main user interface
	 */
	public UIJMenuBar(final UIMainJFrame uiMainJFrame) {
		super();

		// file menu /////////////////////////////////////////////////////////

		final JMenu fileJMenu = new JMenu("File");
		fileJMenu.setMnemonic(KeyEvent.VK_F);
		fileJMenu.addMenuListener(new MenuListener() {
			@Override
			public void menuSelected(MenuEvent e) {
				
				// new treespace /////////////////////////////////////////////
				
				fileJMenu.add(new UIJMenuItem("New treespace",
						UIIcons.FILE_NEW_TREESPACE, KeyEvent.VK_N, 'N',
						new ActionListener() {
							public void actionPerformed(ActionEvent ae) {
								uiMainJFrame.getUIJDesktopPane()
										.addUIJInternalFrame();
							}
						}, true));

				// separator /////////////////////////////////////////////////
				
				fileJMenu.insertSeparator(fileJMenu.getItemCount());

				// save treespace ////////////////////////////////////////////
				
				boolean saveEnabled = false;
				if (uiMainJFrame.getUIJDesktopPane()
						.getSelectedUIJInternalFrame() != null)
					saveEnabled = uiMainJFrame.getUIJDesktopPane()
							.getSelectedUIJInternalFrame().isModified();
				fileJMenu.add(new UIJMenuItem("Save treespace",
						UIIcons.FILE_SAVE_TREESPACE, KeyEvent.VK_S, 'S',
						new ActionListener() {
							public void actionPerformed(ActionEvent ae) {
								uiMainJFrame.getUIJDesktopPane()
										.getSelectedUIJInternalFrame()
										.saveTreespace();
							}
						}, saveEnabled));

				// save treespace as /////////////////////////////////////////
				
				fileJMenu.add(new UIJMenuItem("Save treespace as",
						UIIcons.FILE_SAVEAS_TREESPACE, KeyEvent.VK_A, 'A',
						new ActionListener() {
							public void actionPerformed(ActionEvent ae) {
								uiMainJFrame.getUIJDesktopPane()
										.getSelectedUIJInternalFrame()
										.saveTreespaceAs();
							}
						}, true));

				// open treespace ////////////////////////////////////////////
				
				fileJMenu.add(new UIJMenuItem("Open treespace",
						UIIcons.FILE_OPEN_TREESPACE, KeyEvent.VK_O,
						new ActionListener() {
							public void actionPerformed(ActionEvent ae) {
								uiMainJFrame.getUIJDesktopPane()
										.openTreespace();
							}
						}, true));

				// close treespace ///////////////////////////////////////////
				
				fileJMenu.add(new UIJMenuItem("Close treespace",
						UIIcons.FILE_CLOSE_TREESPACE, KeyEvent.VK_C,
						new ActionListener() {
							public void actionPerformed(ActionEvent ae) {
								uiMainJFrame.getUIJDesktopPane()
										.getSelectedUIJInternalFrame()
										.closeTreespace();
							}
						}, (uiMainJFrame.getUIJDesktopPane()
								.getUIJInternalFrames().length > 0)));

				// separator /////////////////////////////////////////////////
				
				fileJMenu.insertSeparator(fileJMenu.getItemCount());

				// page setup ////////////////////////////////////////////////
				
				fileJMenu.add(new UIJMenuItem("Page setup",
						UIIcons.FILE_PAGE_SETUP, KeyEvent.VK_E,
						new ActionListener() {
							public void actionPerformed(ActionEvent ae) {
							}
						}, true));

				// print treespace ///////////////////////////////////////////
				
				fileJMenu.add(new UIJMenuItem("Print",
						UIIcons.FILE_PRINT_TREESPACE, KeyEvent.VK_P,
						new ActionListener() {
							public void actionPerformed(ActionEvent ae) {
							}
						}, true));

				// separator /////////////////////////////////////////////////
				
				fileJMenu.insertSeparator(fileJMenu.getItemCount());

				// export treespace //////////////////////////////////////////
				
				fileJMenu.add(new UIJMenuItem("Export treespace",
						UIIcons.FILE_EXPORT_TREESPACE, KeyEvent.VK_E,
						new ActionListener() {
							public void actionPerformed(ActionEvent ae) {
							}
						}, true));

				// separator /////////////////////////////////////////////////
				
				fileJMenu.insertSeparator(fileJMenu.getItemCount());

				// exit program //////////////////////////////////////////////
				
				fileJMenu.add(new UIJMenuItem("Exit program",
						UIIcons.FILE_EXIT_PROGRAM, KeyEvent.VK_X,
						new ActionListener() {
							public void actionPerformed(ActionEvent ae) {
								uiMainJFrame.cleanExit();
							}
						}, true));

			}

			@Override
			public void menuDeselected(MenuEvent e) {
				fileJMenu.removeAll();

			}

			@Override
			public void menuCanceled(MenuEvent e) {
			}
		});
		this.add(fileJMenu);

		// edit menu /////////////////////////////////////////////////////////

		final JMenu editJMenu = new JMenu("Edit");
		editJMenu.setMnemonic(KeyEvent.VK_E);
		editJMenu.addMenuListener(new MenuListener() {
			@Override
			public void menuSelected(MenuEvent e) {
				
				// undo //////////////////////////////////////////////////////
				
				if (uiMainJFrame.getUIJDesktopPane()
						.getSelectedUIJInternalFrame() != null) {
					editJMenu.add(new UIJMenuItem(uiMainJFrame
							.getUIJDesktopPane().getSelectedUIJInternalFrame()
							.getTreeEditorJSVGScrollPane()
							.getTreeEditorBridge().getUndoRedoManager()
							.getUndoPresentationName(), UIIcons.EDIT_UNDO,
							KeyEvent.VK_U, new ActionListener() {
								public void actionPerformed(ActionEvent ae) {
									uiMainJFrame.getUIJDesktopPane()
											.getSelectedUIJInternalFrame()
											.getTreeEditorJSVGScrollPane()
											.getTreeEditorBridge()
											.getUndoRedoManager().undo();
								}
							}, uiMainJFrame.getUIJDesktopPane()
									.getSelectedUIJInternalFrame()
									.getTreeEditorJSVGScrollPane()
									.getTreeEditorBridge().getUndoRedoManager()
									.canUndo()));

					// redo //////////////////////////////////////////////////
					
					editJMenu.add(new UIJMenuItem(uiMainJFrame
							.getUIJDesktopPane().getSelectedUIJInternalFrame()
							.getTreeEditorJSVGScrollPane()
							.getTreeEditorBridge().getUndoRedoManager()
							.getRedoPresentationName(), UIIcons.EDIT_REDO,
							KeyEvent.VK_R, new ActionListener() {
								public void actionPerformed(ActionEvent ae) {
									uiMainJFrame.getUIJDesktopPane()
											.getSelectedUIJInternalFrame()
											.getTreeEditorJSVGScrollPane()
											.getTreeEditorBridge()
											.getUndoRedoManager().redo();
								}
							}, uiMainJFrame.getUIJDesktopPane()
									.getSelectedUIJInternalFrame()
									.getTreeEditorJSVGScrollPane()
									.getTreeEditorBridge().getUndoRedoManager()
									.canRedo()));

					// separator /////////////////////////////////////////////
					
					editJMenu.insertSeparator(editJMenu.getItemCount());

					// cut ///////////////////////////////////////////////////
					
					editJMenu.add(new UIJMenuItem("Cut", UIIcons.EDIT_CUT,
							KeyEvent.VK_T, new ActionListener() {
								public void actionPerformed(ActionEvent ae) {
								}
							}, true));

					// copy //////////////////////////////////////////////////
					
					editJMenu.add(new UIJMenuItem("Copy", UIIcons.EDIT_COPY,
							KeyEvent.VK_C, new ActionListener() {
								public void actionPerformed(ActionEvent ae) {
								}
							}, true));

					// paste /////////////////////////////////////////////////
					
					editJMenu.add(new UIJMenuItem("Paste", UIIcons.EDIT_PASTE,
							KeyEvent.VK_P, new ActionListener() {
								public void actionPerformed(ActionEvent ae) {
								}
							}, true));

					// delete ////////////////////////////////////////////////
					
					editJMenu.add(new UIJMenuItem("Delete",
							UIIcons.EDIT_DELETE, KeyEvent.VK_D,
							new ActionListener() {
								public void actionPerformed(ActionEvent ae) {
								}
							}, true));
				}
			}

			@Override
			public void menuDeselected(MenuEvent e) {
				editJMenu.removeAll();
			}

			@Override
			public void menuCanceled(MenuEvent e) {

			}
		});
		this.add(editJMenu);

		// format menu ///////////////////////////////////////////////////////

		final JMenu formatJMenu = new JMenu("Format");
		formatJMenu.setMnemonic(KeyEvent.VK_O);
		formatJMenu.addMenuListener(new MenuListener() {
			@Override
			public void menuSelected(MenuEvent e) {

			}

			@Override
			public void menuDeselected(MenuEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void menuCanceled(MenuEvent e) {
				// TODO Auto-generated method stub

			}
		});
		this.add(formatJMenu);

		// view menu /////////////////////////////////////////////////////////

		final JMenu viewJMenu = new JMenu("View");
		viewJMenu.setMnemonic(KeyEvent.VK_V);
		viewJMenu.addMenuListener(new MenuListener() {
			@Override
			public void menuSelected(MenuEvent e) {

			}

			@Override
			public void menuDeselected(MenuEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void menuCanceled(MenuEvent e) {
				// TODO Auto-generated method stub

			}
		});
		this.add(viewJMenu);

		// window menu ///////////////////////////////////////////////////////

		final JMenu windowJMenu = new JMenu("Window");
		windowJMenu.setMnemonic(KeyEvent.VK_W);
		windowJMenu.addMenuListener(new MenuListener() {
			@Override
			public void menuSelected(MenuEvent e) {

				// cascade ///////////////////////////////////////////////////
				
				windowJMenu.add(new UIJMenuItem("Cascade",
						UIIcons.WINDOWS_CASCADE, KeyEvent.VK_C,
						new ActionListener() {
							public void actionPerformed(ActionEvent ae) {
								uiMainJFrame.getUIJDesktopPane().cascade();
							}
						}, true));

				// separator /////////////////////////////////////////////////
				
				windowJMenu.insertSeparator(windowJMenu.getItemCount());

				// tile horizontal ///////////////////////////////////////////
				
				windowJMenu.add(new UIJMenuItem("Tile horizontal",
						UIIcons.WINDOWS_TILE_HORIZONTAL, KeyEvent.VK_H,
						new ActionListener() {
							public void actionPerformed(ActionEvent ae) {
								uiMainJFrame.getUIJDesktopPane()
										.tileHorizontal();
							}
						}, true));

				// tile vertical /////////////////////////////////////////////
				
				windowJMenu.add(new UIJMenuItem("Tile vertical",
						UIIcons.WINDOWS_TILE_VERTICAL, KeyEvent.VK_V,
						new ActionListener() {
							public void actionPerformed(ActionEvent ae) {
								uiMainJFrame.getUIJDesktopPane().tileVertical();
							}
						}, true));

				// separator /////////////////////////////////////////////////
				
				windowJMenu.insertSeparator(windowJMenu.getItemCount());

				// view icons ////////////////////////////////////////////////
				
				windowJMenu.add(new UIJMenuItem("View icons",
						UIIcons.WINDOWS_VIEW_ICONS, KeyEvent.VK_I,
						new ActionListener() {
							public void actionPerformed(ActionEvent ae) {
								uiMainJFrame.getUIJDesktopPane().viewIcons();
							}
						}, true));
			}

			@Override
			public void menuDeselected(MenuEvent e) {
				windowJMenu.removeAll();
			}

			@Override
			public void menuCanceled(MenuEvent e) {
			}
		});
		this.add(windowJMenu);

		// help menu /////////////////////////////////////////////////////////

		final JMenu helpJMenu = new JMenu("Help");
		helpJMenu.setMnemonic(KeyEvent.VK_H);
		helpJMenu.addMenuListener(new MenuListener() {
			@Override
			public void menuSelected(MenuEvent e) {

				// help //////////////////////////////////////////////////////
				
				helpJMenu.add(new UIJMenuItem("Help", UIIcons.HELP_HELP,
						KeyEvent.VK_H, new ActionListener() {
							public void actionPerformed(ActionEvent ae) {
							}
						}, true));

				// separator /////////////////////////////////////////////////
				
				helpJMenu.insertSeparator(helpJMenu.getItemCount());

				// about /////////////////////////////////////////////////////
				
				helpJMenu.add(new UIJMenuItem("About", UIIcons.HELP_ABOUT,
						KeyEvent.VK_A, new ActionListener() {
							public void actionPerformed(ActionEvent ae) {
							}
						}, true));
			}

			@Override
			public void menuDeselected(MenuEvent e) {
				helpJMenu.removeAll();
			}

			@Override
			public void menuCanceled(MenuEvent e) {
			}
		});
		this.add(helpJMenu);
	}
}