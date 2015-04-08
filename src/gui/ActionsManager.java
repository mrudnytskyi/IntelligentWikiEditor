package gui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.text.DefaultEditorKit;

/**
 * 
 * @author Mir4ik
 * @version 0.1 29.08.2014
 */
public class ActionsManager {
	
	private class Action extends AbstractAction {

		private static final long serialVersionUID = 1663210869532963249L;
		
		public Action(PropertyChangeListener listener, String name,
				String desc, String icon, String bigIcon, int key) {
			
			super(name, new ImageIcon(icon));
			putValue(LARGE_ICON_KEY, new ImageIcon(bigIcon));
			putValue(SHORT_DESCRIPTION, desc);
			putValue(MNEMONIC_KEY, key);
			addPropertyChangeListener(listener);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			firePropertyChange(getValue(Action.NAME).toString(), null, null);
		}
	}
	
	private final AbstractAction[] actions;
	
	public ActionsManager(PropertyChangeListener[] listeners) {
		PropertyChangeListener mainFrame = listeners[0];
		List<AbstractAction> actions = new ArrayList<AbstractAction>();
		actions.add(new Action(mainFrame, "Open", "Open article file",
				"res\\open.png", "res\\open_big.png",
				new Integer(KeyEvent.VK_O)));
		actions.add(new Action(mainFrame, "Save as", "Save article to file",
				"res\\save.png", "res\\save_big.png",
				new Integer(KeyEvent.VK_S)));
		actions.add(new Action(mainFrame, "Exit", "Exit the application",
				"res\\exit.png", "res\\exit_big.png",
				new Integer(KeyEvent.VK_E)));
		actions.add(new Action(mainFrame, "Insert link",
				"Insert link to another Wikipedia page", "res\\wikilink.png",
				"res\\wikilink_big.png", new Integer(KeyEvent.VK_L)));
		actions.add(new Action(mainFrame, "Insert category", "Insert category",
				"res\\category.png", "res\\category_big.png",
				new Integer(KeyEvent.VK_C)));
		actions.add(new Action(mainFrame, "Insert template", "Insert template",
				"res\\template.png", "res\\template_big.png",
				new Integer(KeyEvent.VK_T)));
		actions.add(new Action(mainFrame, "About",
				"Show information about software", "res\\info.png",
				"res\\info_big.png", new Integer(KeyEvent.VK_A)));
		actions.add(parametizeAction(new DefaultEditorKit.CutAction(), "Cut",
				"Cut selected text fragment", "res\\cut.png",
				"res\\cut_big.png", new Integer(KeyEvent.VK_T)));
		actions.add(parametizeAction(new DefaultEditorKit.CopyAction(), "Copy",
				"Copy selected text fragment", "res\\copy.png",
				"res\\copy_big.png", new Integer(KeyEvent.VK_C)));
		actions.add(parametizeAction(new DefaultEditorKit.PasteAction(), "Paste",
				"Paste text fragment", "res\\paste.png",
				"res\\paste_big.png", new Integer(KeyEvent.VK_P)));
		this.actions = actions.toArray(new AbstractAction[actions.size()]);
	}

	public AbstractAction getAction(String name) {
		for (AbstractAction action : actions) {
			if (name.equalsIgnoreCase(action.getValue(Action.NAME).toString())) {
				return action;
			}
		}
		return null;
	}
	
	private static AbstractAction parametizeAction(AbstractAction action,
			String name, String desc, String icon, String bigIcon, int key) {
		action.putValue(Action.NAME, name);
		action.putValue(Action.SMALL_ICON, new ImageIcon(icon));
		action.putValue(Action.LARGE_ICON_KEY, new ImageIcon(bigIcon));
		action.putValue(Action.SHORT_DESCRIPTION, desc);
		action.putValue(Action.MNEMONIC_KEY, key);
		return action;
	}
}