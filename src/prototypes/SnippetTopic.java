/*
 * SnippetTopic.java	20.01.2015
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
package prototypes;

import bot.nlp.Snippet;

/**
 * Enumeration contains topics of {@link Snippet}s.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 20.01.2015
 */
public enum SnippetTopic {
	
	INTRO,
	ETYMOLOGY,
	HISTORY,
	ECONOMY,
	POPULATION,
	CULTURE,
	ARHITECTURE,
	PEOPLE;
	
	@Override
	public String toString() {
		switch (this) {
		case CULTURE:
			return "Культура";
		case ECONOMY:
			return "Економіка";
		case HISTORY:
			return "Історія";
		case POPULATION:
			return "Населення";
		case PEOPLE:
			return "Персоналії";	
		case ARHITECTURE:
			return "Архітектура";
		case ETYMOLOGY:
			return "Етимологія";
		default:
			return "";
		}
	}
	
	public static int count() {
		return SnippetTopic.values().length;
	}
}