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

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;

/**
 * Class, representing dialog for inserting data to construct wiki link: article name and caption.
 *
 * @author Myroslav Rudnytskyi
 * @version 25.09.2015
 */
public class ModifyWikiLinkDialog extends InputDialog {

	private final TextField linkInput = TextFields.createClearableTextField();
	private final TextField captionInput = TextFields.createClearableTextField();

	protected ModifyWikiLinkDialog(String linkText, String captionText, String titleId, String headerId, String contentId) {
		super(titleId, headerId, contentId);
		getDialogPane().getStyleClass().add("text-input-dialog");

		initContent();
		initButtons();
		initInputControls(linkText, captionText);
		buildPreview();
	}

	private void initContent() {
		content.add(new Label(i18n.getString("insert-wiki-link-dialog.name-label-text")), 0, 0);
		content.add(linkInput, 1, 0);
		GridPane.setHgrow(linkInput, Priority.ALWAYS);

		content.add(new Label(i18n.getString("insert-wiki-link-dialog.caption-label-text")), 0, 1);
		content.add(captionInput, 1, 1);
		GridPane.setHgrow(captionInput, Priority.ALWAYS);
	}

	private void initInputControls(String linkText, String captionText) {
		if (linkText != null) {
			linkInput.setText(linkText);
		}

		if (captionText != null) {
			captionInput.setText(captionText);
		}

		Platform.runLater(linkInput::requestFocus);

		buildValidation(linkInput, "insert-wiki-link-dialog.empty", "insert-wiki-link-dialog.not-exists");

		buildAutocompletion(linkInput);
	}

	private void initButtons() {
		Node okButton = getDialogPane().lookupButton(okType);
		okButton.setDisable(true);

		ChangeListener<String> onEnteringText = (observable, oldString, newString) -> {
			okButton.setDisable(linkInput.getText().trim().isEmpty());
			fillPreview();
		};
		linkInput.textProperty().addListener(onEnteringText);
		captionInput.textProperty().addListener(onEnteringText);
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
		String name = linkInput.getText().trim();
		String caption = captionInput.getText().trim();
		String nameWithCaption =
				(name.equals(caption) || caption.trim().isEmpty()) ? name : String.join("|", name, caption);
		return nameWithCaption.isEmpty() ? "" : String.join("", "[[", nameWithCaption, "]] ");
	}
}
