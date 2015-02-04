package bot.nlp.processors;

import java.util.StringTokenizer;

import bot.nlp.Snippet;

/**
 * 
 * @author Mir4ik
 * @version 0.1 18.1.2015
 */
public class SenceTokenizerProcessor extends TextProcessor {
	
	@Override
	public void process(Snippet snippet) {
		String fragment = snippet.getText();
		StringTokenizer st = new StringTokenizer(fragment, "\r\n");
		result = new Snippet[st.countTokens()];
		int i = 0;
		while (st.hasMoreTokens()) {
			result[i] = new Snippet(st.nextToken(), snippet.getSource());
			i++;
		}
	}
}