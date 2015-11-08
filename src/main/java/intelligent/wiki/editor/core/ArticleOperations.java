/*
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

package intelligent.wiki.editor.core;

import javafx.beans.property.StringProperty;

/**
 * Interface, specifying set of operations with article in application.
 *
 * @author Myroslav Rudnytskyi
 * @version 25.10.2015
 */
public interface ArticleOperations {

	StringProperty articleTextProperty();

	StringProperty articleTitleProperty();

}
