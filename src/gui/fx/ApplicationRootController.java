/*
 * ApplicationRootController.java	30.05.2015
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
package gui.fx;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.Clipboard;

import org.controlsfx.control.textfield.TextFields;

import bot.io.MediaWikiFacade;

/**
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 30.05.2015
 */
public class ApplicationRootController {

	private final Clipboard clipboard = Clipboard.getSystemClipboard();

	private final ResourceBundle i18n = ResourceBundleFactory
			.getBundle(new Locale("uk", "ua"));

	@FXML
	private Button cutButton;

	@FXML
	private MenuItem cutMenuItem;

	@FXML
	private Button copyButton;

	@FXML
	private MenuItem copyMenuItem;

	@FXML
	private Button pasteButton;

	@FXML
	private MenuItem pasteMenuItem;

	@FXML
	private TextArea text;

	@FXML
	public void initialize() {
		text.setWrapText(true);
		text.setOnMouseMoved(event -> {
			pasteButton.setDisable(!clipboard.hasString());
			pasteMenuItem.setDisable(!clipboard.hasString());
		});
		text.selectedTextProperty().addListener(listener -> {
			if (text.getSelectedText().length() != 0) {
				cutButton.setDisable(false);
				cutMenuItem.setDisable(false);
				copyButton.setDisable(false);
				copyMenuItem.setDisable(false);
			} else {
				cutButton.setDisable(true);
				cutMenuItem.setDisable(true);
				copyButton.setDisable(true);
				copyMenuItem.setDisable(true);
			}
		});
	}

	private TextInputDialog textInputDialogFactory() {
		TextInputDialog tid = new TextInputDialog();
		int popupWidth = 250;
		tid.getEditor().setMinWidth(popupWidth);
		tid.setHeaderText(i18n
				.getString("text-input-dialog_header-text_article"));
		tid.setContentText(i18n
				.getString("text-input-dialog_content-text_article"));
		tid.setTitle(i18n.getString("text-input-dialog_title_article"));
		tid.getEditor().setPromptText(
				i18n.getString("text-input-dialog_prompt-text_article"));
		return tid;
	}

	public void newAction() {
		Dialogs.showNotImplementedError();
	}

	public void openFileAction() {
		Dialogs.showNotImplementedError();
	}

	public void openURLAction() {
		TextInputDialog tid = textInputDialogFactory();

		//TODO autocomplete
		TextFields.bindAutoCompletion(tid.getEditor(), "TODO");
		tid.showAndWait();

		String result = tid.getResult();
		if ((result != null) && result.isEmpty()) {
			try {
				text.setText(MediaWikiFacade.getArticleText(tid.getResult()));
			} catch (IOException e) {
				Dialogs.showError(e);
			}
		}
	}

	public void saveAction() {
		Dialogs.showNotImplementedError();
	}

	public void saveAsAction() {
		Dialogs.showNotImplementedError();
	}

	public void closeAction() {
		Dialogs.showNotImplementedError();
	}

	public void cutAction() {
		text.cut();
	}

	public void copyAction() {
		text.copy();
	}

	public void pasteAction() {
		text.paste();
	}

	public void selectAllAction() {
		text.selectAll();
	}

	public void insertWikiLinkAction() {
		TextInputDialog tid = textInputDialogFactory();

		//TODO autocomplete
		TextFields.bindAutoCompletion(tid.getEditor(), "TODO");
		tid.showAndWait();

		String result = tid.getResult();
		if (result != null) {
			//@formatter:off
			String[] replacement = { "[[", tid.getResult(), 
				(text.getSelection().getLength() != 0 ? (result.isEmpty() ? 
					"" : "|") + text.getSelectedText() : ""), "]]" };
			//@formatter:on
			text.replaceSelection(String.join("", replacement));
		}
	}

	public void insertExternalLinkAction() {
		Dialogs.showNotImplementedError();
	}

	public void insertHeadingAction() {
		Dialogs.showNotImplementedError();
	}

	public void insertSnippetAction() {
		Dialogs.showNotImplementedError();
	}

	public void insertTemplateAction() {
		Dialogs.showNotImplementedError();
	}

	public void addCategoriesAction() {
		Dialogs.showNotImplementedError();
	}

	public void helpAction() {
		Dialogs.showNotImplementedError();
	}

	public void aboutAction() {
		Dialogs.showNotImplementedError();
	}

	public void quitAction() {
		Dialogs.showNotImplementedError();
	}
}