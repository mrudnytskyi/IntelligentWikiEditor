package intelligent.wiki.editor.gui;
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

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import intelligent.wiki.editor.utils.AutoCompletePanel;
import intelligent.wiki.editor.utils.AutoCompleteSource;
import intelligent.wiki.editor.bot.compiler.AST.CategoryDeclaration;
import intelligent.wiki.editor.bot.io.MediaWikiFacade;
import intelligent.wiki.editor.bot.io.MediaWikiFacade.Language;

/**
 * Frame for adding categories to article with autocompletion function.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 24.04.2015
 */
public class AddCategoriesFrame extends ApplicationFrame {

	private class CategorySource implements AutoCompleteSource {

		@Override
		public String[] getSource(String... params) {
			MediaWikiFacade.setLanguage(Language.UKRAINIAN);
			String[] result = new String[] {};
			try {
				String[] categories = MediaWikiFacade
						.getCategoriesStartingWith(params[0]);
				result = new String[categories.length];
				for (int i = 0; i < categories.length; i++) {
					result[i] = categories[i].substring(MediaWikiFacade
							.getLanguage().getCategoryPreffix().length(),
							categories[i].length());
				}
			} catch (IOException e) {
				new MessagesFrame(AddCategoriesFrame.this).showError(e
						.getMessage());
			}
			return result;
		}

	}

	private static final long serialVersionUID = -4238311663986639159L;

	private final AutoCompletePanel autoComplete = new AutoCompletePanel(
			new CategorySource(), "Enter category name:",
			getAction("add-categories-add"));

	private final java.awt.List resultCategories = new java.awt.List();

	/**
	 * Constructs new frame with specified content and title.
	 * 
	 * @param listener
	 *            necessary object, which will receive array of categories
	 *            objects, listening for changing <code>add-categories</code>
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
		autoComplete.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

		JPanel buttons = new JPanel(new GridLayout(1, 2, 200, 0));
		buttons.setBorder(BorderFactory.createEmptyBorder(20, 100, 0, 100));
		buttons.add(new JButton(getAction("add-categories-OK")));
		buttons.add(new JButton(getAction("add-categories-cancel")));

		content.add(autoComplete, BorderLayout.NORTH);
		content.add(new JScrollPane(resultCategories), BorderLayout.CENTER);
		content.add(buttons, BorderLayout.SOUTH);

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
			CategoryDeclaration[] result = new CategoryDeclaration[resultCategories
					.getItemCount()];
			for (int i = 0; i < result.length; i++) {
				result[i] = new CategoryDeclaration(
						resultCategories.getItems()[i]);
			}
			firePropertyChange("add-categories", null, result);
			setVisible(false);
			break;
		case "add-categories-add":
			String input = autoComplete.getInputedText();
			if (!input.isEmpty()) {
				if (autoComplete.searchStringInProposed(input) == -1) {
					if (!messager.showQuestion("It seems that inputed "
							+ "category name does not exists. Do you want "
							+ "to add it anyway?")) {
						return;
					}
				}
				resultCategories.add(input);
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
}