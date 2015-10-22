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

import intelligent.wiki.editor.bot.compiler.AST.TemplateArgument;
import intelligent.wiki.editor.bot.compiler.AST.TemplateDeclaration;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Class, representing dialog for inserting data to construct template: name and parameters.
 *
 * @author Myroslav Rudnytskyi
 * @version 27.09.2015
 */
public class ModifyTemplateDialog extends InputDialog {

	private final TableView<TemplateArgument> parametersTable = new TableView<>();
	private final TextField nameInput = TextFields.createClearableTextField();
	private ObservableList<TemplateArgument> parameters = FXCollections.observableArrayList();

	protected ModifyTemplateDialog(String captionText, String titleId, String headerId, String contentId) {
		super(titleId, headerId, contentId);
		getDialogPane().getStyleClass().add("text-input-dialog");

		initContent();
		initButtons();
		initInputControls(captionText);
		buildPreview();
	}

	private void initContent() {
		content.add(new Label(i18n.getString("insert-template-dialog.label-text")), 0, 0);
		content.add(nameInput, 1, 0);
		GridPane.setHgrow(nameInput, Priority.ALWAYS);
		content.add(parametersTable, 0, 1, 2, 1);

		createTable();
		createInputParameterTextFieldAndButton();
	}

	private void createInputParameterTextFieldAndButton() {
		TextField parameterNameInput = TextFields.createClearableTextField();
		parameterNameInput.setPromptText(i18n.getString("insert-template-dialog.name-column"));

		Button addButton = new Button("+");
		addButton.setOnAction(e -> {
			parameters.add(new TemplateArgument(parameterNameInput.getText()));
			parameterNameInput.clear();
			fillPreview();
		});

		content.add(parameterNameInput, 0, 2);
		content.add(addButton, 1, 2);
	}

	private void createTable() {
		TableColumn<TemplateArgument, String> nameColumn = new TableColumn<>(i18n.getString("insert-template-dialog.name-column"));
		nameColumn.setMinWidth(250);
		TableColumn<TemplateArgument, String> valueColumn = new TableColumn<>(i18n.getString("insert-template-dialog.value-column"));
		valueColumn.setMinWidth(250);

		parametersTable.setEditable(true);
		parametersTable.setRowFactory(tv -> new TableRow<TemplateArgument>() {
			@Override
			public void updateItem(TemplateArgument parameter, boolean empty) {
				super.updateItem(parameter, empty);
				setTooltip(new Tooltip(parameter == null ? "" : parameter.getDescription()));
			}
		});

		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		valueColumn.setCellValueFactory(new PropertyValueFactory<>("default"));
		valueColumn.setCellFactory(p -> new EditingCell());
		valueColumn.setOnEditCommit(event -> {
			TemplateArgument current = event.getTableView().getItems().get(event.getTablePosition().getRow());
			if (validEntering(event)) {
				current.setValue(event.getNewValue());
			} else {
				current.setValue(event.getOldValue());
			}
			fillPreview();
		});
		parametersTable.getColumns().addAll(nameColumn, valueColumn);
	}

	private boolean validEntering(TableColumn.CellEditEvent<TemplateArgument, String> event) {
		boolean notInputted = event.getNewValue().trim().isEmpty();
		boolean isRequired = event.getRowValue().isRequired();
		return !(isRequired && notInputted);
	}

	private void initInputControls(String captionText) {
		if (captionText != null) {
			nameInput.setText(captionText);
		}

		Platform.runLater(nameInput::requestFocus);

		buildValidation(nameInput, "insert-template-dialog.empty", "insert-template-dialog.not-exists");

		buildTemplateAutocompletion(nameInput);
	}

	private void initButtons() {
		Node okButton = getDialogPane().lookupButton(okType);
		okButton.setDisable(true);

		nameInput.textProperty().addListener((observable, oldString, newString) -> {
			okButton.setDisable(newString.trim().isEmpty() || !validate(newString));
			if (validate(newString)) {
				loadParameters(newString);
			}
			fillPreview();
		});
	}

	private void loadParameters(String templateName) {
		parameters = FXCollections.observableArrayList();
		try {
			parameters.addAll(wiki.getTemplateParameters(templateName).stream().map(TemplateArgument::new).collect(Collectors.toList()));
		} catch (IOException e) {
			log.warning("Loading failed!");
			log.severe(e.getMessage());
		}
		parametersTable.setItems(parameters);
	}

	@Override
	protected boolean validate(String name) {
		try {
			return wiki.existsTemplate(name);
		} catch (IOException e) {
			log.warning("Validation failed!");
			log.severe(e.getMessage());
		}
		// if an exception occurs - skip validation
		return true;
	}

	@Override
	public String getInputtedResult() {
		String templateName = addPrefixIfNotPresent(nameInput.getText(), wiki.getTemplateNamespacePrefix());
		return new TemplateDeclaration(templateName, parameters).toString();
	}

	private String addPrefixIfNotPresent(String arg, String prefix) {
		return arg.startsWith(prefix) ? arg.trim() : (prefix + arg).trim();
	}

	class EditingCell extends TableCell<TemplateArgument, String> {

		private TextField textField;

		@Override
		public void startEdit() {
			if (!isEmpty()) {
				super.startEdit();
				createTextField();
				setText(null);
				setGraphic(textField);
				textField.selectAll();
			}
		}

		@Override
		public void cancelEdit() {
			super.cancelEdit();

			setText(getItem());
			setGraphic(null);
		}

		@Override
		public void updateItem(String item, boolean empty) {
			super.updateItem(item, empty);

			if (empty) {
				setText(null);
				setGraphic(null);
			} else {
				if (isEditing()) {
					if (textField != null) {
						textField.setText(getString());
					}
					setText(null);
					setGraphic(textField);
				} else {
					setText(getString());
					setGraphic(null);
				}
			}
		}

		private void createTextField() {
			textField = TextFields.createClearableTextField();
			textField.setText(getString());
			textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
			textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
				if (!newValue) {
					commitEdit(textField.getText());
				}
			});
		}

		private String getString() {
			return getItem() == null ? "" : getItem();
		}
	}
}
