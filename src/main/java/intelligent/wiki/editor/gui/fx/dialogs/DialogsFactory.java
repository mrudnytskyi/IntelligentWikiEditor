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

import intelligent.wiki.editor.app_api.Version;
import intelligent.wiki.editor.gui.fx.ResourceBundleFactory;
import intelligent.wiki.editor.io_api.WikiOperations;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * Class, containing methods to create dialogs, used in application.
 *
 * @author Myroslav Rudnytskyi
 * @version 20.09.2015
 */
public class DialogsFactory {

	private static final ResourceBundle i18n = ResourceBundleFactory.getBundle(new Locale("uk", "ua"));
	private static final Logger log = Logger.getLogger(DialogsFactory.class.getName());
	private final Version version;
	private final WikiOperations wiki;

	public DialogsFactory(Version version, WikiOperations wiki) {
		this.wiki = Objects.requireNonNull(wiki, "Null wiki operations!");
		this.version = Objects.requireNonNull(version, "Null version object!");
	}

	private static Dialog prepareDialog(Dialog dialog, String title, String header, String content) {
		dialog.setTitle(title);
		dialog.setHeaderText(header);
		dialog.setContentText(content);
		return dialog;
	}

	protected static Dialog setI18nStrings(Dialog dialog, String titleId, String headerId, String contentId) {
		return prepareDialog(dialog, i18n.getString(titleId), i18n.getString(headerId), i18n.getString(contentId));
	}

	protected static Dialog setExpandableTextArea(Dialog dialog, TextArea textArea, String previewLabelCaptionId) {
		textArea.setEditable(false);
		textArea.setWrapText(true);
		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);

		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(new Label(i18n.getString(previewLabelCaptionId)), 0, 0);
		expContent.add(textArea, 0, 1);

		DialogPane pane = dialog.getDialogPane();
		pane.setExpandableContent(expContent);
		pane.setExpanded(true);

		return dialog;
	}

	private Dialog makeErrorDialog(String title, String header, String content) {
		log.severe(header + " " + content);
		return prepareDialog(new Alert(AlertType.ERROR), title, header, content);
	}

	/**
	 * Method is used to create (not show!) exit dialog.
	 *
	 * @return dialog with question, "OK" and "Cancel" buttons
	 */
	public Dialog makeExitDialog() {
		return setI18nStrings(new Alert(AlertType.CONFIRMATION),
				"question-dialog.title",
				"question-dialog.header-text.exit",
				"question-dialog.content-text.exit");
	}

	/**
	 * Method is used to create (not show!) error dialog when some exception occurs.
	 *
	 * @param e exception object
	 * @return constructed error dialog
	 */
	public Dialog makeErrorDialog(Exception e) {
		Dialog alert = makeErrorDialog(
				i18n.getString("error-dialog.title"),
				e.toString(),
				i18n.getString("error-dialog.header-text")
		);

		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String exceptionText = sw.toString();

		setExpandableTextArea(alert, new TextArea(exceptionText), "error-dialog.stacktrace-title");

		return alert;
	}

	/**
	 * Method is used to stub not realized functions with error dialog.
	 *
	 * @return constructed not implemented error dialog
	 */
	public Dialog makeNotImplementedErrorDialog() {
		return makeErrorDialog(
				i18n.getString("error-dialog.title"),
				i18n.getString("error-dialog.header-text.not-implemented"),
				i18n.getString("error-dialog.content-text.not-implemented"));
	}

	/**
	 * Method is used to create (not show!) message about author.
	 *
	 * @return constructed about dialog
	 */
	public Dialog makeAboutDialog() {
		//TODO: expand message to show also used libraries and their license, version and so on
		return prepareDialog(new Alert(AlertType.INFORMATION),
				i18n.getString("info-dialog.title"),
				i18n.getString("info-dialog.header-text.about"),
				"version " + version + " Copyright (c) Myroslav Rudnytskyi, Kyiv, Ukraine, 2014-2016"
		);
	}

	/**
	 * @return created object of {@link ArticleInputDialog}
	 */
	public Dialog<String> makeArticleNameInputDialog() {
		return new ArticleInputDialog(wiki, i18n);
	}

	/**
	 * @param linkText    text, placed in link text field
	 * @param captionText text, placed in caption text field
	 * @return created object of {@link ModifyWikiLinkDialog}
	 */
	public Dialog<String> makeInsertWikiLinkDialog(String linkText, String captionText) {
		return new ModifyWikiLinkDialog(linkText, captionText,
				"insert-wiki-link-dialog.title",
				"insert-wiki-link-dialog.header",
				"insert-wiki-link-dialog.content", wiki, i18n);
	}

	/**
	 * @param urlText     text, placed in url text field
	 * @param captionText text, placed in caption text field
	 * @return created object of {@link ModifyLinkDialog}
	 */
	public Dialog<String> makeInsertExternalLinkDialog(String urlText, String captionText) {
		return new ModifyLinkDialog(urlText, captionText,
				"insert-link-dialog.title",
				"insert-link-dialog.header",
				"insert-link-dialog.content", i18n);
	}

	/**
	 * @param headingText text, placed in heading
	 * @return created object of {@link ModifyHeadingDialog}
	 */
	public Dialog<String> makeInsertHeadingDialog(String headingText) {
		return new ModifyHeadingDialog(headingText,
				"insert-heading-dialog.title",
				"insert-heading-dialog.header",
				"insert-heading-dialog.content", i18n);
	}

	/**
	 * @param captionText text, placed in template name text field
	 * @return created object of {@link ModifyTemplateDialog}
	 */
	public Dialog<String> makeInsertTemplateDialog(String captionText) {
		return new ModifyTemplateDialog(captionText,
				"insert-template-dialog.title",
				"insert-template-dialog.header",
				"insert-template-dialog.content", wiki, i18n);
	}
}