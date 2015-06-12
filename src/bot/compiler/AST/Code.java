/*
 * Code.java	19.11.2014
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
package bot.compiler.AST;

import java.util.Objects;

import bot.compiler.Visitor;

/**
 * Class represents AST node of code snippet.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 19.11.2014
 */
// TODO: make language recognizing mechanism
public class Code extends Characters {

	public enum Language {
		JAVA5, CXX, C_SHARP, C, UNKNOWN;
	}

	protected final Language language;

	public Code(CharSequence chars, Language language) {
		super(chars);
		Objects.requireNonNull(language, "Language can not be null!");
		this.language = language;
	}

	public Language getLanguage() {
		return language;
	}

	@Override
	public String toString() {
		return String.join("", "<source",
				(!language.equals(Language.UNKNOWN) ? " lang = "
						+ language.toString().toLowerCase() : ""), ">",
				super.toString(), "</source>");
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visitCode(this);
	}

	@Override
	public CharSequence getWikiSource() {
		return toString();
	}
}