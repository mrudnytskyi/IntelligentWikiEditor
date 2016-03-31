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

import intelligent.wiki.editor.core_api.ASTNode;

/**
 * Interface for object, storing information about problems, found by inspectors.
 *
 * @author Myroslav Rudnytskyi
 * @version 02.03.2016
 */
public interface Problems {

	/**
	 * Store potential problem object for future processing.
	 *
	 * @param inspector inspector class object
	 * @param suspect   AST node, which is, probably, source of problem
	 */
	void registerProblem(Class<? extends Inspection> inspector, ASTNode suspect);
}
