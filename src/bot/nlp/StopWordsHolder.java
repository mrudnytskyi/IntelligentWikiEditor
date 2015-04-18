/*
 * StopWordsHolder.java	25.01.2015
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
package bot.nlp;

import java.io.IOException;
import java.util.List;

import utils.StringArrayList;
import bot.io.FilesFacade;

/**
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 25.01.2015
 */
// TODO: Languages support?
public class StopWordsHolder {
	
	private final List<String> STOP_WORDS = new StringArrayList();
	
	public StopWordsHolder() {
		String file = "stop_words_uk.txt";
		try {
			STOP_WORDS.addAll(new StringArrayList(FilesFacade.readTXT(file)));
		} catch (IOException e) {
			//throw new ApplicationException(e);
		}
	}
	
	public boolean isStopWord(String word) {
		return STOP_WORDS.contains(word);
	}
	
	public boolean notStopWord(String word) {
		return !isStopWord(word);
	}
}