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

package intelligent.wiki.editor.sweble;

import intelligent.wiki.editor.core.*;
import intelligent.wiki.editor.spring.TestConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

/**
 * Class for testing {@link SwebleWikiArticleParser} class.
 *
 * @author Myroslav Rudnytskyi
 * @version 11.11.2015
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class SwebleWikiArticleParserTest {

	@Inject
	private WikiArticleParser parser;

	@Test(expected = NullPointerException.class)
	public void testConstructor() throws Exception {
		new SwebleWikiArticleParser(null);
	}

	@Test
	public void testParseSimpleArticle() throws Exception {
		WikiArticle article = parser.parse(new SimpleArticle());
		Assert.assertEquals(SwebleWikiArticle.class, article.getClass());
		Assert.assertEquals(SwebleASTNode.class, article.getRoot().getClass());
		Assert.assertEquals(true, article.getRoot() != null);
	}

	@Test(expected = WikiArticleParser.ParserException.class)
	public void testParseEmptyTitleArticle() throws Exception {
		parser.parse(new EmptyTitleArticle());
	}

	@Test(expected = WikiArticleParser.ParserException.class)
	public void testParseNullArticle() throws Exception {
		parser.parse(null);
	}

	@Test(expected = WikiArticleParser.ParserException.class)
	public void testParseWrongArticle() throws Exception {
		parser.parse(new WrongArticle());
	}

	public void setParser(WikiArticleParser parser) {
		this.parser = parser;
	}
}