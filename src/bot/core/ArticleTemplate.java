/*
 * ArticleTemplate.java	19.01.2015
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

import java.util.Objects;

import utils.StringArrayList;

/**
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 19.01.2015
 */
public class ArticleTemplate {
	
	private final StringArrayList[] keywords;
	
	protected ArticleTemplate(StringArrayList[] keywords) {
		Objects.requireNonNull(keywords, "Keywords can not be null!");
		this.keywords = keywords;
	}
	
	public boolean containsWord(String word, int index) {
		return keywords[index].contains(word);
	}
}