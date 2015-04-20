/*
 * ApplicationFrame.java	19.04.2015
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

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.text.DefaultEditorKit;

/**
 * This is superclass for all frame objects in application and has extended from
 * {@link JFrame} interface. Note, that it implements
 * {@link PropertyChangeListener} interface, but is abstract and does not have
 * {@link PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
 * propertyChange()} method realization. Also, it contains {@link Action} class
 * definition.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 19.04.2015
 */
public abstract class ApplicationFrame extends JFrame implements
		PropertyChangeListener {

	/**
	 * This is superclass for all action objects in application and has extended
	 * from {@link AbstractAction} interface.
	 * 
	 * @author Mir4ik
	 * @version 0.1 09.04.2015
	 */
	protected class Action extends AbstractAction {

		private static final long serialVersionUID = 1663210869532963249L;

		/**
		 * Note, that constructor can take <code>null</code> values, but they
		 * will be ignored.
		 * 
		 * @param listener
		 *            For details, see
		 *            {@link Action#actionPerformed(ActionEvent)
		 *            actionPerformed()} method
		 * @param name
		 *            action name
		 * @param id
		 *            unique action id, used to recognize action, stored in
		 *            {@link javax.swing.Action#LONG_DESCRIPTION
		 *            LONG_DESCRIPTION} constant
		 * @param desc
		 *            action description, used to show tips
		 * @param icon
		 *            path to small icon, used in menus
		 * @param bigIcon
		 *            path to big icon, used in toolbars and so on
		 * @param key
		 *            key, used to invoke action, stored in {@link KeyEvent}
		 *            constant
		 */
		protected Action(PropertyChangeListener listener, String name,
				String id, String desc, String icon, String bigIcon, int key) {

			super(name, new ImageIcon(icon));
			putValue(LONG_DESCRIPTION, id);
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
			String id = getValue(Action.LONG_DESCRIPTION).toString();
			firePropertyChange(id, null, null);
		}
	}

	private static final long serialVersionUID = 7429012575959712724L;

	/**
	 * array, storing actions to avoid creation new objects
	 */
	protected AbstractAction[] actions;

	/**
	 * Returns {@link Action} object, which can be used in menu items, toolbar
	 * buttons, etc.
	 * 
	 * @param id
	 *            action id
	 * @return {@link Action} object
	 */
	protected AbstractAction getAction(String id) {
		for (AbstractAction action : actions) {
			String curId = action.getValue(Action.LONG_DESCRIPTION).toString();
			if (id.equalsIgnoreCase(curId)) {
				return action;
			}
		}
		return null;
	}

	/**
	 * Returns array, initialized by actions objects. Note, that method is
	 * abstract, so every frame can create different objects of {@link Action}
	 * class.
	 * 
	 * @return array, storing actions
	 */
	protected abstract AbstractAction[] createActions();

	/**
	 * Constructs a new frame with the specified title and
	 * {@link WindowConstants#DISPOSE_ON_CLOSE DISPOSE_ON_CLOSE} close
	 * operation.
	 * 
	 * @param title
	 *            title for the frame
	 */
	public ApplicationFrame(String title) {
		super(title);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		actions = createActions();
	}

	/**
	 * Method appends specified parameters to existing {@link AbstractAction}
	 * object. It can be useful when actions are not constructed, but
	 * predefined, as in {@link DefaultEditorKit} class.
	 * <p>
	 * Note, that method can take <code>null</code> values, which will be
	 * ignored, except <code>action</code> parameter.
	 * 
	 * @param action
	 *            necessary existing object
	 * @param name
	 *            action name
	 * @param id
	 *            unique action id
	 * @param desc
	 *            action description, used to show tips
	 * @param icon
	 *            path to small icon, used in menus
	 * @param bigIcon
	 *            path to big icon, used in toolbars and so on
	 * @param key
	 *            key, used to invoke action, stored in {@link KeyEvent}
	 *            constant
	 * @return object with parameters
	 * @see DefaultEditorKit.CutAction
	 * @see DefaultEditorKit.CopyAction
	 * @see DefaultEditorKit.PasteAction
	 */
	protected static AbstractAction parametizeAction(AbstractAction action,
			String name, String id, String desc, String icon, String bigIcon,
			int key) {

		action.putValue(Action.NAME, name);
		action.putValue(Action.LONG_DESCRIPTION, id);
		action.putValue(Action.SMALL_ICON, new ImageIcon(icon));
		action.putValue(Action.LARGE_ICON_KEY, new ImageIcon(bigIcon));
		action.putValue(Action.SHORT_DESCRIPTION, desc);
		action.putValue(Action.MNEMONIC_KEY, key);
		return action;
	}
}