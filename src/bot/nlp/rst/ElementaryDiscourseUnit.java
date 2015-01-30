package bot.nlp.rst;

import java.util.Objects;

/**
 * Class, representing simplest text element in 
 * <a href='https://en.wikipedia.org/wiki/Rhetorical_Structure_Theory'>
 * Rhetorical Structure Theory</a>.
 * 
 * @author Mir4ik
 * @version 0.1 20.10.2014
 */
public class ElementaryDiscourseUnit {
	
	private String textFragment;
	
	public ElementaryDiscourseUnit(String textFragment) {
		setTextFragment(textFragment);
	}
	
	public String getTextFragment() {
		return textFragment;
	}
	
	private void setTextFragment(String textFragment) {
		Objects.requireNonNull(textFragment, "Text fragment can not be null!");
		this.textFragment = textFragment;
	}
	
	@Override
	public String toString() {
		return "[EDU: " + textFragment + "]";
	}
}