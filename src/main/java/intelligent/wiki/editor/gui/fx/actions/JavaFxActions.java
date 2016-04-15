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
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Control;
import javafx.scene.control.TreeView;
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
	private final ReadOnlyBooleanProperty alwaysFalse = new SimpleBooleanProperty(false);
	private final BooleanProperty noSelection = new SimpleBooleanProperty();
	private final BooleanProperty noStringInClipboard = new SimpleBooleanProperty();

	public JavaFxActions(ObservableCodeArea area, TreeView tree) {
		Objects.requireNonNull(area, "Null code area!");
		Objects.requireNonNull(tree, "Null tree view!");
		initProperties(area);
		initActions(area, tree);
	}

	private void initActions(ObservableCodeArea area, TreeView tree) {
		Control areaControl = (Control) area;
		init(CUT, action().withText("menu.cut").withAccelerator("Shortcut+X").withIcon("images/cut_big.png")
				.withSmallIcon("images/cut_small.png").withTooltip("toolbar.cut")
				.withDisabled(noSelection).withHandler(event -> area.cut()).build());
		init(COPY, action().withText("menu.copy").withAccelerator("Shortcut+C").withIcon("images/copy_big.png")
				.withSmallIcon("images/copy_small.png").withTooltip("toolbar.copy")
				.withDisabled(noSelection).withHandler(event -> area.copy()).build());
		init(PASTE, action().withText("menu.paste").withAccelerator("Shortcut+V").withIcon("images/paste_big.png")
				.withSmallIcon("images/paste_small.png").withTooltip("toolbar.paste")
				.withDisabled(noStringInClipboard).withHandler(event -> area.paste()).build());
		init(REQUEST_FOCUS_TREE, action().withText("menu.request_focus_tree").withAccelerator("alt+f1")
				.withIcon("images/tree_big.png").withSmallIcon("images/tree_small.png")
				.withTooltip("toolbar.request_focus_tree").withDisabled(alwaysFalse)
				.withHandler(event -> requestFocusTree(tree)).build());
		init(REQUEST_FOCUS_TEXT, action().withText("menu.request_focus_text").withAccelerator("alt+f2")
				.withIcon("images/text_big.png").withSmallIcon("images/text_small.png")
				.withTooltip("toolbar.request_focus_text").withDisabled(alwaysFalse)
				.withHandler(event -> areaControl.requestFocus()).build());

//		init(, action().withText().withAccelerator().withIcon().withSmallIcon().withTooltip().withDisabled().withHandler().build());
//		init(, action().withText().withAccelerator().withIcon().withSmallIcon().withTooltip().withDisabled().withHandler().build());
//		init(, action().withText().withAccelerator().withIcon().withSmallIcon().withTooltip().withDisabled().withHandler().build());
//		init(, action().withText().withAccelerator().withIcon().withSmallIcon().withTooltip().withDisabled().withHandler().build());
//		init(, action().withText().withAccelerator().withIcon().withSmallIcon().withTooltip().withDisabled().withHandler().build());
//		init(, action().withText().withAccelerator().withIcon().withSmallIcon().withTooltip().withDisabled().withHandler().build());
//		init(, action().withText().withAccelerator().withIcon().withSmallIcon().withTooltip().withDisabled().withHandler().build());
//		init(, action().withText().withAccelerator().withIcon().withSmallIcon().withTooltip().withDisabled().withHandler().build());
//		init(, action().withText().withAccelerator().withIcon().withSmallIcon().withTooltip().withDisabled().withHandler().build());
//		init(, action().withText().withAccelerator().withIcon().withSmallIcon().withTooltip().withDisabled().withHandler().build());
//		init(, action().withText().withAccelerator().withIcon().withSmallIcon().withTooltip().withDisabled().withHandler().build());
//		init(, action().withText().withAccelerator().withIcon().withSmallIcon().withTooltip().withDisabled().withHandler().build());
//		init(, action().withText().withAccelerator().withIcon().withSmallIcon().withTooltip().withDisabled().withHandler().build());
//		init(, action().withText().withAccelerator().withIcon().withSmallIcon().withTooltip().withDisabled().withHandler().build());
	}

	private void initProperties(ObservableCodeArea area) {
		EventStreams.ticks(Duration.ofMillis(200)).subscribe(tick -> {
			noStringInClipboard.setValue(!Clipboard.getSystemClipboard().hasString());
			noSelection.setValue(area.getSelectedCode().isEmpty());
		});
	}

	private void init(ActionId id, JavaFxAction action) {
		actions.put(id, action);
	}

	private void requestFocusTree(TreeView tree) {
		if (tree.getRoot() != null) {
			tree.requestFocus();
			tree.getRoot().setExpanded(true);
			tree.getFocusModel().focus(0);
		}
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
