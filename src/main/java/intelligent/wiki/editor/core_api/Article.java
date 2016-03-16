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
 * Interface for article object, containing article title, markup text and AST representation.
 *
 * @author Myroslav Rudnytskyi
 * @version 08.11.2015
 */
public interface Article {

	Article EMPTY_ARTICLE = new EmptyArticle();

	MarkupText getMarkupText();

	void setMarkupText(MarkupText text);

	Title getTitle();

	void setTitle(Title title);

	ASTNode getASTRoot();

	void setASTRoot(ASTNode root);
}
