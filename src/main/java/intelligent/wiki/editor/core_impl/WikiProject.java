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

package intelligent.wiki.editor.core_impl;

import intelligent.wiki.editor.core_api.Article;
import intelligent.wiki.editor.core_api.Parser;
import intelligent.wiki.editor.core_api.Project;

import javax.inject.Inject;
import java.util.Objects;

/**
 * Default implementation of {@link Project}.
 *
 * @author Myroslav Rudnytskyi
 * @version 12.03.2016
 */
public class WikiProject implements Project {
	@Inject
	private final Parser parser;
	private Article article;

	public WikiProject(Parser parser) {
		this.parser = Objects.requireNonNull(parser, "Null parser!");
	}

	@Override
	public void makeArticle(String title, String text) {
		if (text == null || text.isEmpty() || title == null || title.isEmpty()) {
			article = Article.EMPTY_ARTICLE;
		} else {
			WikiMarkup markup = new WikiMarkup(text);
			article = new WikiArticle(markup, parser.parse(markup), new WikiArticleTitle(title));
		}
	}

	@Override
	public Article getArticle() {
		return article;
	}

	@Override
	public Parser getParser() {
		return parser;
	}
}
