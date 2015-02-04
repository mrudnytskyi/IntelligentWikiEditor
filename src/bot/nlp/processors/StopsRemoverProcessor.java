package bot.nlp.processors;

import bot.nlp.Snippet;

/**
 * 
 * 
 * @author Mir4ik
 * @version 0.1 24.1.2015
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
		result[0] = new Snippet(fragment, snippet.getSource());
	}
}