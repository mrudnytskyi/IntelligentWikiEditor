package tests;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import utils.StringArrayList;
import bot.io.FilesFacade;
import bot.nlp.summarize.KeywordsSummarizer;
import bot.nlp.summarize.Summarizer;

/**
 * Class for testing {@link KeywordsSummarizer} class.
 * 
 * @author Mir4ik
 * @version 0.1 12.11.2014
 * @see Test
 * @see Assert
 */
public class KeywordsSummarizerTest {

	@Test
	public void start() {
		StringArrayList listSet = null;
		String setFile = "keywordsSummarizerTestFile.txt";
		try {
			listSet = new StringArrayList(FilesFacade.readTXT(setFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] testSet = new String[listSet.size()];
		int i = 0;
		for (String str : listSet) {
			testSet[i] = str.replace('_', ' ');
			i++;
		}
		String[] correctResult = Arrays.copyOf(testSet, testSet.length - 1);
		StringArrayList listWords = null;
		String wordsFile = "keywordsTestFile.txt";
		try {
			listWords = new StringArrayList(FilesFacade.readTXT(wordsFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Set<CharSequence> keywords = new HashSet<CharSequence>();
		for (String str : listWords) {
			keywords.add(str);
		}
		Summarizer summarizer = new Summarizer(
				new KeywordsSummarizer(null, keywords));
		CharSequence[] result = summarizer.summarize(testSet);
		System.out.println(Arrays.deepToString(result));
		Assert.assertArrayEquals(correctResult, result);
	}
}