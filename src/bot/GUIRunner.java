package bot;

import gui.MainFrame;

import javax.swing.SwingUtilities;

/**
 * Class, containing code for starting application.
 * 
 * @author Mir4ik
 * @version 0.1 12.11.2014
 */
/*
 * TODO
 * 1. add CLI starting
 * 2. parameters resolving
 */
public class GUIRunner {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new MainFrame().setVisible(true);
			}
		});
	}
}