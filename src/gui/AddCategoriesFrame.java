/*
 * AddCategoriesFrame.java	24.04.2015
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

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
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

import bot.io.MediaWikiFacade;
import bot.io.MediaWikiFacade.Language;

/**
 * Frame for adding categories to article with autocompletion function.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 24.04.2015
 */
public class AddCategoriesFrame extends ApplicationFrame implements
		DocumentListener, CaretListener, KeyListener {

	private static final long serialVersionUID = -4238311663986639159L;

	private static final int SUITABLE_MAX_COUNT = 10;

	private final JTextField inputCategory = new JTextField();

	private final JList<String> suitableList = new JList<String>();

	private final java.awt.List resultCategories = new java.awt.List();

	private boolean loaded = false;

	private String[] allCategoriesNames;

	/**
	 * Constructs new frame with specified content and title.
	 * 
	 * @param listener
	 *            necessary object, which will receive array of categories
	 *            names, listening for changing <code>add-categories</code>
	 *            property
	 */
	public AddCategoriesFrame(PropertyChangeListener listener) {
		super("Append categories");
		setLayout(new BorderLayout());
		add(createContent(), BorderLayout.CENTER);
		addPropertyChangeListener(listener);
		setResizable(false);
		setAlwaysOnTop(true);
		pack();
		moveToScreenCenter();
	}

	private JPanel createContent() {
		JPanel content = new JPanel(new BorderLayout());

		JPanel top = new JPanel();
		top.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
		top.add(new JLabel("Enter category name:"));
		top.add(Box.createHorizontalStrut(10));
		inputCategory.getDocument().addDocumentListener(this);
		inputCategory.addCaretListener(this);
		inputCategory.addKeyListener(this);
		inputCategory.setToolTipText("Use CTRL + SPACE key to autocomplete");
		top.add(inputCategory);
		top.add(Box.createHorizontalStrut(10));
		top.add(new JButton(getAction("add-categories-add")));

		JPanel center = new JPanel(new GridLayout(2, 1, 50, 20));
		center.add(new JScrollPane(suitableList));
		center.add(new JScrollPane(resultCategories));

		JPanel bottom = new JPanel(new GridLayout(1, 2, 200, 0));
		bottom.setBorder(BorderFactory.createEmptyBorder(20, 100, 0, 100));
		bottom.add(new JButton(getAction("add-categories-OK")));
		bottom.add(new JButton(getAction("add-categories-cancel")));

		content.add(top, BorderLayout.NORTH);
		content.add(center, BorderLayout.CENTER);
		content.add(bottom, BorderLayout.SOUTH);

		content.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
		return content;
	}

	/**
	 * Method provides code for frame actions.
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		MessagesFrame messager = new MessagesFrame(this);
		switch (evt.getPropertyName()) {
		case "add-categories-cancel":
			setVisible(false);
			break;
		case "add-categories-OK":
			if (resultCategories.getItemCount() == 0) {
				if (!messager.showQuestion("It seems that result is empty! "
						+ "Click \"Yes\" to continue anyway")) {
					return;
				}
			}
			firePropertyChange("add-categories", null,
					resultCategories.getItems());
			setVisible(false);
			break;
		case "add-categories-add":
			String input = inputCategory.getText();
			if (!input.isEmpty()) {
				String categoryName = "Категорія:" + input;
				if (searchJList(suitableList, categoryName) == -1) {
					if (!messager.showQuestion("It seems that inputed "
							+ "category name does not exists. Do you want "
							+ "to add it anyway?")) {
						return;
					}
				}
				resultCategories.add(categoryName);
			}
			break;
		}
	}

	/**
	 * Returns array, initialized by actions objects.
	 */
	@Override
	protected AbstractAction[] createActions() {
		List<AbstractAction> actions = new ArrayList<AbstractAction>();
		actions.add(new Action(this, "Cancel", "add-categories-cancel",
				"Cancel all changes", "", "res\\cancel_big.png", 0));
		actions.add(new Action(this, "OK", "add-categories-OK",
				"Apply all changes", "", "res\\ok_big.png", 0));
		actions.add(new Action(this, "", "add-categories-add", "Add category",
				"", "res\\add_small.png", 0));
		return actions.toArray(new AbstractAction[actions.size()]);
	}

	private DefaultListModel<String> createListModel(String[] src) {
		DefaultListModel<String> result = new DefaultListModel<String>();
		for (String s : src) {
			result.addElement(s);
		}
		return result;
	}

	private int searchJList(JList<String> list, String str) {
		ListModel<String> model = list.getModel();
		for (int i = 0; i < model.getSize(); i++) {
			if (model.getElementAt(i).toString().equals(str)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Creates array of first {@link #SUITABLE_MAX_COUNT 10} categories names,
	 * starting with specified preffix.
	 * 
	 * @param preffix
	 *            preffix
	 * @return 10 categories names, stored in array
	 */
	private String[] createSuitable(String preffix) {
		String[] suitable = new String[SUITABLE_MAX_COUNT];
		for (int i = 0, j = 0; i < allCategoriesNames.length; i++) {
			if (j == SUITABLE_MAX_COUNT) {
				break;
			}
			if (allCategoriesNames[i].startsWith("Категорія:" + preffix)) {
				suitable[j] = allCategoriesNames[i];
				j++;
			}
		}
		return suitable;
	}

	/**
	 * Method is invoked when user inputs text into {@link #inputCategory input
	 * text field}. After receiving this signal, method checks if data for
	 * completion is loaded. Finally, {@link #suitableList list} with suitable
	 * categories names update it's data.
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
			try {
				MediaWikiFacade.setLanguage(Language.UKRAINIAN);
				allCategoriesNames = MediaWikiFacade
						.getCategoriesStartingWith(enteredText);
			} catch (IOException ioe) {
				new MessagesFrame(this).showError(ioe.getMessage());
			}
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		}
		suitableList.setModel(createListModel(createSuitable(enteredText)));
	}

	/**
	 * Method is invoked when user removes text from {@link #inputCategory input
	 * text field}. After receiving this signal, method checks if input text
	 * field is empty. If yes, it means, that data for completion must be
	 * update. Otherwise, {@link #suitableList list} with suitable categories
	 * names update it's data.
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
			suitableList.setModel(createListModel(createSuitable(enteredText)));
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
		int textLength = inputCategory.getText().length();
		if (inputCategory.getCaretPosition() != textLength) {
			inputCategory.setCaretPosition(textLength);
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
		ListModel<String> model = suitableList.getModel();
		boolean isCtrlSpace = KeyEvent.VK_SPACE == e.getKeyCode()
				&& e.isControlDown();
		if (isCtrlSpace && model.getSize() > 0) {
			String firstSuitable = model.getElementAt(0);
			firstSuitable = firstSuitable.substring("Категорія:".length(),
					firstSuitable.length());
			// do not use setText - it removes all test first!
			Document doc = inputCategory.getDocument();
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