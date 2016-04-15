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

import intelligent.wiki.editor.io_api.WikiOperations;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Class, representing dialog for inserting article name.
 *
 * @author Myroslav Rudnytskyi
 * @version 30.09.2015
 */
public class ArticleInputDialog extends InputDialog {

	private final WikiOperations wiki;
	private final TextField articleNameInput = TextFields.createClearableTextField();

	protected ArticleInputDialog(WikiOperations wiki, ResourceBundle i18n) {
		super("article-input-dialog.title", "article-input-dialog.header", "article-input-dialog.content", i18n);
		this.wiki = Objects.requireNonNull(wiki, "Null wiki operations!");
		getDialogPane().getStyleClass().add("text-input-dialog");

		initContent();
		initButtons();
		initInputControls();
	}

	private void initContent() {
		content.add(new Label(i18n.getString("article-input-dialog.name-label-text")), 0, 0);
		content.add(articleNameInput, 1, 0);
		GridPane.setHgrow(articleNameInput, Priority.ALWAYS);
	}

	private void initButtons() {
		Node okButton = getDialogPane().lookupButton(okType);
		okButton.setDisable(true);

		articleNameInput.textProperty().addListener((observable, oldString, newString) -> {
			okButton.setDisable(newString.trim().isEmpty() || !validate(newString));
		});
	}

	private void initInputControls() {
		articleNameInput.setPromptText(i18n.getString("article-input-dialog.prompt-text.article"));

		Platform.runLater(articleNameInput::requestFocus);

		buildValidation(articleNameInput, "article-input-dialog.empty", "article-input-dialog.not-exists");

		buildArticleAutocompletion();
	}

	@Override
	protected boolean validate(String name) {
		try {
			return wiki.existsArticle(name);
		} catch (IOException e) {
			log.warning("Validation failed!");
			log.severe(e.getMessage());
		}
		// if an exception occurs - skip validation
		return true;
	}

	@Override
	public String getInputtedResult() {
		return articleNameInput.getText();
	}

	private void buildArticleAutocompletion() {
		TextFields.bindAutoCompletion(articleNameInput, param -> {
			if (!param.getUserText().isEmpty()) {
				try {
					return wiki.getArticlesStartingWith(param.getUserText(), 10);
				} catch (IOException e) {
					log.warning("Autocompletion failed!");
					log.severe(e.getMessage());
				}
			}
			return Collections.emptyList();
		});
	}
}
