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

import intelligent.wiki.editor.gui.CodeArea;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ContextMenu;

/**
 * Interface represents suitable API for JavaFx clients of {@link CodeArea}.
 *
 * @author Myroslav Rudnytskyi
 * @version 13.04.2016
 */
public interface ObservableCodeArea extends CodeArea {

	ObservableValue<String> codeProperty();

	ObservableValue<String> selectedCodeProperty();

	ObjectProperty<ContextMenu> contentMenuProperty();
}
