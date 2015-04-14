package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import utils.MutableString;
import bot.io.FilesFacade;

/**
 * Main frame of application.
 * 
 * @author Mir4ik
 * @version 0.2 28.08.2014
 */
public class MainFrame extends JFrame implements PropertyChangeListener {

	private static final long serialVersionUID = -3904199062763114676L;

	private final JTextArea article = new JTextArea();
	
	private final MessagesPane messages = new MessagesPane();
	
	private final JPanel toolpane = new JPanel();
	
	private final ActionsManager actions = new ActionsManager(
			new PropertyChangeListener[] {this});
	
	private final MessagesFrame messager = new MessagesFrame(this);
	
	private static final String LINE = System.getProperty("line.separator");
	
	private final FileFiltersManager filters = new FileFiltersManager();
	
	/**
	 * Constructs main frame with specified content and title.
	 */
	public MainFrame() {
		super("Wiki Editor 1.0");
		setLayout(new BorderLayout());
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setJMenuBar(createMenu());
		add(createToolbar(), BorderLayout.NORTH);
		add(createContent(), BorderLayout.CENTER);
	}
	
	private JMenuBar createMenu() {
		JMenu file = new JMenu("File");
		file.add(actions.getAction("Open"));
		file.add(actions.getAction("Save as"));
		file.addSeparator();
		file.add(actions.getAction("Exit"));
		JMenu edit = new JMenu("Edit");
		edit.add(actions.getAction("Cut"));
		edit.add(actions.getAction("Copy"));
		edit.add(actions.getAction("Paste"));
		JMenu insert = new JMenu("Insert");
		insert.add(actions.getAction("Insert link"));
		insert.add(actions.getAction("Insert category"));
		insert.add(actions.getAction("Insert template"));
		insert.add(actions.getAction("Insert heading"));
		insert.add(actions.getAction("Insert external link"));
		JMenu help = new JMenu("?");
		help.add(actions.getAction("About"));
		JMenuBar menu = new JMenuBar();
		menu.add(file);
		menu.add(edit);
		menu.add(insert);
		menu.add(Box.createHorizontalGlue());
		menu.add(help);
		return menu;
	}
	
	private JToolBar createToolbar() {
		JToolBar toolbar = new JToolBar();
		toolbar.add(actions.getAction("Open"));
		toolbar.add(actions.getAction("Save as"));
		toolbar.addSeparator();
		toolbar.add(actions.getAction("Cut"));
		toolbar.add(actions.getAction("Copy"));
		toolbar.add(actions.getAction("Paste"));
		toolbar.addSeparator();
		toolbar.add(actions.getAction("Insert link"));
		toolbar.add(actions.getAction("Insert category"));
		toolbar.add(actions.getAction("Insert template"));
		toolbar.add(actions.getAction("Insert heading"));
		toolbar.add(actions.getAction("Insert external link"));
		return toolbar;
	}
	
	private JPanel createContent() {
		article.setFont(new Font("Consolas", Font.PLAIN, 14));
		article.setLineWrap(true);
		JPanel content = new JPanel(new BorderLayout());
		JSplitPane splitRight = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				new JScrollPane(article), new JScrollPane(messages));
		JSplitPane splitLeft = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				toolpane, splitRight);
		splitRight.setDividerLocation(getHeight()/2 + getHeight()/4);
		splitRight.setOneTouchExpandable(true);
		splitLeft.setDividerLocation(getWidth()/6);
		splitLeft.setOneTouchExpandable(true);
		content.add(splitLeft, BorderLayout.CENTER);
		return content;
	}

	/**
	 * Method provides code for frame actions.
	 */
	//TODO: add i18n, divide into separate methods
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		switch (evt.getPropertyName()) {
		case "Open":
			JFileChooser chooserOpen = new JFileChooser(".");
			chooserOpen.setFileFilter(filters.getFilter("txt"));
			int resultOpen = chooserOpen.showOpenDialog(MainFrame.this);
			if (resultOpen == JFileChooser.APPROVE_OPTION) {
				try {
					article.setText(FilesFacade.readTXT(
							chooserOpen.getSelectedFile().getPath()));
				} catch (IOException e) {
					messages.error(e.toString());
					messager.showError(e.toString());
				}
			}
			break;
		case "Save as":
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
			break;
		case "Exit":
			if (messager.showQuestion("Do you want to exit?")) {
				System.exit(0);
			}
			break;
		case "Insert link":
			String articleName = messager.showInput("Input article name");
			if (articleName != null) {
				MutableString ms = new MutableString(articleName.length() + 10);
				if (article.getSelectedText() == null) {
					ms.append("[[", articleName, "]]");
					article.insert(ms.toString(), article.getCaretPosition());
				} else {
					ms.append("[[", articleName, "|",
							article.getSelectedText(), "]]");
					article.replaceSelection(ms.toString());
				}
			}
			break;
		case "Insert category":
			String categoryName = messager.showInput("Input category name");
			if (categoryName != null) {
				MutableString ms = new MutableString(categoryName.length() + 20);
				ms.append(LINE, "[[Категорія:", categoryName, "]]", LINE);
				article.insert(ms.toString(), article.getText().length());
			}
			break;
		case "Insert template":
			String templateName = messager.showInput("Input template name");
			if (templateName != null) {
				MutableString ms = new MutableString(templateName.length() + 10);
				ms.append("{{", templateName, "}}", LINE);
				article.insert(ms.toString(), article.getCaretPosition());
			}
			break;
		case "Insert heading":
			String stringHeadingType = messager.showInput("Input heading type",
					new Integer[] {2, 3, 4, 5, 6}, 2);
			if (stringHeadingType != null) {
				int headingType = Integer.parseInt(stringHeadingType);
				MutableString ms = new MutableString(2*headingType + 10);
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
			break;
		case "Insert external link":
			String resourceName = messager.showInput("Input resource name");
			if (resourceName != null) {
				MutableString ms = new MutableString(resourceName.length() + 5);
				if (article.getSelectedText() == null) {
					ms.append("[", resourceName, "]");
					article.insert(ms.toString(), article.getCaretPosition());
				} else {
					ms.append("[", resourceName, " ", 
							article.getSelectedText(), "]");
					article.replaceSelection(ms.toString());
				}
			}
			break;
		case "About":
			messager.showInfo("Written by Myroslav Rudnytskyi, 2015.");
			break;
		}
	}

	// TODO: this is code for debug application starting. Will be removed later!
	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
		SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
	}
}