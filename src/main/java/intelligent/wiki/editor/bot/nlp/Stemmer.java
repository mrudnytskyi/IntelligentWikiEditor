package intelligent.wiki.editor.bot.nlp;
/*
 * Stemmer.java	22.04.2015
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import intelligent.wiki.editor.utils.StringArrayList;

/**
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 22.04.2015
 */
public class Stemmer {
	
	private static final String[] WORD_ENDINGS = new String("а ам ами ах та"
			+ " в вав вавсь вався вала валась валася вали вались валися вало"
			+ " валось валося вати ватись ватися всь вся е еві ем ею є ємо"
			+ " ємось ємося ється єте єтесь єтеся єш єшся єю и ив ий ила или"
			+ " ило илося им ими имо имось имося ите итесь итеся ити ить"
			+ " иться их иш ишся й ймо ймось ймося йсь йся йте йтесь йтеся і"
			+ " ів ій ім імо ість істю іть ї ла лась лася ло лось лося ли"
			+ " лись лися о ові овував овувала овувати ого ої ок ом ому осте"
			+ " ості очка очкам очками очках очки очків очкові очком очку"
			+ " очок ою ти тись тися у ував увала увати ь ці ю юст юсь юся"
			+ " ють ються я ям ями ях").split(" ");
	
	private static final String[] STABLE_ENDINGS = "ер ск".split(" ");
	
	private static final List<String> STABLE_EXCLUSIONS = new StringArrayList();
	
	private static final Map<String, String> EXCLUSIONS = 
			new HashMap<String, String>();
	
	private static final Map<String, String> CHANGE_ENDINGS = 
			new HashMap<String, String>();
	
	static {
		STABLE_EXCLUSIONS.add("баядер");	STABLE_EXCLUSIONS.add("неначе");
		STABLE_EXCLUSIONS.add("беатріче");	STABLE_EXCLUSIONS.add("одначе");
		STABLE_EXCLUSIONS.add("віче");		STABLE_EXCLUSIONS.add("паче");
		STABLE_EXCLUSIONS.add("наче");
		
		EXCLUSIONS.put("відер", "відр");	EXCLUSIONS.put("був", "бува");

		CHANGE_ENDINGS.put("аче", "ак");
		CHANGE_ENDINGS.put("іче", "ік");
		CHANGE_ENDINGS.put("йовував", "йов");
		CHANGE_ENDINGS.put("йовувала", "йов");
		CHANGE_ENDINGS.put("йовувати", "йов");
		CHANGE_ENDINGS.put("ьовував", "ьов");
		CHANGE_ENDINGS.put("ьовувала", "ьов");
		CHANGE_ENDINGS.put("ьовувати", "ьов");
		CHANGE_ENDINGS.put("цьовував", "ц");
		CHANGE_ENDINGS.put("цьовувала", "ц");
		CHANGE_ENDINGS.put("цьовувати", "ц");
		CHANGE_ENDINGS.put("ядер", "ядр");
	}
	
	private Stemmer() {}
	
	public static String stem(String word) {
		String newWord = word;
		newWord = word.replace("а́", "а");
		newWord = word.replace("е́", "е");
		newWord = word.replace("є́", "є");
		newWord = word.replace("и́", "и");
		newWord = word.replace("і́", "і");
		newWord = word.replace("ї́", "ї");
		newWord = word.replace("о́", "о");
		newWord = word.replace("у́", "у");
		newWord = word.replace("ю́", "ю");
		newWord = word.replace("я́", "я");
		if (newWord.length() <= 2) {
			return newWord;
		}
		if (STABLE_EXCLUSIONS.contains(newWord)) {
			return newWord;
		}
		if (EXCLUSIONS.keySet().contains(newWord)) {
			return EXCLUSIONS.get(newWord);
		}
		for (String eow : CHANGE_ENDINGS.keySet()) {
			if (newWord.endsWith(eow)) {
				return newWord.substring(0, newWord.length() - eow.length())
						+ CHANGE_ENDINGS.get(eow);
			}
		}
		for (String eow : STABLE_ENDINGS) {
			if (newWord.endsWith(eow)) {
				return newWord;
			}
		}
		for (String eow : WORD_ENDINGS) {
			if (newWord.endsWith(eow)) {
				String trimmed = newWord.substring(0,
						newWord.length() - eow.length());
				if (trimmed.length() > 2) {
					return trimmed;
				}
			}
		}
		return newWord;
	}
}