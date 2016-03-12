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

import intelligent.wiki.editor.core_api.ASTNode;
import intelligent.wiki.editor.core_api.MarkupText;
import intelligent.wiki.editor.core_impl.ASTNodeID;
import org.apache.commons.lang.StringEscapeUtils;
import org.sweble.wikitext.parser.nodes.WtNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Class represents every node in the article AST, wrapping
 * <a href=https://github.com/sweble/sweble-wikitext>Sweble library</a>
 * AST nodes. It is default implementation of {@link ASTNode}.
 *
 * @author Myroslav Rudnytskyi
 * @version 10.02.2016
 */
public class SwebleASTNode implements ASTNode {
	private final WtNode node;
	private final ASTNodeID id;
	private final ASTNode parent;
	private List<ASTNode> children = new ArrayList<>();

	public SwebleASTNode(WtNode content, ASTNode parent) {
		// parent check is unnecessary - recursive initializer would not compile
		id = new ASTNodeID();
		this.node = Objects.requireNonNull(content, "Null WtNode!");
		this.parent = parent;
	}

	@Override
	public ASTNodeID getUID() {
		return id;
	}

	@Override
	public ASTNode getParent() {
		return parent;
	}

	@Override
	public void addChild(ASTNode child) {
		if (this.equals(child)) {
			throw new IllegalArgumentException("Can not be child to yourself!");
		}
		Objects.requireNonNull(child, "Child node null!");
		children.add(child);
	}

	@Override
	public MarkupText getMarkupText() {
		return null;
	}

	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}
		if (object instanceof SwebleASTNode) {
			SwebleASTNode thatObject = (SwebleASTNode) object;
			return id.equals(thatObject.id);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public String toString() {
		String nodeName = node.getNodeName();
		if (node.size() == 0) {
			// unescape string, because Sweble parser escapes to \\u...
			nodeName = StringEscapeUtils.unescapeJava(node.toString());
		}
		return nodeName;
	}

	@Override
	public Iterator<ASTNode> iterator() {
		return children.iterator();
	}
}
