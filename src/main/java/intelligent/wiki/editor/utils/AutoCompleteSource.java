package intelligent.wiki.editor.utils;
/*
 * AutoCompleteSource.java	27.04.2015
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

/**
 * Interface to provide data getter for autocomplete function in
 * {@link AutoCompletePanel}.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 27.04.2015
 */
public interface AutoCompleteSource {

	/**
	 * Returns {@link String strings} array, used for autocomplete function in
	 * {@link AutoCompletePanel}.
	 * 
	 * @param params
	 *            parameters to create strings array
	 * @return strings array
	 */
	String[] getSource(String... params);
}