/*
 * Classifier.java	20.01.2015
 * Copyright (C) 2015 Myroslav Rudnytskyi
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 */
package bot.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import bot.nlp.Snippet;
import bot.nlp.SnippetTopic;
import bot.nlp.Stemmer;
import bot.nlp.StopWordsHolder;

/**
 * Class, created to classify text fragment using types, defined in
 * {@link SnippetTopic}. First of all, text is divided into words. Than all stop
 * words will be found and removed. Than words will be stemmed and counted.
 * After all, it will determine type of fragment counting how often key words
 * (stored in {@link ArticleTemplate} object) will be found in the most used
 * words in the fragment.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 20.01.2015
 */
public class Classifier {

	private class EntryComparator implements Comparator<Entry<String, Integer>> {

		@Override
		public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
			return o1.getValue() - o2.getValue();
		}
	}

	private static final boolean DEBUG = true;

	protected static final int COUNT = 5;

	private final ArticleTemplate at;

	public Classifier(ArticleTemplate at) {
		Objects.requireNonNull(at, "Article template can not be null!");
		this.at = at;
	}

	public SnippetTopic classify(Snippet snippet) {
		String text = snippet.getText();
		Map<String, Integer> wordsUsingMap = createWordsUsageMap(text);
		List<String> wordsList = createOftenWordsList(wordsUsingMap);
		int[] results = new int[SnippetTopic.values().length];
		for (String word : wordsList) {
			if (word.length() == 0) {
				continue;
			}
			if (Character.isDigit(word.charAt(0))) {
				boolean itsYear = true;
				for (int i = 1; i < word.length(); i++) {
					if (!Character.isDigit(word.charAt(i))) {
						itsYear = false;
						break;
					}
				}
				if (itsYear) {
					results[2]++;
					continue;
				}
			}
			for (int i = 0; i < results.length; i++) {
				if (at.containsWord(word, i)) {
					results[i]++;
				}
			}
		}
		int maxPos = maximumPosition(results);
		return SnippetTopic.values()[maxPos];
	}

	private Map<String, Integer> createWordsUsageMap(String text) {
		String[] words = text.split(" ");
		Map<String, Integer> wordsUse = new HashMap<String, Integer>();
		StopWordsHolder ukrainianStopWords = new StopWordsHolder();
		for (String word : words) {
			if (ukrainianStopWords.notStopWord(word)) {
				String stemmed = Stemmer.stem(word);
				if (Classifier.DEBUG) {
					System.out.println(word + " -> " + stemmed);
				}
				Integer used = wordsUse.get(stemmed);
				if (used == null) {
					used = 0;
				}
				used++;
				wordsUse.put(stemmed, used);
			}
		}
		return wordsUse;
	}

	private List<String> createOftenWordsList(Map<String, Integer> wordsUse) {
		List<String> oftenWords = new ArrayList<String>();
		@SuppressWarnings("unchecked")
		Entry<String, Integer>[] entries = new Entry[wordsUse.size()];
		Iterator<Entry<String, Integer>> iter = wordsUse.entrySet().iterator();
		for (int i = 0; i < entries.length; i++) {
			entries[i] = iter.next();
		}
		Arrays.sort(entries, new EntryComparator());
		if (Classifier.DEBUG) {
			System.out.println("Entries count = " + entries.length);
		}
		for (int i = entries.length - 1, j = 0; j < Math.min(Classifier.COUNT,
				entries.length); i--, j++) {
			oftenWords.add(entries[i].getKey());
		}
		if (Classifier.DEBUG) {
			System.out.println(Arrays.deepToString(entries));
		}
		return oftenWords;
	}

	private int maximumPosition(int[] array) {
		int max = 0;
		int maxPos = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i] > max) {
				max = array[i];
				maxPos = i;
			}
		}
		return maxPos;
	}
}