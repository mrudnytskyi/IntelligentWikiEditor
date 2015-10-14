/*
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
package intelligent.wiki.editor.bot.io.wiki;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

/**
 * Enumeration, containing language codes, such as "uk" stands for
 * Ukrainian, "en" for English and so on.
 *
 * @author Myroslav Rudnytskyi
 * @version 27.10.2014
 */
enum Language {
	UKRAINIAN("uk", "Категорія:", "Шаблон:"),
	ENGLISH("en", "Category:", "Template:");

	private final String code;
	private final String categoryPrefix;
	private final String templatePrefix;

	Language(String code, String categoryPrefix, String templatePrefix) {
		this.code = code;
		this.categoryPrefix = categoryPrefix;
		this.templatePrefix = templatePrefix;
	}

	static Language createLanguage(Locale locale) {
		assert locale != null;
		String languageCode = locale.getLanguage();

		Optional<Language> searchResult = Arrays.stream(values()).filter(current -> languageCode.equals(current.toString())).findFirst();
		if (searchResult.isPresent()) {
			return searchResult.get();
		}
		throw new RuntimeException("Add new language constant for " + languageCode);
	}

	String getCode() {
		return code;
	}

	String getCategoryPrefix() {
		return categoryPrefix;
	}

	String getTemplatePrefix() {
		return templatePrefix;
	}

	@Override
	public String toString() {
		return code;
	}
}
