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
import intelligent.wiki.editor.gui.fx.ResourceBundleFactory;
import javafx.beans.value.ObservableBooleanValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class represents action, that can be used to create JavaFx menus and toolbars.
 *
 * @author Myroslav Rudnytskyi
 * @version 07.04.2016
 */
public class JavaFxAction implements Action {
	private static final ResourceBundle i18n = ResourceBundleFactory.getBundle(new Locale("uk", "ua"));
	private final String text;
	private final KeyCombination accelerator;
	private final Image icon;
	private final Image smallIcon;
	private final Tooltip tooltip;
	private final ObservableBooleanValue disabled;
	private final EventHandler<ActionEvent> handler;

	public JavaFxAction(JavaFxActionBuilder builder) {
		text = !isNullOrEmpty(builder.textID) ? i18n.getString(builder.textID) : null;
		accelerator = !isNullOrEmpty(builder.acceleratorName) ? KeyCombination.keyCombination(builder.acceleratorName) : null;
		icon = !isNullOrEmpty(builder.iconFilename) ? new Image(builder.iconFilename) : null;
		smallIcon = !isNullOrEmpty(builder.smallIconFilename) ? new Image(builder.smallIconFilename) : null;
		tooltip = !isNullOrEmpty(builder.tooltipID) ? new Tooltip(i18n.getString(builder.tooltipID)) : null;
		disabled = builder.disabled;
		handler = builder.handler;
	}

	private static boolean isNullOrEmpty(String str) {
		return str == null || str.isEmpty();
	}

	public static MenuItem toMenuItem(Action action) {
		return toMenuItem(MenuItem.class, (JavaFxAction) action);
	}

	public static MenuItem toMenuItem(Class<? extends MenuItem> menuItemClass, JavaFxAction action) {
		MenuItem menuItem;
		try {
			menuItem = menuItemClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		menuItem.setText(action.getText());
		menuItem.setAccelerator(action.getAccelerator());
		menuItem.setGraphic(new ImageView(action.getSmallIcon()));
		menuItem.disableProperty().bind(action.getDisabled());
		// menu items can not have tooltips
		menuItem.setOnAction(action.getHandler());
		return menuItem;
	}

	public static Button toButton(Action action) {
		return toButton(Button.class, (JavaFxAction) action);
	}

	private static Button toButton(Class<Button> buttonClass, JavaFxAction action) {
		Button button;
		try {
			button = buttonClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		// buttons can not have text and accelerators
		button.setGraphic(new ImageView(action.getIcon()));
		button.disableProperty().bind(action.getDisabled());
		button.setTooltip(action.getTooltip());
		button.setOnAction(action.getHandler());
		button.setPrefSize(48.0, 48.0);
		return button;
	}

	public String getText() {
		return text;
	}

	public KeyCombination getAccelerator() {
		return accelerator;
	}

	public Image getIcon() {
		return icon;
	}

	public Image getSmallIcon() {
		return smallIcon;
	}

	public ObservableBooleanValue getDisabled() {
		return disabled;
	}

	public Tooltip getTooltip() {
		return tooltip;
	}

	public EventHandler<ActionEvent> getHandler() {
		return handler;
	}
}
