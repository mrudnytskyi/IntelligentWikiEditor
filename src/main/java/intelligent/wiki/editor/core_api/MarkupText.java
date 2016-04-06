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

import intelligent.wiki.editor.core_impl.WikiMarkup;

import java.util.Set;

/**
 * Interface encapsulates information about markup text representation.
 *
 * @author Myroslav Rudnytskyi
 * @version 12.03.2016
 * @see WikiMarkup
 */
public interface MarkupText {

	/**
	 * @return markup text in string representation
	 */
	String getMarkup();

	/**
	 * @param words set, storing words to search for
	 * @return {@code true} if text contains any word from set
	 */
	boolean contains(Set<String> words);
}
