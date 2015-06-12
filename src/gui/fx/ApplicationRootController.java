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
import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.Clipboard;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import org.controlsfx.control.textfield.TextFields;

import bot.io.MediaWikiFacade;

/**
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 30.05.2015
 */
public class ApplicationRootController {

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
		text.setWrapText(true);
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

	private void showError(Exception e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Look, an exception!");
		alert.setContentText(e.getMessage());

		// Create expandable Exception.
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String exceptionText = sw.toString();

		Label label = new Label("The exception stacktrace was:");

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(expContent);

		alert.show();
	}

	public void openFileAction() {
		notImpl();
	}

	public void openURLAction() {
		TextInputDialog tid = textInputDialogFactory();
		//TODO
		tid.getEditor().setPromptText("Start entering article name...");
		TextFields.bindAutoCompletion(tid.getEditor(), "TODO");

		tid.showAndWait();
		if (tid.getResult() != null) {
			try {
				text.setText(MediaWikiFacade.getArticleText(tid.getResult()));
			} catch (IOException e) {
				showError(e);
			}
		}
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
		TextInputDialog tid = textInputDialogFactory();
		//TODO
		tid.getEditor().setPromptText("Start entering article name...");
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

	private TextInputDialog textInputDialogFactory() {
		TextInputDialog tid = new TextInputDialog();
		int popupWidth = 250;
		tid.getEditor().setMinWidth(popupWidth);
		return tid;
	}
}