package bot.nlp;

import java.io.IOException;
import java.util.List;

import utils.StringArrayList;
import bot.io.FilesFacade;

/**
 * 
 * @author Mir4ik
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