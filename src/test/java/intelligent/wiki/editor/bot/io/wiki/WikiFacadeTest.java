/*
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
package intelligent.wiki.editor.bot.io.wiki;

import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;

/**
 * Class for testing {@link WikiFacade} class.
 *
 * @author Myroslav Rudnytskyi
 * @version 01.10.2015
 */
public class WikiFacadeTest {

	private final WikiOperations wiki = new WikiFacade(new Locale("en"));
	private final String right = "Ukrain";
	private final String wrong = "Urkaine";
	private final String[] emptyArray = new String[]{};

	@Test
	public void testExistsArticle() throws Exception {
		Assert.assertEquals(true, wiki.existsArticle("Main"));
		Assert.assertEquals(false, wiki.existsArticle(wrong));
		Assert.assertEquals(false, wiki.existsArticle(null));
		Assert.assertEquals(false, wiki.existsArticle(""));
	}

	@Test
	public void testGetArticlesStartingWith() throws Exception {
		Assert.assertEquals(1, wiki.getArticlesStartingWith(right, 1).size());
		Assert.assertArrayEquals(emptyArray, wiki.getArticlesStartingWith(wrong, 1).toArray(emptyArray));
		Assert.assertArrayEquals(emptyArray, wiki.getArticlesStartingWith(null, 1).toArray(emptyArray));
		Assert.assertArrayEquals(emptyArray, wiki.getArticlesStartingWith(right, -1).toArray(emptyArray));
	}

	@Test
	public void testGetArticleContent() throws Exception {
		Assert.assertNotNull(wiki.getArticleContent("Main"));
		Assert.assertEquals("", wiki.getArticleContent(wrong));
		Assert.assertEquals("", wiki.getArticleContent(null));
		Assert.assertEquals("", wiki.getArticleContent(""));
	}

	@Test
	public void testExistsCategory() throws Exception {
		//TODO
	}

	@Test
	public void testGetCategoriesStartingWith() throws Exception {
		Assert.assertEquals(1, wiki.getCategoriesStartingWith(right, 1).size());
		Assert.assertArrayEquals(emptyArray, wiki.getCategoriesStartingWith(wrong, 1).toArray(emptyArray));
		Assert.assertArrayEquals(emptyArray, wiki.getCategoriesStartingWith(null, 1).toArray(emptyArray));
		Assert.assertArrayEquals(emptyArray, wiki.getCategoriesStartingWith(right, -1).toArray(emptyArray));
	}

	@Test
	public void testExistsTemplate() throws Exception {
		//TODO
	}

	@Test
	public void testGetTemplatesStartingWith() throws Exception {
		Assert.assertEquals(1, wiki.getTemplatesStartingWith(right, 1).size());
		Assert.assertArrayEquals(emptyArray, wiki.getTemplatesStartingWith(wrong, 1).toArray(emptyArray));
		Assert.assertArrayEquals(emptyArray, wiki.getTemplatesStartingWith(null, 1).toArray(emptyArray));
		Assert.assertArrayEquals(emptyArray, wiki.getTemplatesStartingWith(right, -1).toArray(emptyArray));
	}
}