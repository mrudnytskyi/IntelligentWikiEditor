/*
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
package intelligent.wiki.editor.sweble;

import intelligent.wiki.editor.bot.compiler.AST.CategoryDeclaration;
import intelligent.wiki.editor.core.WikiArticle;
import org.sweble.wikitext.engine.nodes.EngProcessedPage;

import java.util.Collections;
import java.util.List;

/**
 * Class, representing Wikipedia article in application.
 *
 * @author Myroslav Rudnytskyi
 * @version 19.09.2015
 */
public class SwebleWikiArticle implements WikiArticle {

	private final EngProcessedPage articlePage;

	public SwebleWikiArticle(EngProcessedPage articlePage) {
		this.articlePage = articlePage;
	}

	@Override
	public List<CategoryDeclaration> getCategories() {
		return Collections.emptyList();
	}

	@Override
	public String toString() {
		return "SwebleWikiArticle{" +
				"articlePage=" + articlePage +
				'}';
	}
}