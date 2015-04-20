/*
 * CategoryDeclaration.java	24.09.2014
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

import utils.MutableString;
import bot.compiler.Visitor;

/**
 * Class represents category declaration node.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 24.09.2014
 */
// TODO: use object of Category class instead String
public class CategoryDeclaration implements Content {
	
	protected final CharSequence category;
	
	public CategoryDeclaration(CharSequence category) {
		Objects.requireNonNull(category, "Category object can not be null!");
		this.category = category;
	}
	
	public CharSequence getCategory() {
		return category;
	}
	
	@Override
	public String toString() {
		MutableString ms = new MutableString("[[Категорія:", category, "]]");
		return ms.toString();
	}

	@Override
	public String getWikiSource() {
		return toString();
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visitCategoryDeclaration(this);
	}
}