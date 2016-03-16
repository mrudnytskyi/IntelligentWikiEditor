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

package intelligent.wiki.editor.core_api;

/**
 * Interface represents every node in article AST. Note, tht it extends
 * {@code Iterable}, so you can iterate over children in such manner:
 * <pre>
 *     ASTNode parent = ... // get node
 *     for (ASTNode child : parent) {
 *         // do something useful here
 *     }</pre>
 * or, if you are using Java 8:
 * <pre>
 *     ASTNode parent = ... // get node
 *     parent.forEach(child -&gt; {
 *     		// do something useful here
 *     });</pre>
 *
 * @author Myroslav Rudnytskyi
 * @version 10.02.2016
 */
public interface ASTNode extends Iterable<ASTNode> {

	/**
	 * @return unique id for every node
	 */
	ASTNodeID getUID();

	/**
	 * @return parent node or {@code null} if root
	 */
	ASTNode getParent();

	/**
	 * @param child node, for storing as child
	 * @throws IllegalArgumentException if this object is passed as parameter
	 */
	void addChild(ASTNode child);

	/**
	 * @return representation in human-readable form
	 */
	String toString();

	/**
	 * @return representation in mark-up form
	 */
	MarkupText getMarkupText();
}