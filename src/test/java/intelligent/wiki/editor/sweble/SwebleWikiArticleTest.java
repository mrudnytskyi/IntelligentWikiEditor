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

import intelligent.wiki.editor.core.WikiArticle;
import intelligent.wiki.editor.spring.TestConfig;
import javafx.scene.control.TreeItem;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.Collections;

/**
 * Class for testing {@link SwebleWikiArticle} class.
 *
 * @author Myroslav Rudnytskyi
 * @version 11.11.2015
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class SwebleWikiArticleTest {

	@Inject
	private WikiArticle article;

	@Test(expected = NullPointerException.class)
	public void testConstructor() throws Exception {
		new SwebleWikiArticle(null);
	}

	@Test
	public void testGetCategories() throws Exception {
		Assert.assertEquals(Collections.emptyList(), article.getCategories());
	}

	@Test
	public void testGetRoot() throws Exception {
		Assert.assertEquals(TreeItem.class, article.getRoot().getClass());
		Assert.assertEquals(true, article.getRoot().getValue() != null);
	}

	public void setArticle(WikiArticle article) {
		this.article = article;
	}
}