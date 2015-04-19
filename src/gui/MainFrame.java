/*
 * MainFrame.java	28.08.2014
 * Copyright (C) 2014 Myroslav Rudnytskyi
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
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.DefaultEditorKit;

import utils.MutableString;
import bot.io.DatabaseFacade;
import bot.io.FilesFacade;
import bot.nlp.Snippet;

/**
 * Main frame of application.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.2 18.04.2015
 */
public class MainFrame extends ApplicationFrame {

	private static final long serialVersionUID = -3904199062763114676L;

	private final JTextArea article = new JTextArea();

	private final MessagesPane messages = new MessagesPane();

	private final JPanel toolpane = new JPanel();

	private final MessagesFrame messager = new MessagesFrame(this);

	private static final String LINE = System.getProperty("line.separator");

	private final FileFiltersManager filters = new FileFiltersManager();

	/**
	 * Constructs new frame with specified content and title.
	 */
	public MainFrame() {
		super("Wiki Editor 1.0");
		setLayout(new BorderLayout());
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setExtendedState(MAXIMIZED_BOTH);
		setJMenuBar(createMenu());
		add(createContent(), BorderLayout.CENTER);
		add(createToolbar(), BorderLayout.NORTH);
		initDatabase();
	}

	private JPanel createContent() {
		article.setFont(new Font("Consolas", Font.PLAIN, 14));
		article.setLineWrap(true);
		JPanel content = new JPanel(new BorderLayout());
		JSplitPane splitRight = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				new JScrollPane(article), new JScrollPane(messages));
		JSplitPane splitLeft = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				toolpane, splitRight);
		splitRight.setDividerLocation(getHeight() / 2 + getHeight() / 4);
		splitRight.setOneTouchExpandable(true);
		splitLeft.setDividerLocation(getWidth() / 6);
		splitLeft.setOneTouchExpandable(true);
		content.add(splitLeft, BorderLayout.CENTER);
		return content;
	}

	/**
	 * Returns array, initialized by actions objects.
	 */
	@Override
	protected AbstractAction[] createActions() {
		List<AbstractAction> actions = new ArrayList<AbstractAction>();
		actions.add(new Action(this, "Open", "open", "Open article file",
				"res\\open.png", "res\\open_big.png",
				new Integer(KeyEvent.VK_O)));
		actions.add(new Action(this, "Save as", "save-as",
				"Save article to file", "res\\save.png", "res\\save_big.png",
				new Integer(KeyEvent.VK_S)));
		actions.add(new Action(this, "Exit", "exit", "Exit the application",
				"res\\exit.png", "res\\exit_big.png",
				new Integer(KeyEvent.VK_E)));
		actions.add(new Action(this, "Insert link", "insert-link",
				"Insert link to another Wikipedia page", "res\\wikilink.png",
				"res\\wikilink_big.png", new Integer(KeyEvent.VK_L)));
		actions.add(new Action(this, "Insert category", "insert-category",
				"Insert category", "res\\category.png",
				"res\\category_big.png", new Integer(KeyEvent.VK_C)));
		actions.add(new Action(this, "Insert template", "insert-template",
				"Insert template", "res\\template.png",
				"res\\template_big.png", new Integer(KeyEvent.VK_T)));
		actions.add(new Action(this, "About", "about",
				"Show information about software", "res\\info.png",
				"res\\info_big.png", new Integer(KeyEvent.VK_A)));
		actions.add(parametizeAction(new DefaultEditorKit.CutAction(), "Cut",
				"cut", "Cut selected text fragment", "res\\cut.png",
				"res\\cut_big.png", new Integer(KeyEvent.VK_T)));
		actions.add(parametizeAction(new DefaultEditorKit.CopyAction(), "Copy",
				"copy", "Copy selected text fragment", "res\\copy.png",
				"res\\copy_big.png", new Integer(KeyEvent.VK_C)));
		actions.add(parametizeAction(new DefaultEditorKit.PasteAction(),
				"Paste", "paste", "Paste text fragment", "res\\paste.png",
				"res\\paste_big.png", new Integer(KeyEvent.VK_P)));
		actions.add(new Action(this, "Insert heading", "insert-heading",
				"Insert heading", "res\\heading.png", "res\\heading_big.png",
				new Integer(KeyEvent.VK_H)));
		actions.add(new Action(this, "Insert external link",
				"insert-external-link", "Insert link to external resources",
				"res\\link.png", "res\\link_big.png",
				new Integer(KeyEvent.VK_E)));
		actions.add(new Action(this, "Add snippet", "show-add-snippet",
				"Add snippet to sources", "res\\add-snippet.png",
				"res\\add-snippet_big.png", new Integer(KeyEvent.VK_A)));
		return actions.toArray(new AbstractAction[actions.size()]);
	}

	private JMenuBar createMenu() {
		JMenu file = new JMenu("File");
		file.add(getAction("open"));
		file.add(getAction("save-as"));
		file.addSeparator();
		file.add(getAction("exit"));
		JMenu edit = new JMenu("Edit");
		edit.add(getAction("cut"));
		edit.add(getAction("copy"));
		edit.add(getAction("paste"));
		JMenu insert = new JMenu("Insert");
		insert.add(getAction("insert-link"));
		insert.add(getAction("insert-category"));
		insert.add(getAction("insert-template"));
		insert.add(getAction("insert-heading"));
		insert.add(getAction("insert-external-link"));
		JMenu article = new JMenu("Article");
		article.add(getAction("show-add-snippet"));
		JMenu help = new JMenu("?");
		help.add(getAction("about"));
		JMenuBar menu = new JMenuBar();
		menu.add(file);
		menu.add(edit);
		menu.add(insert);
		menu.add(article);
		menu.add(Box.createHorizontalGlue());
		menu.add(help);
		return menu;
	}

	private JToolBar createToolbar() {
		JToolBar toolbar = new JToolBar();
		toolbar.add(getAction("open"));
		toolbar.add(getAction("save-as"));
		toolbar.addSeparator();
		toolbar.add(getAction("cut"));
		toolbar.add(getAction("copy"));
		toolbar.add(getAction("paste"));
		toolbar.addSeparator();
		toolbar.add(getAction("insert-link"));
		toolbar.add(getAction("insert-category"));
		toolbar.add(getAction("insert-template"));
		toolbar.add(getAction("insert-heading"));
		toolbar.add(getAction("insert-external-link"));
		toolbar.addSeparator();
		toolbar.add(getAction("show-add-snippet"));
		return toolbar;
	}

	private void initDatabase() {
		try {
			if (!DatabaseFacade.existReplacementTable()) {
				DatabaseFacade.createReplacementTable();
			}
		} catch (IOException e) {
			messages.error(e.toString());
			messager.showError(e.toString());
		}
	}

	/**
	 * Method provides code for frame actions.
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		switch (evt.getPropertyName()) {
		case "open":
			open();
			break;
		case "save-as":
			save();
			break;
		case "exit":
			if (messager.showQuestion("Do you want to exit?")) {
				System.exit(0);
			}
			break;
		case "insert-link":
			insertLink();
			break;
		case "insert-category":
			insertCategory();
			break;
		case "insert-template":
			insertTemplate();
			break;
		case "insert-heading":
			insertHeading();
			break;
		case "insert-external-link":
			insertExternalLink();
			break;
		case "about":
			messager.showInfo("Written by Myroslav Rudnytskyi, 2015.");
			break;
		case "show-add-snippet":
			new AddSnippetFrame(this).setVisible(true);
			break;
		case "add-snippet":
			OKAddSnippetFrame((Snippet) evt.getNewValue());
			break;
		}
	}

	private void open() {
		JFileChooser chooserOpen = new JFileChooser(".");
		chooserOpen.setFileFilter(filters.getFilter("txt"));
		int resultOpen = chooserOpen.showOpenDialog(MainFrame.this);
		if (resultOpen == JFileChooser.APPROVE_OPTION) {
			try {
				article.setText(FilesFacade.readTXT(chooserOpen
						.getSelectedFile().getPath()));
			} catch (IOException e) {
				messages.error(e.toString());
				messager.showError(e.toString());
			}
		}
	}

	private void save() {
		JFileChooser chooserSave = new JFileChooser(".");
		chooserSave.setFileFilter(filters.getFilter("txt"));
		int resultSave = chooserSave.showSaveDialog(MainFrame.this);
		if (resultSave == JFileChooser.APPROVE_OPTION) {
			try {
				FilesFacade.writeTXT(chooserSave.getSelectedFile().getPath(),
						article.getText());
			} catch (IOException e) {
				messages.error(e.toString());
				messager.showError(e.toString());
			}
		}
	}

	private void insertLink() {
		String articleName = messager.showInput("Input article name");
		if (articleName != null) {
			try {
				MutableString ms = new MutableString(articleName.length() + 10);
				if (article.getSelectedText() == null) {
					ms.append("[[", articleName, "]]");
					article.insert(ms.toString(), article.getCaretPosition());
					DatabaseFacade.addReplacement(articleName, articleName);
				} else {
					String selectedText = article.getSelectedText();
					ms.append("[[", articleName, "|", selectedText, "]]");
					article.replaceSelection(ms.toString());
					DatabaseFacade.addReplacement(selectedText, articleName);
				}
			} catch (IOException e) {
				messages.error(e.toString());
				messager.showError(e.toString());
			}
		}
	}

	private void insertCategory() {
		String categoryName = messager.showInput("Input category name");
		if (categoryName != null) {
			MutableString ms = new MutableString(categoryName.length() + 20);
			ms.append(LINE, "[[Категорія:", categoryName, "]]", LINE);
			article.insert(ms.toString(), article.getText().length());
		}
	}

	private void insertTemplate() {
		String templateName = messager.showInput("Input template name");
		if (templateName != null) {
			MutableString ms = new MutableString(templateName.length() + 10);
			ms.append("{{", templateName, "}}", LINE);
			article.insert(ms.toString(), article.getCaretPosition());
		}
	}

	private void insertHeading() {
		String stringHeadingType = messager.showInput("Input heading type",
				new Integer[] { 2, 3, 4, 5, 6 }, 2);
		if (stringHeadingType != null) {
			int headingType = Integer.parseInt(stringHeadingType);
			MutableString ms = new MutableString(2 * headingType + 10);
			String heading = ms.append('=', headingType).toString();
			ms.clear();
			if (article.getSelectedText() == null) {
				ms.append(LINE, heading, "  ", heading, LINE);
				article.insert(ms.toString(), article.getCaretPosition());
			} else {
				ms.append(heading, article.getSelectedText(), heading);
				article.replaceSelection(ms.toString());
			}
		}
	}

	private void insertExternalLink() {
		String resourceName = messager.showInput("Input resource name");
		if (resourceName != null) {
			MutableString ms = new MutableString(resourceName.length() + 5);
			if (article.getSelectedText() == null) {
				ms.append("[", resourceName, "]");
				article.insert(ms.toString(), article.getCaretPosition());
			} else {
				ms.append("[", resourceName, " ", article.getSelectedText(),
						"]");
				article.replaceSelection(ms.toString());
			}
		}
	}

	private void OKAddSnippetFrame(Snippet snippet) {
		try {
			FilesFacade.writeXML(snippet.hashCode() + ".xml", snippet);
		} catch (IOException e) {
			messages.error(e.toString());
			messager.showError(e.toString());
		}
	}

	// TODO: this is code for debug application starting. Will be removed later!
	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
		SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
	}
}