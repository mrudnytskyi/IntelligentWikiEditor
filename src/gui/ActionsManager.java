package gui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.text.DefaultEditorKit;

/**
 * Class, created to manage all actions in application. To avoid creating a
 * lot of small {@link AbstractAction} objects, we invoke special method
 * {@link ActionsManager#getAction(String)} to get them, using unique action
 * <code>name</code>.
 * 
 * @author Mir4ik
 * @version 0.2 29.08.2014
 */
public class ActionsManager {
	
	/**
	 * This is superclass for all action objects in application and has
	 * extended from {@link AbstractAction} interface.
	 * 
	 * @author Mir4ik
	 * @version 0.1 09.04.2015
	 */
	private class Action extends AbstractAction {

		private static final long serialVersionUID = 1663210869532963249L;

		/**
		 * Note, that constructor can take <code>null</code> values, except
		 * <code>listener</code> and <code>name</code> parameters.
		 * 
		 * @param listener	necessary listener. For details, see
		 * 					{@link Action#actionPerformed(ActionEvent)}
		 * @param name		necessary unique action name
		 * @param desc		action description, used to show tips
		 * @param icon		path to small icon, used in menus
		 * @param bigIcon	path to big	icon, used in toolbars
		 * @param key		key, used to invoke action, stored in 
		 * 					{@link KeyEvent} constant 
		 */
		private Action(PropertyChangeListener listener, String name,
				String desc, String icon, String bigIcon, int key) {
			
			super(name, new ImageIcon(icon));
			putValue(LARGE_ICON_KEY, new ImageIcon(bigIcon));
			putValue(SHORT_DESCRIPTION, desc);
			putValue(MNEMONIC_KEY, key);
			addPropertyChangeListener(listener);
		}

		/**
		 * Default realization of these method signals <code>listener</code>
		 * about performing action. After receiving action name, listener 
		 * decides what to do (it can even ignore message). Note, that ignoring 
		 * message may show disadvantages of application architecture.
		 * <p>
		 * These realization helps to save encapsulation, because all code is
		 * stored together and object can hide realization details. Also it
		 * helps to keep {@link Action} objects clean.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			firePropertyChange(getValue(Action.NAME).toString(), null, null);
		}
	}
	
	private final AbstractAction[] actions;

	/**
	 * Note, that constructor can not take <code>null</code> values.
	 * 
	 * @param listeners		actions listeners array
	 */
	//TODO: will be refactored after adding ResourceManager to provide i18n
	public ActionsManager(PropertyChangeListener[] listeners) {
		Objects.requireNonNull(listeners, "Listeners array can not be null!");
		for (PropertyChangeListener listener : listeners) {
			Objects.requireNonNull(listener, "Listener can not be null!");
		}
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

	/**
	 * Returns {@link Action} object, which can be used in menu items, toolbar
	 * buttons, etc.
	 * 
	 * @param name	action name
	 * @return		{@link Action} object
	 */
	//TODO: adding i18n will require stop using name as unique parameter 
	public AbstractAction getAction(String name) {
		for (AbstractAction action : actions) {
			if (name.equalsIgnoreCase(action.getValue(Action.NAME).toString())) {
				return action;
			}
		}
		return null;
	}
	
	/**
	 * Method appends specified parameters to existing {@link AbstractAction} 
	 * object. It can be useful when actions are not constructed, but 
	 * predefined, as in {@link DefaultEditorKit} class.
	 * 
	 * @param action	existing object
	 * @param name		necessary unique action name
	 * @param desc		action description, used to show tips
	 * @param icon		path to small icon, used in menus
	 * @param bigIcon	path to big	icon, used in toolbars
	 * @param key		key, used to invoke action, stored in 
	 * 					{@link KeyEvent} constant 
	 * @return			object with parameters
	 * @see DefaultEditorKit.CutAction
	 * @see DefaultEditorKit.CopyAction
	 * @see DefaultEditorKit.PasteAction
	 */
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