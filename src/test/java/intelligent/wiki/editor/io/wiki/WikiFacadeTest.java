/*
 * Copyright (C) 2016 Myroslav Rudnytskyi
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
package intelligent.wiki.editor.io.wiki;

import intelligent.wiki.editor.io_api.wiki.WikiOperations;
import intelligent.wiki.editor.io_impl.wiki.WikiFacade;
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

	@Test
	public void testExistsArticle() throws Exception {
		Assert.assertEquals(true, wiki.existsArticle("Ukraine"));
		Assert.assertEquals(true, wiki.existsArticle("  Ukraine  "));
		Assert.assertEquals(true, wiki.existsArticle("Bright, Victoria"));
		Assert.assertEquals(false, wiki.existsArticle(wrong));
		Assert.assertEquals(false, wiki.existsArticle(null));
		Assert.assertEquals(false, wiki.existsArticle(""));
	}

	@Test
	public void testGetArticles() throws Exception {
		Assert.assertEquals(1, wiki.getArticlesStartingWith(right, 1).size());
		Assert.assertEquals(1, wiki.getArticlesStartingWith("  Bright, Victoria  ", 1).size());
		Assert.assertEquals(1, wiki.getArticlesStartingWith("Bright, Victoria", 1).size());
		Assert.assertEquals(0, wiki.getArticlesStartingWith(wrong, 1).size());
		Assert.assertEquals(0, wiki.getArticlesStartingWith(null, 1).size());
		Assert.assertEquals(0, wiki.getArticlesStartingWith(right, -1).size());
		Assert.assertEquals(0, wiki.getArticlesStartingWith(right, 501).size());
		Assert.assertEquals(0, wiki.getArticlesStartingWith(right, 0).size());
	}

	@Test
	public void testGetArticleContent() throws Exception {
		Assert.assertNotEquals("", wiki.getArticleContent("Main"));
		Assert.assertNotEquals("", wiki.getArticleContent("@"));
		Assert.assertNotEquals("", wiki.getArticleContent("    Main    "));
		Assert.assertNotEquals("", wiki.getArticleContent("Bright, Victoria"));
		Assert.assertEquals("", wiki.getArticleContent(wrong));
		Assert.assertEquals("", wiki.getArticleContent(null));
		Assert.assertEquals("", wiki.getArticleContent(""));
	}

	@Test
	public void testExistsCategory() throws Exception {
		Assert.assertEquals(true, wiki.existsCategory("Ukraine"));
		Assert.assertEquals(true, wiki.existsCategory("    Airliner seating "));
		Assert.assertEquals(true, wiki.existsCategory("Airliner seating"));
		Assert.assertEquals(false, wiki.existsCategory(null));
		Assert.assertEquals(false, wiki.existsCategory(""));
		Assert.assertEquals(false, wiki.existsCategory("LALKA"));
		String prefix = wiki.getCategoryNamespacePrefix();
		Assert.assertEquals(true, wiki.existsCategory(prefix + "Ukraine"));
		Assert.assertEquals(true, wiki.existsCategory(prefix + "Airliner seating "));
		Assert.assertEquals(true, wiki.existsCategory(prefix + "Airliner seating"));
		Assert.assertEquals(false, wiki.existsCategory(prefix + ""));
		Assert.assertEquals(false, wiki.existsCategory(prefix + "LALK"));
	}

	@Test
	public void testGetCategories() throws Exception {
		Assert.assertEquals(1, wiki.getCategoriesStartingWith(right, 1).size());
		Assert.assertEquals(1, wiki.getCategoriesStartingWith("   Airliner seating   ", 1).size());
		Assert.assertEquals(1, wiki.getCategoriesStartingWith("Airliner seating", 1).size());
		Assert.assertEquals(0, wiki.getCategoriesStartingWith(wrong, 1).size());
		Assert.assertEquals(0, wiki.getCategoriesStartingWith(null, 1).size());
		Assert.assertEquals(0, wiki.getCategoriesStartingWith(right, -1).size());
		Assert.assertEquals(0, wiki.getCategoriesStartingWith(right, 501).size());
		Assert.assertEquals(0, wiki.getCategoriesStartingWith(right, 0).size());
	}

	@Test
	public void testExistsTemplate() throws Exception {
		Assert.assertEquals(true, wiki.existsTemplate("Ukraine"));
		Assert.assertEquals(true, wiki.existsTemplate("    Loose Women "));
		Assert.assertEquals(true, wiki.existsTemplate("Loose Women"));
		Assert.assertEquals(false, wiki.existsTemplate(null));
		Assert.assertEquals(false, wiki.existsTemplate(""));
		Assert.assertEquals(false, wiki.existsTemplate("LALKA"));
		String prefix = wiki.getTemplateNamespacePrefix();
		Assert.assertEquals(true, wiki.existsTemplate(prefix + "Ukraine"));
		Assert.assertEquals(true, wiki.existsTemplate(prefix + "Loose Women "));
		Assert.assertEquals(true, wiki.existsTemplate(prefix + "Loose Women"));
		Assert.assertEquals(false, wiki.existsTemplate(prefix + ""));
		Assert.assertEquals(false, wiki.existsTemplate(prefix + "LALK"));
	}

	@Test
	public void testGetTemplates() throws Exception {
		Assert.assertEquals(1, wiki.getTemplatesStartingWith(right, 1).size());
		Assert.assertEquals(1, wiki.getTemplatesStartingWith("    Loose Women ", 1).size());
		Assert.assertEquals(1, wiki.getTemplatesStartingWith("Loose Women", 1).size());
		Assert.assertEquals(0, wiki.getTemplatesStartingWith(wrong, 1).size());
		Assert.assertEquals(0, wiki.getTemplatesStartingWith(null, 1).size());
		Assert.assertEquals(0, wiki.getTemplatesStartingWith(right, -1).size());
		Assert.assertEquals(0, wiki.getTemplatesStartingWith(right, 501).size());
		Assert.assertEquals(0, wiki.getTemplatesStartingWith(right, 0).size());
	}

	@Test
	public void testGetCategoryPrefix() throws Exception {
		Assert.assertEquals("Category:", wiki.getCategoryNamespacePrefix());
	}

	@Test
	public void testGetTemplatePrefix() throws Exception {
		Assert.assertEquals("Template:", wiki.getTemplateNamespacePrefix());
	}

	@Test
	public void testGetTemplateParameters() throws Exception {
		String prefix = wiki.getTemplateNamespacePrefix();
		Assert.assertEquals(43, wiki.getTemplateParameters("Infobox football biography").size());
		Assert.assertEquals(43, wiki.getTemplateParameters("  Infobox football biography  ").size());
		Assert.assertEquals(43, wiki.getTemplateParameters(prefix + "Infobox football biography").size());
		Assert.assertEquals(43, wiki.getTemplateParameters(prefix + "  Infobox football biography   ").size());
		Assert.assertEquals(0, wiki.getTemplateParameters("").size());
		Assert.assertEquals(0, wiki.getTemplateParameters(prefix + "").size());
		Assert.assertEquals(0, wiki.getTemplateParameters(null).size());
		Assert.assertEquals(0, wiki.getTemplateParameters(prefix + "Infobox").size());
		Assert.assertEquals(0, wiki.getTemplateParameters("Infobox").size());
	}
}