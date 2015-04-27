/*
 * AutocompletePanel.java	25.04.2015
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
package utils;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * Graphical component, used to make autocompleting function. Note, that it is
 * based on {@link JPanel} and uses {@link JTextField} to enter text data and
 * {@link JList} to show proposed text items.
 * <p>
 * To start autocompleting, enter some data into text field and, if list is not
 * empty, enter CTRL + SPACE buttons. Then first item from list will be written
 * into text field.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 25.04.2015
 * @see AutoCompleteSource
 */
public class AutoCompletePanel extends JPanel implements DocumentListener,
		CaretListener, KeyListener {

	private static final long serialVersionUID = 6566480860339877695L;

	private int proposedItemsMaxCount = 10;

	private final JTextField input = new JTextField();

	private final JList<String> proposedItems = new JList<String>();

	private final AutoCompleteSource source;

	private boolean loaded = false;

	private String[] allProposedItems;

	private final String caption;

	private final Action action;

	/**
	 * Constructs new panel with specified content. Note, that
	 * <code>caption</code> and <code>action</code> parameters can store
	 * <code>null</code> value.
	 * 
	 * @param source
	 *            object, storing all items for autocompletion function
	 * @param caption
	 *            caption string, displayed near text field
	 * @param action
	 *            action for button, displayed near text field
	 */
	public AutoCompletePanel(AutoCompleteSource source, String caption,
			Action action) {

		super(new BorderLayout());
		this.source = Objects.requireNonNull(source,
				"AutoCompleteSource object null!");
		this.caption = caption;
		this.action = action;
		createContent();
	}

	/**
	 * Returns max count of proposed items. Default value is 10.
	 * 
	 * @return max count of proposed items
	 */
	public int getProposedItemsMaxCount() {
		return proposedItemsMaxCount;
	}

	/**
	 * Sets max count of proposed items. If <code>maxCount</code> is negative,
	 * no changes will be made.
	 * 
	 * @param maxCount
	 *            not negative count of proposed items
	 */
	public void setProposedItemsMaxCount(int maxCount) {
		if (maxCount > 0) {
			proposedItemsMaxCount = maxCount;
		}
	}

	/**
	 * Returns text in the input text field.
	 * 
	 * @return text from text field
	 */
	public String getInputedText() {
		return input.getText();
	}

	/**
	 * Returns real length of proposed list. Can be equal or less than value,
	 * returned by {@link #getProposedItemsLength()}.
	 * 
	 * @return length of proposed list
	 */
	public int getProposedItemsLength() {
		return proposedItems.getModel().getSize();
	}

	/**
	 * Returns index of specified string in proposed list.
	 * 
	 * @param str
	 *            string to find
	 * @return index if specified string is presented in proposed list, -1
	 *         otherwise
	 */
	public int searchStringInProposed(String str) {
		ListModel<String> model = proposedItems.getModel();
		for (int i = 0; i < model.getSize(); i++) {
			if (model.getElementAt(i).toString().equals(str)) {
				return i;
			}
		}
		return -1;
	}

	private void createContent() {
		input.getDocument().addDocumentListener(this);
		input.addCaretListener(this);
		input.addKeyListener(this);
		input.setToolTipText("Use CTRL + SPACE key to autocomplete");

		JPanel top = new JPanel();
		top.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));

		if (caption != null) {
			top.add(new JLabel(caption));
			top.add(Box.createHorizontalStrut(10));
		}
		top.add(input);
		if (action != null) {
			top.add(Box.createHorizontalStrut(10));
			top.add(new JButton(action));
		}

		add(top, BorderLayout.NORTH);
		add(new JScrollPane(proposedItems), BorderLayout.SOUTH);
	}

	private DefaultListModel<String> createListModel(String[] src) {
		DefaultListModel<String> result = new DefaultListModel<String>();
		for (String s : src) {
			result.addElement(s);
		}
		return result;
	}

	private String[] createProposedItems(String preffix) {
		String[] suitable = new String[proposedItemsMaxCount];
		for (int i = 0, j = 0; i < allProposedItems.length; i++) {
			if (j == proposedItemsMaxCount) {
				break;
			}
			if (allProposedItems[i].startsWith(preffix)) {
				suitable[j] = allProposedItems[i];
				j++;
			}
		}
		return suitable;
	}

	/**
	 * Method is invoked when user inputs text into {@link #input text field}.
	 * After receiving this signal, method checks if data for completion is
	 * loaded. Finally, {@link #proposedItems list} with proposed items updates
	 * it's data.
	 */
	@Override
	public void insertUpdate(DocumentEvent e) {
		Document document = e.getDocument();
		String enteredText = null;
		try {
			enteredText = document.getText(0, document.getLength());
		} catch (BadLocationException exception) {
			// can not catch it here
		}
		if (!loaded) {
			loaded = true;
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			allProposedItems = source.getSource(enteredText);
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		proposedItems
				.setModel(createListModel(createProposedItems(enteredText)));
	}

	/**
	 * Method is invoked when user removes text from {@link #input text field}.
	 * After receiving this signal, method checks if it is empty. If yes, it
	 * means, that data for completion must be update. Otherwise,
	 * {@link #proposedItems list} with proposed items updates it's data.
	 */
	@Override
	public void removeUpdate(DocumentEvent e) {
		Document document = e.getDocument();
		String enteredText = null;
		try {
			enteredText = document.getText(0, document.getLength());
		} catch (BadLocationException exception) {
			// can not catch it here
		}
		if (document.getLength() == 0) {
			loaded = false;
		} else {
			proposedItems
					.setModel(createListModel(createProposedItems(enteredText)));
		}
	}

	/**
	 * Method does nothing.
	 */
	@Override
	public void changedUpdate(DocumentEvent e) {
	}

	/**
	 * Method is invoked when caret position is updated. It is used to keep
	 * caret position always at the end of inputed text.
	 */
	@Override
	public void caretUpdate(CaretEvent e) {
		int textLength = input.getText().length();
		if (input.getCaretPosition() != textLength) {
			input.setCaretPosition(textLength);
		}
	}

	/**
	 * Method does nothing.
	 */
	@Override
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * Method is invoked when a key has been pressed. It is used to check if
	 * user entered CTRL + SPACE. If yes, then autocompletion mechanism starts.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		ListModel<String> model = proposedItems.getModel();
		boolean isCtrlSpace = KeyEvent.VK_SPACE == e.getKeyCode()
				&& e.isControlDown();
		if (isCtrlSpace && model.getSize() > 0) {
			String firstSuitable = model.getElementAt(0);
			// do not use setText - it removes all test first!
			Document doc = input.getDocument();
			int alreadyInputedLength = doc.getLength();
			try {
				doc.insertString(doc.getLength(), firstSuitable.substring(
						alreadyInputedLength, firstSuitable.length()), null);
			} catch (BadLocationException exception) {
				// can not catch
			}
		}
	}

	/**
	 * Method does nothing.
	 */
	@Override
	public void keyReleased(KeyEvent e) {
	}
}