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

package intelligent.wiki.editor.core_impl.sweble;

import intelligent.wiki.editor.core_api.ASTNode;
import intelligent.wiki.editor.core_api.MarkupText;
import intelligent.wiki.editor.core_api.Parser;
import org.sweble.wikitext.engine.PageId;
import org.sweble.wikitext.engine.PageTitle;
import org.sweble.wikitext.engine.WtEngine;
import org.sweble.wikitext.engine.WtEngineImpl;
import org.sweble.wikitext.engine.nodes.EngPage;
import org.sweble.wikitext.engine.utils.DefaultConfigEnWp;
import org.sweble.wikitext.parser.nodes.WtNode;

/**
 * Parser for transforming markup text into AST nodes using
 * <a href=https://github.com/sweble/sweble-wikitext>Sweble library</a>.
 *
 * @author Myroslav Rudnytskyi
 * @version 24.10.2015
 */
public class SwebleParser implements Parser {

	private WtEngine parser;

	public SwebleParser() {
		this.parser = new WtEngineImpl(DefaultConfigEnWp.generate());
	}

	@Override
	public ASTNode parse(MarkupText article) throws ParserException {
		try {
			// root element parent is always null
			return createAST(swebleParse(article.getMarkup()), null);
		} catch (Exception e) {
			throw new ParserException(e);
		}
	}

	private EngPage swebleParse(String markup) throws Exception {
		PageId page = new PageId(PageTitle.make(parser.getWikiConfig(), "unnamed"), -1);
		return parser.postprocess(page, markup, null).getPage();
	}

	private ASTNode createAST(WtNode content, ASTNode parent) {
		ASTNode tree = new SwebleASTNode(content, parent);
		content.forEach(current -> tree.addChild(createAST(current, tree)));
		return tree;
	}
}
