package intelligent.wiki.editor.gui;
/*
 * NewProjectFrame.java	22.04.2015
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
import java.awt.Dimension;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Frame for setting new project settings.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 22.04.2015
 */
public class NewProjectFrame extends ApplicationFrame {

	private static final long serialVersionUID = 8695497808956299337L;

	private final JTextField location = new JTextField();

	private final JTextField src = new JTextField("src");

	private final JComboBox<String> template = new JComboBox<String>();

	private final JTextField article = new JTextField("Article.txt");

	/**
	 * Constructs new frame with specified content and title.
	 * 
	 * @param listener
	 *            necessary object, which will receive {@link Project} object,
	 *            listening for changing <code>new-project</code> property
	 * @param templates
	 *            array of available templates to create article
	 */
	public NewProjectFrame(PropertyChangeListener listener, String[] templates) {
		super("New project");
		template.setModel(new DefaultComboBoxModel<String>(templates));
		setLayout(new BorderLayout());
		add(createContent(), BorderLayout.CENTER);
		addPropertyChangeListener(listener);
		setResizable(false);
		setAlwaysOnTop(true);
		pack();
		moveToScreenCenter();
	}

	/**
	 * Returns array, initialized by actions objects.
	 */
	@Override
	protected AbstractAction[] createActions() {
		List<AbstractAction> actions = new ArrayList<AbstractAction>();
		actions.add(new Action(this, "Cancel", "new-project-cancel",
				"Cancel all changes", "", "res\\cancel_big.png", 0));
		actions.add(new Action(this, "OK", "new-project-OK",
				"Apply all changes", "", "res\\ok_big.png", 0));
		actions.add(new Action(this, "", "new-project-open", "Open location",
				"", "res\\open_small.png", 0));
		return actions.toArray(new AbstractAction[actions.size()]);
	}

	private JPanel createContent() {
		JPanel content = new JPanel(new BorderLayout());

		JPanel top = new JPanel();
		top.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
		top.add(new JLabel("Enter project location:"));
		top.add(Box.createHorizontalStrut(10));
		location.setPreferredSize(new Dimension(250, location
				.getPreferredSize().height));
		top.add(location);
		top.add(Box.createHorizontalStrut(10));
		location.setEditable(false);
		top.add(new JButton(getAction("new-project-open")));

		JPanel center = new JPanel(new GridLayout(3, 2, 50, 20));
		center.setBorder(BorderFactory.createTitledBorder("Project files:"));
		center.add(new JLabel("Source folder:", SwingConstants.CENTER));
		center.add(src);
		src.setEnabled(false);
		center.add(new JLabel("Choose template file:", SwingConstants.CENTER));
		center.add(template);
		template.setEditable(false);
		center.add(new JLabel("Enter article name:", SwingConstants.CENTER));
		center.add(article);
		article.setEnabled(false);

		JPanel bottom = new JPanel(new GridLayout(1, 2, 200, 0));
		bottom.setBorder(BorderFactory.createEmptyBorder(20, 100, 0, 100));
		bottom.add(new JButton(getAction("new-project-OK")));
		bottom.add(new JButton(getAction("new-project-cancel")));

		content.add(top, BorderLayout.NORTH);
		content.add(center, BorderLayout.CENTER);
		content.add(bottom, BorderLayout.SOUTH);

		content.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
		return content;
	}

	/**
	 * Method provides code for frame actions.
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		switch (evt.getPropertyName()) {
		case "new-project-OK":
			boolean locationEmpty = location.getText().isEmpty();
			if (!locationEmpty) {
				Project project =
						new Project(location.getText(), template
								.getSelectedItem().toString(),
								article.getText(), src.getText());
				firePropertyChange("new-project", null, project);
				setVisible(false);
			} else {
				new MessagesFrame(this)
						.showInfo("Input project location, please!");
			}
			break;
		case "new-project-cancel":
			setVisible(false);
			break;
		case "new-project-open":
			JFileChooser chooser = new JFileChooser(".");
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int result = chooser.showOpenDialog(NewProjectFrame.this);
			if (result == JFileChooser.APPROVE_OPTION) {
				String location = chooser.getSelectedFile().getPath();
				String separator = FileSystems.getDefault().getSeparator();
				this.location.setText(location);
				article.setText(String.join("", location, separator,
						article.getText()));
				src.setText(String.join("", location, separator, "src"));
			}
			break;
		}
	}
}