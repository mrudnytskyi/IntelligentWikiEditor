package bot.nlp;

import java.util.Objects;

/**
 * Class, created to encapsulate information about text fragment (snippet) 
 * and source, where it was taken.
 * 
 * @author Mir4ik
 * @version 0.1 19.1.2015
 */
public class Snippet {
	
	private final String text;
	
	private final String source;
	
	public Snippet(String text, String source) {
		Objects.requireNonNull(text, "Text fragment can not be null!");
		this.text = text;
		this.source = source;
	}
	
	public String getText() {
		return text;
	}
	
	public String getSource() {
		return source;
	}
	
	@Override
	public String toString() {
		return "Snippet: \"" + text + "\" from \"" + source + "\"";
	}
}