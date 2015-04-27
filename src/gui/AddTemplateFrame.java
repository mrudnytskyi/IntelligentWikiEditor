/*
 * AddTemplateFrame.java	27.04.2015
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

import utils.AutoCompletePanel;
import utils.AutoCompleteSource;
import bot.compiler.AST.TemplateDeclaration;
import bot.io.MediaWikiFacade;
import bot.io.MediaWikiFacade.Language;

/**
 * Frame for adding templates to article with autocompletion function.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 27.04.2015
 */
public class AddTemplateFrame extends ApplicationFrame {

	private class TemplateSource implements AutoCompleteSource {

		@Override
		public String[] getSource(String... params) {
			MediaWikiFacade.setLanguage(Language.UKRAINIAN);
			String[] result = new String[] {};
			try {
				String[] templates = MediaWikiFacade
						.getTemplatesStartingWith(params[0]);
				result = new String[templates.length];
				for (int i = 0; i < templates.length; i++) {
					result[i] = templates[i].substring("Шаблон:".length(),
							templates[i].length());
				}
			} catch (IOException e) {
				new MessagesFrame(AddTemplateFrame.this).showError(e
						.getMessage());
			}
			return result;
		}
	}

	private static final long serialVersionUID = -6937956054699961023L;

	private final AutoCompletePanel autoComplete = new AutoCompletePanel(
			new TemplateSource(), "Enter template name:",
			getAction("add-template-add"));

	/**
	 * Constructs new frame with specified content and title.
	 * 
	 * @param listener
	 *            necessary object, which will receive template object,
	 *            listening for changing <code>add-template</code> property
	 */
	public AddTemplateFrame(PropertyChangeListener listener) {
		super("Add template");
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

		JPanel params = new JPanel(new GridLayout(2, 1, 50, 20));
		// TODO template parameters panel containment

		JPanel buttons = new JPanel(new GridLayout(1, 2, 200, 0));
		buttons.setBorder(BorderFactory.createEmptyBorder(20, 100, 0, 100));
		buttons.add(new JButton(getAction("add-template-OK")));
		buttons.add(new JButton(getAction("add-template-cancel")));

		content.add(autoComplete, BorderLayout.NORTH);
		content.add(params, BorderLayout.CENTER);
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
		case "add-template-cancel":
			setVisible(false);
			break;
		case "add-template-OK":
			TemplateDeclaration template = new TemplateDeclaration(
					autoComplete.getInputedText());
			// TODO pack parameters
			firePropertyChange("add-template", null, template);
			setVisible(false);
			break;
		case "add-template-add":
			// TODO make parameters loading
			break;
		}
	}

	/**
	 * Returns array, initialized by actions objects.
	 */
	@Override
	protected AbstractAction[] createActions() {
		List<AbstractAction> actions = new ArrayList<AbstractAction>();
		actions.add(new Action(this, "Cancel", "add-template-cancel",
				"Cancel all changes", "", "res\\cancel_big.png", 0));
		actions.add(new Action(this, "OK", "add-template-OK",
				"Apply all changes", "", "res\\ok_big.png", 0));
		actions.add(new Action(this, "", "add-template-add", "Add teplate", "",
				"res\\add_small.png", 0));
		return actions.toArray(new AbstractAction[actions.size()]);
	}
}