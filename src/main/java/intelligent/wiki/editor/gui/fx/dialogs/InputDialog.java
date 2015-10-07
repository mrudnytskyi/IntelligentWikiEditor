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

import intelligent.wiki.editor.bot.io.MediaWikiFacade;
import intelligent.wiki.editor.gui.fx.ResourceBundleFactory;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.controlsfx.control.textfield.TextFields;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * Abstract class for all input dialogs in application.
 *
 * @author Myroslav Rudnytskyi
 * @version 07.10.2015
 */
public abstract class InputDialog extends Dialog<String> {

	protected static final ResourceBundle i18n = ResourceBundleFactory.getBundle(new Locale("uk", "ua"));
	protected static final Logger log = Logger.getLogger(InputDialog.class.getName());
	protected final GridPane content = new GridPane();
	protected ButtonType okType;
	private TextArea preview;

	protected InputDialog(String titleId, String headerId, String contentId) {
		DialogsFactory.setI18nStrings(this, titleId, headerId, contentId);
		buildContentPane();
		buildButtons();
		setResultConverter(pressedButton -> {
			if (pressedButton.getButtonData().isCancelButton()) {
				return null;
			}
			return getInputtedResult();
		});
	}

	private void buildButtons() {
		okType = new ButtonType(
				i18n.getString("dialog.ok"), ButtonBar.ButtonData.OK_DONE);
		ButtonType cancelType = new ButtonType(
				i18n.getString("dialog.cancel"), ButtonBar.ButtonData.CANCEL_CLOSE);
		getDialogPane().getButtonTypes().addAll(okType, cancelType);
	}

	private void buildContentPane() {
		content.setHgap(10);
		content.setVgap(10);
		content.setPadding(new Insets(20, 10, 10, 10));
		getDialogPane().setContent(content);
	}

	/**
	 * @return inputted text
	 */
	public abstract String getInputtedResult();

	protected void buildAutocompletion(TextField requireAutocompletion) {
		TextFields.bindAutoCompletion(requireAutocompletion, param -> {
			if (!param.getUserText().isEmpty()) {
				try {
					return MediaWikiFacade.getAriclesStartingWith(param.getUserText());
				} catch (IOException e) {
					log.warning("Autocompletion failed!");
					log.severe(e.getMessage());
				}
			}
			return new ArrayList<>();
		});
	}

	protected void buildValidation(Control requireValidation, String emptyId, String validationId) {
		new ValidationSupport().registerValidator(requireValidation, true, Validator.combine(
				Validator.createEmptyValidator(i18n.getString(emptyId)),
				(control, value) ->
						ValidationResult.fromMessageIf(
								control,
								i18n.getString(validationId),
								Severity.ERROR,
								!validate(value.toString())
						)
		));
	}

	protected boolean validate(String str) {
		return false;
	}

	protected void buildPreview() {
		preview = new TextArea();
		DialogsFactory.setExpandableTextArea(this, preview, "dialog.preview-label-text");
	}

	protected void fillPreview() {
		if (preview != null) {
			preview.clear();
			preview.appendText(getResultConverter().call(okType));
		}
	}
}
