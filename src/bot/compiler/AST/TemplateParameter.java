/*
 * TemplateParameter.java	29.04. 2015
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
package bot.compiler.AST;

import java.util.Objects;

import utils.MutableString;

/**
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 29.04. 2015
 */
public class TemplateParameter {

	public enum TemplateParameterType {
		NUMBER("number"), STRING("string"), LINE("line"), BOOLEAN("boolean"), DATE(
				"date"), WIKI_PAGE_NAME("wiki-page-name"), WIKI_FILE_NAME(
				"wiki-file-name"), WIKI_USER_NAME("wiki-user-name"), CONTENT(
				"content"), UNBALANCED_WIKITEXT("unbalanced-wikitext"), UNKNOWN(
				"");

		private final String str;

		private TemplateParameterType(String str) {
			this.str = str;
		}

		@Override
		public String toString() {
			return str;
		}

		public static TemplateParameterType parse(String str) {
			TemplateParameterType[] values = values();
			for (int i = 0; i < values.length; i++) {
				if (str.equals(values[i])) {
					return values[i];
				}
			}
			return UNKNOWN;
		}
	}

	private final String name;

	private final String description;

	private final String def;

	private final TemplateParameterType type;

	private final boolean required;

	private final boolean suggested;

	public TemplateParameter(String name, String description, String def,
			String type, boolean required, boolean suggested) {

		this.name = Objects.requireNonNull(name,
				"Template parameter name can not be null!");
		this.description = description;
		this.def = def;
		this.type = TemplateParameterType.parse(type);
		this.required = required;
		this.suggested = suggested;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getDefault() {
		return def;
	}

	public TemplateParameterType getType() {
		return type;
	}

	public boolean isRequired() {
		return required;
	}

	public boolean isSuggested() {
		return suggested;
	}

	@Override
	public String toString() {
		MutableString ms = new MutableString("name ", name, ", description ",
				description, ", def ", def, ", type ", type.toString(),
				", required ", String.valueOf(required), ", suggested ",
				String.valueOf(suggested), "\n");
		return ms.toString();
	}
}