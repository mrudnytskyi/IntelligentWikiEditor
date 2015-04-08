package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

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
import javax.swing.filechooser.FileFilter;

import bot.core.ApplicationException;
import bot.io.FilesFacade;

/**
 * 
 * @author Mir4ik
 * @version 0.1 28.08.2014
 */
public class MainFrame extends JFrame implements PropertyChangeListener {

	private static final long serialVersionUID = -3904199062763114676L;

	private final JTextArea article = new JTextArea();
	
	private final MessagesPane messages = new MessagesPane();
	
	private final JPanel toolpane = new JPanel();
	
	private final ActionsManager actions = new ActionsManager(
			new PropertyChangeListener[] {this});
	
	private final MessagesFrame messager = new MessagesFrame(this);
	
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
		JMenu article = new JMenu("Article");
		article.add(actions.getAction("Insert link"));
		article.add(actions.getAction("Insert category"));
		article.add(actions.getAction("Insert template"));
		JMenu help = new JMenu("?");
		help.add(actions.getAction("About"));
		JMenuBar menu = new JMenuBar();
		menu.add(file);
		menu.add(edit);
		menu.add(article);
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
		return toolbar;
	}
	
	private JPanel createContent() {
		article.setFont(new Font("Consolas", Font.PLAIN, 14));
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

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		switch (evt.getPropertyName()) {
		case "Open":
			JFileChooser chooserOpen = new JFileChooser(".");
			chooserOpen.setFileFilter(new TXTFileFilter());
			chooserOpen.setSelectedFile(new File("temp.txt"));
			int resultOpen = chooserOpen.showOpenDialog(MainFrame.this);
			if (resultOpen == JFileChooser.APPROVE_OPTION) {
				String text = "";
				try {
					text = FilesFacade.readTXT(chooserOpen.getSelectedFile());
				} catch (ApplicationException e) {
					messager.showError(e.toString());
				}
				article.setText(text);
			}
			break;
		case "Save as":
			JFileChooser chooserSave = new JFileChooser(".");
			chooserSave.setFileFilter(new TXTFileFilter());
			chooserSave.setSelectedFile(new File("temp.txt"));
			int resultSave = chooserSave.showSaveDialog(MainFrame.this);
			if (resultSave == JFileChooser.APPROVE_OPTION) {
				try {
					FilesFacade.writeTXT(
							chooserSave.getSelectedFile(), article.getText());
				} catch (ApplicationException e) {
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
			String link = messager.showInput("Input article name");
			if (link != null) {
				if (article.getSelectedText() == null) {
					article.insert("[[" + link + "]]", article.getCaretPosition());
				} else {
					String name = article.getSelectedText();
					article.replaceSelection("[[" + link + "|" + name + "]]");
				}
			}
			break;
		case "Insert category":
			String category = messager.showInput("Input category name");
			if (category != null) {
				article.insert("\r\n[[Категорія:" + category + "]]\r\n", 
						article.getText().length());
			}
			break;
		case "Insert template":
			String template = messager.showInput("Input template name");
			if (template != null) {
				article.insert("{{" + template + "}}\r\n", article.getCaretPosition());
			}
			break;
		case "About":
			messager.showInfo("Written by Myroslav Rudnytskyi, 2015.");
			break;
		}
		
	}
	
	private class TXTFileFilter extends FileFilter {

		@Override
		public boolean accept(File file) {
			boolean isFile = file.isFile();
			boolean isDir = file.isDirectory();
			String fileName = file.getName();
			boolean filterNameLower = fileName.endsWith("TXT");
			boolean filterNameUpper = fileName.endsWith("txt");
			if (isDir || (isFile) && (filterNameLower || filterNameUpper)) {
				return true;
			}
			return false;
		}

		@Override
		public String getDescription() {
			return "TeXT files (TXT)";
		}
	}
	
	// TODO This is code for debug application starting. Will be moved later!
	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
		SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
	}
}