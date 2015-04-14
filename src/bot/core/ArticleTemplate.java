package bot.core;

import java.util.Objects;

import utils.StringArrayList;

/**
 * 
 * @author Mir4ik
 * @version 0.1 19.01.2015
 */
public class ArticleTemplate {
	
	private final StringArrayList[] keywords;
	
	protected ArticleTemplate(StringArrayList[] keywords) {
		Objects.requireNonNull(keywords, "Keywords can not be null!");
		this.keywords = keywords;
	}
	
	public boolean containsWord(String word, int index) {
		return keywords[index].contains(word);
	}
}