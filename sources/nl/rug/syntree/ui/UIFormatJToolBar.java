/* UIFormatJToolBar.java */

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

import nl.rug.syntree.tree.component.TextElement;

/**
 * This class represents a formatting tool bar for the user interface.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class UIFormatJToolBar extends JToolBar {
	/**
	 * Serial version identifier constant
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Font face combo box
	 */
	protected UIFontFaceJComboBox uiFontFaceJComboBox;

	/**
	 * Font size combo box
	 */
	protected UIFontSizeJComboBox uiFontSizeJComboBox;

	/**
	 * Font face color chooser
	 */
	protected UIColorChooserJButton uiFontFaceColorChooser;

	/**
	 * Format bold button
	 */
	protected UIToolBarJButton formatBoldButton;

	/**
	 * Format italic button
	 */
	protected UIToolBarJButton formatItalicButton;

	/**
	 * Format strikethrough button
	 */
	protected UIToolBarJButton formatStrikethroughButton;

	/**
	 * Format underline button
	 */
	protected UIToolBarJButton formatUnderlineButton;

	/**
	 * Format subscript button
	 */
	protected UIToolBarJButton formatSubscriptButton;

	/**
	 * Format superscript button
	 */
	protected UIToolBarJButton formatSuperscriptButton;

	/**
	 * Format smallcaps button
	 */
	protected UIToolBarJButton formatSmallcapsButton;

	/**
	 * Format left alignment button
	 */
	protected UIToolBarJButton formatLeftAlignmentButton;

	/**
	 * Format center alignment button
	 */
	protected UIToolBarJButton formatCenterAlignmentButton;

	/**
	 * Format right alignment button
	 */
	protected UIToolBarJButton formatRightAlignmentButton;

	/**
	 * Constructs a formatting tool bar for the user interface.
	 * 
	 * @param uiMainJFrame
	 *            reference to the user interface main frame
	 */
	public UIFormatJToolBar(UIMainJFrame uiMainJFrame) {
		super();
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));

		uiFontFaceJComboBox = new UIFontFaceJComboBox();
		this.add(uiFontFaceJComboBox);
		uiFontSizeJComboBox = new UIFontSizeJComboBox();
		this.add(uiFontSizeJComboBox);
		uiFontFaceColorChooser = new UIColorChooserJButton("Font face color");
		this.add(uiFontFaceColorChooser);

		// format bold
		formatBoldButton = new UIToolBarJButton(UIIcons.FORMAT_BOLD, "Bold",
				new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						if (formatBoldButton.getIsEnabled()) {
							formatBoldButton.setIsEnabled(false);
						} else {
							formatBoldButton.setIsEnabled(true);
						}
					}
				}, true);
		this.add(formatBoldButton);

		// format italic
		formatItalicButton = new UIToolBarJButton(UIIcons.FORMAT_ITALIC,
				"Italic", new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						if (formatItalicButton.getIsEnabled()) {
							formatItalicButton.setIsEnabled(false);
						} else {
							formatItalicButton.setIsEnabled(true);
						}
					}
				}, true);
		this.add(formatItalicButton);

		// format strikethrough
		formatStrikethroughButton = new UIToolBarJButton(
				UIIcons.FORMAT_STRIKETHROUGH, "Strikethrough",
				new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						if (formatStrikethroughButton.getIsEnabled()) {
							formatStrikethroughButton.setIsEnabled(false);
						} else {
							formatStrikethroughButton.setIsEnabled(true);
						}
					}
				}, true);
		this.add(formatStrikethroughButton);

		// format underline
		formatUnderlineButton = new UIToolBarJButton(UIIcons.FORMAT_UNDERLINE,
				"Underline", new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						if (formatUnderlineButton.getIsEnabled()) {
							formatUnderlineButton.setIsEnabled(false);
						} else {
							formatUnderlineButton.setIsEnabled(true);
						}
					}
				}, true);
		this.add(formatUnderlineButton);

		// format subscript
		formatSubscriptButton = new UIToolBarJButton(UIIcons.FORMAT_SUBSCRIPT,
				"Subscript", new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						setSubscriptFormatting(!formatSubscriptButton
								.getIsEnabled());
					}
				}, true);
		this.add(formatSubscriptButton);

		// format superscript
		formatSuperscriptButton = new UIToolBarJButton(
				UIIcons.FORMAT_SUPERSCRIPT, "Superscript",
				new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						setSuperscriptFormatting(!formatSuperscriptButton
								.getIsEnabled());
					}
				}, true);
		this.add(formatSuperscriptButton);

		// format smallcaps
		formatSmallcapsButton = new UIToolBarJButton(UIIcons.FORMAT_SMALLCAPS,
				"Smallcaps", new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						if (formatSmallcapsButton.getIsEnabled()) {
							formatSmallcapsButton.setIsEnabled(false);
						} else {
							formatSmallcapsButton.setIsEnabled(true);
						}
					}
				}, true);
		this.add(formatSmallcapsButton);

		// format left alignment
		formatLeftAlignmentButton = new UIToolBarJButton(
				UIIcons.FORMAT_LEFT_ALIGNMENT, "Left alignment",
				new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						setLeftAlignment();
					}
				}, true);
		this.add(formatLeftAlignmentButton);

		// format center alignment
		formatCenterAlignmentButton = new UIToolBarJButton(
				UIIcons.FORMAT_CENTER_ALIGNMENT, "Center alignment",
				new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						setCenterAlignment();
					}
				}, true);
		this.add(formatCenterAlignmentButton);

		// format right alignment
		formatRightAlignmentButton = new UIToolBarJButton(
				UIIcons.FORMAT_RIGHT_ALIGNMENT, "Right alignment",
				new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						setRightAlignment();
					}
				}, true);
		this.add(formatRightAlignmentButton);

		this.setCenterAlignment();
	}

	/**
	 * Returns the alignment property that represents the current formatting
	 * configuration.
	 * 
	 * @return alignment
	 */
	public String getAlignment() {
		String alignment = TextElement.ALIGN_CENTER;
		if (formatLeftAlignmentButton.getIsEnabled())
			alignment = TextElement.ALIGN_LEFT;
		if (formatCenterAlignmentButton.getIsEnabled())
			alignment = TextElement.ALIGN_CENTER;
		if (formatRightAlignmentButton.getIsEnabled())
			alignment = TextElement.ALIGN_RIGHT;
		return alignment;
	}

	/**
	 * Set the current formatting configuration to the specified alignment
	 * propery.
	 * 
	 * @param alignment
	 *            alignment
	 */
	public void setAlignment(String alignment) {
		if (alignment.compareTo(TextElement.ALIGN_LEFT) == 0)
			this.setLeftAlignment();
		if (alignment.compareTo(TextElement.ALIGN_CENTER) == 0)
			this.setCenterAlignment();
		if (alignment.compareTo(TextElement.ALIGN_RIGHT) == 0)
			this.setRightAlignment();
	}

	/**
	 * Sets the current formatting configuration to left alignment.
	 */
	public void setLeftAlignment() {
		formatLeftAlignmentButton.setIsEnabled(true);
		formatCenterAlignmentButton.setIsEnabled(false);
		formatRightAlignmentButton.setIsEnabled(false);
	}

	/**
	 * Sets the current formatting configuration to center alignment.
	 */
	public void setCenterAlignment() {
		formatLeftAlignmentButton.setIsEnabled(false);
		formatCenterAlignmentButton.setIsEnabled(true);
		formatRightAlignmentButton.setIsEnabled(false);
	}

	/**
	 * Sets the current formatting configuration to right alignment.
	 */
	public void setRightAlignment() {
		formatLeftAlignmentButton.setIsEnabled(false);
		formatCenterAlignmentButton.setIsEnabled(false);
		formatRightAlignmentButton.setIsEnabled(true);
	}

	/**
	 * Return svg style properties that represents the current formatting
	 * configuration.
	 * 
	 * @return svg style properties
	 */
	public String getFontFormatting() {
		String styleProperties;

		// font-family
		styleProperties = "font-family:'"
				+ (String) uiFontFaceJComboBox.getSelectedItem() + "';";

		// font-size
		styleProperties += "font-size:"
				+ (String) uiFontSizeJComboBox.getSelectedItem() + "pt;";

		// fill
		styleProperties += "fill:"
				+ (String) uiFontFaceColorChooser.getHexadecimalColor() + ";";

		// font-weight
		styleProperties += (formatBoldButton.getIsEnabled()) ? "font-weight:bold;"
				: "font-weight:normal;";

		// font-style
		styleProperties += (formatItalicButton.getIsEnabled()) ? "font-style:italic;"
				: "font-style:normal;";

		// font-variant
		styleProperties += (formatSmallcapsButton.getIsEnabled()) ? "font-variant:small-caps;"
				: "font-variant:normal;";

		// text-decoration
		if (formatStrikethroughButton.getIsEnabled()
				&& formatUnderlineButton.getIsEnabled())
			styleProperties += "text-decoration:line-through underline;";
		if (formatStrikethroughButton.getIsEnabled()
				&& !formatUnderlineButton.getIsEnabled())
			styleProperties += "text-decoration:line-through;";
		if (!formatStrikethroughButton.getIsEnabled()
				&& formatUnderlineButton.getIsEnabled())
			styleProperties += "text-decoration:underline;";

		// baseline-shift
		if (formatSubscriptButton.getIsEnabled()
				|| formatSuperscriptButton.getIsEnabled())
			styleProperties += formatSubscriptButton.getIsEnabled() ? "baseline-shift:sub;"
					: "baseline-shift:super;";

		return styleProperties;
	}

	/**
	 * Set the current style formatting configuration to the specified style
	 * properties.
	 * 
	 * @param styleProperties
	 *            svg style properties.
	 */
	public void setFontFormatting(String styleProperties) {
		int i, j;
		String styleProperty;

		// font-family
		styleProperty = "font-family:";
		i = styleProperties.indexOf(styleProperty) + styleProperty.length() + 1;
		j = styleProperties.indexOf(";", i) - 1;
		uiFontFaceJComboBox.setSelectedItem(styleProperties.substring(i, j));

		// font-size
		styleProperty = "font-size:";
		i = styleProperties.indexOf(styleProperty) + styleProperty.length();
		j = styleProperties.indexOf("pt;", i);
		uiFontSizeJComboBox.setSelectedItem(styleProperties.substring(i, j));

		// fill
		styleProperty = "fill:";
		i = styleProperties.indexOf(styleProperty) + styleProperty.length();
		j = styleProperties.indexOf(";", i);
		uiFontFaceColorChooser.setHexadecimalColor(styleProperties.substring(i,
				j));

		// font-weight
		styleProperty = "font-weight:";
		i = styleProperties.indexOf(styleProperty) + styleProperty.length();
		j = styleProperties.indexOf(";", i);
		if (styleProperties.substring(i, j).compareTo("normal") == 0)
			formatBoldButton.setEnabled(false);
		else
			formatBoldButton.setEnabled(true);

		// font-style
		styleProperty = "font-style:";
		i = styleProperties.indexOf(styleProperty) + styleProperty.length();
		j = styleProperties.indexOf(";", i);
		if (styleProperties.substring(i, j).compareTo("normal") == 0)
			formatItalicButton.setEnabled(false);
		else
			formatItalicButton.setEnabled(true);

		// font-variant
		styleProperty = "font-variant:";
		i = styleProperties.indexOf(styleProperty) + styleProperty.length();
		j = styleProperties.indexOf(";", i);
		if (styleProperties.substring(i, j).compareTo("normal") == 0)
			formatSmallcapsButton.setEnabled(false);
		else
			formatSmallcapsButton.setEnabled(true);

		// text-decoration
		styleProperty = "text-decoration:";
		i = styleProperties.indexOf(styleProperty) + styleProperty.length();
		j = styleProperties.indexOf(";", i);
		if (styleProperties.substring(i, j).compareTo("line-through underline") == 0) {
			formatStrikethroughButton.setIsEnabled(true);
			formatUnderlineButton.setIsEnabled(true);
		}
		if (styleProperties.substring(i, j).compareTo("line-through") == 0) {
			formatStrikethroughButton.setIsEnabled(true);
			formatUnderlineButton.setIsEnabled(false);
		}
		if (styleProperties.substring(i, j).compareTo("underline") == 0) {
			formatStrikethroughButton.setIsEnabled(false);
			formatUnderlineButton.setIsEnabled(true);
		}

		// baseline-shift
		styleProperty = "baseline-shift:";
		i = styleProperties.indexOf(styleProperty) + styleProperty.length();
		j = styleProperties.indexOf(";", i);
		if (styleProperties.substring(i, j).compareTo("sub") == 0) {
			formatSubscriptButton.setIsEnabled(true);
			formatSuperscriptButton.setIsEnabled(false);
		}
		if (styleProperties.substring(i, j).compareTo("super") == 0) {
			formatSubscriptButton.setIsEnabled(false);
			formatSuperscriptButton.setIsEnabled(true);
		}
	}

	/**
	 * Sets the current formatting configuration to subscript formatting if the
	 * specified boolean is true.
	 * 
	 * @param formatSubscript
	 *            boolean
	 */
	public void setSubscriptFormatting(boolean formatSubscript) {
		if (formatSubscript) {
			formatSubscriptButton.setIsEnabled(true);
			formatSuperscriptButton.setIsEnabled(false);
		} else {
			formatSubscriptButton.setIsEnabled(false);
		}
	}

	/**
	 * Sets the current formatting configuration to superscript formatting if
	 * the specified boolean is true.
	 * 
	 * @param formatSuperscript
	 *            boolean
	 */
	public void setSuperscriptFormatting(boolean formatSuperscript) {
		if (formatSuperscript) {
			formatSubscriptButton.setIsEnabled(false);
			formatSuperscriptButton.setIsEnabled(true);
		} else {
			formatSuperscriptButton.setIsEnabled(false);
		}
	}
}
