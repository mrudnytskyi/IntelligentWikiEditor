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

import intelligent.wiki.editor.bot.io.wiki.templatedata.TemplateParameter;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.Callback;
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

	private final TableView<Parameter> parametersTable = new TableView<>();
	private final TextField nameInput = TextFields.createClearableTextField();
	private ObservableList<Parameter> parameters = FXCollections.observableArrayList();

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
			parameters.add(new Parameter(parameterNameInput.getText(), ""));
			parameterNameInput.clear();
		});

		content.add(parameterNameInput, 0, 2);
		content.add(addButton, 1, 2);
	}

	private void createTable() {
		TableColumn nameColumn = new TableColumn(i18n.getString("insert-template-dialog.name-column"));
		nameColumn.setMinWidth(250);
		TableColumn valueColumn = new TableColumn(i18n.getString("insert-template-dialog.value-column"));
		valueColumn.setMinWidth(250);

		parametersTable.setEditable(true);
		parametersTable.setRowFactory(tv -> new TableRow<Parameter>() {
			@Override
			public void updateItem(Parameter parameter, boolean empty) {
				super.updateItem(parameter, empty);
				setTooltip(new Tooltip(parameter == null ? "" : parameter.getDescription()));
			}
		});

		Callback<TableColumn, TableCell> cellFactory = p -> new EditingCell();

		nameColumn.setCellValueFactory(new PropertyValueFactory<Parameter, String>("name"));
		valueColumn.setCellValueFactory(new PropertyValueFactory<Parameter, String>("default"));
		valueColumn.setCellFactory(cellFactory);
		valueColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Parameter, String>>() {
			@Override
			public void handle(TableColumn.CellEditEvent<Parameter, String> event) {
				Parameter currentParameter = event.getTableView().getItems().get(event.getTablePosition().getRow());
				if (validEntering(event)) {
					currentParameter.setValue(event.getNewValue());
				} else {
					currentParameter.setValue(event.getOldValue());
				}
				fillPreview();
			}
		});
		parametersTable.getColumns().addAll(nameColumn, valueColumn);
	}

	private boolean validEntering(TableColumn.CellEditEvent<Parameter, String> event) {
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
			parameters.addAll(wiki.getTemplateParameters(templateName).stream().map(Parameter::new).collect(Collectors.toList()));
			parametersTable.setItems(parameters);
		} catch (IOException e) {
			log.warning("Loading failed!");
			log.severe(e.getMessage());
		}
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
		StringBuilder sb = new StringBuilder("{{");
		String newLine = System.lineSeparator();
		String templateName = nameInput.getText().trim();
		String prefix = wiki.getTemplateNamespacePrefix();
		if (!templateName.contains(prefix)) {
			templateName = prefix + templateName;
		}
		sb.append(templateName).append(newLine);
		for (Parameter current : parametersTable.getItems()) {
			sb.append("| ").append(current).append(newLine);
		}
		sb.append("}}");
		return sb.toString();
	}

	//TODO rename to TemplateArgumentImp, move to AST package, implements interface TA, which extends TemplateParameter
	public class Parameter {
		private TemplateParameter parameter;
		private StringProperty value = new SimpleStringProperty();
		private String name;

		public Parameter(TemplateParameter parameter) {
			this.parameter = parameter;
		}

		public Parameter(String name, String value) {
			this.name = name;
			setValue(value);
		}

		public String getName() {
			if (parameter != null) {
				return parameter.getName();
			} else {
				return name;
			}
		}

		public String getDescription() {
			if (parameter != null) {
				return parameter.getDescription();
			} else {
				return "";
			}
		}

		public String getDefault() {
			if (parameter != null) {
				return parameter.getDefault();
			} else {
				return "";
			}
		}

		public boolean isRequired() {
			if (parameter != null) {
				return parameter.isRequired();
			} else {
				return false;
			}
		}

		public String getValue() {
			return value.get();
		}

		public void setValue(String value) {
			this.value.set(value);
		}

		public StringProperty valueProperty() {
			return value;
		}

		@Override
		public String toString() {
			return getName() + " = " + (value.get() == null ? getDefault() : value.get());
		}
	}

	class EditingCell extends TableCell<Parameter, String> {

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
