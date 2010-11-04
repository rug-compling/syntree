/* TreeEditorJSVGScrollPane.java */

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

import java.awt.Color;
import java.util.List;

import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import nl.rug.syntree.drawer.TreeDrawer;
import nl.rug.syntree.editor.interactor.CreateBranchInteractor;
import nl.rug.syntree.editor.interactor.MergeBranchInteractor;
import nl.rug.syntree.editor.interactor.ImageZoomInteractor;
import nl.rug.syntree.editor.interactor.MdomInteractor;
import nl.rug.syntree.editor.interactor.PanInteractor;
import nl.rug.syntree.editor.interactor.ResetTransformInteractor;
import nl.rug.syntree.editor.interactor.RotateInteractor;
import nl.rug.syntree.editor.interactor.TextInteractor;
import nl.rug.syntree.editor.interactor.ZoomInteractor;
import nl.rug.syntree.editor.listener.SVGLoadEventDispatcherListener;
import nl.rug.syntree.editor.listener.TreeEditorJSVGCanvasMouseListener;

import org.apache.batik.swing.JSVGScrollPane;
import org.apache.batik.swing.gvt.Interactor;

/**
 * This class represents a scroll pane that embeds a Scalable Vector Graphics
 * canvas and provides a framework for a tree editor.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class TreeEditorJSVGScrollPane extends JSVGScrollPane {
	/**
	 * Serial version identifier constant
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Embedded scalable vector graphics canvas
	 */
	protected TreeEditorJSVGCanvas treeEditorJSVGCanvas;

	/**
	 * List of interactors attached to the tree editor canvas
	 */
	protected List<Interactor> treeEditorInteractors;

	/**
	 * Tree drawer
	 */
	protected TreeDrawer treeDrawer;

	/**
	 * Tree editor bridge
	 */
	protected TreeEditorBridge treeEditorBridge;

	/**
	 * Create branch interactor
	 */
	protected CreateBranchInteractor createBranchInteractor;

	/**
	 * Merge branch interactor 
	 */
	protected MergeBranchInteractor mergeBranchInteractor;
		
	/**
	 * Mdom interactor
	 */
	protected MdomInteractor mdomInteractor;

	/**
	 * Text interactor
	 */
	protected TextInteractor textInteractor;

	/**
	 * Constructs a new scroll pane and embeds a Scalable Vector Graphics
	 * canvas.
	 * 
	 * @param treeEditorBridge
	 *            tree editor bridge
	 */
	@SuppressWarnings("unchecked")
	public TreeEditorJSVGScrollPane(TreeEditorBridge treeEditorBridge) {
		super(new TreeEditorJSVGCanvas(treeEditorBridge));
		treeEditorJSVGCanvas = (TreeEditorJSVGCanvas) this.getCanvas();
		this.treeEditorBridge = treeEditorBridge;
		this.setBorder(new CompoundBorder(
				new TitledBorder("[view: plain tree]"), new EmptyBorder(5, 5,
						5, 5)));
		this.setBackground(Color.WHITE);
		treeDrawer = new TreeDrawer(treeEditorBridge.getTreeRepresentation());

		treeEditorJSVGCanvas.setDocument(treeEditorBridge
				.getTreeRepresentation().getSVGDocument().getDocument());
		treeEditorJSVGCanvas.setRecenterOnResize(true);
		treeEditorJSVGCanvas
				.addSVGLoadEventDispatcherListener(new SVGLoadEventDispatcherListener(
						this));
		treeEditorJSVGCanvas
				.addMouseListener(new TreeEditorJSVGCanvasMouseListener(this));

		treeEditorInteractors = treeEditorJSVGCanvas.getInteractors();
		treeEditorInteractors.clear();
		treeEditorInteractors.add(new ImageZoomInteractor());
		treeEditorInteractors.add(new PanInteractor());
		treeEditorInteractors.add(new ResetTransformInteractor());
		treeEditorInteractors.add(new RotateInteractor());
		treeEditorInteractors.add(new ZoomInteractor());

		createBranchInteractor = new CreateBranchInteractor(this);
		treeEditorInteractors.add(createBranchInteractor);

		mergeBranchInteractor = new MergeBranchInteractor(this);
		treeEditorInteractors.add(mergeBranchInteractor);
		
		mdomInteractor = new MdomInteractor(this);
		treeEditorInteractors.add(mdomInteractor);

		textInteractor = new TextInteractor(this);
		treeEditorInteractors.add(textInteractor);
	}
	
	/**
	 * Returns the embedded Scalable Vector Graphics canvas.
	 * 
	 * @return scalable vector graphics canvas
	 */
	public TreeEditorJSVGCanvas getTreeEditorJSVGCanvas() {
		return treeEditorJSVGCanvas;
	}

	/**
	 * Returns the create branch interactor.
	 * 
	 * @return create branch interactor
	 */
	public CreateBranchInteractor getCreateBranchInteractor() {
		return createBranchInteractor;
	}
	
	/**
	 * Returns the merge branch interactor.
	 * 
	 * @return merge branch interactor
	 */
	public MergeBranchInteractor getMergeBranchInteractor() {
		return mergeBranchInteractor;
	}

	/**
	 * Returns the mdom interactor.
	 * 
	 * @return mdom interactor
	 */
	public MdomInteractor getMdomInteractor() {
		return mdomInteractor;
	}

	/**
	 * Returns the text interactor.
	 * 
	 * @return text interactor
	 */
	public TextInteractor getTextInteractor() {
		return textInteractor;
	}

	/**
	 * Returns the tree drawer.
	 * 
	 * @return tree drawer
	 */
	public TreeDrawer getTreeDrawer() {
		return treeDrawer;
	}

	/**
	 * Returns the tree editor bridge.
	 * 
	 * @return tree editor bridge
	 */
	public TreeEditorBridge getTreeEditorBridge() {
		return treeEditorBridge;
	}
}