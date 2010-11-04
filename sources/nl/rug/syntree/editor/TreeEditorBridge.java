/* TreeEditorBridge.java */

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

package nl.rug.syntree.editor;

import java.io.File;

import javax.swing.event.UndoableEditEvent;

import nl.rug.syntree.editor.listener.dom.DOMBranchMouseClickEventListener;
import nl.rug.syntree.editor.listener.dom.DOMBranchMouseOutEventListener;
import nl.rug.syntree.editor.listener.dom.DOMBranchMouseOverEventListener;
import nl.rug.syntree.editor.listener.dom.DOMMdomMouseClickEventListener;
import nl.rug.syntree.editor.listener.dom.DOMMdomMouseOutEventListener;
import nl.rug.syntree.editor.listener.dom.DOMMdomMouseOverEventListener;
import nl.rug.syntree.editor.listener.dom.DOMNodeMouseClickEventListener;
import nl.rug.syntree.editor.listener.dom.DOMNodeMouseOutEventListener;
import nl.rug.syntree.editor.listener.dom.DOMNodeMouseOverEventListener;
import nl.rug.syntree.editor.manager.BranchSelectionManager;
import nl.rug.syntree.editor.manager.MdomSelectionManager;
import nl.rug.syntree.editor.manager.NodeCopyCutManager;
import nl.rug.syntree.editor.manager.NodeSelectionManager;
import nl.rug.syntree.editor.manager.UndoRedoManager;
import nl.rug.syntree.editor.manager.edit.UndoableCreateDaughterEdit;
import nl.rug.syntree.editor.manager.edit.UndoableCreateTreeEdit;
import nl.rug.syntree.tree.TreeRepresentation;
import nl.rug.syntree.tree.component.BranchElement;
import nl.rug.syntree.tree.component.BranchRectElement;
import nl.rug.syntree.tree.component.MdomBranchBackgroundPathElement;
import nl.rug.syntree.tree.component.MdomBranchElement;
import nl.rug.syntree.tree.component.NodeElement;
import nl.rug.syntree.tree.component.NodeRectElement;
import nl.rug.syntree.tree.component.TreeElement;
import nl.rug.syntree.ui.UIFormatJToolBar;
import nl.rug.syntree.ui.UIJInternalFrame;

import org.apache.batik.bridge.UpdateManager;
import org.apache.batik.util.RunnableQueue;
import org.w3c.dom.events.EventTarget;

