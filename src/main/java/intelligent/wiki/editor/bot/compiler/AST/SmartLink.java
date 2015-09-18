package intelligent.wiki.editor.bot.compiler.AST;
/*
 * SmartLink.java	19.11.2014
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

import java.util.Objects;

import intelligent.wiki.editor.bot.compiler.Visitor;

/**
 * Class represents smart link in article, such as [[Java]] or [[Java|Java]].
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 19.11.2014
 */
public class SmartLink implements Content {

	protected final CharSequence destination;

	protected final CharSequence caption;

	public SmartLink(CharSequence destination) {
		this(destination, null);
	}

	public SmartLink(CharSequence destination, CharSequence caption) {
		Objects.requireNonNull(destination, "Destination can not be null!");
		this.destination = destination;
		this.caption = caption;
	}

	public CharSequence getDestination() {
		return destination;
	}

	public CharSequence getCaption() {
		return caption;
	}

	@Override
	public String toString() {
		return String.join("", "[[", destination, (caption != null ? "|"
				+ caption : ""), "]]");
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visitSmartLink(this);
	}

	@Override
	public CharSequence getWikiSource() {
		return toString();
	}
}