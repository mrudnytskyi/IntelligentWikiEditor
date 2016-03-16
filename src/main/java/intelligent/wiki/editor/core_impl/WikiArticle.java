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

import intelligent.wiki.editor.core_api.ASTNode;
import intelligent.wiki.editor.core_api.Article;
import intelligent.wiki.editor.core_api.MarkupText;
import intelligent.wiki.editor.core_api.Title;

import java.util.Objects;

/**
 * Default implementation for {@link Article}.
 *
 * @author Myroslav Rudnytskyi
 * @version 25.10.2015
 */
public class WikiArticle implements Article {
	private MarkupText text;
	private Title title;
	private ASTNode root;

	public WikiArticle(MarkupText text, ASTNode root, Title title) {
		setMarkupText(text);
		setASTRoot(root);
		setTitle(title);
	}

	@Override
	public MarkupText getMarkupText() {
		return text;
	}

	@Override
	public void setMarkupText(MarkupText text) {
		this.text = Objects.requireNonNull(text, "Null article markup!");
	}

	@Override
	public Title getTitle() {
		return title;
	}

	@Override
	public void setTitle(Title title) {
		this.title = Objects.requireNonNull(title, "Null article title!");
	}

	@Override
	public ASTNode getASTRoot() {
		return root;
	}

	@Override
	public void setASTRoot(ASTNode root) {
		this.root = Objects.requireNonNull(root, "Null article root!");
	}
}
