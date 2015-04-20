/*
 * StopsRemoverProcessor.java	24.01.2015
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
 * @version 0.1 24.01.2015
 */
public class StopsRemoverProcessor extends TextProcessor {

	@Override
	public void process(Snippet snippet) {
		String fragment = snippet.getText();
		fragment = fragment.replace(".", "");
		fragment = fragment.replace(",", "");
		fragment = fragment.replace(":", "");
		fragment = fragment.replace(";", "");
		fragment = fragment.replace("!", "");
		fragment = fragment.replace("?", "");
		fragment = fragment.replace("(", "");
		fragment = fragment.replace(")", "");
		fragment = fragment.replace(" — ", " ");
		fragment = fragment.replace("_", "");
		result = new Snippet[1];
		result[0] = new Snippet(fragment, snippet.getSource(), 
				snippet.getTags());
	}
}