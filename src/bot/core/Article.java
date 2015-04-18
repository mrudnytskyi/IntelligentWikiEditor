/*
 * Article.java	20.01.2015
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
package bot.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import bot.nlp.Snippet;
import bot.nlp.SnippetTopic;

/**
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 20.01.2015
 */
// TODO: write other article parts
public class Article {
	
	private Map<SnippetTopic, List<Snippet>> content =
			new HashMap<SnippetTopic, List<Snippet>>();
	
	public void add(Snippet snippet, SnippetTopic topic) {
		if (content.get(topic) == null) {
			content.put(topic, new ArrayList<Snippet>());
		}
		content.get(topic).add(snippet);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Iterator<Entry<SnippetTopic, List<Snippet>>> i = 
				content.entrySet().iterator();
		while (i.hasNext()) {
			Entry<SnippetTopic, List<Snippet>> current = i.next();
			sb.append("\r\n== ");
			sb.append(current.getKey());
			sb.append(" ==\r\n");
			Iterator<Snippet> iter = current.getValue().iterator();
			while (iter.hasNext()) {
				sb.append(iter.next().getText());
			}
		}
		return sb.toString();
	}
}