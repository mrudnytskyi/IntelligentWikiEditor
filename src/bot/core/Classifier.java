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

import bot.nlp.StopWordsHolder;
import bot.nlp.StringArrayList;
import bot.nlp.TextFragment;
import bot.nlp.TextFragmentTopic;

/**
 * Class, created to classify text fragment using types, defined in 
 * {@link TextFragmentTopic}. First of all, text is divided into words. Than
 * all stop words will be found and removed. Than words will be stemmed and 
 * counted. After all, it will determine type of fragment counting how often
 * key words (stored in {@link ArticleTemplate} object) will be found in the
 * most used words in the fragment.
 * 
 * @author Mir4ik
 * @version 0.1 20.1.2015
 */
/*
 * TODO separate class Stemmer?
 */
public class Classifier {
	
	private class EntryComparator implements Comparator<Entry<String, Integer>> {

		@Override
		public int compare(Entry<String, Integer> o1, 
				Entry<String, Integer> o2) {
			return o1.getValue() - o2.getValue();
		}
	}
	
	private static final boolean DEBUG = true;
	
	private static final String[] WORD_ENDINGS = new String("а ам ами ах та"
			+ " в вав вавсь вався вала валась валася вали вались валися вало"
			+ " валось валося вати ватись ватися всь вся е еві ем ею є ємо"
			+ " ємось ємося ється єте єтесь єтеся єш єшся єю и ив ий ила или"
			+ " ило илося им ими имо имось имося ите итесь итеся ити ить"
			+ " иться их иш ишся й ймо ймось ймося йсь йся йте йтесь йтеся і"
			+ " ів ій ім імо ість істю іть ї ла лась лася ло лось лося ли"
			+ " лись лися о ові овував овувала овувати ого ої ок ом ому осте"
			+ " ості очка очкам очками очках очки очків очкові очком очку"
			+ " очок ою ти тись тися у ував увала увати ь ці ю юст юсь юся"
			+ " ють ються я ям ями ях").split(" ");
	
	private static final String[] STABLE_ENDINGS = "ер ск".split(" ");
	
	private static final List<String> STABLE_EXCLUSIONS = new StringArrayList();
	
	private static final Map<String, String> EXCLUSIONS = 
			new HashMap<String, String>();
	
	private static final Map<String, String> CHANGE_ENDINGS = 
			new HashMap<String, String>();
	
	protected static final int COUNT = 5;
	
	private final ArticleTemplate at;
	
	static {
		STABLE_EXCLUSIONS.add("баядер");	STABLE_EXCLUSIONS.add("неначе");
		STABLE_EXCLUSIONS.add("беатріче");	STABLE_EXCLUSIONS.add("одначе");
		STABLE_EXCLUSIONS.add("віче");		STABLE_EXCLUSIONS.add("паче");
		STABLE_EXCLUSIONS.add("наче");
		
		EXCLUSIONS.put("відер", "відр");	EXCLUSIONS.put("був", "бува");

		CHANGE_ENDINGS.put("аче", "ак");
		CHANGE_ENDINGS.put("іче", "ік");
		CHANGE_ENDINGS.put("йовував", "йов");
		CHANGE_ENDINGS.put("йовувала", "йов");
		CHANGE_ENDINGS.put("йовувати", "йов");
		CHANGE_ENDINGS.put("ьовував", "ьов");
		CHANGE_ENDINGS.put("ьовувала", "ьов");
		CHANGE_ENDINGS.put("ьовувати", "ьов");
		CHANGE_ENDINGS.put("цьовував", "ц");
		CHANGE_ENDINGS.put("цьовувала", "ц");
		CHANGE_ENDINGS.put("цьовувати", "ц");
		CHANGE_ENDINGS.put("ядер", "ядр");
	}
	
	public Classifier(ArticleTemplate at) {
		Objects.requireNonNull(at, "Article template can not be null!");
		this.at = at;
	}
	
	public TextFragmentTopic classify(TextFragment fragment) {
		String text = fragment.getText();
		Map<String, Integer> wordsUsingMap = createWordsUsageMap(text);
		List<String> wordsList = createOftenWordsList(wordsUsingMap);
		int[] results = new int[TextFragmentTopic.values().length];
		for (String word : wordsList) {
			if (word.length() == 0) continue;
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
				if (at.containsWord(word, i)) results[i]++;
			}
		}
		int maxPos = maximumPosition(results);
		return TextFragmentTopic.values()[maxPos];
	}
	
	private Map<String, Integer> createWordsUsageMap(String text) {
		String[] words = text.split(" ");
		Map<String, Integer> wordsUse = new HashMap<String, Integer>();
		StopWordsHolder ukrainianStopWords = new StopWordsHolder();
		for (String word : words) {
			if (ukrainianStopWords.notStopWord(word)) {
				String stemmed = stem(word);
				if (DEBUG) System.out.println(word + " -> " + stemmed);
				Integer used = wordsUse.get(stemmed);
				if (used == null) used = 0;
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
		if (DEBUG) System.out.println("Entries count = " + entries.length);
		for (int i = entries.length - 1, j = 0; 
				j < Math.min(COUNT, entries.length); i--, j++) {
			oftenWords.add(entries[i].getKey());
		}
		if (DEBUG) System.out.println(Arrays.deepToString(entries));
		return oftenWords;
	}

	protected static String stem(String word) {
		String newWord = word;
		newWord = word.replace("а́", "а");
		newWord = word.replace("е́", "е");
		newWord = word.replace("є́", "є");
		newWord = word.replace("и́", "и");
		newWord = word.replace("і́", "і");
		newWord = word.replace("ї́", "ї");
		newWord = word.replace("о́", "о");
		newWord = word.replace("у́", "у");
		newWord = word.replace("ю́", "ю");
		newWord = word.replace("я́", "я");
		if (newWord.length() <= 2) {
			return newWord;
		}
		if (STABLE_EXCLUSIONS.contains(newWord)) {
			return newWord;
		}
		if (EXCLUSIONS.keySet().contains(newWord)) {
			return EXCLUSIONS.get(newWord);
		}
		for (String eow : CHANGE_ENDINGS.keySet()) {
			if (newWord.endsWith(eow)) {
				return newWord.substring(0, newWord.length() - eow.length())
						+ CHANGE_ENDINGS.get(eow);
			}
		}
		for (String eow : STABLE_ENDINGS) {
			if (newWord.endsWith(eow)) {
				return newWord;
			}
		}
		for (String eow : WORD_ENDINGS) {
			if (newWord.endsWith(eow)) {
				String trimmed = newWord.substring(0,
						newWord.length() - eow.length());
				if (trimmed.length() > 2) {
					return trimmed;
				}
			}
		}
		return newWord;
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