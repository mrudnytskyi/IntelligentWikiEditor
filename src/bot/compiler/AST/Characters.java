/*
 * Characters.java	19.11.2014
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
 * Class represents every simple char sequence.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 19.11.2014
 */
public class Characters implements Content {
	
	protected final CharSequence chars;

	public Characters(CharSequence chars) {
		Objects.requireNonNull(chars, "Char sequence can not be null!");
		this.chars = chars;
	}
	
	public CharSequence getCharacters() {
		return chars;
	}
	
	@Override
	public String toString() {
		return chars.toString();
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visitCharacters(this);
	}

	@Override
	public CharSequence getWikiSource() {
		return toString();
	}
}