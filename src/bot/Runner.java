package bot;

import java.util.Scanner;

/**
 * Class, containing code for system commands interpreter. Note, that system 
 * was created in UNIX style: as set of small parts and every program make only
 * one task. All operations are listed below:
 * <li>exit - stop work with interpreter
 * <li>help - view detailed info
 * TODO add more here
 * 
 * @author Mir4ik
 * @version 0.1 12.11.2014
 */
/*
 * TODO
 * 1. make Strings loaded from file
 */
public class Runner {

	public static void main(String[] args) {
		System.out.println("Welcome! I am robot for text manipulating and "
				+ "working with Wikipedia. \r\nWhat can I do for you?");
		System.out.println("Shh! Need some help? Enter \"help\" below...");
		Scanner input = new Scanner(System.in);
		while (true) {
			System.out.print("> ");
			String currLine = input.nextLine();
			String[] tokens = currLine.split(" ");
			String command = tokens.length != 0 ? tokens[0] : currLine;
			switch (command) {
			case "exit":
				input.close();
				handleExit();
				break;
			case "help":
				handleHelp(tokens);
				break;
			//TODO add more here
			default:
				System.err.println("Can not find command \"" + command + "\"!");
			}
		}
	}
	
	private static void handleExit() {
		System.out.println("Thanks for using! Goodbye!");
		System.exit(0);
	}
	
	private static void handleHelp(String[] tokens) {
		if (tokens.length == 1) System.out.println("This is second "
				+ "version of Wikipedia bot.");
		final String template = "NAME\r\n\t%s\r\n"
				+ "USAGE\r\n\t%s\r\n"
				+ "DESCRIPTION\r\n\t%s\r\n"
				+ "EXAMPLES\r\n\t%s\r\n"
				+ "SEE ALSO\r\n\t%s";
		for (int i = 1; i < tokens.length; i++) {
			switch (tokens[i]) {
			case "list":
				System.out.println("Now you can use: exit\r\nhelp\r\n");
				//TODO add more here
				break;
			case "exit":
				System.out.println(String.format(template,
						"exit - stop the application",
						"exit",
						"Command stops the application. No options are "
								+ "available.",
						"> exit",
						"None"));
				break;
			case "help":
				System.out.println(String.format(template,
						"help - display documentation pages",
						"help\r\n\thelp <command name>\r\n\thelp <command name>"
								+ " <command name> ... <command name>\r\n\thelp"
								+ " list",
						"Outputs information about every command. Can be used "
								+ "without parameters, \r\n\tthen displays "
								+ "general info about software. When multiply "
								+ "parameters used, \r\n\tcommand will show "
								+ "information about every command, named in "
								+ "parameters. \r\n\tCommand write warnings, "
								+ "when some parameters are unknown. When "
								+ "parameter \r\n\t\"list\" used, command "
								+ "displays list of all available commands.",
						"> help enabled\r\n\t> help help\r\n\t> help help exit",
						"None"));
				break;
				//TODO add more here
			default:
				System.err.println("Can not find command \"" +
						tokens[i] + "\"!");
			}
		}
	}
}