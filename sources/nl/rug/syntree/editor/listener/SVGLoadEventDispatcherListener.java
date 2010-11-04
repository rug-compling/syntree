/* SVGLoadEventDispatcherListener.java */

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

package nl.rug.syntree.editor.listener;

import nl.rug.syntree.editor.TreeEditorJSVGScrollPane;

import org.apache.batik.bridge.UpdateManager;
import org.apache.batik.swing.svg.SVGLoadEventDispatcherAdapter;
import org.apache.batik.swing.svg.SVGLoadEventDispatcherEvent;
import org.apache.batik.util.RunnableQueue;

/**
 * This class represents a scalable vector graphics load event dispatcher
 * listener.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class SVGLoadEventDispatcherListener extends
		SVGLoadEventDispatcherAdapter {
	/**
	 * Tree editor scroll pane
	 */
	TreeEditorJSVGScrollPane treeEditorJSVGScrollPane;

	/**
	 * Constructs a new svg load event dispatcher listener.
	 * 
	 * @param treeEditorJSVGScrollPane
	 *            tree editor scroll pane
	 */
	public SVGLoadEventDispatcherListener(
			TreeEditorJSVGScrollPane treeEditorJSVGScrollPane) {
		this.treeEditorJSVGScrollPane = treeEditorJSVGScrollPane;
	}

	/**
	 * Processes a svg load event dispatch completed event.
	 * 
	 * @param slede
	 *            svg load event dispatch event
	 */
	public void svgLoadEventDispatchCompleted(SVGLoadEventDispatcherEvent slede) {
		UpdateManager um = treeEditorJSVGScrollPane.getTreeEditorJSVGCanvas()
				.getUpdateManager();
		RunnableQueue rq = um.getUpdateRunnableQueue();
		if (um != null && rq != null) {
			rq.invokeLater(new Runnable() {
				public void run() {
					if (treeEditorJSVGScrollPane.getTreeEditorBridge()
							.getTreeRepresentation().getTreeVector().size() == 0) {
						treeEditorJSVGScrollPane.getTreeEditorBridge()
								.createTree();
					} else {						
						treeEditorJSVGScrollPane.getTreeDrawer().draw();						
						treeEditorJSVGScrollPane.getTreeEditorBridge()
								.getTreeRepresentation().getSVGDocument()
								.computeViewBox();
					}
				}
			});
		}
	}
}