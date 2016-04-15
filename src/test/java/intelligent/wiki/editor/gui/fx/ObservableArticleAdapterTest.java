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

package intelligent.wiki.editor.gui.fx;

import intelligent.wiki.editor.core_api.Article;
import intelligent.wiki.editor.core_api.Parser;
import intelligent.wiki.editor.core_api.Project;
import intelligent.wiki.editor.spring.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Class for testing {@link ObservableArticleAdapter} class.
 *
 * @author Myroslav Rudnytskyi
 * @version 11.11.2015
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class ObservableArticleAdapterTest {

	@Test(expected = NullPointerException.class)
	public void testConstructorFirstArg() throws Exception {
		new ObservableArticleAdapter(null, node -> null);
	}

	@Test(expected = NullPointerException.class)
	public void testConstructorSecondArg() throws Exception {
		new ObservableArticleAdapter(new Project() {
			@Override
			public void makeArticle(String title, String text) {
			}

			@Override
			public Article getArticle() {
				return null;
			}

			@Override
			public Parser getParser() {
				return null;
			}
		}, null);
	}
}