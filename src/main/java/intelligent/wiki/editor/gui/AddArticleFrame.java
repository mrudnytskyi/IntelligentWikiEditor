package intelligent.wiki.editor.gui;
/*
 * AddArticleFrame.java	28.04.2015
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

import intelligent.wiki.editor.bot.io.MediaWikiFacade;
import intelligent.wiki.editor.bot.io.MediaWikiFacade.Language;
import intelligent.wiki.editor.utils.AutoCompletePanel;
import intelligent.wiki.editor.utils.AutoCompleteSource;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Frame for adding links to another wiki pages to article with autocompletion
 * function.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 28.04.2015
 */
@Deprecated
public class AddArticleFrame extends ApplicationFrame {

	private static final long serialVersionUID = -9035746329104764814L;
	private final AutoCompletePanel autoComplete = new AutoCompletePanel(
			new ArticleSource(), "Enter article name:");

	/**
	 * Constructs new frame with specified content and title.
	 *
	 * @param listener
	 *            necessary object, which will receive {@link String} article
	 *            name, listening for changing <code>add-article</code> property
	 */
	public AddArticleFrame(PropertyChangeListener listener) {
		super("Add article");
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

		JPanel buttons = new JPanel(new GridLayout(1, 2, 200, 0));
		buttons.setBorder(BorderFactory.createEmptyBorder(20, 100, 0, 100));
		buttons.add(new JButton(getAction("add-article-OK")));
		buttons.add(new JButton(getAction("add-article-cancel")));

		content.add(autoComplete, BorderLayout.NORTH);
		content.add(buttons, BorderLayout.SOUTH);

		content.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
		return content;
	}

	/**
	 * Method provides code for frame actions.
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		switch (evt.getPropertyName()) {
			case "add-article-cancel":
				setVisible(false);
				break;
			case "add-article-OK":
				String input = autoComplete.getInputedText();
				MessagesFrame messager = new MessagesFrame(this);
				if (input.isEmpty()) {
					if (!messager.showQuestion("It seems that result is empty! "
							+ "Click \"Yes\" to continue anyway")) {
						return;
					}
				} else {
					if (autoComplete.searchStringInProposed(input) == -1) {
						if (!messager.showQuestion("It seems that inputed article "
								+ "name does not exists. Do you want to add it "
								+ "anyway?")) {
							return;
						}
					}
				}
				firePropertyChange("add-article", null, input);
				setVisible(false);
				break;
		}
	}

	/**
	 * Returns array, initialized by actions objects.
	 */
	@Override
	protected AbstractAction[] createActions() {
		List<AbstractAction> actions = new ArrayList<AbstractAction>();
		actions.add(new Action(this, "Cancel", "add-article-cancel",
				"Cancel all changes", "", "src/main/resources/images/cancel_big.png", 0));
		actions.add(new Action(this, "OK", "add-article-OK",
				"Apply all changes", "", "src/main/resources/images/ok_big.png", 0));
		return actions.toArray(new AbstractAction[actions.size()]);
	}

	private class ArticleSource implements AutoCompleteSource {

		@Override
		public String[] getSource(String... params) {
			MediaWikiFacade.setLanguage(Language.UKRAINIAN);
			String[] result = new String[]{};
			try {
				result = MediaWikiFacade.getArticlesStartingWith(params[0]);
			} catch (IOException e) {
				new MessagesFrame(AddArticleFrame.this).showError(e
						.getMessage());
			}
			return result;
		}
	}
}