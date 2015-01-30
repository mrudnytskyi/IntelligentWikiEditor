package tests;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import bot.nlp.summarize.KeywordsSummarizer;
import bot.nlp.summarize.Summarizer;

/**
 * 
 * @author Mir4ik
 * @version 0.1 12.11.2014
 */
public class KeywordsSummarizerTest {
	
	private Summarizer summarizer;

	private final String[] testSet = {
			"Реферування – процес, що потребує осмислення,"
			+ " аналітичної переробки інформації.",
			"Методи реферування поділяються на статистичні,"
			+ " позиційні та індикативні.",
			"Статистичні методи базуються на розробках вченого Луна,"
			+ " який у 1958 році отримав машинний реферат.",
			"Метод локалізації ґрунтується на припущенні, що найсуттєвіша"
			+ " інформація концентрується на початку параграфів.",
			"Індикативні методи дають змогу формалізувати виклад"
			+ " основного змісту документа."
		};
	
	private CharSequence[] result;
	
	private final String[] correctResult = {
			testSet[0], testSet[1], testSet[2], testSet[3]
		};
	
	@Before
	public void setUp() throws Exception {
		Set<CharSequence> keywords = new HashSet<CharSequence>();
		keywords.add("рефер");
		keywords.add("19");
		keywords.add("інформац");
		summarizer = new Summarizer(new KeywordsSummarizer(null, keywords));
	}

	@Test
	public void testSummarize() {
		result = summarizer.summarize(testSet);
	}

	@After
	public void tearDown() throws Exception {
		System.out.println(Arrays.deepToString(result));
		Assert.assertArrayEquals(correctResult, result);
	}
}