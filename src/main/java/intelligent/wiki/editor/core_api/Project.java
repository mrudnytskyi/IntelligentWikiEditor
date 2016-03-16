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
 * This interface provides <a href=https://en.wikipedia.org/wiki/Facade_pattern>facade</a> to model package
 * for easy manipulating complex business objects.
 *
 * @author Myroslav Rudnytskyi
 * @version 12.03.2016
 * @see intelligent.wiki.editor.core_impl.WikiProject
 */
public interface Project {

	void makeArticle(String title, String text);

	Article getArticle();

	Parser getParser();
}
