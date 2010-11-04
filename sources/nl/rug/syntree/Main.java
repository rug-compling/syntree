/* Main.java */

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

package nl.rug.syntree;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import nl.rug.syntree.ui.UIMainJFrame;

/**
 * The Main class contains the main() method and is therefore the entry point of
 * the program. This class is merely the entry and exit point and provides no
 * further functionality.
 * 
 * @author <a href="mailto:harm.brouwer[at]rug.nl">Harm Brouwer</a>
 * @version 1.0
 * @since 1.0
 */
public class Main {
	/**
	 * Application identifier constant
	 */
	public static final String APPLICATION_IDENTIFIER = "syntree 0.5a";

	/**
	 * Exit success constant
	 */
	public static final int EXIT_SUCCESS = 0;

	/**
	 * Exit failure constant
	 */
	public static final int EXIT_FAILURE = -1;

	/**
	 * Constructs the user interface and schedules it for later invocation.
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String args[]) {
		if (args.length > 0) {
			if (args[0].compareTo("--oslf") == 0) {
				System.out
						.println("[main()] attempting to set OS-specific look and feel\n");
				try {
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new UIMainJFrame();
			}
		});
	}

	/**
	 * Clean program termination point.
	 * 
	 * @param status
	 *            exit status
	 */
	public static void exit(int status) {
		System.exit(status);
	}
}