/*
 * SenceTokenizerProcessor.java	18.01.2015
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

import java.util.StringTokenizer;

import bot.nlp.Snippet;

/**
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 18.01.2015
 */
public class SenceTokenizerProcessor extends TextProcessor {
	
	@Override
	public void process(Snippet snippet) {
		String fragment = snippet.getText();
		StringTokenizer st = new StringTokenizer(fragment, "\r\n");
		result = new Snippet[st.countTokens()];
		int i = 0;
		while (st.hasMoreTokens()) {
			result[i] = new Snippet(st.nextToken(), snippet.getSource(),
					snippet.getTags());
			i++;
		}
	}
}