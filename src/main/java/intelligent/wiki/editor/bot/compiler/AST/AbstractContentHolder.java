package intelligent.wiki.editor.bot.compiler.AST;
/*
 * AbstractContentHolder.java	20.11.2014
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

/**
 * Class represents abstraction for every AST node with content.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 20.11.2014
 */
public abstract class AbstractContentHolder implements ContentHolder {
	
	protected final Content[] content;

	public AbstractContentHolder(Content[] content) {
		Objects.requireNonNull(content, "Content array can not be null!");
		this.content = content;
	}

	public Content[] getContent() {
		return content;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < content.length; i++) {
			sb.append(content[i].toString());
		}
		return sb.toString();
	}
}