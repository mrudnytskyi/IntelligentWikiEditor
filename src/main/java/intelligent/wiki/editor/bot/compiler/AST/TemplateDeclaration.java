package intelligent.wiki.editor.bot.compiler.AST;
/*
 * TemplateDeclaration.java	22.11.2014
 * Copyright (C) 2014 Myroslav Rudnytskyi
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

import intelligent.wiki.editor.bot.compiler.Visitor;
import intelligent.wiki.editor.bot.io.wiki.templatedata.TemplateParameter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

/**
 * Class represents template declaration node.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 22.11.2014
 */
public class TemplateDeclaration implements Content {

	protected final String name;

	protected final TemplateParameter[] parameters;

	protected final Map<TemplateParameter, String> parametersValues =
			new LinkedHashMap<TemplateParameter, String>();

	public TemplateDeclaration(String name, TemplateParameter ... params) {
		this.name = Objects.requireNonNull(name, "Template name null!");
		parameters = params;
	}

	public String getName() {
		return name;
	}

	public TemplateParameter getParameter(String label) {
		for (TemplateParameter parameter : parameters) {
			if (parameter.getName().equals(label)) {
				return parameter;
			}
		}
		return null;
	}

	public String getValue(String name) {
		TemplateParameter parameter = getParameter(name);
		if (parameter == null) {
			return null;
		}
		return parametersValues.get(name);
	}

	public boolean putValue(String name, String value) {
		TemplateParameter parameter = getParameter(name);
		if (parameter == null) {
			return false;
		}
		parametersValues.put(parameter, value);
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{{" + name);
		if (!parametersValues.isEmpty()) {
			for (Entry<TemplateParameter, String> e : parametersValues
					.entrySet()) {
				String value = e.getValue();
				if ((value == null) || value.equals("null")) {
					if (e.getKey().getDefault() != null) {
						value = e.getKey().getDefault();
					}
				} else {
					sb.append("|").append(e.getKey().getName()).append("=")
							.append(value);
				}
			}
		}
		sb.append("}}");
		return sb.toString();
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visitTemplateDeclaration(this);
	}

	@Override
	public CharSequence getWikiSource() {
		return toString();
	}
}