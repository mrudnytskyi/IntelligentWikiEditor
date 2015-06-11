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

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.Clipboard;

import org.controlsfx.control.textfield.TextFields;

/**
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 30.05.2015
 */
public class ApplicationRootController {

	/**
	 * Control FX issue
	 */
	private static final int POPUP_WIDTH = 250;

	private final Clipboard cb = Clipboard.getSystemClipboard();

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
		text.setOnMouseMoved(event -> {
			pasteButton.setDisable(!cb.hasString());
			pasteMenuItem.setDisable(!cb.hasString());
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

	public void newAction() {
		notImpl();
	}

	private void notImpl() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Not implemented error");
		alert.setContentText("Sorry, not implemented yet");
		alert.show();
	}

	public void openFileAction() {
		notImpl();
	}

	public void openURLAction() {
		notImpl();
	}

	public void saveAction() {
		notImpl();
	}

	public void saveAsAction() {
		notImpl();
	}

	public void closeAction() {
		notImpl();
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
		TextInputDialog tid = new TextInputDialog();
		tid.getEditor().setMinWidth(ApplicationRootController.POPUP_WIDTH);
		//TODO
		tid.getEditor().setPromptText("Start entering...");
		TextFields.bindAutoCompletion(tid.getEditor(), "TODO");

		tid.showAndWait();
		if (tid.getResult() != null) {
			text.replaceSelection(String.join(
					"",
					"[[",
					tid.getResult(),
					(text.getSelection().getLength() != 0 ? "|"
							+ text.getSelectedText() : ""), "]]"));
		}
	}

	public void insertExternalLinkAction() {
		notImpl();
	}

	public void insertHeadingAction() {
		notImpl();
	}

	public void insertSnippetAction() {
		notImpl();
	}

	public void insertTemplateAction() {
		notImpl();
	}

	public void addCategoriesAction() {
		notImpl();
	}

	public void helpAction() {
		notImpl();
	}

	public void aboutAction() {
		notImpl();
	}

	public void quitAction() {
		notImpl();
	}
}