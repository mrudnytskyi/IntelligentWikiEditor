package bot.nlp.processors;

import java.util.StringTokenizer;

import bot.nlp.TextFragment;

/**
 * 
 * @author Mir4ik
 * @version 0.1 18.1.2015
 */
public class SenceTokenizerProcessor extends TextProcessor {
	
	@Override
	public void process(TextFragment tf) {
		String fragment = tf.getText();
		StringTokenizer st = new StringTokenizer(fragment, "\r\n");
		result = new TextFragment[st.countTokens()];
		int i = 0;
		while (st.hasMoreTokens()) {
			result[i] = new TextFragment(st.nextToken(), tf.getSource());
			i++;
		}
	}
}