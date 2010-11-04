/* TextEditor.java */

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

package nl.rug.syntree.editor.text;

import java.util.Vector;

import nl.rug.syntree.tree.TreeRepresentation;
import nl.rug.syntree.tree.component.NodeElement;
import nl.rug.syntree.tree.component.TSpanElement;
import nl.rug.syntree.tree.component.TextElement;

/**
 * This class represents text editor functionality for the tree representation
 * editor.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class TextEditor {
	/**
	 * Whitespace character constant
	 */
	public static final char WHITESPACE_CHARACTER = '_';

	/**
	 * Adds the specifies key character at the specified position to the
	 * specified node element.
	 * 
	 * @param treeRepresentation
	 *            tree representation
	 * @param nodeElement
	 *            node element
	 * @param keyChar
	 *            key character
	 * @param position
	 *            position
	 * @return requires redraw
	 */
	public static boolean addChar(TreeRepresentation treeRepresentation,
			NodeElement nodeElement, char keyChar, int position) {
		TextElement textElement = nodeElement.getTextElement();

		// obtain formatting style
		String formatStyle = treeRepresentation.getTreeEditorBridge()
				.getUIFormatJToolBar().getFontFormatting();

		// modify formatting and key character for whitespaces
		if (keyChar == ' ') {
			int i = formatStyle.indexOf("fill:");
			String prefix = formatStyle.substring(0, i);
			String suffix = formatStyle.substring(formatStyle.indexOf(";", i),
					formatStyle.length());
			formatStyle = prefix + "fill:none" + suffix;
			keyChar = WHITESPACE_CHARACTER;
		}

		// obtain text span vector
		Vector<TSpanElement> tspanVector = textElement.getTSpanVector();

		// add key character to the relevant tspan element
		int length = 0;
		for (int i = 0; i < tspanVector.size(); i++) {
			TSpanElement tspanElement = tspanVector.elementAt(i);
			length += tspanElement.getText().length();

			// relevant position resides within current tspan element
			if (position <= length) {
				// compute position within tspan element
				int insertPosition = position
						- (length - tspanElement.getText().length());

				// get tspan element properties
				String tspanStyle = tspanElement.getStyle();
				int tspanLine = tspanElement.getLine();
				String tspanText = tspanElement.getText();

				// split text in prefix and suffix
				String prefix = tspanText.substring(0, insertPosition);
				String suffix = tspanText.substring(insertPosition, tspanText
						.length());

				// toolbar style either equals or differs from tspan
				if (tspanStyle.compareTo(formatStyle) == 0) {
					// simply insert key character
					tspanElement.setText(prefix + Character.toString(keyChar)
							+ suffix);
				} else {
					if (tspanElement.getType() != TSpanElement.HEAD_TYPE) {
						// split tspan element among suffix and prefix
						tspanElement.setText(suffix);
						textElement.createTextTSpanElement(tspanElement,
								Character.toString(keyChar), formatStyle,
								tspanLine);
						int tspanIndex = tspanVector.indexOf(tspanElement);
						textElement.createTextTSpanElement(tspanVector
								.elementAt(tspanIndex - 1), prefix, tspanStyle,
								tspanLine);
					} else {
						// create a new tspan element
						int tspanIndex = tspanVector.indexOf(tspanElement);
						textElement.createTextTSpanElement(tspanVector
								.elementAt(tspanIndex + 1), Character
								.toString(keyChar), formatStyle, tspanLine);
					}

				}

				// taint tspan element
				tspanElement.setTaintMode(TSpanElement.TAINTED);
				i = tspanVector.size();
			}
		}

		// taint text element
		textElement.setTaintMode(TextElement.TAINTED);

		// taint all daughters
		for (int i = 0; i < nodeElement.getDaughterVector().size(); i++)
			nodeElement.getDaughterVector().elementAt(i).setTaintMode(
					NodeElement.TAINTED);	

		// taint node and all ancestors
		NodeElement iteratorElement = nodeElement;
		while (iteratorElement != null) {
			iteratorElement.setTaintMode(NodeElement.TAINTED);
			iteratorElement = iteratorElement.getMother();
		}

		return false;
	}

	/**
	 * Removes the specified key character from the specified node element which
	 * resides in the specifed tree representation.
	 * 
	 * @param treeRepresentation
	 *            tree representation
	 * @param nodeElement
	 *            node element
	 * @param position
	 *            position
	 * @return requires redraw
	 */
	public static boolean removeChar(TreeRepresentation treeRepresentation,
			NodeElement nodeElement, int position) {
		TextElement textElement = nodeElement.getTextElement();
		boolean requiresRedraw = false;

		// obtain tspan element vector
		Vector<TSpanElement> tspanVector = textElement.getTSpanVector();

		// determine last line
		int lastLine = tspanVector.lastElement().getLine();

		// remove character from relevant tspan element
		int length = 0;
		for (int i = 0; i < tspanVector.size(); i++) {
			TSpanElement tspanElement = tspanVector.elementAt(i);
			length += tspanElement.getText().length();

			// relevant position resides within tspan element
			if ((tspanElement.getType() != TSpanElement.HEAD_TYPE)
					&& (tspanElement.getType() != TSpanElement.TAIL_TYPE)) {
				if (position < length) {
					int removePosition = position
							- (length - tspanElement.getText().length());

					// we cannot remove negative positions
					if (removePosition >= 0) {
						String text = tspanElement.getText();

						// remove character
						String prefix = text.substring(0, removePosition);
						String suffix = "";
						if (text.length() > removePosition + 1)
							suffix = text.substring(removePosition + 1, text
									.length());
						tspanElement.setText(prefix + suffix);

						// remove empty tspan element
						if (tspanElement.getText().length() == 0)
							textElement.removeTSpanElement(tspanElement);
					}

					// taint tspan element
					tspanElement.setTaintMode(TSpanElement.TAINTED);
					i = tspanVector.size();
				}
			}
		}

		// taint text element
		textElement.setTaintMode(TextElement.TAINTED);

		// taint the appropriate nodes
		if (tspanVector.lastElement().getLine() < lastLine) {
			// taint all nodes in tree
			Vector<NodeElement> nodeVector = nodeElement.getTreeElement()
					.getNodeVector();
			for (int i = 0; i < nodeVector.size(); i++)
				nodeVector.elementAt(i).setTaintMode(NodeElement.TAINTED);
			requiresRedraw = true;
		} else {
			// taint all ancestors
			NodeElement iteratorElement = nodeElement;
			while (iteratorElement != null) {
				iteratorElement.setTaintMode(NodeElement.TAINTED);
				iteratorElement = iteratorElement.getMother();
			}
		}

		return requiresRedraw;
	}

	/**
	 * Adds a line break at the specified position in the specified node element
	 * which resides in specified tree representation.
	 * 
	 * @param treeRepresentation
	 *            tree representation
	 * @param nodeElement
	 *            node element
	 * @param position
	 *            position
	 * @return requires redraw
	 */
	public static boolean addLine(TreeRepresentation treeRepresentation,
			NodeElement nodeElement, int position) {
		TextElement textElement = nodeElement.getTextElement();

		// obtain text span vector
		Vector<TSpanElement> tspanVector = textElement.getTSpanVector();

		// find relevant tspan element
		int length = 0;
		for (int i = 0; i < tspanVector.size(); i++) {
			TSpanElement tspanElement = tspanVector.elementAt(i);
			length += tspanElement.getText().length();

			// relevant position resides within current tspan element
			if (position < length) {
				int line = tspanElement.getLine() + 1;
				int addPosition = position
						- (length - tspanElement.getText().length());

				// split text in prefix and suffix
				String text = tspanElement.getText();
				String prefix = text.substring(0, addPosition);
				String suffix = "";
				if (text.length() > addPosition)
					suffix = text.substring(addPosition, text.length());

				// set text of current element to prefix
				if (tspanElement.getType() != TSpanElement.TAIL_TYPE)
					tspanElement.setText(prefix);

				// determine whether we deal with a multline node
				TSpanElement nextHeadTSpanElement = null;
				for (int x = i; x < tspanVector.size(); x++) {
					if (tspanVector.elementAt(x).getType() == TSpanElement.HEAD_TYPE)
						nextHeadTSpanElement = tspanVector.elementAt(x);
				}

				// create new head, text, and tail tspan element
				textElement.createHeadTSpanElementBefore(nextHeadTSpanElement,
						line);
				if (tspanElement.getType() != TSpanElement.TAIL_TYPE)
					if (suffix.length() > 0)
						textElement.createTextTSpanElement(
								nextHeadTSpanElement, suffix, tspanElement
										.getStyle(), line);
				TSpanElement newTailTSpanElement = textElement
						.createTailTSpanElementBefore(nextHeadTSpanElement,
								line);

				// move other relevant tspan elements to the new line
				TSpanElement textTSpanElement = tspanVector.elementAt(i + 1);
				while ((textTSpanElement.getType() != TSpanElement.HEAD_TYPE)
						&& (textTSpanElement.getType() != TSpanElement.TAIL_TYPE)) {
					textElement.createTextTSpanElement(newTailTSpanElement,
							textTSpanElement.getText(), textTSpanElement
									.getStyle(), line);
					textElement.removeTSpanElement(textTSpanElement);
					textTSpanElement = tspanVector.elementAt(i + 1);
				}

				// update line numbers in case of multiline node
				if (nextHeadTSpanElement != null) {
					for (int x = tspanVector.indexOf(nextHeadTSpanElement); x < tspanVector
							.size(); x++) {
						tspanVector.elementAt(x).setLine(
								tspanVector.elementAt(x).getLine() + 1);
					}
				}

				// taint all tspan element
				for (int x = 0; x < tspanVector.size(); x++) {
					tspanVector.elementAt(x).setTaintMode(TSpanElement.TAINTED);
				}
				i = tspanVector.size();
			}
		}

		// linearize tspan vector
		textElement.linearizeTSpanVector();

		// taint text element
		textElement.setTaintMode(TextElement.TAINTED);
		nodeElement.setTaintMode(NodeElement.TAINTED);

		// taint all nodes in tree
		/*
		Vector<NodeElement> nodeVector = nodeElement.getTreeElement().getNodeVector();		
		for (int i = 0; i < nodeVector.size(); i++) {
			nodeVector.elementAt(i).setTaintMode(NodeElement.TAINTED);
		}
		*/

		return true;
	}

	/**
	 * Removes a line break which covers the specified position in the node
	 * element which resides in the specified tree representation.
	 * 
	 * @param treeRepresentation
	 *            tree representation
	 * @param nodeElement
	 *            node element
	 * @param position
	 *            position
	 * @return requires redraw
	 */
	public static boolean removeLine(TreeRepresentation treeRepresentation,
			NodeElement nodeElement, int position) {
		TextElement textElement = nodeElement.getTextElement();

		// obtain text span vector
		Vector<TSpanElement> tspanVector = textElement.getTSpanVector();

		// find relevant tspan element
		int length = 0;
		for (int i = 0; i < tspanVector.size(); i++) {
			TSpanElement tspanElement = tspanVector.elementAt(i);
			length += tspanElement.getText().length();

			// relevant position resided within current tspan element
			if (position < length) {
				int line = tspanElement.getLine();
				if (line != 0) {
					for (int x = 0; x < tspanVector.size(); x++) {
						// remove all element from the relevant line
						if (tspanVector.elementAt(x).getLine() == line) {
							textElement.removeTSpanElement(tspanVector
									.elementAt(x));
							x = 0;
						}
					}
				}
				i = tspanVector.size();
				
				// linearize tspan vector
				textElement.linearizeTSpanVector();

				// taint text element
				textElement.setTaintMode(TextElement.TAINTED);
				nodeElement.setTaintMode(NodeElement.TAINTED);
			}			
		}
		return true;
	}
}
