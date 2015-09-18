package intelligent.wiki.editor.bot.nlp.summarize;
/*
 * SummarizeDecider.java	12.11.2014
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

/**
 * Implementing this interface allows every Summarizer decide if concrete
 * {@link CharSequence} is suitable for text summary.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 12.11.2014
 */
public interface SummarizeDecider {

	/**
	 * Method for deciding if concrete {@link CharSequence} is suitable for
	 * adding it into results pool. 
	 * 
	 * @param charSequence	char sequence for deciding
	 * @return true if parameter is suitable for summary, false - otherwise
	 */
	boolean isSuitable(CharSequence charSequence);
}
