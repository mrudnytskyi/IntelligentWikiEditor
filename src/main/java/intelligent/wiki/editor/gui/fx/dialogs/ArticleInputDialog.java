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
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * Class, representing dialog for inserting article name.
 *
 * @author Myroslav Rudnytskyi
 * @version 30.09.2015
 */
//TODO rewrite with DRY and move interface part to fxml!
public class ArticleInputDialog extends Dialog<String> {

	private static final Logger log = Logger.getLogger(ArticleInputDialog.class.getName());
	private final TextField inputTextField = TextFields.createClearableTextField();
	private ResourceBundle i18n = ResourceBundleFactory.getBundle(new Locale("uk", "ua"));

	public ArticleInputDialog() {
		getDialogPane().getStyleClass().add("text-input-dialog");

		Dialogs.prepareDialog(this,
				i18n.getString("article-input-dialog.title"),
				i18n.getString("article-input-dialog.header"),
				i18n.getString("article-input-dialog.content"));

		initContent();
		initBehavior();
	}

	private void initContent() {
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 10, 10, 10));

		grid.add(new Label(i18n.getString("article-input-dialog.name-label-text")), 0, 0);
		grid.add(inputTextField, 1, 0);
		GridPane.setHgrow(inputTextField, Priority.ALWAYS);

		inputTextField.setPromptText(i18n.getString("article-input-dialog.prompt-text.article"));

		getDialogPane().setContent(grid);
	}

	private void initBehavior() {
		ButtonType okType = new ButtonType(
				i18n.getString("article-input-dialog.ok"), ButtonBar.ButtonData.OK_DONE);
		ButtonType cancelType = new ButtonType(
				i18n.getString("article-input-dialog.cancel"), ButtonBar.ButtonData.CANCEL_CLOSE);
		getDialogPane().getButtonTypes().addAll(okType, cancelType);

		Node okButton = getDialogPane().lookupButton(okType);
		okButton.setDisable(true);

		inputTextField.textProperty().addListener((observable, oldString, newString) -> {
			okButton.setDisable(newString.trim().isEmpty() || !isValidWikiLink(newString));
		});

		Platform.runLater(inputTextField::requestFocus);

		setResultConverter(pressedButton -> {
			if (pressedButton.getButtonData().isCancelButton()) {
				return null;
			}
			return inputTextField.getText();
		});

		new ValidationSupport().registerValidator(inputTextField, true, Validator.combine(
				Validator.createEmptyValidator(i18n.getString("article-input-dialog.empty")),
				(control, value) ->
						ValidationResult.fromMessageIf(
								control,
								i18n.getString("article-input-dialog.not-exists"),
								Severity.ERROR,
								!isValidWikiLink(value.toString())
						)
		));

		TextFields.bindAutoCompletion(inputTextField, param -> {
			if (!param.getUserText().isEmpty()) {
				try {
					return MediaWikiFacade.getAriclesStartingWith(param.getUserText());
				} catch (IOException e) {
					log.warning("Autocompletion failed!");
					log.severe(e.getMessage());
				}
			}
			return new ArrayList<>();
		});
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
