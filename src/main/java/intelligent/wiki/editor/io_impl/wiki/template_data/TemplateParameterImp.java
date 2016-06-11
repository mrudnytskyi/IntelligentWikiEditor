/*
 * Copyright (C) 2016 Myroslav Rudnytskyi
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
package intelligent.wiki.editor.io_impl.wiki.template_data;

import intelligent.wiki.editor.io_api.wiki.template_data.TemplateParameter;

import java.util.Objects;

/**
 * Default implementation of {@link TemplateParameter}, which will be used in most cases.
 *
 * @author Myroslav Rudnytskyi
 * @version 29.04.2015
 */
public class TemplateParameterImp implements TemplateParameter {
	private final String name;
	private final String description;
	private final String def;
	private final TemplateParameterType type;
	private final boolean required;
	private final boolean suggested;
	private final boolean deprecated;

	/**
	 * Constructor for creating template parameter using special builder.
	 *
	 * @param builder special object to reduce these constructor parameters count
	 */
	public TemplateParameterImp(TemplateParameterBuilder builder) {
		Objects.requireNonNull(builder, "Template parameterWithName builder can not be null!");
		this.name = builder.name;
		this.description = builder.description;
		this.def = builder.def;
		this.type = TemplateParameterType.parse(builder.type);
		this.required = builder.required;
		this.suggested = builder.suggested;
		this.deprecated = builder.deprecated;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getDefault() {
		return def;
	}

	@Override
	public TemplateParameterType getType() {
		return type;
	}

	@Override
	public boolean isRequired() {
		return required;
	}

	@Override
	public boolean isSuggested() {
		return suggested;
	}

	@Override
	public boolean isDeprecated() {
		return deprecated;
	}
}