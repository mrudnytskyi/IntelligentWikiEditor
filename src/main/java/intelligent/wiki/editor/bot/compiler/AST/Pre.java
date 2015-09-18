package intelligent.wiki.editor.bot.compiler.AST;
/*
 * Pre.java	04.02.2015
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

import intelligent.wiki.editor.bot.compiler.Visitor;

/**
 * 
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 04.02.2015
 */
//TODO: creating Nowiki object in constructor?
public class Pre implements Content {

	private Nowiki nowiki;

	public Pre(Nowiki nowiki) {
		this.nowiki = nowiki;
	}

	public Nowiki getNowiki() {
		return nowiki;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visitPre(this);
	}

	@Override
	public CharSequence getWikiSource() {
		return toString();
	}
}