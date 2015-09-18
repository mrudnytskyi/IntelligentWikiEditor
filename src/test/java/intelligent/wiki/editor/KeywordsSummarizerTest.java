package intelligent.wiki.editor;
/*
 * KeywordsSummarizerTest.java	12.11.2014
 * Copyright (C) 2014 Myroslav Rudnytskyi
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

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import intelligent.wiki.editor.utils.StringArrayList;
import intelligent.wiki.editor.bot.io.FilesFacade;
import intelligent.wiki.editor.bot.nlp.summarize.KeywordsSummarizer;
import intelligent.wiki.editor.bot.nlp.summarize.Summarizer;

/**
 * Class for testing {@link KeywordsSummarizer} class.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 12.11.2014
 * @see Test
 * @see Assert
 */
public class KeywordsSummarizerTest {

	@Test
	public void start() {
		StringArrayList listSet = null;
		String setFile = "src/test/resources/keywordsSummarizerTestFile.txt";
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
		String wordsFile = "src/test/resources/keywordsTestFile.txt";
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