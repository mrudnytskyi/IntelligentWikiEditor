package gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import bot.core.ApplicationException;

/**
 * 
 * @author Mir4ik
 * @version 0.1 29.08.2014
 */
/*
 * TODO
 * 1. write actions
 */
public class ActionsFactory {
	
	private Action[] actions; 
	
	protected static class Exit extends AbstractAction {
		
		private static final long serialVersionUID = 1L;
		
		protected Exit() {
			putValue(Action.NAME, "exit");
			putValue(Action.SHORT_DESCRIPTION, "");
			putValue(Action.SMALL_ICON, "");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
	
	public ActionsFactory() throws ApplicationException {
		Class<? extends ActionsFactory> clazz = getClass();
		Class<?>[] declaredClasses = clazz.getDeclaredClasses();
		actions = new Action[declaredClasses.length];
		int i = 0;
		for (Class<?> temp : declaredClasses) {
			try {
				actions[i] = (Action) temp.newInstance();
			} catch (Exception e) {
				throw new ApplicationException("Check ActionsFactory class!", e);
			}
			i++;
		}
	}

	public Action getAction(String id) {
		for (Action action : actions) {
			if (id.equalsIgnoreCase((String) action.getValue(Action.NAME))) {
				return action;
			}
		}
		return null;
	}
}