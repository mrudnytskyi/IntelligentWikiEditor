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
package intelligent.wiki.editor.bot.compiler.AST;

import intelligent.wiki.editor.bot.compiler.Visitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Class represents template declaration node.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 22.11.2014
 */
public class TemplateDeclaration implements Content {

	protected final String name;

	protected final List<TemplateArgument> args = new ArrayList<>();

	public TemplateDeclaration(String name, TemplateArgument... args) {
		this(name, Arrays.asList(args));
	}

	public TemplateDeclaration(String name, List<TemplateArgument> args) {
		this.name = Objects.requireNonNull(name, "Template name null!");
		this.args.addAll(args);
	}

	public String getName() {
		return name;
	}

	public TemplateArgument getArgument(String label) {
		for (TemplateArgument current : args) {
			if (current.getName().equals(label)) {
				return current;
			}
		}
		return null;
	}

	public String getValue(String name) {
		TemplateArgument arg = getArgument(name);
		return arg == null ? null : arg.getValue();
	}

	public void putValue(String name, String value) {
		getArgument(name).setValue(value);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{{").append(name);
		if (!args.isEmpty()) {
			sb.append(System.lineSeparator());
			for (TemplateArgument current : args) {
				sb.append("|").append(current).append(System.lineSeparator());
			}
		}
		sb.append("}}");
		return sb.toString();
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visitTemplateDeclaration(this);
	}
}