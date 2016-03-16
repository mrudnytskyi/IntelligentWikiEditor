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

import intelligent.wiki.editor.core_api.MarkupText;

import java.util.Objects;

/**
 * Class represents object of <a href=https://en.wikipedia.org/wiki/Help:Wiki_markup>Wiki markup</a> and is default
 * implementation of {@link MarkupText}.
 *
 * @author Myroslav Rudnytskyi
 * @version 12.03.2016
 */
public class WikiMarkup implements MarkupText {

	private final StringBuilder markup;

	public WikiMarkup(String markup) {
		Objects.requireNonNull(markup, "Markup text can not be null!");
		this.markup = new StringBuilder(markup);
	}

	@Override
	public String getMarkup() {
		return markup.toString();
	}
}
