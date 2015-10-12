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
package intelligent.wiki.editor.bot.io.wiki;

/**
 * Enumeration, containing namespace constants, such as "0" stands for Main
 * namespace, "10" for templates namespace (starting with "Template:" preffix)
 * and so on. Note, that only necessary in code constants is presented here.
 *
 * @author Myroslav Rudnytskyi
 * @version 23.04.2015
 */
enum WikiNamespace {
	MAIN(0),
	TEMPLATE(10),
	CATEGORY(14);

	private final int code;

	WikiNamespace(int code) {
		this.code = code;
	}

	/**
	 * Returns namespace constants in {@link String} representation.
	 *
	 * @return namespace constant for this enum constant
	 */
	@Override
	public String toString() {
		return String.valueOf(code);
	}
}
