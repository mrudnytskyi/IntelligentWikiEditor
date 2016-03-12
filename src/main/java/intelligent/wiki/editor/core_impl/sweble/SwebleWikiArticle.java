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
package intelligent.wiki.editor.core_impl.sweble;

import intelligent.wiki.editor.core_api.ASTNode;
import intelligent.wiki.editor.core_api.WikiArticle;
import org.sweble.wikitext.engine.nodes.EngPage;
import org.sweble.wikitext.engine.nodes.EngProcessedPage;
import org.sweble.wikitext.parser.nodes.WtNode;

import java.util.Objects;

/**
 * Class, representing Wikipedia article realization using
 * <a href=https://github.com/sweble/sweble-wikitext>Sweble library</a> in application.
 * It is default implementation of {@link WikiArticle}.
 * See {@link SwebleWikiArticleParser} for this class objects creation details.
 *
 * @author Myroslav Rudnytskyi
 * @version 19.09.2015
 */
public class SwebleWikiArticle implements WikiArticle {
	private final EngPage content;

	public SwebleWikiArticle(EngProcessedPage content) {
		this.content = Objects.requireNonNull(content.getPage(), "Null wiki article content!");
	}

	@Override
	public ASTNode getRoot() {
		return createTree(content, null);
	}

	private ASTNode createTree(WtNode content, ASTNode parent) {
		ASTNode tree = new SwebleASTNode(content, parent);
		content.forEach(current -> tree.addChild(createTree(current, tree)));
		return tree;
	}
}