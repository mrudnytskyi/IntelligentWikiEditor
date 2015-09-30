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

import intelligent.wiki.editor.bot.io.MediaWikiFacade;
import intelligent.wiki.editor.gui.fx.ResourceBundleFactory;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import org.controlsfx.control.textfield.TextFields;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * Class, representing dialog for inserting data to construct wiki link: article name and caption.
 *
 * @author Myroslav Rudnytskyi
 * @version 25.09.2015
 */
//TODO rewrite with DRY and move interface part to fxml!
public class InsertWikiLinkDialog extends Dialog<String> {

	private static final Logger log = Logger.getLogger(InsertWikiLinkDialog.class.getName());
	private final TextField nameTextField = TextFields.createClearableTextField();
	private final TextField captionTextField = TextFields.createClearableTextField();
	private final TextArea textArea = new TextArea();
	private ResourceBundle i18n = ResourceBundleFactory.getBundle(new Locale("uk", "ua"));

	/**
	 * @param text text, selected in text area is inserted to name and caption text fields
	 */
	public InsertWikiLinkDialog(String text) {
		getDialogPane().getStyleClass().add("text-input-dialog");

		Dialogs.prepareDialog(this,
				i18n.getString("insert-wiki-link-dialog.title"),
				i18n.getString("insert-wiki-link-dialog.header"),
				i18n.getString("insert-wiki-link-dialog.content"));

		initContent(text);

		Dialogs.appendExpandable(this, textArea, i18n.getString("insert-wiki-link-dialog.preview-label-text"));

		initBehavior();
	}

	private void initContent(String text) {
		if (text != null) {
			captionTextField.setText(text);
			nameTextField.setText(text);
		}

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 10, 10, 10));

		grid.add(new Label(i18n.getString("insert-wiki-link-dialog.name-label-text")), 0, 0);
		grid.add(nameTextField, 1, 0);
		GridPane.setHgrow(nameTextField, Priority.ALWAYS);
		grid.add(new Label(i18n.getString("insert-wiki-link-dialog.caption-label-text")), 0, 1);
		grid.add(captionTextField, 1, 1);
		GridPane.setHgrow(captionTextField, Priority.ALWAYS);

		getDialogPane().setContent(grid);
	}

	private void initBehavior() {
		ButtonType okType = new ButtonType(
				i18n.getString("insert-wiki-link-dialog.ok"), ButtonBar.ButtonData.OK_DONE);
		ButtonType cancelType = new ButtonType(
				i18n.getString("insert-wiki-link-dialog.cancel"), ButtonBar.ButtonData.CANCEL_CLOSE);
		getDialogPane().getButtonTypes().addAll(okType, cancelType);

		Node okButton = getDialogPane().lookupButton(okType);
		okButton.setDisable(true);

		ChangeListener<String> onEnteringText = (observable, oldString, newString) -> {
			okButton.setDisable(nameTextField.getText().trim().isEmpty());
			simulateInserting(okType);
		};

		nameTextField.textProperty().addListener(onEnteringText);
		captionTextField.textProperty().addListener(onEnteringText);

		Platform.runLater(nameTextField::requestFocus);

		setResultConverter(pressedButton -> {
			if (pressedButton.getButtonData().isCancelButton()) {
				return null;
			}
			String name = nameTextField.getText().trim();
			String caption = captionTextField.getText().trim();
			String nameWithCaption =
					(name.equals(caption) || caption.trim().isEmpty()) ? name : String.join("|", name, caption);
			return nameWithCaption.isEmpty() ? "" : String.join("", "[[", nameWithCaption, "]] ");
		});

		new ValidationSupport().registerValidator(nameTextField, true, Validator.combine(
				Validator.createEmptyValidator(i18n.getString("insert-wiki-link-dialog.empty")),
				(control, value) ->
						ValidationResult.fromMessageIf(
								control,
								i18n.getString("insert-wiki-link-dialog.not-exists"),
								Severity.ERROR,
								!isValidWikiLink(value.toString())
						)
		));

		Dialogs.appendAutocompletion(nameTextField);
	}

	private void simulateInserting(ButtonType okType) {
		textArea.clear();
		textArea.appendText(getResultConverter().call(okType));
	}

	private boolean isValidWikiLink(String name) {
		if (name == null || name.isEmpty()) {
			return false;
		}
		try {
			return MediaWikiFacade.existArticle(name);
		} catch (IOException e) {
			log.warning("Validation failed!");
			log.severe(e.getMessage());
		}
		// if an exception occurs - skip validation
		return true;
	}
}
