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

import intelligent.wiki.editor.core_api.ASTNode;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TreeItem;

/**
 * Interface, specifying API to observe article updates in JavaFX application.
 *
 * @author Myroslav Rudnytskyi
 * @version 25.10.2015
 */
public interface ObservableArticle {

	/**
	 * @return property for markup text (plain content) of article
	 */
	StringProperty textProperty();

	/**
	 * @return property for title of article
	 */
	StringProperty titleProperty();

	/**
	 * @return property for tree root (AST content) of article
	 */
	ObjectProperty<TreeItem<ASTNode>> rootProperty();
}
