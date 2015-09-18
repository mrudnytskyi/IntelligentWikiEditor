package intelligent.wiki.editor.gui.fx;
/*
 * Dialogs.java	13.06.2015
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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 13.06.2015
 */
public class Dialogs {

	private static final ResourceBundle i18n = ResourceBundleFactory
			.getBundle(new Locale("uk", "ua"));

	private static final Logger log = Logger.getLogger(Dialogs.class.getName());

	private Dialogs() {}

	private static Alert makeErrorDialog(String title, String header,
			String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);

		Dialogs.log.severe(content);

		return alert;
	}

	public static void showError(Exception e) {
		Alert alert =
				Dialogs.makeErrorDialog(
						Dialogs.i18n.getString("error-dialog_title"),
						String.join("", e.getClass().getSimpleName(), " : ",
								e.getLocalizedMessage()),
						Dialogs.i18n.getString("error-dialog_header-text"));

		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String exceptionText = sw.toString();

		Label label =
				new Label(
						Dialogs.i18n.getString("error-dialog_stacktrace-title"));

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

	public static void showNotImplementedError() {
		Alert alert =
				Dialogs.makeErrorDialog(
						Dialogs.i18n.getString("error-dialog_title"),
						Dialogs.i18n
								.getString("error-dialog_header-text_not-implemented"),
						Dialogs.i18n
								.getString("error-dialog_content-text_not-implemented"));

		alert.show();
	}
}