/*
 * ArticleProcessor.java	23.04.2015
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
package bot.core.processors;

import bot.core.WikiArticle;

/**
 * Interface for all possible article processors realizations. Provide only one
 * method: {@link #process(WikiArticle) process()}.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 23.04.2015
 */
public interface ArticleProcessor {

	/**
	 * Makes some useful changes in {@link WikiArticle} object.
	 * 
	 * @param target
	 *            article object to modify
	 * @return modified article
	 */
	WikiArticle process(WikiArticle target);
}