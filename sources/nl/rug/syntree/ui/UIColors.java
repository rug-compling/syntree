/* UIColors.java */

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

import java.awt.Color;

/**
 * This class contains color constants for the user interface.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class UIColors {
	// editor
	public static final Color EDITOR_CURSOR_STRING = new Color(70, 50, 50, 125);
	public static final Color EDITOR_MDOM_INTERACTOR_LINE = Color.BLACK;
	public static final Color EDITOR_MDOM_INTERACTOR_POINT = Color.BLACK;
	public static final Color EDITOR_MDOM_INTERACTOR_CONTROL_POINT = Color.BLUE;
	public static final Color EDITOR_PENDULUM_BOB = new Color(175, 175, 175,
			125);
	public static final Color EDITOR_PENDULUM_STRING = new Color(70, 50, 50,
			125);
	public static final Color TOOLBAR_BUTTON_ENABLED = new Color(175, 175, 175,
			125);
	public static final Color TOOLBAR_BUTTON_MOUSE_OVER = new Color(70, 50, 50,
			125);

	// tree
	public static final String TREE_BRANCH_RECTANGLE_HIGHLIGHT_FILL = "#330099";
	public static final String TREE_BRANCH_RECTANGLE_HIGHLIGHT_STROKE = "#3300FF";
	public static final String TREE_BRANCH_RECTANGLE_SELECT_FILL = "#330099";
	public static final String TREE_BRANCH_RECTANGLE_SELECT_STROKE = "#3300FF";
	public static final String TREE_BRANCH_RECTANGLE_STANDARD_FILL = "#FFFFFF";
	public static final String TREE_BRANCH_RECTANGLE_STANDARD_STROKE = "#FFFFFF";
	public static final String TREE_NODE_RECTANGLE_EDIT_FILL = "#CC0066";
	public static final String TREE_NODE_RECTANGLE_EDIT_STROKE = "#FF0066";
	public static final String TREE_NODE_RECTANGLE_HIGHLIGHT_FILL = "#006600";
	public static final String TREE_NODE_RECTANGLE_HIGHLIGHT_STROKE = "#00CC00";
	public static final String TREE_NODE_RECTANGLE_SELECT_FILL = "#009966";
	public static final String TREE_NODE_RECTANGLE_SELECT_STROKE = "#00CC66";
	public static final String TREE_NODE_RECTANGLE_STANDARD_FILL = "#FFFFFF";
	public static final String TREE_NODE_RECTANGLE_STANDARD_STROKE = "#FFFFFF";

	// user interface
	public static final Color UI_DESKTOP_BACKGROUND = new Color(50, 40, 40);
}
