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
package intelligent.wiki.editor.gui.fx;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * Class, containing methods to interact with user using different dialogs:
 * input, error and so on.
 *
 * @author Myroslav Rudnytskyi
 * @version 19.09.2015
 */
public class Dialogs {

	private static final ResourceBundle i18n = ResourceBundleFactory.getBundle(new Locale("uk", "ua"));

	private static final Logger log = Logger.getLogger(Dialogs.class.getName());

	/**
	 * Do not instantiate it, use static methods!
	 */
	private Dialogs() {
	}

	private static Alert makeErrorDialog(String title, String header, String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);

		log.severe(String.join(" ", header, content));

		return alert;
	}

	private static TextInputDialog makeTextInputDialog(String title, String header, String content) {
		TextInputDialog tid = new TextInputDialog();
		tid.setHeaderText(header);
		tid.setContentText(content);
		tid.setTitle(title);

		log.info("Created text input dialog!");

		return tid;
	}

	/**
	 * Method is used to show message when some exception occurs.
	 *
	 * @param e exception object
	 */
	public static void showError(Exception e) {
		Alert alert = makeErrorDialog(
				i18n.getString("error-dialog_title"),
				String.join("", e.getClass().getSimpleName(), " : ", e.getLocalizedMessage()),
				i18n.getString("error-dialog_header-text"));

		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String exceptionText = sw.toString();

		Label label = new Label(i18n.getString("error-dialog_stacktrace-title"));

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

		alert.getDialogPane().setExpandableContent(expContent);

		alert.show();
	}

	/**
	 * Method is used to stub not realized functions.
	 */
	public static void showNotImplementedError() {
		Alert alert = makeErrorDialog(
				i18n.getString("error-dialog_title"),
				i18n.getString("error-dialog_header-text_not-implemented"),
				i18n.getString("error-dialog_content-text_not-implemented"));

		alert.show();
	}

	/**
	 * Method is used to create (not show!) text input dialogs.
	 *
	 * @return dialog with text input field
	 */
	public static TextInputDialog makeTextInputDialog() {
		TextInputDialog tid = makeTextInputDialog(
				i18n.getString("text-input-dialog_title_article"),
				i18n.getString("text-input-dialog_header-text_article"),
				i18n.getString("text-input-dialog_content-text_article")
		);

		// dirty hack with popup menu
		int popupWidth = 250;
		tid.getEditor().setMinWidth(popupWidth);

		tid.getEditor().setPromptText(i18n.getString("text-input-dialog_prompt-text_article"));

		return tid;
	}
}