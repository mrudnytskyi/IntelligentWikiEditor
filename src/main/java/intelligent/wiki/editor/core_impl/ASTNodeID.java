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

package intelligent.wiki.editor.core_impl;

/**
 * Class encapsulates unique id and was created to simplify checking nodes
 * equality. As id is unique, you may check only it, so no additional checks
 * are necessary. Note, that class is NOT thread-safe!
 *
 * @author Myroslav Rudnytskyi
 * @version 11.02.2016
 */
public class ASTNodeID {
	private static int counter = 0;
	private final int id;

	public ASTNodeID() {
		id = createId();
	}

	private int createId() {
		return counter++;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (o instanceof ASTNodeID) {
			ASTNodeID other = (ASTNodeID) o;
			return id == other.id;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public String toString() {
		return String.valueOf(id);
	}
}
