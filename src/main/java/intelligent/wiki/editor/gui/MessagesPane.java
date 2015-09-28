package intelligent.wiki.editor.gui;
/*
 * MessagesPane.java	06.04.2015
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

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * MessagesPane is a not-editable component, created to show different types of
 * text messages: errors, warnings and information messages. Every type is
 * highlighted using color: for errors it is red, for warnings - blue and only
 * information is not highlighted and painted using black color.
 * <p>
 * Every message has it's own time, which is displayed also.
 * <p>
 * Note, that this component use {@link JTextPane} for displaying messages and
 * is subclass of {@link JPanel}.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.2 06.04.2014
 */
//TODO: decide if refactore as console
@Deprecated
public class MessagesPane extends JPanel {

	private static final long serialVersionUID = -7051388875654936594L;
	
	private final JTextPane pane = new JTextPane();
	
	private final AttributeSet errorHighliter = 
		StyleContext.getDefaultStyleContext().addAttribute(
		SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.RED);
	
	private final AttributeSet warnHighliter = 
		StyleContext.getDefaultStyleContext().addAttribute(
		SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.BLUE);
	
	/**
	 * Constructs empty MessagesPane.
	 */
	public MessagesPane() {
		setLayout(new BorderLayout());
		add(new JScrollPane(pane), BorderLayout.CENTER);
		pane.setEditable(false);
	}
	
	/**
	 * Appends information message to pane, painted using black color.
	 * 
	 * @param text	message
	 */
	public void info(String text) {
		append(text, null);
	}
	
	/**
	 * Appends error message to pane, painted using red color.
	 *  
	 * @param text	message
	 */
	public void error(String text) {
		append(text, errorHighliter);
	}
	
	/**
	 * Appends warning message to pane, painted using blue color.
	 * 
	 * @param text	message
	 */
	public void warn(String text) {
		append(text, warnHighliter);
	}
	
	private void append(String text, AttributeSet highliter) {
		StringBuilder sb = new StringBuilder();
		String pattern = "d.MM.YYYY H:m:s";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		sb.append(formatter.format(LocalDateTime.now()));
		sb.append(" ");
		sb.append(text);
		sb.append(System.getProperty("line.separator"));
		String dateText = sb.toString();
		Document doc = pane.getStyledDocument();
		try {
			doc.insertString(doc.getLength(), dateText, highliter);
		} catch (BadLocationException ignoredException) {
			// can not catch 
		}
	}
}