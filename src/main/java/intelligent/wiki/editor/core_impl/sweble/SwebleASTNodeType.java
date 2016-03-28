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

package intelligent.wiki.editor.core_impl.sweble;

import intelligent.wiki.editor.core_api.ASTNodeType;
import org.sweble.wikitext.parser.nodes.WtNode;

import java.util.Objects;

/**
 * Class represents type for AST node and encapsulates int constants from Sweble library.
 * Note, that it can be used only with {@link SwebleASTNode} class.
 *
 * @author Myroslav Rudnytskyi
 * @version 27.02.2016
 */
public class SwebleASTNodeType implements ASTNodeType {
	private final SwebleASTNode node;

	public SwebleASTNodeType(SwebleASTNode node) {
		this.node = Objects.requireNonNull(node, "Null WtNode!");
	}

	@Override
	public boolean isText() {
		return node.getNode().getNodeType() == WtNode.NT_TEXT;
	}

	@Override
	public boolean isLinkTitle() {
		return node.getNode().getNodeType() == WtNode.NT_LINK_TITLE;
	}

	@Override
	public boolean isURL() {
		return node.getNode().getNodeType() == WtNode.NT_URL;
	}
}

