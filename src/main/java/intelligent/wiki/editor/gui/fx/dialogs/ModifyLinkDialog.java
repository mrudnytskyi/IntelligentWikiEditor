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
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import org.apache.commons.validator.routines.UrlValidator;
import org.controlsfx.control.textfield.TextFields;

/**
 * Class, representing dialog for inserting data to construct link: url and caption.
 *
 * @author Myroslav Rudnytskyi
 * @version 27.09.2015
 */
public class ModifyLinkDialog extends InputDialog {

	private final TextField urlInput = TextFields.createClearableTextField();
	private final TextField captionInput = TextFields.createClearableTextField();

	protected ModifyLinkDialog(String urlText, String captionText, String titleId, String headerId, String contentId) {
		super(titleId, headerId, contentId);
		getDialogPane().getStyleClass().add("text-input-dialog");

		initContent();
		buildPreview();
		initButtons();
		initInputControls(urlText, captionText);
	}

	private void initInputControls(String urlText, String captionText) {
		if (urlText != null) {
			urlInput.setText(urlText);
		}
		urlInput.setPromptText("http://example.com");

		if (captionText != null) {
			captionInput.setText(captionText);
		}
		captionInput.setPromptText("example.com");

		Platform.runLater(urlInput::requestFocus);

		buildValidation(urlInput, "insert-link-dialog.empty-url", "insert-link-dialog.not-valid-url");
	}

	private void initButtons() {
		Node okButton = getDialogPane().lookupButton(okType);
		okButton.setDisable(true);

		urlInput.textProperty().addListener((observable, oldString, newString) -> {
			okButton.setDisable(newString.trim().isEmpty() || !validate(newString));
			fillPreview();
		});

		captionInput.textProperty().addListener((observable, oldString, newString) -> {
			fillPreview();
		});
	}

	private void initContent() {
		content.add(new Label(i18n.getString("insert-link-dialog.url-label-text")), 0, 0);
		content.add(urlInput, 1, 0);
		GridPane.setHgrow(urlInput, Priority.ALWAYS);

		content.add(new Label(i18n.getString("insert-link-dialog.caption-label-text")), 0, 1);
		content.add(captionInput, 1, 1);
		GridPane.setHgrow(captionInput, Priority.ALWAYS);
	}

	@Override
	protected boolean validate(String url) {
		return UrlValidator.getInstance().isValid(url);
	}

	@Override
	public String getInputtedResult() {
		String url = urlInput.getText().trim();
		String caption = captionInput.getText().trim();
		String urlWithCaption = String.join(" ", url, caption).trim();
		return urlWithCaption.isEmpty() ? "" : String.join("", "[", urlWithCaption, "] ");
	}
}
