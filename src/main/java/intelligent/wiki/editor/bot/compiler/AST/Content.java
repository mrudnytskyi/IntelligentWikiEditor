package intelligent.wiki.editor.bot.compiler.AST;
/*
 * Content.java	20.11.2014
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
 * Abstraction for every AST node.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 20.11.2014
 */
public interface Content {

	void accept(Visitor visitor);

	CharSequence getWikiSource();
}