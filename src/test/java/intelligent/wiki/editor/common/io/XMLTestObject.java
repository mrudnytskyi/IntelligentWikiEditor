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

package intelligent.wiki.editor.common.io;

import java.util.Arrays;
import java.util.List;

/**
 * Object for testing XML serialization.
 *
 * @author Myroslav Rudnytskyi
 * @version 30.01.2016
 */
public class XMLTestObject {

	private int number = 1994;

	private double[] array = {2.1, 0.3};

	private List<String> list = Arrays.asList("This", "is", "test", "String", "~!@#$%^&|:\"<>`\\';/.,");

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof XMLTestObject)) {
			return false;
		}
		XMLTestObject casted = (XMLTestObject) object;
		return casted.number == number &&
				Arrays.equals(casted.array, array) &&
				casted.list.equals(list);
	}
}
