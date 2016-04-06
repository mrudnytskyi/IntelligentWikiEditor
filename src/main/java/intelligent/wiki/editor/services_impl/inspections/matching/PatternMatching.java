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

package intelligent.wiki.editor.services_impl.inspections.matching;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Class for functional-style pattern matching for hierarchic objects. Simple usage:
 * <pre>
 *     import static PatternMatching.findIf;
 *     ...
 *     DomainObject root = new HierarchicDomainObject(); // complex object
 *     Collection&lt;DomainObject&gt; = findIf(obj -&gt; obj.someType()).andIf(obj -&gt; obj.requirement()).matchRecursiveFrom(root);
 *     // do smth with matched
 * </pre>
 * Note, that you will get only objects, which met all requirements, passed as parameters in {@link Pattern} objects.
 *
 * @param <T> type of hierarchic object to match. It is necessary to be able to get child objects using {@link Iterable}.
 * @author Myroslav Rudnytskyi
 * @version 27.02.2016
 */
public class PatternMatching<T extends Iterable<T>> {

	private final List<Pattern<T>> patterns = new LinkedList<>();

	/**
	 * To prevent external instantiation.
	 */
	private PatternMatching() {
	}

	/**
	 * Start point of pattern matching.
	 *
	 * @param pattern object, storing some requirements
	 * @return link to this object
	 */
	public static PatternMatching findIf(Pattern pattern) {
		PatternMatching pm = new PatternMatching();
		if (pattern != null) {
			pm.patterns.add(pattern);
		}
		return pm;
	}

	/**
	 * Method for adding more patterns.
	 *
	 * @param pattern object, storing some requirements
	 * @return link to this object
	 */
	public PatternMatching andIf(Pattern<T> pattern) {
		if (pattern != null) {
			patterns.add(pattern);
		}
		return this;
	}

	/**
	 * End point of pattern matching. Note, that parameter must be set.
	 *
	 * @param value object to start matching
	 * @return collection of objects, which met all requirements
	 */
	public Collection<T> matchRecursiveFrom(T value) {
		if (value == null) {
			throw new IllegalArgumentException("Null matching object!");
		}
		List<T> matched = new ArrayList<>();
		if (allPatternsMatched(value)) {
			matched.add(value);
		}
		for (T child : value) {
			matched.addAll(matchRecursiveFrom(child));
		}
		return matched;
	}

	private boolean allPatternsMatched(T value) {
		int matchedCount = 0;
		for (Pattern pattern : patterns) {
			if (pattern.matches(value)) {
				matchedCount++;
			}
		}
		return matchedCount == patterns.size();
	}
}
