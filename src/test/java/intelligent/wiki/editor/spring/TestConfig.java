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

package intelligent.wiki.editor.spring;

import intelligent.wiki.editor.core.SimpleMarkup;
import intelligent.wiki.editor.core_api.ASTNode;
import intelligent.wiki.editor.core_api.Parser;
import intelligent.wiki.editor.core_impl.ASTNodeID;
import intelligent.wiki.editor.core_impl.sweble.SwebleASTNode;
import intelligent.wiki.editor.core_impl.sweble.SwebleParser;
import intelligent.wiki.editor.gui.fx.TreeItemFactory;
import javafx.scene.control.TreeItem;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sweble.wikitext.parser.nodes.WtContentNode;
import org.sweble.wikitext.parser.nodes.WtNode;

/**
 * Spring configuration class for testing.
 *
 * @author Myroslav Rudnytskyi
 * @version 11.11.2015
 */
@Configuration
public class TestConfig {

	@Bean
	public Parser parser() {
		return new SwebleParser();
	}

	@Bean
	public ASTNode root() {
		return parser().parse(new SimpleMarkup());
	}

	@Bean
	public TreeItemFactory<ASTNode> simpleTreeItemFactory() {
		return TreeItem::new;
	}

	@Bean
	public WtNode contentNode() {
		return new WtContentNode.WtContentNodeImpl() {};
	}

	@Bean
	public ASTNode astNode() {
		return new SwebleASTNode(contentNode(), null);
	}

	@Bean
	public ASTNode childAstNode() {
		return new SwebleASTNode(contentNode(), astNode());
	}

	@Bean
	public ASTNodeID id() {
		return new ASTNodeID();
	}

	@Bean
	public ASTNodeID anotherId() {
		return new ASTNodeID();
	}
}
