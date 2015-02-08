package bot;

import java.util.Arrays;
import java.util.Scanner;

import cli.Command;
import cli.CommandsFactory;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

/**
 * Class, containing code for system commands interpreter. Note, that system 
 * was created in UNIX style: as set of small parts and every program make only
 * one task.
 *  
 * @author Mir4ik
 * @version 0.1 12.11.2014
 */
public class Runner {

	public static void main(String[] args) {
		System.out.println("Welcome! I am robot for text manipulating and "
				+ "working with Wikipedia. \r\nWhat can I do for you?");
		Scanner input = new Scanner(System.in);
		while (true) {
			System.out.print("> ");
			String currLine = input.nextLine();
			String[] tokens = currLine.split(" ");
			String command = tokens.length != 0 ? tokens[0] : currLine;
			Command currCommand = CommandsFactory.getCommand(command);
			if (currCommand == null) {
				System.err.println("Can not find command \"" + command + 
						"\"!\r\nShh! Do not know how to work with me? "
						+ "Enter \"help help\" and \"help -a\" below...");
				continue;
			}
			if (currCommand.isExit()) {
				input.close();
			}
			JCommander jc = new JCommander(currCommand);
			try {
				jc.parse(Arrays.copyOfRange(tokens, 1, tokens.length));
			} catch (ParameterException e) {
				System.err.println(e);
			}
			currCommand.doAction();
		}
	}
}