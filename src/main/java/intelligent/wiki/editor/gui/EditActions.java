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

package intelligent.wiki.editor.gui;

/**
 * Interface provides methods for editing content in {@link CodeArea}.
 *
 * @author Myroslav Rudnytskyi
 * @version 13.04.2016
 */
public interface EditActions {

	void append(String text);

	void insert(String text, int pos);

	void delete(int start, int end);

	void clear();

	void replace(int start, int end, String text);

	void replaceSelection(String text);

	void moveSelectedText(int pos);
}
