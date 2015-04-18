/*
 * CleanProcessor.java	22.01.2015
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
package bot.nlp.processors;

import bot.nlp.Snippet;

/**
 * 
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 22.01.2015
 */
public class CleanProcessor extends TextProcessor {

	@Override
	public void process(Snippet snippet) {
		String text = snippet.getText();
		text = text.replace(" - ", " $ ");
		text = text.replace(".\r\n", ".~");
		// ^ replacement for savings
		text = text.replace("¬ ", "");
		text = text.replace("\r\n", "");
		text = text.replace("  ", " ");
		text = text.replace("\t", "");
		text = text.replace("- ", "");
		// replacement returning
		text = text.replace(" $ ", " - ");
		text = text.replace(".~", ".\r\n ");
		result = new Snippet[1];
		result[0] = new Snippet(text, snippet.getSource(), snippet.getTopic());
	}
}