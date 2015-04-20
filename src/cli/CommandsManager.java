/*
 * CommandsManager.java	07.02.2015
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
package cli;

/**
 * Class, containing set of available system commands objects. Note, that to
 * create new command, write subclass of {@link Command} class in those file.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 07.02.2015
 */
public class CommandsManager {
	
	private static final Command[] available;
	
	private static final String[] availableNames;
	
	private CommandsManager() {}
	
	static {
		Class<?>[] declaredClasses = Command.class.getDeclaredClasses();
		available = new Command[declaredClasses.length];
		availableNames = new String[declaredClasses.length];
		int i = 0;
		for (Class<?> temp : declaredClasses) {
			try {
				available[i] = (Command) temp.newInstance();
				availableNames[i] = available[i].getName();
			} catch (Exception e) {
				throw new RuntimeException("Check Command class!", e);
			}
			i++;
		}
	}

	public static Command getCommand(String command) {
		for (int i = 0; i < availableNames.length; i++) {
			if (availableNames[i].equalsIgnoreCase(command)) {
				return available[i];
			}
		}
		return null;
	}
	
	public static String[] getAvailable() {
		return availableNames;
	}
}