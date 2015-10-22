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

/**
 * Class implements
 * <a href=https://dzone.com/articles/java-forum-nord-2015-keynote-by-adam-bien>fluent interface pattern</a>.
 * It was created to make template parameters using readable methods,
 * not with ugly constructor with a lot of parameters.
 *
 * @author Myroslav Rudnytskyi
 * @version 17.10.2015
 * @see TemplateParameter
 */
public class TemplateParameterBuilder {
	String name;
	String description;
	String def;
	String type;
	boolean required;
	boolean suggested;
	boolean deprecated;

	/**
	 * Start point of template parameter creation. Note, that it is necessary
	 * to set parameter name, so {@link IllegalArgumentException} will be
	 * thrown if method argument is <code>null</code> or empty.
	 *
	 * @param name name of template parameter
	 * @return builder object
	 * @throws IllegalArgumentException if parameter is null or empty
	 */
	public static TemplateParameterBuilder parameterWithName(String name) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Illegal template parameter: " + name);
		}

		TemplateParameterBuilder builder = new TemplateParameterBuilder();
		builder.name = name;
		return builder;
	}

	public TemplateParameterBuilder withDescription(String description) {
		this.description = replaceNullWithEmpty(description);
		return this;
	}

	public TemplateParameterBuilder withDefaultValue(String def) {
		this.def = replaceNullWithEmpty(def);
		return this;
	}

	public TemplateParameterBuilder withType(String type) {
		this.type = replaceNullWithEmpty(type);
		return this;
	}

	public TemplateParameterBuilder withRequiredFlag(boolean required) {
		this.required = required;
		return this;
	}

	public TemplateParameterBuilder withSuggestedFlag(boolean suggested) {
		this.suggested = suggested;
		return this;
	}

	public TemplateParameterBuilder withDeprecatedFlag(boolean deprecated) {
		this.deprecated = deprecated;
		return this;
	}

	/**
	 * End point of template parameter creation.
	 *
	 * @return full-constructed template parameter object
	 */
	public TemplateParameter build() {
		return new TemplateParameterImp(this);
	}

	private String replaceNullWithEmpty(String arg) {
		return arg == null ? "" : arg;
	}
}
