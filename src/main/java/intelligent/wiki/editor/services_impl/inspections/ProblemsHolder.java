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

package intelligent.wiki.editor.services_impl.inspections;

import intelligent.wiki.editor.core_api.ASTNode;
import intelligent.wiki.editor.services_api.inspections.Inspection;
import intelligent.wiki.editor.services_api.inspections.Problems;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Myroslav Rudnytskyi
 * @version 31.03.2016
 */
public class ProblemsHolder implements Problems {
	private Map<Class<? extends Inspection>, ASTNode> problems = new HashMap<>();

	@Override
	public void registerProblem(Class<? extends Inspection> inspector, ASTNode suspect) {
		problems.put(inspector, suspect);
	}

	@Override
	public boolean isEmpty() {
		return problems.isEmpty();
	}
}
