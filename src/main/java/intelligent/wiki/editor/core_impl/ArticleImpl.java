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

/**
 * Default implementation for {@link Article}.
 *
 * @author Myroslav Rudnytskyi
 * @version 25.10.2015
 */
public class ArticleImpl implements Article {
	private final String wikiText;
	private final String title;

	public ArticleImpl(String title, String wikiText) {
		this.wikiText = wikiText;
		this.title = title;
	}

	@Override
	public String getWikiText() {
		return wikiText;
	}

	@Override
	public String getTitle() {
		return title;
	}
}
