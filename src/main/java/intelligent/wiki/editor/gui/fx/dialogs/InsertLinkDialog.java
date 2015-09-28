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
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import org.apache.commons.validator.routines.UrlValidator;
import org.controlsfx.control.textfield.TextFields;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class, representing dialog for inserting data to construct link: url and caption.
 *
 * @author Myroslav Rudnytskyi
 * @version 27.09.2015
 */
public class InsertLinkDialog extends Dialog<String> {

	private final TextField urlTextField = TextFields.createClearableTextField();
	private final TextField captionTextField = TextFields.createClearableTextField();
	private final TextArea textArea = new TextArea();
	private ResourceBundle i18n = ResourceBundleFactory.getBundle(new Locale("uk", "ua"));

	/**
	 * @param captionText text, selected in text area is inserted to caption text field
	 */
	public InsertLinkDialog(String captionText) {
		getDialogPane().getStyleClass().add("text-input-dialog");

		Dialogs.prepareDialog(this,
				i18n.getString("insert-link-dialog.title"),
				i18n.getString("insert-link-dialog.header"),
				i18n.getString("insert-link-dialog.content"));

		initInputingGrid(captionText);

		Dialogs.appendExpandable(this, textArea, i18n.getString("insert-link-dialog.preview-label-text"));

		initBehavior();
	}

	private void initInputingGrid(String captionText) {
		urlTextField.setPromptText("http://example.com");

		if (captionText != null) {
			captionTextField.setText(captionText);
		}
		captionTextField.setPromptText("example.com");

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 10, 10, 10));

		grid.add(new Label(i18n.getString("insert-link-dialog.url-label-text")), 0, 0);
		grid.add(urlTextField, 1, 0);
		GridPane.setHgrow(urlTextField, Priority.ALWAYS);
		grid.add(new Label(i18n.getString("insert-link-dialog.caption-label-text")), 0, 1);
		grid.add(captionTextField, 1, 1);
		GridPane.setHgrow(captionTextField, Priority.ALWAYS);

		getDialogPane().setContent(grid);
	}

	private void initBehavior() {
		ButtonType okType = new ButtonType(
				i18n.getString("insert-link-dialog.ok"), ButtonBar.ButtonData.OK_DONE);
		ButtonType cancelType = new ButtonType(
				i18n.getString("insert-link-dialog.cancel"), ButtonBar.ButtonData.CANCEL_CLOSE);
		getDialogPane().getButtonTypes().addAll(okType, cancelType);

		Node okButton = getDialogPane().lookupButton(okType);
		okButton.setDisable(true);

		urlTextField.textProperty().addListener((observable, oldString, newString) -> {
			okButton.setDisable(newString.trim().isEmpty() || !isValidURL(newString));
			simulateInserting(okType);
		});

		captionTextField.textProperty().addListener((observable, oldString, newString) -> {
			simulateInserting(okType);
		});

		Platform.runLater(urlTextField::requestFocus);

		setResultConverter(pressedButton -> {
			if (pressedButton.getButtonData().isCancelButton()) {
				return null;
			}
			String url = urlTextField.getText().trim();
			String caption = captionTextField.getText().trim();
			String urlWithCaption = String.join(" ", url, caption).trim();
			return urlWithCaption.isEmpty() ? "" : String.join("", "[", urlWithCaption, "] ");
		});

		new ValidationSupport().registerValidator(urlTextField, true, Validator.combine(
				Validator.createEmptyValidator(i18n.getString("insert-link-dialog.empty-url")),
				(control, value) ->
						ValidationResult.fromMessageIf(
								control,
								i18n.getString("insert-link-dialog.not-valid-url"),
								Severity.ERROR,
								!isValidURL(value.toString())
						)
		));
	}

	private void simulateInserting(ButtonType okType) {
		textArea.clear();
		textArea.appendText(getResultConverter().call(okType));
	}

	private boolean isValidURL(String url) {
		return UrlValidator.getInstance().isValid(url);
	}
}
