package intelligent.wiki.editor.gui;
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

import intelligent.wiki.editor.bot.compiler.AST.CategoryDeclaration;
import intelligent.wiki.editor.bot.compiler.AST.SmartLink;
import intelligent.wiki.editor.bot.compiler.AST.TemplateDeclaration;
import intelligent.wiki.editor.bot.io.DatabaseFacade;
import intelligent.wiki.editor.bot.io.FilesFacade;
import intelligent.wiki.editor.bot.nlp.Snippet;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	private Project currentProject;

	/**
	 * Constructs new frame with specified content and title.
	 */
	public MainFrame() {
		super("Wiki Editor 1.0");
		setLayout(new BorderLayout());
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setJMenuBar(createMenu());
		add(createContent(), BorderLayout.CENTER);
		add(createToolbar(), BorderLayout.NORTH);
		initDatabase();
	}

	// TODO: this is code for debug application starting. Will be removed later!
	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
		SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
	}

	private JPanel createContent() {
		article.setFont(new Font("Consolas", Font.PLAIN, 14));
		article.setLineWrap(true);
		JPanel content = new JPanel(new BorderLayout());
		JSplitPane splitRight =
				new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(
						article), new JScrollPane(messages));
		JSplitPane splitLeft =
				new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, toolpane,
						splitRight);
		splitRight.setDividerLocation((getHeight() / 2) + (getHeight() / 4));
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
		actions.add(new Action(this, "New project", "show-new-project",
				"Create new project", "src/main/resources/images/new.png", "src/main/resources/images/new_big.png",
				new Integer(KeyEvent.VK_N)));
		actions.add(new Action(this, "Open project", "open-project",
				"Open project file", "src/main/resources/images/open.png", "src/main/resources/images/open_big.png",
				new Integer(KeyEvent.VK_O)));
		actions.add(new Action(this, "Save changes", "save-changes",
				"Save article to file", "src/main/resources/images/save.png", "src/main/resources/images/save_big.png",
				new Integer(KeyEvent.VK_S)));
		actions.add(new Action(this, "Exit", "exit", "Exit the application",
				"src/main/resources/images/exit.png", "src/main/resources/images/exit_big.png",
				new Integer(KeyEvent.VK_E)));
		actions.add(new Action(this, "Add link to article", "show-add-article",
				"Add link to another Wikipedia page", "src/main/resources/images/wikilink.png",
				"src/main/resources/images/wikilink_big.png", new Integer(KeyEvent.VK_L)));
		actions.add(new Action(this, "Add categories", "show-add-categories",
				"Add categories", "src/main/resources/images/category.png", "src/main/resources/images/category_big.png",
				new Integer(KeyEvent.VK_C)));
		actions.add(new Action(this, "Add template", "show-add-template",
				"Add template", "src/main/resources/images/template.png", "src/main/resources/images/template_big.png",
				new Integer(KeyEvent.VK_T)));
		actions.add(new Action(this, "About", "about",
				"Show information about software", "src/main/resources/images/info.png",
				"src/main/resources/images/info_big.png", new Integer(KeyEvent.VK_A)));
		actions.add(ApplicationFrame.parametizeAction(
				new DefaultEditorKit.CutAction(), "Cut", "cut",
				"Cut selected text fragment", "src/main/resources/images/cut.png",
				"src/main/resources/images/cut_big.png", new Integer(KeyEvent.VK_T)));
		actions.add(ApplicationFrame.parametizeAction(
				new DefaultEditorKit.CopyAction(), "Copy", "copy",
				"Copy selected text fragment", "src/main/resources/images/copy.png",
				"src/main/resources/images/copy_big.png", new Integer(KeyEvent.VK_C)));
		actions.add(ApplicationFrame.parametizeAction(
				new DefaultEditorKit.PasteAction(), "Paste", "paste",
				"Paste text fragment", "src/main/resources/images/paste.png", "src/main/resources/images/paste_big.png",
				new Integer(KeyEvent.VK_P)));
		actions.add(new Action(this, "Insert heading", "insert-heading",
				"Insert heading", "src/main/resources/images/heading.png", "src/main/resources/images/heading_big.png",
				new Integer(KeyEvent.VK_H)));
		actions.add(new Action(this, "Insert external link",
				"insert-external-link", "Insert link to external resources",
				"src/main/resources/images/link.png", "src/main/resources/images/link_big.png",
				new Integer(KeyEvent.VK_E)));
		actions.add(new Action(this, "Add snippet", "show-add-snippet",
				"Add snippet to sources", "src/main/resources/images/add-snippet.png",
				"src/main/resources/images/add-snippet_big.png", new Integer(KeyEvent.VK_A)));
		actions.add(new Action(this, "Generate article", "generate-article",
				"Generate article text from snippets", "src/main/resources/images/wizard.png",
				"src/main/resources/images/wizard_big.png", new Integer(KeyEvent.VK_G)));

		return actions.toArray(new AbstractAction[actions.size()]);
	}

	private JMenuBar createMenu() {
		JMenu file = new JMenu("Project");
		file.add(getAction("show-new-project"));
		file.add(getAction("open-project"));
		file.add(getAction("save-changes"));
		file.addSeparator();
		file.add(getAction("exit"));
		JMenu edit = new JMenu("Edit");
		edit.add(getAction("cut"));
		edit.add(getAction("copy"));
		edit.add(getAction("paste"));
		JMenu insert = new JMenu("Insert");
		insert.add(getAction("show-add-article"));
		insert.add(getAction("show-add-categories"));
		insert.add(getAction("show-add-template"));
		insert.add(getAction("insert-heading"));
		insert.add(getAction("insert-external-link"));
		JMenu article = new JMenu("Article");
		article.add(getAction("show-add-snippet"));
		article.add(getAction("generate-article"));
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
		toolbar.add(getAction("show-new-project"));
		toolbar.add(getAction("open-project"));
		toolbar.add(getAction("save-changes"));
		toolbar.addSeparator();
		toolbar.add(getAction("cut"));
		toolbar.add(getAction("copy"));
		toolbar.add(getAction("paste"));
		toolbar.addSeparator();
		toolbar.add(getAction("show-add-article"));
		toolbar.add(getAction("show-add-categories"));
		toolbar.add(getAction("show-add-template"));
		toolbar.add(getAction("insert-heading"));
		toolbar.add(getAction("insert-external-link"));
		toolbar.addSeparator();
		toolbar.add(getAction("show-add-snippet"));
		toolbar.add(getAction("generate-article"));
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
		case "show-new-project":
			newProject();
			break;
		case "new-project":
			OKNewProject((Project) evt.getNewValue());
			break;
		case "open-project":
			open();
			break;
		case "save-changes":
			save();
			break;
		case "exit":
			if (messager.showQuestion("Do you want to exit?")) {
				System.exit(0);
			}
			break;
		case "show-add-article":
			showAddArticle();
			break;
		case "show-add-categories":
			showAddCategories();
			break;
		case "show-add-template":
			showAddTemplate();
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
			String[] tags = new String[] { "LOL", "LIL", "LEL" };
			new AddSnippetFrame(this, tags).setVisible(true);
			break;
		case "add-snippet":
			OKAddSnippetFrame((Snippet) evt.getNewValue());
			break;
		case "generate-article":
			break;
		case "add-categories":
			addCategories((CategoryDeclaration[]) evt.getNewValue());
			break;
		case "add-template":
			addTemplate((TemplateDeclaration) evt.getNewValue());
			break;
		case "add-article":
			addArticle((String) evt.getNewValue());
			break;
		}
	}

	private void showAddCategories() {
		new AddCategoriesFrame(this).setVisible(true);
	}

	private void showAddTemplate() {
		new AddTemplateFrame(this).setVisible(true);
	}

	private void showAddArticle() {
		new AddArticleFrame(this).setVisible(true);
	}

	private void newProject() {
		String[] templates = new String[] { "LOL.xml", "LIL.xml", "LEL.xml" };
		new NewProjectFrame(this, templates).setVisible(true);
	}

	private void OKNewProject(Project project) {
		currentProject = project;
	}

	private void open() {
		JFileChooser chooser = new JFileChooser(".");
		chooser.setFileFilter(new FileNameExtensionFilter(
				"eXtensible Markup Language files (XML)", "xml", "XML"));
		int result = chooser.showOpenDialog(MainFrame.this);
		if (result == JFileChooser.APPROVE_OPTION) {
			try {
				currentProject =
						(Project) FilesFacade.readXML(chooser.getSelectedFile()
								.getPath());
				article.setText(FilesFacade.readTXT(currentProject
						.getArticleFile()));
			} catch (IOException e) {
				messages.error(e.toString());
				messager.showError(e.toString());
			}
		}
	}

	private void save() {
		if (currentProject == null) {
			messager.showInfo("No opened projects!");
			return;
		}
		try {
			FilesFacade.writeTXT(currentProject.getArticleFile(),
					article.getText());
		} catch (IOException e) {
			messages.error(e.toString());
			messager.showError(e.toString());
		}
	}

	private void addArticle(String articleName) {
		if (articleName != null) {
			SmartLink smartLink = null;
			try {
				if (article.getSelectedText() == null) {
					smartLink = new SmartLink(articleName);
					article.insert(smartLink.toString(),
							article.getCaretPosition());
					DatabaseFacade.addReplacement(articleName, articleName);
				} else {
					String selectedText = article.getSelectedText();
					smartLink = new SmartLink(articleName, selectedText);
					article.replaceSelection(smartLink.toString());
					DatabaseFacade.addReplacement(selectedText, articleName);
				}
			} catch (IOException e) {
				messages.error(e.toString());
				messager.showError(e.toString());
			}
		}
	}

	private void addCategories(CategoryDeclaration[] categories) {
		for (CategoryDeclaration category : categories) {
			if (category != null) {
				String line = System.lineSeparator();
				article.insert(
						String.join("", line, category.toString(), line),
						article.getText().length());
			}
		}
	}

	private void addTemplate(TemplateDeclaration template) {
		article.insert(template.toString(), article.getCaretPosition());
	}

	private void insertHeading() {
		String stringHeadingType =
				messager.showInput("Input heading type", new Integer[] { 2, 3,
						4, 5, 6 }, 2);
		if (stringHeadingType != null) {
			int headingType = Integer.parseInt(stringHeadingType);
			char[] headingChars = new char[headingType];
			Arrays.fill(headingChars, '=');
			String heading = new String(headingChars);
			String line = System.lineSeparator();
			if (article.getSelectedText() == null) {
				article.insert(
						String.join("", line, heading, "  ", heading, line),
						article.getCaretPosition());
			} else {
				article.replaceSelection(String.join("", heading,
						article.getSelectedText(), heading));
			}
		}
	}

	private void insertExternalLink() {
		String resourceName = messager.showInput("Input resource name");
		if (resourceName != null) {
			if (article.getSelectedText() == null) {
				article.insert(String.join("", "[", resourceName, "]"),
						article.getCaretPosition());
			} else {
				article.replaceSelection(String.join("", "[", resourceName,
						" ", article.getSelectedText(), "]"));
			}
		}
	}

	private void OKAddSnippetFrame(Snippet snippet) {
		if (currentProject == null) {
			messager.showInfo("No opened projects!");
			return;
		}
		File srcFolder = new File(currentProject.getSrcFolder());
		if (!srcFolder.exists()) {
			srcFolder.mkdir();
		}
		try {
			FilesFacade.writeXML(
					String.join("", srcFolder.getPath(), File.separator,
							String.valueOf(snippet.hashCode()), ".xml"),
					snippet);
		} catch (IOException e) {
			messages.error(e.toString());
			messager.showError(e.toString());
		}
	}
}