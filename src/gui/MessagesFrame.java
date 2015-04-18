/*
 * MessagesFrame.java	08.04.2015
 * Copyright (C) 2015 Myroslav Rudnytskyi
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 */
package gui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 08.04.2015
 */
public class MessagesFrame {
	
	private JFrame parent;
	
	public MessagesFrame(JFrame parent) {
		this.parent = parent;
	}

	public void showError(String msg) {
		String path = "res\\error_big.png";
		JOptionPane.showMessageDialog(parent, msg, "Error",
				JOptionPane.ERROR_MESSAGE, new ImageIcon(path));
	}
	
	public void showInfo(String msg) {
		String path = "res\\info_big.png";
		JOptionPane.showMessageDialog(parent, msg, "Info",
				JOptionPane.INFORMATION_MESSAGE, new ImageIcon(path));
	}
	
	public boolean showQuestion(String msg) {
		String path = "res\\question_big.png";
		if (JOptionPane.showConfirmDialog(parent, msg, "Question",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
				new ImageIcon(path)) == JOptionPane.YES_OPTION) {
					return true;
		}
		return false;
	}
	
	public void showWarning(String msg) {
		String path = "res\\warning_big.png";
		JOptionPane.showMessageDialog(parent, msg, "Warning",
				JOptionPane.WARNING_MESSAGE, new ImageIcon(path));
	}
	
	public String showInput(String msg) {
		return showInput(msg, null, null);
	}
	
	public String showInput(String msg, Object[] values, Object value) {
		String path = "res\\question_big.png";
		Object o = JOptionPane.showInputDialog(parent, msg, "Input",
				JOptionPane.QUESTION_MESSAGE, new ImageIcon(path),
				values, value);
		return o == null ? null : o.toString();		
	}
}