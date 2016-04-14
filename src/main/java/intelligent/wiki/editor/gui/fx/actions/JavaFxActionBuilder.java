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

import javafx.beans.value.ObservableBooleanValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Class implements
 * <a href=https://dzone.com/articles/java-forum-nord-2015-keynote-by-adam-bien>fluent interface pattern</a>.
 * It was created to make actions using readable methods, not with ugly constructor with a lot of parameters.
 *
 * @author Myroslav Rudnytskyi
 * @version 08.04.2016
 * @see JavaFxAction
 */
public class JavaFxActionBuilder {
	String textID;
	String acceleratorName;
	String iconFilename;
	String smallIconFilename;
	String tooltipID;
	ObservableBooleanValue disabled;
	EventHandler<ActionEvent> handler;

	private JavaFxActionBuilder() {
	}

	/**
	 * Start point of action creation.
	 */
	public static JavaFxActionBuilder action() {
		return new JavaFxActionBuilder();
	}

	public JavaFxActionBuilder withText(String textID) {
		this.textID = textID;
		return this;
	}

	public JavaFxActionBuilder withAccelerator(String acceleratorName) {
		this.acceleratorName = acceleratorName;
		return this;
	}

	public JavaFxActionBuilder withIcon(String iconFilename) {
		this.iconFilename = iconFilename;
		return this;
	}

	public JavaFxActionBuilder withSmallIcon(String smallIconFilename) {
		this.smallIconFilename = smallIconFilename;
		return this;
	}

	public JavaFxActionBuilder withTooltip(String tooltipID) {
		this.tooltipID = tooltipID;
		return this;
	}

	public JavaFxActionBuilder withDisabled(ObservableBooleanValue disabled) {
		this.disabled = disabled;
		return this;
	}

	public JavaFxActionBuilder withHandler(EventHandler<ActionEvent> handler) {
		this.handler = handler;
		return this;
	}

	/**
	 * End point of action creation.
	 */
	public JavaFxAction build() {
		return new JavaFxAction(this);
	}
}
