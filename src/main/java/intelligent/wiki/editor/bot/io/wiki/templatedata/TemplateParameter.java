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
package intelligent.wiki.editor.bot.io.wiki.templatedata;

import java.util.Arrays;
import java.util.Optional;

/**
 * Interface for template parameter object. Implemented by {@link TemplateParameterImp}.
 * It is useful option to enhance testability, as it can easily be mocked or stubbed.
 * See <a href=https://www.mediawiki.org/wiki/Extension:TemplateData>"Param object" table</a> for details.
 *
 * @author Myroslav Rudnytskyi
 * @version 17.10.2015
 */
public interface TemplateParameter {

	/**
	 * Returns string, that is situated before '=' sign in template declaration.
	 *
	 * @return template parameter name
	 */
	String getName();

	/**
	 * Returns brief description of the parameter, for users to know which to pick from an option list.
	 *
	 * @return template parameter description
	 */
	String getDescription();

	/**
	 * Returns if parameter is required for the template to work.
	 *
	 * @return <code>true</code> if this parameter must be specified
	 */
	boolean isRequired();

	/**
	 * Returns if parameter is suggested for the template to be useful.
	 *
	 * @return <code>true</code> if this parameter should be specified
	 */
	boolean isSuggested();

	/**
	 * Returns if parameter is deprecated.
	 *
	 * @return <code>true</code> if this parameter should not be used
	 */
	boolean isDeprecated();

	/**
	 * Returns default value used by the template if no value is assigned to the parameter.
	 *
	 * @return template parameter default value
	 */
	String getDefault();

	/**
	 * Returns type of the parameter, for (soft) type hinting.
	 *
	 * @return template parameter type
	 */
	TemplateParameterType getType();

	enum TemplateParameterType {

		/**
		 * Any numerical value (without decimal points or thousand separators)
		 */
		NUMBER("number"),

		/**
		 * Any textual value
		 */
		STRING("string"),

		/**
		 * Short text field - use for names, labels, and other short-form fields
		 */
		LINE("line"),

		/**
		 * A boolean value ('1' for true, '0' for false, '' for unknown)
		 */
		BOOLEAN("boolean"),

		/**
		 * A date in ISO 8601 format, e.g. "2014-05-09" or "2014-05-09T16:01:12Z"
		 */
		DATE("date"),

		/**
		 * A valid MediaWiki page name for the current wiki.
		 * Doesn't have to exist, but if not, should be a valid page name which could be created
		 */
		WIKI_PAGE_NAME("wiki-page-name"),

		/**
		 * A valid MediaWiki file name for the current wiki.
		 * Doesn't have to exist, but if not, should be a valid file name which could be uploaded.
		 * Should not include the namespace prefix (e.g. "Foo.svg" not "File:Foo.svg")
		 */
		WIKI_FILE_NAME("wiki-file-name"),

		/**
		 * A valid MediaWiki user name for the current wiki.
		 * Doesn't have to exist, but if not, should be a valid user name which could be created.
		 * Should not include the namespace prefix (e.g. "Foo" not "User:Foo")
		 */
		WIKI_USER_NAME("wiki-user-name"),

		/**
		 * Page content in wikitext, such as text style, links, images, etc
		 */
		CONTENT("content"),

		/**
		 * Raw wikitext that should not be treated as standalone content because it is unbalanced
		 */
		UNBALANCED_WIKITEXT("unbalanced-wikitext"),

		/**
		 * Assumed type if not set
		 */
		UNKNOWN("unknown");

		private final String str;

		TemplateParameterType(String str) {
			this.str = str;
		}

		static TemplateParameterType parse(String str) {
			Optional<TemplateParameterType> searchResult =
					Arrays.stream(values())
							.filter(current -> current.toString().equals(str))
							.findFirst();
			if (searchResult.isPresent()) {
				return searchResult.get();
			}
			return UNKNOWN;
		}

		@Override
		public String toString() {
			return str;
		}
	}
}
