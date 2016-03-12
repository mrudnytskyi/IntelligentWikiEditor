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

import intelligent.wiki.editor.core_api.Title;

/**
 * Class represents object of wiki article title and is default implementation of {@link Title}.
 *
 * @author Myroslav Rudnytskyi
 * @version 12.03.2016
 */
public class WikiArticleTitle implements Title {

	private final String name;

	public WikiArticleTitle(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
}
