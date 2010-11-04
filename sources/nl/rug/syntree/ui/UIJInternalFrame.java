/* UIJInternalFrame.java */

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
import java.beans.PropertyVetoException;
import java.io.File;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import nl.rug.syntree.Main;
import nl.rug.syntree.editor.TreeEditorBridge;
import nl.rug.syntree.editor.TreeEditorJSVGScrollPane;
import nl.rug.syntree.io.IOJFileChooser;
import nl.rug.syntree.io.IOSVGXMLFileWriter;
import nl.rug.syntree.tree.TreeRepresentation;

import org.apache.batik.bridge.UpdateManager;

/**
 * This class represents an internal frame for the desktop pane. This internal
 * frame represents a treespace.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class UIJInternalFrame extends JInternalFrame {
	/**
	 * Serial version identifier constant
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Reference to the desktop pane of the user interface
	 */
	protected UIJDesktopPane uiJDesktopPane;

	/**
	 * Internal frame number for this treespace
	 */
	protected int uiJInternalFrameNumber;

	/**
	 * File name for this treespace
	 */
	protected String svgXMLFileName = null;

	/**
	 * Graphical tree editor
	 */
	TreeEditorJSVGScrollPane treeEditorJSVGScrollPane;

	/**
	 * Tree editor bridge
	 */
	TreeEditorBridge treeEditorBridge;

	/**
	 * Boolean indicated whether the document is this frame is modified
	 */
	boolean isModified = false;

	/**
	 * Constructs a new internal frame for the desktop pane.
	 * 
	 * @param uiJDesktopPane
	 *            reference to the desktop pane
	 * @param uiJInternalFrameNumber
	 *            internal frame number
	 */
	public UIJInternalFrame(UIJDesktopPane uiJDesktopPane,
			int uiJInternalFrameNumber) {
		super("[new document (" + uiJInternalFrameNumber + ")]", true, true,
				true, true);
		this.uiJInternalFrameNumber = uiJInternalFrameNumber;
		this.setFrameIcon(UIIcons.APPLICATION_ICON);
		this.uiJDesktopPane = uiJDesktopPane;
		treeEditorBridge = new TreeEditorBridge(this);
		setupUIJInternalFrame();
	}

	/**
	 * Constructs a new internal frame for the desktop pane.
	 * 
	 * @param uiJDesktopPane
	 *            reference to the desktop pane
	 * @param uiJInternalFrameNumber
	 *            internal frame number
	 * @param svgXMLFile
	 *            the SVG XML file to open
	 */
	public UIJInternalFrame(UIJDesktopPane uiJDesktopPane,
			int uiJInternalFrameNumber, File svgXMLFile) {
		super(svgXMLFile.getAbsolutePath(), true, true, true, true);
		svgXMLFileName = svgXMLFile.getAbsolutePath();
		this.uiJInternalFrameNumber = uiJInternalFrameNumber;
		this.setFrameIcon(UIIcons.APPLICATION_ICON);
		this.uiJDesktopPane = uiJDesktopPane;
		treeEditorBridge = new TreeEditorBridge(this, svgXMLFile);
		setupUIJInternalFrame();
	}

	/**
	 * Sets up the internal frame.
	 */
	public void setupUIJInternalFrame() {
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		// width
		int w = (int) (UIJDesktopPane.INTERNAL_FRAME_WIDTH_FRACTION * uiJDesktopPane
				.getSize().getWidth());
		// height
		int h = (int) (UIJDesktopPane.INTERNAL_FRAME_HEIGHT_FRACTION * uiJDesktopPane
				.getSize().getHeight());
		// x
		int x = (int) uiJInternalFrameNumber
				* UIJDesktopPane.INTERNAL_FRAME_HORIZONTAL_MARGIN;
		// y
		int y = (int) uiJInternalFrameNumber
				* UIJDesktopPane.INTERNAL_FRAME_VERTICAL_MARGIN;

		this.setBounds(x, y, w, h);
		this.addInternalFrameListener(new InternalFrameAdapter() {
			/**
			 * Frame activated event handler.
			 * 
			 * @param ife
			 *            internal frame event
			 */
			public void internalFrameActivated(InternalFrameEvent ife) {
				UpdateManager um = treeEditorBridge.getUpdateManager();
				if (um != null)
					um.resume();
				uiJDesktopPane.getUIMainJFrame().setTitle(
						Main.APPLICATION_IDENTIFIER + " " + getTitle());
			}

			/**
			 * Frame deactivated event handler.
			 * 
			 * @param ife
			 *            internal frame event
			 */
			public void internalFrameDeactivated(InternalFrameEvent ife) {
				UpdateManager um = treeEditorBridge.getUpdateManager();
				if (um != null)
					um.suspend();
			}

			/**
			 * Frame closing event handler.
			 * 
			 * @param ife
			 *            internal frame event
			 */
			public void internalFrameClosing(InternalFrameEvent ife) {
				closeTreespace();
				uiJDesktopPane.getUIMainJFrame().setTitle(
						Main.APPLICATION_IDENTIFIER);
			}
		});

		treeEditorJSVGScrollPane = new TreeEditorJSVGScrollPane(
				treeEditorBridge);
		this.getContentPane()
				.add(treeEditorJSVGScrollPane, BorderLayout.CENTER);
		this.setVisible(true);
	}

	/**
	 * Closes the current treespace.
	 */
	public void closeTreespace() {
		if (isModified) {
			Object[] options = { "Save", "Don't save", "Cancel" };
			int choice = JOptionPane
					.showOptionDialog(
							null,
							"You have not saved this file. Do you want to save before closing?",
							"Warning", JOptionPane.DEFAULT_OPTION,
							JOptionPane.WARNING_MESSAGE, null, options,
							options[0]);
			// save
			if (choice == 0) {
				saveTreespace();
				uiJDesktopPane.removeUIJInternalFrame(this);
			}
			// don't save
			if (choice == 1) {
				uiJDesktopPane.removeUIJInternalFrame(this);
			}
		} else {
			uiJDesktopPane.removeUIJInternalFrame(this);
		}
	}

	/**
	 * Saves the tree representation in this treespace.
	 */
	public void saveTreespace() {
		if (svgXMLFileName == null) {
			IOJFileChooser ioJFileChooser = new IOJFileChooser(
					IOJFileChooser.SVG_XML);
			if (ioJFileChooser.showSaveDialog(this) == IOJFileChooser.APPROVE_OPTION) {
				TreeRepresentation treeRepresentation = treeEditorBridge
						.getTreeRepresentation();

				// write file
				File svgXMLFile = ioJFileChooser.getSelectedFile();
				IOSVGXMLFileWriter ioSVGXMLFileWriter = new IOSVGXMLFileWriter(
						treeRepresentation, svgXMLFile);
				ioSVGXMLFileWriter.writeFile();
				svgXMLFileName = ioJFileChooser.getSelectedFile().toString();

				// change window and application title
				setTitle("[" + svgXMLFileName + "]");
				uiJDesktopPane.getUIMainJFrame().setTitle(
						Main.APPLICATION_IDENTIFIER + " " + getTitle());

				// set modified
				setModified(false);
			}
		} else {
			File svgXMLFile = new File(svgXMLFileName);
			TreeRepresentation treeRepresentation = treeEditorBridge
					.getTreeRepresentation();
			IOSVGXMLFileWriter ioSVGXMLFileWriter = new IOSVGXMLFileWriter(
					treeRepresentation, svgXMLFile);
			ioSVGXMLFileWriter.writeFile();
			setModified(false);
		}
	}

	/**
	 * Saves the tree representation as the specified file.
	 */
	public void saveTreespaceAs() {
		String oldSVGXMLFileName = svgXMLFileName;
		svgXMLFileName = null;
		saveTreespace();
		if (svgXMLFileName == null)
			svgXMLFileName = oldSVGXMLFileName;
	}

	/**
	 * Sets whether the document in this frame is modified
	 * 
	 * @param isModified
	 *            boolean
	 */
	public void setModified(boolean isModified) {
		this.isModified = isModified;
	}

	/**
	 * Returns whether the document in this frame is modified
	 * 
	 * @return boolean
	 */
	public boolean isModified() {
		return isModified;
	}

	/**
	 * Iconifies or deiconifies this internal frame, if the look and feel
	 * supports iconification.
	 * 
	 * @param isIcon
	 *            boolean
	 */
	public void setIcon(boolean isIcon) {
		try {
			super.setIcon(isIcon);
		} catch (PropertyVetoException pve) {
			System.err.println("[UIJInternalFrame::setIcon()]");
			pve.printStackTrace();
		}
	}

	/**
	 * Maximizes and restores this internal frame.
	 * 
	 * @param isMaximized
	 *            boolean
	 */
	public void setMaximum(boolean isMaximized) {
		try {
			super.setMaximum(isMaximized);
		} catch (PropertyVetoException pve) {
			System.err.println("[UIJInternalFrame::setMaximum()]");
			pve.printStackTrace();
		}
	}

	/**
	 * Selects or deselects the internal frame if it's showing.
	 * 
	 * @param isSelected
	 *            boolean
	 */
	public void setSelected(boolean isSelected) {
		try {
			super.setSelected(isSelected);
		} catch (PropertyVetoException pve) {
			System.err.println("[UIJInternalFrame::setSelected()]");
			pve.printStackTrace();
		}
	}

	/**
	 * Returns the desktop pane.
	 * 
	 * @return desktop pane
	 */
	public UIJDesktopPane getUIJDesktopPane() {
		return uiJDesktopPane;
	}

	/**
	 * Returns the tree editor scroll pane.
	 * 
	 * @return tree editor scroll pane.
	 */
	public TreeEditorJSVGScrollPane getTreeEditorJSVGScrollPane() {
		return treeEditorJSVGScrollPane;
	}
}