/*
 * Copyright (C) 2016 Myroslav Rudnytskyi
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

package intelligent.wiki.editor.services_api.inspections;

import intelligent.wiki.editor.core_api.Article;

/**
 * Interface for "inspection" abstraction. This is special class, containing Wikipedia recommendations
 * and rules how perfect article should looks like. Main function of such classes is to decide if concrete
 * wiki article meets these requirements and notice possible problems if no.
 *
 * @author Myroslav Rudnytskyi
 * @version 27.02.2016
 */
public interface Inspection {

	/**
	 * Method to perform inspection of wiki article.
	 *
	 * @param article  article to inspect
	 * @param problems problems holder
	 */
	void inspect(Article article, Problems problems);
}
