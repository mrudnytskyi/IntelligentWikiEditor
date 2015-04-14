package cli;

/**
 * Class, containing set of available system commands objects. Note, that to
 * create new command, write subclass of {@link Command} class in those file.
 * 
 * @author Mir4ik
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