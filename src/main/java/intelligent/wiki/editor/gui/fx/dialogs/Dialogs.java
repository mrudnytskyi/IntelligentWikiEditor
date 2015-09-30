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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
 * @version 20.09.2015
 */
public class Dialogs {

	private static final ResourceBundle i18n = ResourceBundleFactory.getBundle(new Locale("uk", "ua"));

	private static final Logger log = Logger.getLogger(Dialogs.class.getName());

	/**
	 * Do not instantiate it, use static methods!
	 */
	private Dialogs() {
	}

	protected static Dialog prepareDialog(Dialog dialog, String title, String header, String content) {
		dialog.setTitle(title);
		dialog.setHeaderText(header);
		dialog.setContentText(content);
		return dialog;
	}

	protected static Dialog appendExpandable(Dialog dialog, TextArea textArea, String previewLabelCaption) {
		Label label = new Label(previewLabelCaption);

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

		dialog.getDialogPane().setExpandableContent(expContent);
		dialog.getDialogPane().setExpanded(true);

		return dialog;
	}

	private static Alert makeErrorDialog(String title, String header, String content) {
		log.severe(String.join(" ", header, content));
		return (Alert) prepareDialog(new Alert(AlertType.ERROR), title, header, content);
	}

	private static Alert makeQuestionDialog(String title, String header, String content) {
		return (Alert) prepareDialog(new Alert(AlertType.CONFIRMATION), title, header, content);
	}

	private static Alert makeInfoDialog(String title, String header, String content) {
		return (Alert) prepareDialog(new Alert(AlertType.INFORMATION), title, header, content);
	}

	/**
	 * Method is used to create (not show!) exit dialog.
	 *
	 * @return dialog with question, "OK" and "Cancel" buttons
	 */
	public static Alert makeExitDialog() {
		return makeQuestionDialog(
				i18n.getString("question-dialog.title"),
				i18n.getString("question-dialog.header-text.exit"),
				i18n.getString("question-dialog.content-text.exit"));
	}

	/**
	 * Method is used to show message when some exception occurs.
	 *
	 * @param e exception object
	 */
	public static void showError(Exception e) {
		Alert alert = makeErrorDialog(
				i18n.getString("error-dialog.title"),
				String.join("", e.getClass().getSimpleName(), " : ", e.getLocalizedMessage()),
				i18n.getString("error-dialog.header-text"));

		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String exceptionText = sw.toString();

		appendExpandable(alert, new TextArea(exceptionText), i18n.getString("error-dialog.stacktrace-title"));

		alert.show();
	}

	/**
	 * Method is used to stub not realized functions with error dialog.
	 */
	public static void showNotImplementedError() {
		Alert alert = makeErrorDialog(
				i18n.getString("error-dialog.title"),
				i18n.getString("error-dialog.header-text.not-implemented"),
				i18n.getString("error-dialog.content-text.not-implemented"));

		alert.show();
	}

	/**
	 * Method is used to show message about author.
	 */
	public static void showAboutDialog() {
		//TODO: expand message to show also used libraries and their license
		Alert alert = makeInfoDialog(
				i18n.getString("info-dialog.title"),
				i18n.getString("info-dialog.header-text.about"),
				"Copyright (c) Myroslav Rudnytskyi, Kyiv, Ukraine, 2015"
		);

		alert.show();
	}
}