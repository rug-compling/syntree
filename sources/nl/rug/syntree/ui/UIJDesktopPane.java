/* UIJDesktopPane.java */

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

import java.io.File;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

import nl.rug.syntree.io.IOJFileChooser;

/**
 * This class represents a desktop pane for the user interface.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class UIJDesktopPane extends JDesktopPane {
	/**
	 * Serial version identifier constant
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Internal frame width fraction of the main frame
	 */
	protected static final double INTERNAL_FRAME_WIDTH_FRACTION = 0.75;

	/**
	 * Internal frame height fraction of the main frame
	 */
	protected static final double INTERNAL_FRAME_HEIGHT_FRACTION = 0.90;

	/**
	 * Internal frame horizontal margin constant
	 */
	protected static final int INTERNAL_FRAME_HORIZONTAL_MARGIN = 30;

	/**
	 * Internal frame vertical margin constant
	 */
	protected static final int INTERNAL_FRAME_VERTICAL_MARGIN = 30;

	/**
	 * Reference to the user interface
	 */
	protected UIMainJFrame uiMainJFrame;

	/**
	 * Number of internal frames created so far
	 */
	protected int uiJInternalFrameNumber = 0;

	/**
	 * Constructs a new desktop pane for the user interface.
	 * 
	 * @param uiMainJFrame
	 *            reference to the user interface
	 */
	public UIJDesktopPane(UIMainJFrame uiMainJFrame) {
		super();
		this.uiMainJFrame = uiMainJFrame;
		this.setBackground(UIColors.UI_DESKTOP_BACKGROUND);
	}

	/**
	 * Opens a treespace from a svg xml file.
	 */
	public void openTreespace() {
		IOJFileChooser ioJFileChooser = new IOJFileChooser(
				IOJFileChooser.SVG_XML);
		if (ioJFileChooser.showOpenDialog(this) == IOJFileChooser.APPROVE_OPTION) {
			addUIJInternalFrame(ioJFileChooser.getSelectedFile());
		}
	}

	/**
	 * Adds an internal frame to the desktop pane.
	 */
	public void addUIJInternalFrame() {
		UIJInternalFrame uiJInternalFrame = new UIJInternalFrame(this,
				uiJInternalFrameNumber++);
		this.add(uiJInternalFrame, JDesktopPane.DEFAULT_LAYER);
		uiJInternalFrame.setSelected(true);
		uiJInternalFrame.setVisible(true);
	}

	/**
	 * Opens the specified file in a new internal frame, and adds it to the
	 * desktop pane.
	 * 
	 * @param svgXMLFile
	 *            the svg xml file to open
	 */
	public void addUIJInternalFrame(File svgXMLFile) {
		UIJInternalFrame uiJInternalFrame = new UIJInternalFrame(this,
				uiJInternalFrameNumber++, svgXMLFile);
		this.add(uiJInternalFrame, JDesktopPane.DEFAULT_LAYER);
		uiJInternalFrame.setSelected(true);
		uiJInternalFrame.setVisible(true);
	}

	/**
	 * Removes the specified internal frame from the desktop pane.
	 * 
	 * @param uiJInternalFrame
	 *            internal frame
	 */
	public void removeUIJInternalFrame(UIJInternalFrame uiJInternalFrame) {
		uiJInternalFrame.dispose();
	}

	/**
	 * Returns the currently selected internal frame.
	 * 
	 * @return selected internal frame
	 */
	public UIJInternalFrame getSelectedUIJInternalFrame() {
		return (UIJInternalFrame) this.getSelectedFrame();
	}

	/**
	 * Returns all the internal frames on the desktop pane.
	 * 
	 * @return all internal frames on the desktop pane
	 */
	public UIJInternalFrame[] getUIJInternalFrames() {
		JInternalFrame jInternalFrames[] = getAllFrames();
		UIJInternalFrame uiJInternalFrames[] = new UIJInternalFrame[jInternalFrames.length];
		for (int i = 0; i < jInternalFrames.length; i++)
			uiJInternalFrames[i] = (UIJInternalFrame) jInternalFrames[i];
		return uiJInternalFrames;
	}

	/**
	 * Cascades internal frames.
	 */
	public void cascade() {
		JInternalFrame jInternalFrames[] = this.getAllFrames();
		for (int i = 0; i < jInternalFrames.length; i++) {
			UIJInternalFrame uiJInternalFrame = (UIJInternalFrame) jInternalFrames[i];
			uiJInternalFrame.setIcon(false);

			// width
			int w = (int) (this.getSize().getWidth() - ((jInternalFrames.length - 1) * INTERNAL_FRAME_HORIZONTAL_MARGIN));

			// height
			int h = (int) (this.getSize().getHeight() - ((jInternalFrames.length - 1) * INTERNAL_FRAME_VERTICAL_MARGIN));

			// x
			int x = i * INTERNAL_FRAME_HORIZONTAL_MARGIN;

			// y
			int y = i * INTERNAL_FRAME_VERTICAL_MARGIN;

			uiJInternalFrame.setBounds(x, y, w, h);
			uiJInternalFrame.setSelected(true);
		}
	}

	/**
	 * Tiles internal frames horizontally.
	 */
	public void tileHorizontal() {
		JInternalFrame jInternalFrames[] = this.getAllFrames();
		for (int i = 0; i < jInternalFrames.length; i++) {
			UIJInternalFrame uiJInternalFrame = (UIJInternalFrame) jInternalFrames[i];
			uiJInternalFrame.setIcon(false);

			// width
			int w = (int) (this.getSize().getWidth() / jInternalFrames.length);

			// height
			int h = (int) (this.getSize().getHeight());

			// x
			int x = (int) (i * (this.getSize().getWidth() / jInternalFrames.length));

			uiJInternalFrame.setBounds(x, 0, w, h);
		}

	}

	/**
	 * Tiles internal frames vertically.
	 */
	public void tileVertical() {
		JInternalFrame jInternalFrames[] = this.getAllFrames();
		for (int i = 0; i < jInternalFrames.length; i++) {
			UIJInternalFrame uiJInternalFrame = (UIJInternalFrame) jInternalFrames[i];
			uiJInternalFrame.setIcon(false);

			// width
			int w = (int) (this.getSize().getWidth());

			// height
			int h = (int) (this.getSize().getHeight() / jInternalFrames.length);

			// y
			int y = (int) (i * (this.getSize().getHeight() / jInternalFrames.length));

			uiJInternalFrame.setBounds(0, y, w, h);
		}
	}

	/**
	 * Iconifies internal frames.
	 */
	public void viewIcons() {
		JInternalFrame jInternalFrames[] = this.getAllFrames();
		for (int i = 0; i < jInternalFrames.length; i++) {
			UIJInternalFrame uiJInternalFrame = (UIJInternalFrame) jInternalFrames[i];
			uiJInternalFrame.setIcon(true);
		}
	}

	/**
	 * Returns the main frame.
	 * 
	 * @return main frame
	 */
	public UIMainJFrame getUIMainJFrame() {
		return uiMainJFrame;
	}
}