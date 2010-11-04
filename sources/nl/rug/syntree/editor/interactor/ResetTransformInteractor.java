/* ResetTransformInteractor.java */

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

package nl.rug.syntree.editor.interactor;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

import org.apache.batik.swing.gvt.AbstractResetTransformInteractor;

/**
 * This class represents a scalable vector graphics canvas reset transform
 * interactor.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class ResetTransformInteractor extends AbstractResetTransformInteractor {
	/**
	 * Constructs a new reset transform interactor.
	 */
	public ResetTransformInteractor() {
		super();
	}

	/**
	 * Verifies whether the specified event will start the interaction.
	 * 
	 * Binding: BUTTON2 + CTRL key
	 * 
	 * @param ie
	 *            input event
	 */
	public boolean startInteraction(InputEvent ie) {
		return ie.getID() == MouseEvent.MOUSE_PRESSED
				&& (ie.getModifiers() & InputEvent.BUTTON2_MASK) != 0
				&& (ie.getModifiers() & InputEvent.CTRL_MASK) != 0;
	}
}
