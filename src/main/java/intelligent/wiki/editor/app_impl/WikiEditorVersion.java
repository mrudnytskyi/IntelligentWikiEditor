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

package intelligent.wiki.editor.app_impl;

import intelligent.wiki.editor.app_api.Version;

/**
 * @author Myroslav Rudnytskyi
 * @version 30.01.2016
 */
public class WikiEditorVersion implements Version {

	@Override
	public int getMajorVersion() {
		return 0;
	}

	@Override
	public int getMinorVersion() {
		return 1;
	}

	@Override
	public int getBuildNumber() {
		return 0;
	}

	@Override
	public String toString() {
		return String.format("%d.%d.%d", getMajorVersion(), getMinorVersion(), getBuildNumber());
	}
}
