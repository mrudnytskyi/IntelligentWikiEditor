/*
 * TextProcessor.java	20.01.2015
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
package bot.nlp.processors;

import bot.nlp.Snippet;

/**
 * Abstract class provides interface for every class, transforming 
 * {@link Snippet} data. Interface provide 2 methods for getting results:
 * in array or as single {@code Snippet} object.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 20.01.2015
 */
// TODO: make with Decorator pattern?
public abstract class TextProcessor {
	
	protected Snippet[] result;
	
	public abstract void process(Snippet snippet);
	
	public Snippet getResultSnippet() {
		return result[0];
	}
	
	public Snippet[] getResult() {
		return result;
	}
}