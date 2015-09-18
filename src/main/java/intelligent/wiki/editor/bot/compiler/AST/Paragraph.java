package intelligent.wiki.editor.bot.compiler.AST;
/*
 * Paragraph.java	04.02.2015
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

import java.util.Arrays;

import intelligent.wiki.editor.bot.compiler.Visitor;

/**
 * Class represents some content, grouped together using special symbols.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 04.02.2015
 */
public class Paragraph extends AbstractContentHolder {
	
    public Paragraph(Content[] content) {
        super(content);
    }
    
    @Override
    public String toString() {
    	return Arrays.deepToString(getContent());
    }

	@Override
	public void accept(Visitor visitor) {
		visitor.visitParagraph(this);
	}

	@Override
	public CharSequence getWikiSource() {
		return toString();
	}
}