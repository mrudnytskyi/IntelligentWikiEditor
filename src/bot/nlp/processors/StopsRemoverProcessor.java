package bot.nlp.processors;

import bot.nlp.TextFragment;

/**
 * 
 * 
 * @author Mir4ik
 * @version 0.1 24.1.2015
 */
public class StopsRemoverProcessor extends TextProcessor {

	@Override
	public void process(TextFragment tf) {
		String fragment = tf.getText();
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
		result = new TextFragment[1];
		result[0] = new TextFragment(fragment, tf.getSource());
	}
}