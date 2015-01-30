package gui;

import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

/**
 * 
 * @author Mir4ik
 * @version 0.1 28.08.2014
 */
/*
 * TODO
 * 1. create statusbar
 * 3. new class for toolpane
 * 4. write createMenu, createToolbar
 * 5. language
 */
public class MainFrame extends JFrame implements CaretListener {

	private static final long serialVersionUID = 1L;
	
	public static final double RESIZE_VALUE_RIGHT_SPLIT = .8;
	
	public static final double RESIZE_VALUE_LEFT_SPLIT = .1;

	private final JTextArea text = new JTextArea();
	
	private final MessagesPane messages = new MessagesPane();
	
	private final JPanel toolpane = new JPanel();
	
	public MainFrame() {
		super("Wiki Editor 0.1");
		setLayout(new BorderLayout());
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setJMenuBar(createMenu());
		add(createToolbar(), BorderLayout.NORTH);
		add(createContent(), BorderLayout.CENTER);
	}
	
	private JMenuBar createMenu() {
		JMenu project = new JMenu("Project");
		JMenuItem newItem = new JMenuItem("New project");
		project.add(newItem);
		project.addSeparator();
		JMenuItem openItem = new JMenuItem("Open project");
		project.add(openItem);
		JMenuItem openURLItem = new JMenuItem("Open URL");
		project.add(openURLItem);
		project.addSeparator();
		JMenuItem closeItem = new JMenuItem("Close project");
		project.add(closeItem);
		project.addSeparator();
		JMenuItem saveItem = new JMenuItem("Save project");
		project.add(saveItem);
		JMenuItem saveAsItem = new JMenuItem("Save project as");
		project.add(saveAsItem);
		JMenu edit = new JMenu("Edit");
		JMenuItem undoItem = new JMenuItem("Undo");
		edit.add(undoItem);
		JMenuItem redoItem = new JMenuItem("Redo");
		edit.add(redoItem);
		edit.addSeparator();
		JMenuItem cutItem = new JMenuItem("Cut");
		edit.add(cutItem);
		JMenuItem copyItem = new JMenuItem("Copy");
		edit.add(copyItem);
		JMenuItem pasteItem = new JMenuItem("Paste");
		edit.add(pasteItem);
		JMenuItem deleteItem = new JMenuItem("Delete");
		edit.add(deleteItem);
		edit.addSeparator();
		JMenuItem findItem = new JMenuItem("Find");
		edit.add(findItem);
		JMenuItem replaceItem = new JMenuItem("Replace");
		edit.add(replaceItem);
		edit.addSeparator();
		JMenuItem selectAllItem = new JMenuItem("Select All");
		edit.add(selectAllItem);
		JMenu article = new JMenu("Article");
		JMenuItem insertItem = new JMenuItem("Insert...");
		article.add(insertItem);
		JMenuItem insertLinkItem = new JMenuItem("Insert Link");
		article.add(insertLinkItem);
		JMenuItem insertImageItem = new JMenuItem("Insert Image");
		article.add(insertImageItem);
		JMenuItem insertCategoryItem = new JMenuItem("Insert Category");
		article.add(insertCategoryItem);
		JMenuItem insertTemplateItem = new JMenuItem("Insert Template");
		article.add(insertTemplateItem);
		JMenuItem insertBookItem = new JMenuItem("Insert Book");
		article.add(insertBookItem);
		JMenuItem insertExternalLinkItem = new JMenuItem("Insert External Link");
		article.add(insertExternalLinkItem);
		article.addSeparator();
		JMenuItem checkItem = new JMenuItem("Check Article");
		article.add(checkItem);
		JMenuItem correctItem = new JMenuItem("Correct Article");
		article.add(correctItem);
		JMenuItem statisticItem = new JMenuItem("Statistic");
		article.add(statisticItem);
		JMenu settings = new JMenu("Settings");
		JMenu application = new JMenu("Application");
		JMenuItem helpItem = new JMenuItem("?");
		application.add(helpItem);
		JMenuItem aboutItem = new JMenuItem("About");
		application.add(aboutItem);
		application.addSeparator();
		JMenuItem exitItem = new JMenuItem("Exit");
		application.add(exitItem);
		JMenuBar menu = new JMenuBar();
		menu.add(project);
		menu.add(edit);
		menu.add(article);
		menu.add(settings);
		menu.add(Box.createHorizontalGlue());
		menu.add(application);
		return menu;
	}
	
	private JToolBar createToolbar() {
		JToolBar toolbar = new JToolBar();
		toolbar.addSeparator();
		return toolbar;
	}
	
	private JPanel createContent() {
		text.addCaretListener(this);
		JPanel panel = new JPanel(new BorderLayout());
		JSplitPane splitRight = new JSplitPane(
			JSplitPane.VERTICAL_SPLIT, new JScrollPane(text), messages);
		JSplitPane splitLeft = new JSplitPane(
			JSplitPane.HORIZONTAL_SPLIT, toolpane, splitRight);
		splitRight.setDividerLocation(HEIGHT/2 + HEIGHT/4);
		splitRight.setOneTouchExpandable(true);
		splitRight.setResizeWeight(RESIZE_VALUE_RIGHT_SPLIT);
		splitLeft.setDividerLocation(WIDTH/2 - WIDTH/4);
		splitLeft.setOneTouchExpandable(true);
		splitLeft.setResizeWeight(RESIZE_VALUE_LEFT_SPLIT);
		panel.add(splitLeft, BorderLayout.CENTER);
		return panel;
	}

	@Override
	public void caretUpdate(CaretEvent e) {
		if (text.getSelectedText() != null) {
			//enableAddWikiLink();
		} else {
			//disableAddWikiLink();
		}
	}
}