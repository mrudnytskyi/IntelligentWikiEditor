package bot.nlp.processors;

import bot.nlp.TextFragment;

/**
 * 
 * 
 * @author Mir4ik
 * @version 0.1 22.1.2015
 */
public class CleanProcessor extends TextProcessor {

	@Override
	public void process(TextFragment tf) {
		String text = tf.getText();
		text = text.replace(" - ", " $ ");
		text = text.replace(".\r\n", ".~");
		// ^ replacement for savings
		text = text.replace("¬ ", "");
		text = text.replace("\r\n", "");
		text = text.replace("  ", " ");
		text = text.replace("\t", "");
		text = text.replace("- ", "");
		// replacement returning
		text = text.replace(" $ ", " - ");
		text = text.replace(".~", ".\r\n ");
		result = new TextFragment[1];
		result[0] = new TextFragment(text, tf.getSource());
	}
}