/**
 * This class represents a tree editor bridge between the graphical and the
 * bracket structure representation of a tree.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class TreeEditorBridge {
	/**
	 * The internal frame that contains this bridge
	 */
	protected UIJInternalFrame uiJInternalFrame;

	/**
	 * Tree representation
	 */
	protected TreeRepresentation treeRepresentation;

	/**
	 * Branch selection manager
	 */
	protected BranchSelectionManager branchSelectionManager;

	/**
	 * Mdom branch selection manager
	 */
	protected MdomSelectionManager mdomSelectionManager;

	/**
	 * Node selection manager
	 */
	protected NodeSelectionManager nodeSelectionManager;

	/**
	 * Undo/Redo manager
	 */
	protected UndoRedoManager undoRedoManager;

	/**
	 * Copy/Cut manager
	 */
	protected NodeCopyCutManager nodeCopyCutManager;

	/**
	 * Constructs a new tree editor bridge.
	 * 
	 * @param uiJInternalFrame
	 *            internal frame
	 */
	public TreeEditorBridge(UIJInternalFrame uiJInternalFrame) {
		initializeBridge();
		this.uiJInternalFrame = uiJInternalFrame;
		treeRepresentation = new TreeRepresentation(this);
	}

	/**
	 * Constructs a new tree editor bridge for the specified SVG XML file.
	 * 
	 * @param uiJInternalFrame
	 *            internal frame
	 * @param svgXMLFile
	 *            the svg xml file to represent
	 */
	public TreeEditorBridge(UIJInternalFrame uiJInternalFrame, File svgXMLFile) {
		initializeBridge();
		this.uiJInternalFrame = uiJInternalFrame;
		treeRepresentation = new TreeRepresentation(this, svgXMLFile);		
	}

	/**
	 * Initializes the bridge.
	 */
	public void initializeBridge() {
		// branch selection manager
		branchSelectionManager = new BranchSelectionManager(this);

		// multidominance selection manager
		mdomSelectionManager = new MdomSelectionManager(this);

		// node selection manager
		nodeSelectionManager = new NodeSelectionManager(this);

		// node copy/cut manager
		nodeCopyCutManager = new NodeCopyCutManager(this);

		// undo/redo manager
		undoRedoManager = new UndoRedoManager();
	}

	// create/add/remove tree ////////////////////////////////////////////////

	/**
	 * Create and append a new tree to the tree representation.
	 */
	public void createTree() {
		final TreeEditorBridge treeEditorBridge = this;
		UpdateManager um = uiJInternalFrame.getTreeEditorJSVGScrollPane()
				.getTreeEditorJSVGCanvas().getUpdateManager();
		RunnableQueue rq = um.getUpdateRunnableQueue();
		if (um != null && rq != null) {
			rq.invokeLater(new Runnable() {
				public void run() {
					TreeElement treeElement = treeRepresentation.createTree();
					undoRedoManager.undoableEditHappened(new UndoableEditEvent(
							treeRepresentation, new UndoableCreateTreeEdit(
									treeEditorBridge, treeElement)));
					uiJInternalFrame.setModified(true);
					uiJInternalFrame.getTreeEditorJSVGScrollPane()
							.getTreeDrawer().draw();
					treeRepresentation.getSVGDocument().computeViewBox();
				}
			});
		}
	}

	/**
	 * Adds the specified existing tree to the tree representation.
	 * 
	 * @param treeElement
	 *            tree element
	 */
	public void addTree(final TreeElement treeElement) {
		UpdateManager um = uiJInternalFrame.getTreeEditorJSVGScrollPane()
				.getTreeEditorJSVGCanvas().getUpdateManager();
		RunnableQueue rq = um.getUpdateRunnableQueue();
		if (um != null && rq != null) {
			rq.invokeLater(new Runnable() {
				public void run() {
					treeRepresentation.addTree(treeElement);
					uiJInternalFrame.setModified(true);
					uiJInternalFrame.getTreeEditorJSVGScrollPane()
							.getTreeDrawer().draw();
					treeRepresentation.getSVGDocument().computeViewBox();
				}
			});
		}
	}

	/**
	 * Removes the specified tree from the tree representation.
	 * 
	 * @param treeElement
	 *            tree element
	 */
	public void removeTree(final TreeElement treeElement) {
		UpdateManager um = uiJInternalFrame.getTreeEditorJSVGScrollPane()
				.getTreeEditorJSVGCanvas().getUpdateManager();
		RunnableQueue rq = um.getUpdateRunnableQueue();
		if (um != null && rq != null) {
			rq.invokeLater(new Runnable() {
				public void run() {
					treeRepresentation.removeTree(treeElement);
					uiJInternalFrame.setModified(true);
					uiJInternalFrame.getTreeEditorJSVGScrollPane()
							.getTreeDrawer().draw();
					treeRepresentation.getSVGDocument().computeViewBox();
				}
			});
		}
	}

	// create daughter ///////////////////////////////////////////////////////

	/**
	 * Create and append a new daughter to the mother element.
	 * 
	 * @param motherNode
	 *            mother node
	 */
	public void createDaughter(final NodeElement motherNode) {
		createDaughterBefore(motherNode, null);
	}

	/**
	 * Create a new daughter and insert it before the specified daughter of the
	 * specified mother.
	 * 
	 * @param motherNode
	 *            mother node
	 * @param nextDaughterNode
	 *            next daughter node
	 */
	public void createDaughterBefore(final NodeElement motherNode,
			final NodeElement nextDaughterNode) {
		final TreeEditorBridge treeEditorBridge = this;
		UpdateManager um = uiJInternalFrame.getTreeEditorJSVGScrollPane()
				.getTreeEditorJSVGCanvas().getUpdateManager();
		RunnableQueue rq = um.getUpdateRunnableQueue();
		if (um != null && rq != null) {
			rq.invokeLater(new Runnable() {
				public void run() {
					NodeElement daughterNode = motherNode
							.createDaughterBefore(nextDaughterNode);
					undoRedoManager.undoableEditHappened(new UndoableEditEvent(
							treeRepresentation, new UndoableCreateDaughterEdit(
									treeEditorBridge, motherNode, daughterNode,
									nextDaughterNode)));
					uiJInternalFrame.setModified(true);
					uiJInternalFrame.getTreeEditorJSVGScrollPane()
							.getTreeDrawer().draw();
					treeRepresentation.getSVGDocument().computeViewBox();
				}
			});
		}
	}

	// merge daughter ////////////////////////////////////////////////////////

	public void mergeDaughter(final NodeElement mergeNode,
			final NodeElement motherNode) {
		mergeDaughterBefore(mergeNode, motherNode, null);
	}

	public void mergeDaughterBefore(final NodeElement mergeNode,
			final NodeElement motherNode, final NodeElement nextDaughterNode) {
		// final TreeEditorBridge treeEditorBridge = this;
		UpdateManager um = uiJInternalFrame.getTreeEditorJSVGScrollPane()
				.getTreeEditorJSVGCanvas().getUpdateManager();
		RunnableQueue rq = um.getUpdateRunnableQueue();
		if (um != null && rq != null) {
			rq.invokeLater(new Runnable() {
				public void run() {
					NodeElement daughterNode = motherNode.mergeDaughterBefore(
							mergeNode, nextDaughterNode);
					nodeSelectionManager.setProminentNode(daughterNode);
					nodeSelectionManager.selectSubtree();
					updateSelectionHighlighting();
					// undoRedoManager.undoableEditHappened(new
					// UndoableEditEvent(
					// treeRepresentation, new UndoableCreateDaughterEdit(
					// treeEditorBridge, motherNode, daughterNode,
					// nextDaughterNode)));
					uiJInternalFrame.setModified(true);
					uiJInternalFrame.getTreeEditorJSVGScrollPane()
							.getTreeDrawer().draw();
					treeRepresentation.getSVGDocument().computeViewBox();
				}
			});
		}
	}

	/**
	 * Adds an existing daughter and inserts it before the specified daughter of
	 * the specified mother.
	 * 
	 * @param motherNode
	 *            mother node
	 * @param daughterNode
	 *            daughter node
	 */
	public void addDaughter(final NodeElement motherNode,
			final NodeElement daughterNode, final NodeElement nextDaughterNode) {
		UpdateManager um = uiJInternalFrame.getTreeEditorJSVGScrollPane()
				.getTreeEditorJSVGCanvas().getUpdateManager();
		RunnableQueue rq = um.getUpdateRunnableQueue();
		if (um != null && rq != null) {
			rq.invokeLater(new Runnable() {
				public void run() {
					motherNode.addDaughter(daughterNode, nextDaughterNode);
					uiJInternalFrame.setModified(true);
					uiJInternalFrame.getTreeEditorJSVGScrollPane()
							.getTreeDrawer().draw();
					treeRepresentation.getSVGDocument().computeViewBox();
				}
			});
		}
	}

	/**
	 * Removes the specified daughter node from the specified mother node.
	 * 
	 * @param motherNode
	 *            mother node
	 * @param daughterNode
	 *            daughter node
	 */
	public void removeDaughter(final NodeElement motherNode,
			final NodeElement daughterNode) {
		UpdateManager um = uiJInternalFrame.getTreeEditorJSVGScrollPane()
				.getTreeEditorJSVGCanvas().getUpdateManager();
		RunnableQueue rq = um.getUpdateRunnableQueue();
		if (um != null && rq != null) {
			rq.invokeLater(new Runnable() {
				public void run() {
					if (motherNode != null)
						motherNode.removeDaughter(daughterNode);
					else
						daughterNode.getTreeRepresentation().removeTree(
								daughterNode.getTreeElement());
					uiJInternalFrame.setModified(true);
					uiJInternalFrame.getTreeEditorJSVGScrollPane()
							.getTreeDrawer().draw();
					treeRepresentation.getSVGDocument().computeViewBox();
				}
			});
		}
	}

	/**
	 * Creates a multidominance relation.
	 */
	public void createMultidominanceRelation(final NodeElement motherNode) {
		UpdateManager um = uiJInternalFrame.getTreeEditorJSVGScrollPane()
				.getTreeEditorJSVGCanvas().getUpdateManager();
		RunnableQueue rq = um.getUpdateRunnableQueue();
		if (um != null && rq != null) {
			rq.invokeLater(new Runnable() {
				public void run() {
					treeRepresentation.createMultidominanceRelation(motherNode);
					uiJInternalFrame.setModified(true);
					uiJInternalFrame.getTreeEditorJSVGScrollPane()
							.getTreeDrawer().draw();
					treeRepresentation.getSVGDocument().computeViewBox();
				}
			});
		}
	}

	/**
	 * Updates the selection highlighting.
	 */
	public void updateSelectionHighlighting() {
		UpdateManager um = uiJInternalFrame.getTreeEditorJSVGScrollPane()
				.getTreeEditorJSVGCanvas().getUpdateManager();
		RunnableQueue rq = um.getUpdateRunnableQueue();
		if (um != null && rq != null) {
			rq.preemptLater(new Runnable() {
				public void run() {
					// tree group
					for (int i = 0; i < treeRepresentation.getTreeVector()
							.size(); i++) {
						for (int x = 0; x < treeRepresentation.getTreeVector()
								.elementAt(i).getNodeVector().size(); x++) {
							NodeElement nodeElement = treeRepresentation
									.getTreeVector().elementAt(i)
									.getNodeVector().elementAt(x);
							BranchElement branchElement = nodeElement
									.getBranchElement();

							// branches
							if (branchElement != null)
								if (branchSelectionManager
										.isSelected(branchElement))
									branchElement.getBranchRectElement()
											.setHighlightMode(
													BranchRectElement.SELECT);
								else
									branchElement.getBranchRectElement()
											.setHighlightMode(
													BranchRectElement.STANDARD);

							// nodes
							if (nodeSelectionManager.isSelected(nodeElement))
								nodeElement.getNodeRectElement()
										.setHighlightMode(
												NodeRectElement.SELECT);
							else
								nodeElement.getNodeRectElement()
										.setHighlightMode(
												NodeRectElement.STANDARD);
						}
					}

					// meta group
					for (int i = 0; i < treeRepresentation.getMdomVector()
							.size(); i++) {
						MdomBranchElement mdomBranchElement = treeRepresentation
								.getMdomVector().elementAt(i);
						if (mdomSelectionManager.isSelected(mdomBranchElement))
							mdomBranchElement
									.getMdomBranchBackgroundPathElement()
									.setHighlightMode(
											MdomBranchBackgroundPathElement.SELECT);
						else
							mdomBranchElement
									.getMdomBranchBackgroundPathElement()
									.setHighlightMode(
											MdomBranchBackgroundPathElement.STANDARD);
					}
				}
			});
		}
	}

	/**
	 * Adds branch element event listeners to the branch element of the
	 * specified node.
	 * 
	 * @param nodeElement
	 *            node element
	 */
	public void addBranchElementEventListeners(NodeElement nodeElement) {
		EventTarget et = (EventTarget) nodeElement.getBranchElement()
				.getElement();

		// mouse click
		DOMBranchMouseClickEventListener domBranchMouseClickEventListener;
		domBranchMouseClickEventListener = new DOMBranchMouseClickEventListener(
				this, nodeElement);
		et.addEventListener("click", domBranchMouseClickEventListener, false);

		// mouse over
		DOMBranchMouseOverEventListener domBranchMouseOverEventListener;
		domBranchMouseOverEventListener = new DOMBranchMouseOverEventListener(
				this, nodeElement);
		et
				.addEventListener("mouseover", domBranchMouseOverEventListener,
						false);

		// mouse out
		DOMBranchMouseOutEventListener domBranchMouseOutEventListener;
		domBranchMouseOutEventListener = new DOMBranchMouseOutEventListener(
				this, nodeElement);
		et.addEventListener("mouseout", domBranchMouseOutEventListener, false);		
	}

	/**
	 * Adds branch rectangle event listeners to the branch rectangle element of
	 * the branch element of the specified node.
	 * 
	 * @param nodeElement
	 *            node element
	 */
	public void addBranchRectElementEventListeners(NodeElement nodeElement) {
		EventTarget et = (EventTarget) nodeElement.getBranchElement()
				.getBranchRectElement().getElement();

		// mouse click
		DOMBranchMouseClickEventListener domBranchMouseClickEventListener;
		domBranchMouseClickEventListener = new DOMBranchMouseClickEventListener(
				this, nodeElement);
		et.addEventListener("click", domBranchMouseClickEventListener, false);

		// mouse over
		DOMBranchMouseOverEventListener domBranchMouseOverEventListener;
		domBranchMouseOverEventListener = new DOMBranchMouseOverEventListener(
				this, nodeElement);
		et
				.addEventListener("mouseover", domBranchMouseOverEventListener,
						false);

		// mouse out
		DOMBranchMouseOutEventListener domBranchMouseOutEventListener;
		domBranchMouseOutEventListener = new DOMBranchMouseOutEventListener(
				this, nodeElement);
		et.addEventListener("mouseout", domBranchMouseOutEventListener, false);
	}

	/**
	 * Adds text element event listeners to the text element of specified node.
	 * 
	 * @param nodeElement
	 *            node element
	 */
	public void addTextElementEventListeners(NodeElement nodeElement) {
		EventTarget et = (EventTarget) nodeElement.getTextElement()
				.getElement();

		// mouse click
		DOMNodeMouseClickEventListener domNodeMouseClickEventListener;
		domNodeMouseClickEventListener = new DOMNodeMouseClickEventListener(
				this, nodeElement);
		et.addEventListener("click", domNodeMouseClickEventListener, false);

		// mouse over
		DOMNodeMouseOverEventListener domNodeMouseOverEventListener;
		domNodeMouseOverEventListener = new DOMNodeMouseOverEventListener(this,
				nodeElement);
		et.addEventListener("mouseover", domNodeMouseOverEventListener, false);

		// mouse out
		DOMNodeMouseOutEventListener domNodeMouseOutEventListener;
		domNodeMouseOutEventListener = new DOMNodeMouseOutEventListener(this,
				nodeElement);
		et.addEventListener("mouseout", domNodeMouseOutEventListener, false);
	}

	/**
	 * Adds multidominance branch event listeners to the mdom branch path
	 * element of the specified mdom branch element.
	 * 
	 * @param mdomBranchElement
	 *            mdom branchelement
	 */
	public void addMdomBranchPathElementEventListeners(
			MdomBranchElement mdomBranchElement) {
		EventTarget et = (EventTarget) mdomBranchElement
				.getMdomBranchPathElement().getElement();

		// mouse click
		DOMMdomMouseClickEventListener domMdomMouseClickEventListener;
		domMdomMouseClickEventListener = new DOMMdomMouseClickEventListener(
				this, mdomBranchElement);
		et.addEventListener("click", domMdomMouseClickEventListener, false);

		// mouse over
		DOMMdomMouseOverEventListener domMdomMouseOverEventListener;
		domMdomMouseOverEventListener = new DOMMdomMouseOverEventListener(this,
				mdomBranchElement);
		et.addEventListener("mouseover", domMdomMouseOverEventListener, false);

		// mouse out
		DOMMdomMouseOutEventListener domMdomMouseOutEventListener;
		domMdomMouseOutEventListener = new DOMMdomMouseOutEventListener(this,
				mdomBranchElement);
		et.addEventListener("mouseout", domMdomMouseOutEventListener, false);

	}

	/**
	 * Adds multidominance branch background event listeners to the mdom branch
	 * background path element of the specified brannch element.
	 * 
	 * @param mdomBranchElement
	 *            mdom branchelement
	 */
	public void addMdomBranchBackgroundPathElementEventListeners(
			MdomBranchElement mdomBranchElement) {
		EventTarget et = (EventTarget) mdomBranchElement
				.getMdomBranchBackgroundPathElement().getElement();

		// mouse click
		DOMMdomMouseClickEventListener domMdomMouseClickEventListener;
		domMdomMouseClickEventListener = new DOMMdomMouseClickEventListener(
				this, mdomBranchElement);
		et.addEventListener("click", domMdomMouseClickEventListener, false);

		// mouse over
		DOMMdomMouseOverEventListener domMdomMouseOverEventListener;
		domMdomMouseOverEventListener = new DOMMdomMouseOverEventListener(this,
				mdomBranchElement);
		et.addEventListener("mouseover", domMdomMouseOverEventListener, false);

		// mouse out
		DOMMdomMouseOutEventListener domMdomMouseOutEventListener;
		domMdomMouseOutEventListener = new DOMMdomMouseOutEventListener(this,
				mdomBranchElement);
		et.addEventListener("mouseout", domMdomMouseOutEventListener, false);
	}

	/**
	 * Adds node rectangle event listeners to the node rectangle element of the
	 * specified node.
	 * 
	 * @param nodeElement
	 *            node element
	 */
	public void addNodeRectElementEventListeners(NodeElement nodeElement) {
		EventTarget et = (EventTarget) nodeElement.getNodeRectElement()
				.getElement();

		// mouse click
		DOMNodeMouseClickEventListener domNodeMouseClickEventListener;
		domNodeMouseClickEventListener = new DOMNodeMouseClickEventListener(
				this, nodeElement);
		et.addEventListener("click", domNodeMouseClickEventListener, false);

		// mouse over
		DOMNodeMouseOverEventListener domNodeMouseOverEventListener;
		domNodeMouseOverEventListener = new DOMNodeMouseOverEventListener(this,
				nodeElement);
		et.addEventListener("mouseover", domNodeMouseOverEventListener, false);

		// mouse out
		DOMNodeMouseOutEventListener domNodeMouseOutEventListener;
		domNodeMouseOutEventListener = new DOMNodeMouseOutEventListener(this,
				nodeElement);
		et.addEventListener("mouseout", domNodeMouseOutEventListener, false);
	}

	/**
	 * Returns the branch selection manager.
	 * 
	 * @return branch selection manager
	 */
	public BranchSelectionManager getBranchSelectionManager() {
		return branchSelectionManager;
	}

	/**
	 * Returns the mdom branch selection manager.
	 * 
	 * @return mdom branch selection manager
	 */
	public MdomSelectionManager getMdomSelectionManager() {
		return mdomSelectionManager;
	}

	/**
	 * Returns the node selection manager.
	 * 
	 * @return node selection manager
	 */
	public NodeSelectionManager getNodeSelectionManager() {
		return nodeSelectionManager;
	}

	/**
	 * Returns the update manager of the scalable vector graphics canvas.
	 * 
	 * @return update manager
	 */
	public UpdateManager getUpdateManager() {
		return uiJInternalFrame.getTreeEditorJSVGScrollPane()
				.getTreeEditorJSVGCanvas().getUpdateManager();
	}

	/**
	 * Returns the undo/redo manager
	 * 
	 * @return undo/redo manager
	 */
	public UndoRedoManager getUndoRedoManager() {
		return undoRedoManager;
	}

	/**
	 * Returns the node copy/cut manager
	 * 
	 * @return node copy/cut manager
	 */
	public NodeCopyCutManager getNodeCopyCutManager() {
		return nodeCopyCutManager;
	}

	/**
	 * Returns the tree representation.
	 * 
	 * @return tree representation
	 */
	public TreeRepresentation getTreeRepresentation() {
		return treeRepresentation;
	}

	/**
	 * Returns the internal frame.
	 * 
	 * @return internal frame
	 */
	public UIJInternalFrame getUIJInternalFrame() {
		return uiJInternalFrame;
	}

	/**
	 * Returns the formatting tool bar.
	 * 
	 * @return formatting tool bar
	 */
	public UIFormatJToolBar getUIFormatJToolBar() {
		return uiJInternalFrame.getUIJDesktopPane().getUIMainJFrame()
				.getUIFormatJToolBar();
	}
}