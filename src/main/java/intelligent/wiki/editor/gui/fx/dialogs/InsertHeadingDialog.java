/*
 * Copyright (C) 2015 Myroslav Rudnytskyi
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

package intelligent.wiki.editor.gui.fx.dialogs;

import intelligent.wiki.editor.gui.fx.ResourceBundleFactory;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class, representing dialog to insert heading.
 *
 * @author Myroslav Rudnytskyi
 * @version 27.09.2015
 */
//TODO rewrite with DRY and move interface part to fxml!
public class InsertHeadingDialog extends Dialog<String> {

	private final String heading;
	private final ToggleGroup group = new ToggleGroup();
	private ResourceBundle i18n = ResourceBundleFactory.getBundle(new Locale("uk", "ua"));

	/**
	 * @param heading text, selected in text area is inserted to caption of every radio button
	 */
	public InsertHeadingDialog(String heading) {
		this.heading = heading == null ? "" : heading.trim();
		getDialogPane().getStyleClass().add("choice-dialog");

		Dialogs.prepareDialog(this,
				i18n.getString("insert-heading-dialog.title"),
				i18n.getString("insert-heading-dialog.header"),
				i18n.getString("insert-heading-dialog.content"));

		initContent();
		initBehavior();
	}

	private void initBehavior() {
		ButtonType okType = new ButtonType(
				i18n.getString("insert-heading-dialog.ok"), ButtonBar.ButtonData.OK_DONE);
		ButtonType cancelType = new ButtonType(
				i18n.getString("insert-heading-dialog.cancel"), ButtonBar.ButtonData.CANCEL_CLOSE);
		getDialogPane().getButtonTypes().addAll(okType, cancelType);

		setResultConverter(pressedButton -> {
			if (pressedButton.getButtonData().isCancelButton()) {
				return null;
			}
			return ((RadioButton) group.getSelectedToggle()).getText().trim();
		});
	}

	private void initContent() {
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 10, 10, 10));

		generateRadioButtons(grid);

		getDialogPane().setContent(grid);
	}

	private void generateRadioButtons(GridPane grid) {
		for (int i = 0; i < 5; i++) {
			char[] header = new char[i + 2];
			Arrays.fill(header, '=');
			String headerStr = String.valueOf(header);

			RadioButton rb = new RadioButton(String.join(" ", headerStr, heading, headerStr));
			rb.setToggleGroup(group);
			grid.add(rb, 0, i);

			if (i == 0) {
				rb.setSelected(true);
			}
		}
	}
}
