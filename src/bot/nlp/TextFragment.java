package bot.nlp;

import java.util.Objects;

/**
 * Class, created to encapsulate information about text fragment and source,
 * where it was taken.
 * 
 * @author Mir4ik
 * @version 0.1 19.1.2015
 */
public class TextFragment {
	
	private final String fragment;
	
	private final String source;
	
	public TextFragment(String fragment, String source) {
		Objects.requireNonNull(fragment, "Text fragment can not be null!");
		this.fragment = fragment;
		this.source = source;
	}
	
	public String getText() {
		return fragment;
	}
	
	public String getSource() {
		return source;
	}
	
	@Override
	public String toString() {
		return "Text fragment: \"" + fragment + "\" from \"" + source + "\"";
	}
}