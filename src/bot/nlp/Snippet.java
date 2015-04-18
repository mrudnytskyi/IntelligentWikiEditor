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

import utils.MutableString;

/**
 * Class, created to encapsulate information about text fragment (snippet),
 * source, where it was taken and it's topic.
 * <p>
 * Note, that snippet topic is represented as {@link String} array, containing
 * names of categories, which can be used to describe this text fragment.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.2 19.01.2015
 */
public class Snippet {
	
	private final String text;
	
	private final String source;
	
	private final String[] topic;
	
	/**
	 * Constructs snippet using specified information about text, source and 
	 * topic. Note, that <code>source</code> and <code>topic</code> can be
	 * <code>null</code>, but try to fill all fields, please.
	 * 
	 * @param text		text fragment, also called snippet
	 * @param source	source, where snippet was taken
	 * @param topic		array of tags to categorize this snippet
	 */
	public Snippet(String text, String source, String[] topic) {
		Objects.requireNonNull(text, "Text fragment can not be null!");
		this.text = text;
		this.source = source;
		this.topic = topic;
	}
	
	public String getText() {
		return text;
	}
	
	public String getSource() {
		return source;
	}
	
	public String[] getTopic() {
		return topic;
	}
	
	@Override
	public String toString() {
		MutableString ms = new MutableString("Snippet: [", text, "] from [",
				source, "] and topic: [");
		for (String str : topic) {
			ms.append(str, ", ");
		}
		// delete last comma and whitespace
		ms.sb.delete(ms.length() - 2, ms.length()).append("]");
		return ms.toString();
	}
}