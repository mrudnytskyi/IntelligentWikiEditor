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

package intelligent.wiki.editor.gui.fx;

import javafx.scene.control.TreeItem;

/**
 * Interface, created to provide JavaFX TreeItem objects.
 * <a href=https://en.wikipedia.org/wiki/Factory_method_pattern>Factory method</a>
 * is used to instantiate necessary classes.
 *
 * @author Myroslav Rudnytskyi
 * @version 11.02.2016
 */
@FunctionalInterface
public interface TreeItemFactory<T extends Iterable<T>> {

	TreeItem<T> createItem(T node);

	default TreeItem<T> wrapNode(T node) {
		TreeItem<T> treeItem = createItem(node);
		node.forEach(child -> treeItem.getChildren().add(wrapNode(child)));
		return treeItem;
	}
}
