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
		Assert.assertEquals(true, wiki.existsArticle(right + "e"));
		Assert.assertEquals(false, wiki.existsArticle(wrong));
		Assert.assertEquals(false, wiki.existsArticle(null));
		Assert.assertEquals(false, wiki.existsArticle(""));
	}

	@Test
	public void testGetArticles() throws Exception {
		Assert.assertEquals(1, wiki.getArticlesStartingWith(right, 1).size());
		Assert.assertEquals(0, wiki.getArticlesStartingWith(wrong, 1).size());
		Assert.assertEquals(0, wiki.getArticlesStartingWith(null, 1).size());
		Assert.assertEquals(0, wiki.getArticlesStartingWith(right, -1).size());
		Assert.assertEquals(0, wiki.getArticlesStartingWith(right, 501).size());
		Assert.assertEquals(0, wiki.getArticlesStartingWith(right, 0).size());
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
	public void testGetCategories() throws Exception {
		Assert.assertEquals(1, wiki.getCategoriesStartingWith(right, 1).size());
		Assert.assertEquals(0, wiki.getCategoriesStartingWith(wrong, 1).size());
		Assert.assertEquals(0, wiki.getCategoriesStartingWith(null, 1).size());
		Assert.assertEquals(0, wiki.getCategoriesStartingWith(right, -1).size());
		Assert.assertEquals(0, wiki.getCategoriesStartingWith(right, 501).size());
		Assert.assertEquals(0, wiki.getCategoriesStartingWith(right, 0).size());
	}

	@Test
	public void testExistsTemplate() throws Exception {
		//TODO
	}

	@Test
	public void testGetTemplates() throws Exception {
		Assert.assertEquals(1, wiki.getTemplatesStartingWith(right, 1).size());
		Assert.assertEquals(0, wiki.getTemplatesStartingWith(wrong, 1).size());
		Assert.assertEquals(0, wiki.getTemplatesStartingWith(null, 1).size());
		Assert.assertEquals(0, wiki.getTemplatesStartingWith(right, -1).size());
		Assert.assertEquals(0, wiki.getTemplatesStartingWith(right, 501).size());
		Assert.assertEquals(0, wiki.getTemplatesStartingWith(right, 0).size());
	}

	@Test
	public void testGetCategoryPrefix() throws Exception {
		Assert.assertEquals("Category:", wiki.getCategoryPrefix());
	}

	@Test
	public void testGetTemplatePrefix() throws Exception {
		Assert.assertEquals("Template:", wiki.getTemplatePrefix());
	}
}