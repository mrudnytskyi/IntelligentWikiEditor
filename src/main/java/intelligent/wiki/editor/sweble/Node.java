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

import intelligent.wiki.editor.bot.compiler.AST.Content;
import intelligent.wiki.editor.bot.compiler.Visitor;
import org.apache.commons.lang.StringEscapeUtils;
import org.sweble.wikitext.parser.nodes.WtNode;

import java.util.Objects;

/**
 * Node for any AST content. Will be replaced by set of classes from AST package.
 *
 * @author Myroslav Rudnytskyi
 * @version 11.11.2015
 */
//TODO replace it by set of classes from AST package
public class Node implements Content {

	private WtNode node;

	public Node(WtNode node) {
		this.node = Objects.requireNonNull(node, "Null WtNode!");
	}

	@Override
	public void accept(Visitor visitor) {
	}

	@Override
	public String toString() {
		String nodeStr = node.getNodeName();
		if (node.size() == 0) {
			//TODO don't forget unescape to normal view, because Sweble parser escapes to \\u...
			nodeStr = StringEscapeUtils.unescapeJava(node.toString());
		}
		return nodeStr;
	}
}
