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

import java.util.Set;

/**
 * Implementation of <a href=https://en.wikipedia.org/wiki/Null_Object_pattern>Null Object pattern</a> for
 * {@link Article} abstraction.
 *
 * @author Myroslav Rudnytskyi
 * @version 16.03.2016
 */
public class EmptyArticle implements Article {

	@Override
	public MarkupText getMarkupText() {
		return new MarkupText() {
			@Override
			public String getMarkup() {
				return "";
			}

			@Override
			public boolean contains(Set<String> words) {
				return false;
			}
		};
	}

	@Override
	public void setMarkupText(MarkupText text) {
	}

	@Override
	public Title getTitle() {
		return () -> "";
	}

	@Override
	public void setTitle(Title title) {
	}

	@Override
	public ASTNode getASTRoot() {
		return null;
	}

	@Override
	public void setASTRoot(ASTNode root) {
	}
}
