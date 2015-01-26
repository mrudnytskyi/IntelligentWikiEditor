package bot.nlp;

import java.util.List;

import bot.core.ApplicationException;
import bot.io.FilesFacade;

/**
 * 
 * @author Mir4ik
 * @version 0.1 25.1.2015
 */
/*
 * TODO:
 * 1. Languages support?
 */
public class StopWordsHolder {
	
	private final List<String> STOP_WORDS = new StringArrayList();
	
	public StopWordsHolder() {
		String file = "stop_words_uk.txt";
		try {
			STOP_WORDS.addAll(new StringArrayList(FilesFacade.read(file)));
		} catch (ApplicationException e) {
			// ignore ?
		}
	}
	
	public boolean isStopWord(String word) {
		return STOP_WORDS.contains(word);
	}
	
	public boolean notStopWord(String word) {
		return !isStopWord(word);
	}
}
