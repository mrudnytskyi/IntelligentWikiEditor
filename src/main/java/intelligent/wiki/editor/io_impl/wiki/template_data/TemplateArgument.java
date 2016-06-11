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

/**
 * Class, representing template argument: {@link TemplateParameter template parameter}
 * with some value, divided using '=' (equals) sign.
 *
 * @author Myroslav Rudnytskyi
 * @version 23.10.2015
 */
public class TemplateArgument implements TemplateParameter {
	private final TemplateParameter parameter;
	private final String name;
	private String value;

	public TemplateArgument(TemplateParameter parameter) {
		this.parameter = parameter;
		name = null;
	}

	public TemplateArgument(String name, String value) {
		this.name = name;
		setValue(value);
		parameter = null;
	}

	public TemplateArgument(String name) {
		this(name, "");
	}

	@Override
	public String getName() {
		return isParameterProvided() ? parameter.getName() : name;
	}

	@Override
	public String getDescription() {
		return isParameterProvided() ? parameter.getDescription() : "";
	}

	@Override
	public String getDefault() {
		return isParameterProvided() ? parameter.getDefault() : "";
	}

	@Override
	public TemplateParameterType getType() {
		return isParameterProvided() ? parameter.getType() : TemplateParameterType.UNKNOWN;
	}

	@Override
	public boolean isRequired() {
		return isParameterProvided() && parameter.isRequired();
	}

	@Override
	public boolean isSuggested() {
		return isParameterProvided() && parameter.isSuggested();
	}

	@Override
	public boolean isDeprecated() {
		return isParameterProvided() && parameter.isDeprecated();
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return getName() + " = " + (isValueProvided() ? value : getDefault());
	}

	private boolean isValueProvided() {
		return value != null;
	}

	private boolean isParameterProvided() {
		return parameter != null;
	}
}
