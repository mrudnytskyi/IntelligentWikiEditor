package intelligent.wiki.editor.bot.compiler.AST;
/*
 * Italics.java	19.11.2014
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

/**
 * Class represents content, written with <i>emphasis font</i>.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 19.11.2014
 */
public class Italics extends AbstractContentHolder {

	public Italics(Content[] content) {
		super(content);
	}

	@Override
	public String toString() {
		return String.join("", "''", super.toString(), "''");
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visitItalics(this);
	}

	@Override
	public CharSequence getWikiSource() {
		return toString();
	}
}