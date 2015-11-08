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

import intelligent.wiki.editor.core.Article;
import intelligent.wiki.editor.core.WikiArticle;
import intelligent.wiki.editor.core.WikiArticleParser;
import org.sweble.wikitext.engine.PageId;
import org.sweble.wikitext.engine.PageTitle;
import org.sweble.wikitext.engine.WtEngine;

import javax.inject.Inject;

/**
 * Parser for creating {@link SwebleWikiArticle} from {@link Article} object.
 *
 * @author Myroslav Rudnytskyi
 * @version 24.10.2015
 */
public class SwebleWikiArticleParser implements WikiArticleParser {

	@Inject
	private WtEngine parser;

	public SwebleWikiArticleParser(WtEngine parser) {
		this.parser = parser;
	}

	@Override
	public WikiArticle parse(Article article) throws ParserException {
		try {
			PageId pageId = new PageId(PageTitle.make(parser.getWikiConfig(), article.getTitle()), -1);
			return new SwebleWikiArticle(parser.postprocess(pageId, article.getWikiText(), null));
		} catch (Exception e) {
			throw new ParserException(e);
		}
	}
}
