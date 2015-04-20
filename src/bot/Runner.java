/*
 * Runner.java	12.11.2014
 * Copyright (C) 2014 Myroslav Rudnytskyi
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
package bot;

import java.util.Arrays;
import java.util.Scanner;

import cli.Command;
import cli.CommandsManager;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

/**
 * Class, containing code for system commands interpreter. Note, that system 
 * was created in UNIX style: as set of small parts and every program make only
 * one task.
 *  
 * @author Myroslav Rudnytskyi
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
			Command currCommand = CommandsManager.getCommand(command);
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