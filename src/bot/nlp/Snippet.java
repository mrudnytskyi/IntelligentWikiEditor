/*
 * Snippet.java	19.01.2015
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
package bot.nlp;

import java.util.Objects;

/**
 * Class, created to encapsulate information about text fragment (snippet),
 * source, where it was taken and it's tags.
 * <p>
 * Note, that snippet tags is represented as {@link String} array, containing
 * names of categories, which can be used to describe this text fragment.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.2 19.01.2015
 */
public class Snippet {

	private final String text;

	private final String source;

	private final String[] tags;

	/**
	 * Constructs snippet using specified information about text, source and
	 * tags. Note, that <code>source</code> and <code>tags</code> can be
	 * <code>null</code>, but try to fill all fields, please.
	 * 
	 * @param text
	 *            text fragment, also called snippet
	 * @param source
	 *            source, where snippet was taken
	 * @param tags
	 *            array of tags to categorize this snippet
	 */
	public Snippet(String text, String source, String[] tags) {
		Objects.requireNonNull(text, "Text fragment can not be null!");
		this.text = text;
		this.source = source;
		this.tags = tags;
	}

	public String getText() {
		return text;
	}

	public String getSource() {
		return source;
	}

	public String[] getTags() {
		return tags;
	}

	@Override
	public String toString() {
		StringBuilder sb =
				new StringBuilder(String.join("", "Snippet: [", text,
						"] from [", source, "] and tags: ["));
		for (String str : tags) {
			sb.append(str).append(", ");
		}
		// delete last comma and whitespace
		sb.delete(sb.length() - 2, sb.length()).append("]");
		return sb.toString();
	}
}