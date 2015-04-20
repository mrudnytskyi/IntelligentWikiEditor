/*
 * AddSnippetFrame.java	19.04.2015
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
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import bot.nlp.Snippet;

/**
 * Frame for adding {@link Snippet text fragments} to application.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 19.04.2015
 */
// TODO: database loading?
public class AddSnippetFrame extends ApplicationFrame {

	private static final long serialVersionUID = 5148754148151526106L;

	private final JTextArea text = new JTextArea("Enter snippet here");

	private final JTextField source = new JTextField("Enter source here");

	private final JList<String> topic;

	/**
	 * Constructs new frame with specified content and title.
	 * 
	 * @param listener
	 *            necessary object, which will receive {@link Snippet} object,
	 *            listening for changing <code>add-snippet</code> property
	 * @param topics
	 *            array of available topics names to categorize snippet
	 */
	public AddSnippetFrame(PropertyChangeListener listener, String[] topics) {
		super("Add snippet");
		topic = new JList<String>(topics);
		setLayout(new BorderLayout());
		add(createContent(), BorderLayout.CENTER);
		addPropertyChangeListener(listener);
		setResizable(false);
		setAlwaysOnTop(true);
		pack();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		double x = (d.getWidth() - getWidth()) / 2;
		double y = (d.getHeight() - getHeight()) / 2;
		setLocation((int) x, (int) y);
	}

	/**
	 * Returns array, initialized by actions objects.
	 */
	@Override
	protected AbstractAction[] createActions() {
		List<AbstractAction> actions = new ArrayList<AbstractAction>();
		actions.add(new Action(this, "Cancel", "add-snippet-cancel",
				"Cancel all changes", "", "res\\cancel_big.png", 0));
		actions.add(new Action(this, "OK", "add-snippet-OK",
				"Apply all changes", "", "res\\ok_big.png", 0));
		return actions.toArray(new AbstractAction[actions.size()]);
	}

	private JPanel createContent() {
		JPanel content = new JPanel(new BorderLayout());

		JPanel topPanel = new JPanel();
		JScrollPane text = new JScrollPane(this.text);
		text.setBorder(BorderFactory.createTitledBorder("Snippet"));
		JScrollPane src = new JScrollPane(this.source);
		src.setBorder(BorderFactory.createTitledBorder("Source"));
		JScrollPane topic = new JScrollPane(this.topic);
		topic.setBorder(BorderFactory.createTitledBorder("Topics"));

		GroupLayout group = new GroupLayout(topPanel);
		int WIDTH = 300;
		int HEIGHT = 200;
		int DEF = GroupLayout.DEFAULT_SIZE;
		int PREF = GroupLayout.PREFERRED_SIZE;
		group.setHorizontalGroup(group.createParallelGroup(Alignment.LEADING)
				.addGroup(
						group.createSequentialGroup().addGroup(
								group.createParallelGroup(Alignment.LEADING)
										.addComponent(topic, DEF, WIDTH,
												Short.MAX_VALUE)
										.addComponent(text, DEF, WIDTH,
												Short.MAX_VALUE)
										.addComponent(src, DEF, WIDTH,
												Short.MAX_VALUE))));
		group.setVerticalGroup(group.createParallelGroup(Alignment.LEADING)
				.addGroup(
						group.createSequentialGroup()
								.addComponent(text, PREF, HEIGHT, PREF)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(src, PREF, DEF, PREF)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(topic, PREF, HEIGHT, PREF)));
		topPanel.setLayout(group);

		JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 50, 0));
		bottomPanel.add(new JButton(getAction("add-snippet-OK")));
		bottomPanel.add(new JButton(getAction("add-snippet-cancel")));
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		content.add(topPanel, BorderLayout.CENTER);
		content.add(bottomPanel, BorderLayout.SOUTH);
		content.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
		return content;
	}

	/**
	 * Method provides code for frame actions.
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		switch (evt.getPropertyName()) {
		case "add-snippet-cancel":
			setVisible(false);
			break;
		case "add-snippet-OK":
			setVisible(false);
			String[] topicArray = topic.getSelectedValuesList().toArray(
					new String[topic.getSelectedIndices().length]);
			firePropertyChange("add-snippet", null, new Snippet(text.getText(),
					source.getText(), topicArray));
			break;
		}
	}
}