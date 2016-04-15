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

package intelligent.wiki.editor.gui.fx.actions;

import intelligent.wiki.editor.gui.actions.Action;
import intelligent.wiki.editor.gui.actions.ActionId;
import intelligent.wiki.editor.gui.actions.Actions;
import intelligent.wiki.editor.gui.fx.ObservableCodeArea;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.input.Clipboard;
import org.reactfx.EventStreams;

import java.time.Duration;
import java.util.EnumMap;
import java.util.Objects;

import static intelligent.wiki.editor.gui.actions.ActionId.*;
import static intelligent.wiki.editor.gui.fx.actions.JavaFxActionBuilder.action;

/**
 * Action holder implementation for JavaFx.
 *
 * @author Myroslav Rudnytskyi
 * @version 13.04.2016
 */
public class JavaFxActions implements Actions {

	private final EnumMap<ActionId, JavaFxAction> actions = new EnumMap<>(ActionId.class);

	public JavaFxActions(ObservableCodeArea area) {
		BooleanProperty cantCutOrCopy = new SimpleBooleanProperty();
		BooleanProperty cantPaste = new SimpleBooleanProperty();
		EventStreams.ticks(Duration.ofMillis(200)).subscribe(tick -> {
			cantPaste.setValue(!Clipboard.getSystemClipboard().hasString());
			cantCutOrCopy.setValue(area.getSelectedCode().isEmpty());
		});
		init(CUT, action().withText("menu.cut").withAccelerator("Shortcut+X").withIcon("images/cut_big.png")
				.withSmallIcon("images/cut_small.png").withTooltip("toolbar.cut")
				.withDisabled(cantCutOrCopy).withHandler(event -> area.cut()).build());
		init(COPY, action().withText("menu.copy").withAccelerator("Shortcut+C").withIcon("images/copy_big.png")
				.withSmallIcon("images/copy_small.png").withTooltip("toolbar.copy")
				.withDisabled(cantCutOrCopy).withHandler(event -> area.copy()).build());
		init(PASTE, action().withText("menu.paste").withAccelerator("Shortcut+V").withIcon("images/paste_big.png")
				.withSmallIcon("images/paste_small.png").withTooltip("toolbar.paste")
				.withDisabled(cantPaste).withHandler(event -> area.paste()).build());
	}

	private void init(ActionId id, JavaFxAction action) {
		actions.put(id, action);
	}

	@Override
	public Action get(ActionId id) {
		Objects.requireNonNull(id, "Null action id!");
		Action cachedAction = actions.get(id);
		if (cachedAction == null) {
			throw new AssertionError("Do not forget add action in holder!");
		}
		return cachedAction;
	}
}
