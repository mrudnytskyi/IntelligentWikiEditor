package bot.core;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import utils.StringArrayList;
import bot.nlp.Snippet;
import bot.nlp.SnippetTopic;
import bot.nlp.StopWordsHolder;
import bot.nlp.processors.CleanProcessor;
import bot.nlp.processors.StopsRemoverProcessor;
import bot.nlp.processors.TextProcessor;

/**
 * 
 * @author Mir4ik
 * @version 0.1 25.01.2015
 */
public class Trainer {
	
	private final boolean DEBUG = true;

	private final StringArrayList[] template;
	
	private final Map<Snippet, SnippetTopic> trainSet;
	
	public Trainer(Map<Snippet, SnippetTopic> trainSet) {
		Objects.requireNonNull(trainSet, "Train set can not be null!");
		this.trainSet = trainSet;
		template = new StringArrayList[SnippetTopic.count()];
		for (int i = 0; i < template.length; i++) {
			template[i] = new StringArrayList();
		}
	}
	
	private String prepareTextFragment(Snippet snippet) {
		TextProcessor cleaner = new CleanProcessor();
		cleaner.process(snippet);
		TextProcessor stopsRemover = new StopsRemoverProcessor();
		stopsRemover.process(cleaner.getResultSnippet());
		return stopsRemover.getResultSnippet().getText();
	}
	
	public void train() {
		Iterator<Entry<Snippet, SnippetTopic>> trainIter = 
				trainSet.entrySet().iterator();
		while (trainIter.hasNext()) {
			Entry<Snippet, SnippetTopic> entry = 
					trainIter.next();
			SnippetTopic topic = entry.getValue();
			String text = prepareTextFragment(entry.getKey());
			String[] words = text.split(" ");
			StringArrayList currentList = template[topic.ordinal()];
			StopWordsHolder ukrainianStopWords = new StopWordsHolder();
			for (String word : words) {
				if (ukrainianStopWords.notStopWord(word)) {
					currentList.add(Classifier.stem(word));
				}
			}
			template[topic.ordinal()] = currentList;
		}
		if (DEBUG) System.out.println(Arrays.toString(template));
		for (int i = 0; i < template.length; i++) {
			template[i] = deleteNotOftenUsedWords(template[i]);
		}
	}
	
	private StringArrayList deleteNotOftenUsedWords(StringArrayList words) {
		Set<String> multiUse = new HashSet<String>();
		Set<String> singleUse = new HashSet<String>();
		for (String word : words) {
			if (!singleUse.add(word)) {
				multiUse.add(word);
			}
		}
		StringArrayList list = new StringArrayList(multiUse.size());
		for (String s : multiUse) {
			list.add(s);
		}
		return list;
	}

	public ArticleTemplate getTemplate() {
		if (DEBUG) System.out.println(Arrays.toString(template));
		return new ArticleTemplate(template);
	}
}