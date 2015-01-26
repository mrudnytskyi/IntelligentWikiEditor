package bot.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import bot.nlp.TextFragment;
import bot.nlp.TextFragmentTopic;

/**
 * 
 * @author Mir4ik
 * @version 0.1 20.1.2015
 */
/*
 * TODO write other article parts
 */
public class Article {
	
	private Map<TextFragmentTopic, List<TextFragment>> content =
			new HashMap<TextFragmentTopic, List<TextFragment>>();
	
	public void add(TextFragment tf, TextFragmentTopic topic) {
		if (content.get(topic) == null) {
			content.put(topic, new ArrayList<TextFragment>());
		}
		content.get(topic).add(tf);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Iterator<Entry<TextFragmentTopic, List<TextFragment>>> i = 
				content.entrySet().iterator();
		while (i.hasNext()) {
			Entry<TextFragmentTopic, List<TextFragment>> current = i.next();
			sb.append("\r\n== ");
			sb.append(current.getKey());
			sb.append(" ==\r\n");
			Iterator<TextFragment> iter = current.getValue().iterator();
			while (iter.hasNext()) {
				sb.append(iter.next().getText());
			}
		}
		return sb.toString();
	}
}