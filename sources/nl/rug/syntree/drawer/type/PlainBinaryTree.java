/* PlainBinaryTree.java */

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

package nl.rug.syntree.drawer.type;

import java.util.Vector;

import nl.rug.syntree.tree.TreeRepresentation;
import nl.rug.syntree.tree.component.BranchElement;
import nl.rug.syntree.tree.component.BranchRectElement;
import nl.rug.syntree.tree.component.MdomBranchBackgroundPathElement;
import nl.rug.syntree.tree.component.MdomBranchElement;
import nl.rug.syntree.tree.component.MdomBranchPathElement;
import nl.rug.syntree.tree.component.NodeElement;
import nl.rug.syntree.tree.component.NodeRectElement;
import nl.rug.syntree.tree.component.TSpanElement;
import nl.rug.syntree.tree.component.TextElement;
import nl.rug.syntree.tree.component.TreeElement;

/**
 * This class represents tree drawing functionality for a plain binary tree.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class PlainBinaryTree {
	/**
	 * Horizontal padding constant for a branch rectangle
	 */
	public static final float BRANCH_RECTANGLE_HORIZONTAL_PADDING = 1.5f;

	/**
	 * Vertical padding constant for a branch rectangle
	 */
	public static final float BRANCH_RECTANGLE_VERTICAL_PADDING = 1.5f;

	/**
	 * Empty node vertical padding constant
	 */
	public static final float EMPTY_NODE_VERTICAL_PADDING = 10.0f;

	/**
	 * Invisible node horizontal padding constant
	 */
	public static final float INVISIBLE_NODE_HORIZONTAL_PADDING = 10.0f;

	/**
	 * Invisible node vertical padding constant
	 */
	public static final float INVISIBLE_NODE_VERTICAL_PADDING = 5.0f;

	/**
	 * Invisible node rectangle height constant
	 */
	public static final float INVISIBLE_NODE_RECTANGLE_HEIGHT = 12.0f;

	/**
	 * Invisible node rectangle width constant
	 */
	public static final float INVISIBLE_NODE_RECTANGLE_WIDTH = 12.0f;

	/**
	 * Vertical padding constant for a text line
	 */
	public static final float TEXT_VERTICAL_PADDING = 3.0f;

	/**
	 * Horizontal margin constant for a node
	 */
	public static final float NODE_HORIZONTAL_MARGIN = 25.0f;

	/**
	 * Vertical margin constant for a node
	 */
	public static final float NODE_VERTICAL_MARGIN = 35.0f;

	/**
	 * Vertical padding constant for a node
	 */
	public static final float NODE_VERTICAL_PADDING = 4.0f;

	/**
	 * Horizontal margin constant for a tree
	 */
	public static final float TREE_HORIZONTAL_MARGIN = 75.0f;

	/**
	 * Horizontal padding constant for a node rectangle
	 */
	public static final float NODE_RECTANGLE_HORIZONTAL_PADDING = 0f;

	/**
	 * Vertical padding constant for a node rectangle
	 */
	public static final float NODE_RECTANGLE_VERTICAL_PADDING = 1.5f;

	/**
	 * Unconnected multidominance branch height constant
	 */
	public static final float UNCONNECTED_MDOM_BRANCH_HEIGHT = 35.0f;

	/**
	 * Draws all of the trees in the specified tree representation.
	 * 
	 * @param treeRepresentation
	 *            tree representation
	 */
	public static void draw(TreeRepresentation treeRepresentation) {
		Vector<TreeElement> treeVector = treeRepresentation.getTreeVector();
		float x = 0.0f;
		for (int i = 0; i < treeVector.size(); i++) {
			TreeElement treeElement = treeVector.elementAt(i);
			draw(treeRepresentation, treeElement);
			x += treeElement.getInnerWidth();
			treeElement.setXTranslate(x);
			x += treeElement.getWidth() + TREE_HORIZONTAL_MARGIN;
		}
		Vector<MdomBranchElement> mdomVector = treeRepresentation
				.getMdomVector();
		for (int i = 0; i < mdomVector.size(); i++)
			drawMdomBranch(treeRepresentation, mdomVector.elementAt(i));
	}

	/**
	 * Draws the specified tree in the specified tree representation.
	 * 
	 * @param treeRepresentation
	 *            tree representation
	 * @param treeElement
	 *            tree element
	 */
	public static void draw(TreeRepresentation treeRepresentation,
			TreeElement treeElement) {
		int treeDepth = treeElement.getDepth();
		for (int i = treeDepth; i >= 0; i--) {
			Vector<NodeElement> generationVector = treeElement
					.getNodeGenerationVector(i);
			float generationHeight = 0.0f;
			for (int x = 0; x < generationVector.size(); x++) {
				if (generationVector.elementAt(x).getTextElement()
						.getTaintMode() == TextElement.TAINTED)
					drawNodeText(treeRepresentation, generationVector
							.elementAt(x));
				if (generationVector.elementAt(x).getMother() != null)
					generationHeight = Math.max(generationHeight,
							generationVector.elementAt(x).getMother()
									.getTextElement().getHeight());
			}
			while (generationVector.size() != 0) {
				NodeElement motherNode = generationVector.firstElement()
						.getMother();
				if (motherNode != null) {
					Vector<NodeElement> sisterVector = motherNode
							.getDaughterVector();
					if (motherNode.getTaintMode() == NodeElement.TAINTED)
						drawNodeSet(treeRepresentation, motherNode,
								sisterVector, generationHeight);
					generationVector.removeAll(sisterVector);
				} else {
					if (generationVector.firstElement().getTaintMode() == NodeElement.TAINTED) {
						generationVector.firstElement().computeWidth();
						generationVector.firstElement().setTaintMode(
								NodeElement.UNTAINTED);
					}
					generationVector.clear();
				}
			}
		}
	}

	/**
	 * Draws the set of specified mother and daughter nodes.
	 * 
	 * @param treeRepresentation
	 *            tree representation
	 * @param motherNode
	 *            mother node
	 * @param sisterVector
	 *            sister node vector
	 * @param generationHeight
	 *            generation height
	 */
	protected static void drawNodeSet(TreeRepresentation treeRepresentation,
			NodeElement motherNode, Vector<NodeElement> sisterVector,
			float generationHeight) {
		float width = 0.0f;
		for (int i = 0; i < sisterVector.size(); i++) {
			if (sisterVector.elementAt(i).getTaintMode() == NodeElement.TAINTED) {
				sisterVector.elementAt(i).computeWidth();
			}
		}
		if (motherNode.getTextElement().getTaintMode() == TextElement.TAINTED)
			drawNodeText(treeRepresentation, motherNode);
		if (sisterVector.size() > 1) {
			width += sisterVector.firstElement().getOuterWidth();
			width += sisterVector.lastElement().getInnerWidth();
			width += NODE_HORIZONTAL_MARGIN;
			for (int i = 1; i < sisterVector.size() - 1; i++)
				width += NODE_HORIZONTAL_MARGIN
						+ sisterVector.elementAt(i).getWidth();
		}
		for (int i = 0; i < sisterVector.size(); i++) {
			float x = -(width / 2);
			float y = NODE_VERTICAL_MARGIN;
			if (generationHeight / (0.5 * y) > 1)
				y += generationHeight;
			if (i > 0)
				x += sisterVector.elementAt(i).getInnerWidth();
			for (int z = 0; z < i; z++) {
				if (z == 0)
					x += NODE_HORIZONTAL_MARGIN
							+ sisterVector.elementAt(z).getOuterWidth();
				else
					x += NODE_HORIZONTAL_MARGIN
							+ sisterVector.elementAt(z).getWidth();
			}
			if (sisterVector.elementAt(i).getXTranslate() != x)
				sisterVector.elementAt(i).setTaintMode(NodeElement.TAINTED);
			sisterVector.elementAt(i).setTranslate(x, y);
			drawBranch(treeRepresentation, motherNode, sisterVector
					.elementAt(i), y);
			sisterVector.elementAt(i).setTaintMode(NodeElement.UNTAINTED);
		}
	}

	/**
	 * Draws a branch between the specified mother and a daughter nodes.
	 * 
	 * @param treeRepresentation
	 *            tree representation
	 * @param motherNode
	 *            mother node
	 * @param daughterNode
	 *            daughter node
	 * @param length
	 *            length
	 */
	protected static void drawBranch(TreeRepresentation treeRepresentation,
			NodeElement motherNode, NodeElement daughterNode, float length) {
		BranchElement branchElement = daughterNode.getBranchElement();
		float x1 = 0.0f;
		float y1 = 0.0f;
		float x2 = 0.0f;
		float y2 = 0.0f;
		float x3 = 0.0f;
		float y3 = 0.0f;
		if (motherNode.getTextElement().isVisible()
				&& daughterNode.getTextElement().isVisible()) {
			if (motherNode.getTaintMode() == NodeElement.TAINTED) {
				float motherDimensions[][] = motherNode.getTextElement()
						.getLineDimensions();
				y1 = NODE_VERTICAL_PADDING
						+ motherNode.getTextElement().getHeight()
						- motherDimensions[0][1];
			}
			if (daughterNode.getTaintMode() == NodeElement.TAINTED) {
				if (branchElement.isLine()) {
					x2 = daughterNode.getXTranslate();
					float daughterDimensions[][] = daughterNode
							.getTextElement().getLineDimensions();
					y2 = length - NODE_VERTICAL_PADDING
							- daughterDimensions[0][1];
					if (daughterDimensions[0][1] == 0.0f)
						y2 -= EMPTY_NODE_VERTICAL_PADDING;
				}
				if (branchElement.isTriangle()) {
					x2 = daughterNode.getXTranslate()
							- (daughterNode.getWidth() / 2);
					float daughterDimensions[][] = daughterNode
							.getTextElement().getLineDimensions();
					y2 = length - NODE_VERTICAL_PADDING
							- daughterDimensions[0][1];
					x3 = daughterNode.getXTranslate()
							+ (daughterNode.getWidth() / 2);
					y3 = length - NODE_VERTICAL_PADDING
							- daughterDimensions[0][1];
					if (daughterDimensions[0][1] == 0.0f) {
						y2 -= EMPTY_NODE_VERTICAL_PADDING;
						y3 -= EMPTY_NODE_VERTICAL_PADDING;
					}
				}
			}
		}
		if (motherNode.getTextElement().isVisible()
				&& !daughterNode.getTextElement().isVisible()) {
			if (motherNode.getTaintMode() == NodeElement.TAINTED) {
				float motherDimensions[][] = motherNode.getTextElement()
						.getLineDimensions();
				y1 = NODE_VERTICAL_PADDING
						+ motherNode.getTextElement().getHeight()
						- motherDimensions[0][1];
			}
			if (daughterNode.getTaintMode() == NodeElement.TAINTED) {
				if (branchElement.isLine()) {
					x2 = daughterNode.getXTranslate();
					y2 = daughterNode.getYTranslate()
							- INVISIBLE_NODE_VERTICAL_PADDING;
				}
				if (branchElement.isTriangle()) {
					x2 = daughterNode.getXTranslate()
							- INVISIBLE_NODE_HORIZONTAL_PADDING;
					y2 = daughterNode.getYTranslate()
							- INVISIBLE_NODE_VERTICAL_PADDING;
					x3 = daughterNode.getXTranslate()
							+ INVISIBLE_NODE_HORIZONTAL_PADDING;
					y3 = daughterNode.getYTranslate()
							- INVISIBLE_NODE_VERTICAL_PADDING;
				}
			}
		}
		if (!motherNode.getTextElement().isVisible()
				&& daughterNode.getTextElement().isVisible()) {
			if (motherNode.getTaintMode() == NodeElement.TAINTED)
				y1 = -INVISIBLE_NODE_VERTICAL_PADDING;
			if (daughterNode.getTaintMode() == NodeElement.TAINTED) {
				if (branchElement.isLine()) {
					x2 = daughterNode.getXTranslate();
					float daughterDimensions[][] = daughterNode
							.getTextElement().getLineDimensions();
					y2 = length - NODE_VERTICAL_PADDING
							- daughterDimensions[0][1];
					if (daughterDimensions[0][1] == 0.0f)
						y2 -= EMPTY_NODE_VERTICAL_PADDING;
				}
				if (branchElement.isTriangle()) {
					x2 = daughterNode.getXTranslate()
							- (daughterNode.getWidth() / 2);
					float daughterDimensions[][] = daughterNode
							.getTextElement().getLineDimensions();
					y2 = length - NODE_VERTICAL_PADDING
							- daughterDimensions[0][1];
					x3 = daughterNode.getXTranslate()
							+ (daughterNode.getWidth() / 2);
					y3 = length - NODE_VERTICAL_PADDING
							- daughterDimensions[0][1];
					if (daughterDimensions[0][1] == 0.0f) {
						y2 -= EMPTY_NODE_VERTICAL_PADDING;
						y3 -= EMPTY_NODE_VERTICAL_PADDING;
					}
				}
			}
		}
		if (!motherNode.getTextElement().isVisible()
				&& !daughterNode.getTextElement().isVisible()) {
			if (motherNode.getTaintMode() == NodeElement.TAINTED)
				y1 = -INVISIBLE_NODE_VERTICAL_PADDING;
			if (daughterNode.getTaintMode() == NodeElement.TAINTED) {
				if (branchElement.isLine()) {
					x2 = daughterNode.getXTranslate();
					y2 = daughterNode.getYTranslate()
							- INVISIBLE_NODE_VERTICAL_PADDING;
				}
				if (branchElement.isTriangle()) {
					x2 = daughterNode.getXTranslate()
							- INVISIBLE_NODE_HORIZONTAL_PADDING;
					y2 = daughterNode.getYTranslate()
							- INVISIBLE_NODE_VERTICAL_PADDING;
					x3 = daughterNode.getXTranslate()
							+ INVISIBLE_NODE_HORIZONTAL_PADDING;
					y3 = daughterNode.getYTranslate()
							- INVISIBLE_NODE_VERTICAL_PADDING;
				}
			}
		}
		if (motherNode.getTaintMode() == NodeElement.TAINTED) {
			branchElement.setX1(x1);
			branchElement.setY1(y1);
		}
		if (daughterNode.getTaintMode() == NodeElement.TAINTED) {
			branchElement.setX2(x2);
			branchElement.setY2(y2);
			branchElement.setX3(x3);
			branchElement.setY3(y3);
		}
		branchElement.setTranslate(-daughterNode.getXTranslate(), -daughterNode
				.getYTranslate());
		if (daughterNode.getTaintMode() == NodeElement.TAINTED)
			drawBranchRectangle(treeRepresentation, daughterNode);
	}

	/**
	 * Draws the specified rectangle that faciliates branch highlighting.
	 * 
	 * @param treeRepresentation
	 *            tree representation
	 * @param nodeElement
	 *            node element
	 */
	protected static void drawBranchRectangle(
			TreeRepresentation treeRepresentation, NodeElement nodeElement) {
		BranchElement branchElement = nodeElement.getBranchElement();
		BranchRectElement branchRectElement = branchElement
				.getBranchRectElement();
		float x = 0.0f;
		float y = 0.0f;
		float h = 0.0f;
		float w = 0.0f;
		float a = 0.0f;
		if (branchElement.isLine()) {
			x = branchElement.getX1();
			y = branchElement.getY1();
			h = (float) Math.sqrt(Math.pow(branchElement.getY2() - y, 2)
					+ Math.pow(branchElement.getX2() - x, 2));
			a = -(float) (Math.asin((branchElement.getX2() - x) / h) / Math.PI) * 180;
			branchRectElement.setRotate(a, x, y);
		}
		if (branchElement.isTriangle()) {
			x = branchElement.getX2();
			y = branchElement.getY1();
			h = branchElement.getY2() - y;
			w = branchElement.getX3() - x;
			branchRectElement.setRotate(a, x, y);
		}
		x -= BRANCH_RECTANGLE_HORIZONTAL_PADDING;
		y -= BRANCH_RECTANGLE_VERTICAL_PADDING;
		h += 2 * BRANCH_RECTANGLE_VERTICAL_PADDING;
		w += 2 * BRANCH_RECTANGLE_HORIZONTAL_PADDING;
		branchRectElement.setTranslate(-nodeElement.getXTranslate(),
				-nodeElement.getYTranslate());
		branchRectElement.setHeight(h);
		branchRectElement.setWidth(w);
		branchRectElement.setX(x);
		branchRectElement.setY(y);
	}

	/**
	 * Draws the text of the specified node.
	 * 
	 * @param treeRepresentation
	 *            tree representation
	 * @param nodeElement
	 *            node element
	 */
	public static void drawNodeText(TreeRepresentation treeRepresentation,
			NodeElement nodeElement) {
		float nodeDimensions[][] = nodeElement.getTextElement()
				.getLineDimensions();
		Vector<TSpanElement> tspanVector = nodeElement.getTextElement()
				.getTSpanVector();
		int lines = 0;
		if (tspanVector.size() > 0)
			lines = tspanVector.lastElement().getLine() + 1;
		float width = nodeElement.getTextElement().getWidth();
		for (int i = 0; i < lines; i++) {
			float dx = 0.0f;
			float dy = 0.0f;
			if (i > 0)
				dy = nodeDimensions[i - 1][1] + TEXT_VERTICAL_PADDING;
			boolean delta = false;
			for (int x = 0; x < tspanVector.size(); x++) {
				TSpanElement tspanElement = tspanVector.elementAt(x);
				if (tspanElement.getLine() == i)
					if (!delta && tspanElement.getText().length() > 0) {
						if (nodeElement.getTextElement().getAlignment()
								.compareTo(TextElement.ALIGN_LEFT) == 0)
							if (i == 0)
								dx = -(width / 2);
							else
								dx = -nodeDimensions[i - 1][0];
						if (nodeElement.getTextElement().getAlignment()
								.compareTo(TextElement.ALIGN_CENTER) == 0)
							if (i == 0)
								if (width == nodeDimensions[i][0])
									dx = -(width / 2);
								else
									dx = -((width / 2) - ((width / 2) - (nodeDimensions[i][0] / 2)));
							else
								dx = -nodeDimensions[i - 1][0]
										+ ((nodeDimensions[i - 1][0] - nodeDimensions[i][0]) / 2);
						if (nodeElement.getTextElement().getAlignment()
								.compareTo(TextElement.ALIGN_RIGHT) == 0)
							if (i == 0)
								dx = (width / 2) - nodeDimensions[i][0];
							else
								dx = -nodeDimensions[i][0];
						tspanElement.setDX(dx);
						tspanElement.setDY(dy);
						delta = true;
					} else {
						tspanElement.setDX(0.0f);
						tspanElement.setDY(0.0f);
					}
			}
		}
		drawNodeRectangle(treeRepresentation, nodeElement);
		nodeElement.getTextElement().setTaintMode(TextElement.UNTAINTED);
	}

	/**
	 * Draws the specified rectangle that falicates node highlighting.
	 * 
	 * @param treeRepresentation
	 *            tree representation
	 * @param nodeElement
	 *            node element
	 */
	protected static void drawNodeRectangle(
			TreeRepresentation treeRepresentation, NodeElement nodeElement) {
		NodeRectElement nodeRectElement = nodeElement.getNodeRectElement();
		float h = INVISIBLE_NODE_RECTANGLE_HEIGHT;
		float w = INVISIBLE_NODE_RECTANGLE_WIDTH;
		float x = -(INVISIBLE_NODE_RECTANGLE_WIDTH / 2.0f);
		float y = -(INVISIBLE_NODE_RECTANGLE_HEIGHT - 2.0f);
		if (nodeElement.getTextElement().isVisible()
				&& nodeElement.getTextElement().getText().length() > 0) {
			h = nodeElement.getTextElement().getHeight()
					+ (2 * NODE_RECTANGLE_VERTICAL_PADDING);
			w = nodeElement.getTextElement().getWidth()
					+ (2 * NODE_RECTANGLE_HORIZONTAL_PADDING);
			x = nodeElement.getTextElement().getX()
					- NODE_RECTANGLE_HORIZONTAL_PADDING;
			y = nodeElement.getTextElement().getY()
					- NODE_RECTANGLE_VERTICAL_PADDING;
		}
		nodeRectElement.setHeight(h);
		nodeRectElement.setWidth(w);
		nodeRectElement.setX(x);
		nodeRectElement.setY(y);
	}

	/**
	 * Draws a multidominance branch element.
	 * 
	 * @param treeRepresentation
	 *            tree representation
	 * @param mdomBranchElement
	 *            mdom branch element
	 */
	public static void drawMdomBranch(TreeRepresentation treeRepresentation,
			MdomBranchElement mdomBranchElement) {
		MdomBranchPathElement mdomBranchPathElement = mdomBranchElement
				.getMdomBranchPathElement();
		if (mdomBranchElement.getMotherNode() != null) {
			NodeRectElement nodeRectElement = mdomBranchElement.getMotherNode()
					.getNodeRectElement();
			float x = nodeRectElement.getSVGPoint().getX()
					+ nodeRectElement.getWidth() / 2.0f;
			float y = nodeRectElement.getSVGPoint().getY()
					+ nodeRectElement.getHeight();
			if (!mdomBranchElement.getMotherNode().getTextElement().isVisible())
				y -= (nodeRectElement.getHeight() + 2.0f) / 2.0f;
			mdomBranchPathElement.setMovetoX(x);
			mdomBranchPathElement.setMovetoY(y);
		}
		if (mdomBranchElement.getDaughterNode() != null) {
			NodeRectElement nodeRectElement = mdomBranchElement
					.getDaughterNode().getNodeRectElement();
			float x = nodeRectElement.getSVGPoint().getX()
					+ nodeRectElement.getWidth() / 2.0f;
			float y = nodeRectElement.getSVGPoint().getY();
			x += -mdomBranchPathElement.getFirstCurvetoX()
					- mdomBranchPathElement.getMovetoX();
			y += -mdomBranchPathElement.getFirstCurvetoY()
					- mdomBranchPathElement.getMovetoY();
			if (!mdomBranchElement.getDaughterNode().getTextElement()
					.isVisible())
				y += (nodeRectElement.getHeight() + 2.0f) / 2.0f;
			mdomBranchPathElement.setSecondCurvetoX(x);
			mdomBranchPathElement.setSecondCurvetoY(y);
		}
		drawMdomBranchBackground(treeRepresentation, mdomBranchElement);
	}

	/**
	 * Draws the background of a multidominance branch element.
	 * 
	 * @param treeRepresentation
	 *            tree representation
	 * @param mdomBranchElement
	 *            mdom branch element
	 */
	public static void drawMdomBranchBackground(
			TreeRepresentation treeRepresentation,
			MdomBranchElement mdomBranchElement) {
		MdomBranchPathElement mdomBranchPathElement = mdomBranchElement
				.getMdomBranchPathElement();
		MdomBranchBackgroundPathElement mdomBranchBackgroundPathElement;
		mdomBranchBackgroundPathElement = mdomBranchElement
				.getMdomBranchBackgroundPathElement();
		mdomBranchBackgroundPathElement.setMoveto(mdomBranchPathElement
				.getMovetoX(), mdomBranchPathElement.getMovetoY());
		mdomBranchBackgroundPathElement.setFirstCurveto(mdomBranchPathElement
				.getFirstCurvetoCP1X(), mdomBranchPathElement
				.getFirstCurvetoCP1Y(), mdomBranchPathElement
				.getFirstCurvetoCP2X(), mdomBranchPathElement
				.getFirstCurvetoCP2Y(), mdomBranchPathElement
				.getFirstCurvetoX(), mdomBranchPathElement.getFirstCurvetoY());
		mdomBranchBackgroundPathElement
				.setSecondCurveto(mdomBranchPathElement.getSecondCurvetoCP1X(),
						mdomBranchPathElement.getSecondCurvetoCP1Y(),
						mdomBranchPathElement.getSecondCurvetoCP2X(),
						mdomBranchPathElement.getSecondCurvetoCP2Y(),
						mdomBranchPathElement.getSecondCurvetoX(),
						mdomBranchPathElement.getSecondCurvetoY());
	}
}