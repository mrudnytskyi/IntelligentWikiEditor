package intelligent.wiki.editor.gui;
/*
 * AddTemplateFrame.java	27.04.2015
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

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import intelligent.wiki.editor.utils.AutoCompletePanel;
import intelligent.wiki.editor.utils.AutoCompleteSource;
import intelligent.wiki.editor.bot.compiler.AST.TemplateDeclaration;
import intelligent.wiki.editor.bot.compiler.AST.TemplateParameter;
import intelligent.wiki.editor.bot.io.MediaWikiFacade;
import intelligent.wiki.editor.bot.io.MediaWikiFacade.Language;

/**
 * Frame for adding templates to article with autocompletion function.
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 27.04.2015
 */
public class AddTemplateFrame extends ApplicationFrame {

	private class TemplateSource implements AutoCompleteSource {

		@Override
		public String[] getSource(String... params) {
			MediaWikiFacade.setLanguage(Language.UKRAINIAN);
			String[] result = new String[] {};
			try {
				String[] templates = MediaWikiFacade
						.getTemplatesStartingWith(params[0]);
				result = new String[templates.length];
				for (int i = 0; i < templates.length; i++) {
					result[i] = templates[i].substring(MediaWikiFacade
							.getLanguage().getTemplatePreffix().length(),
							templates[i].length());
				}
			} catch (IOException e) {
				new MessagesFrame(AddTemplateFrame.this).showError(e
						.getMessage());
			}
			return result;
		}
	}

	private class ParametersTableModel extends AbstractTableModel {

		private static final long serialVersionUID = -227344535987221079L;

		private String[][] data;

		private ParametersTableModel() {
			data = new String[15][4];
		}

		private ParametersTableModel(String[][] data) {
			this.data = data;
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			if (columnIndex == VALUE_COLUMN) {
				return true;
			}
			return false;
		}

		@Override
		public String getColumnName(int column) {
			switch (column) {
			case NAME_COLUMN:
				return "Parameter name";
			case VALUE_COLUMN:
				return "Value";
			case DESCRIPTION_COLUMN:
				return "Description";
			case REQUIRED_COLUMN:
				return "Required";
			default:
				return "";
			}
		}

		@Override
		public int getRowCount() {
			return data.length;
		}

		@Override
		public int getColumnCount() {
			return data[0].length;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return data[rowIndex][columnIndex];
		}

		@Override
		public void setValueAt(Object value, int rowIndex, int columnIndex) {
			data[rowIndex][columnIndex] = value.toString();
		}
	}

	private static final long serialVersionUID = -6937956054699961023L;

	private final AutoCompletePanel autoComplete = new AutoCompletePanel(
			new TemplateSource(), "Enter template name:",
			getAction("add-template-add"));

	private TemplateParameter[] params = new TemplateParameter[] {};

	private final JTable paramsTable = new JTable();

	private static final int NAME_COLUMN = 0;

	private static final int VALUE_COLUMN = 1;

	private static final int DESCRIPTION_COLUMN = 2;

	private static final int REQUIRED_COLUMN = 3;

	/**
	 * Constructs new frame with specified content and title.
	 * 
	 * @param listener
	 *            necessary object, which will receive template object,
	 *            listening for changing <code>add-template</code> property
	 */
	public AddTemplateFrame(PropertyChangeListener listener) {
		super("Add template");
		setLayout(new BorderLayout());
		add(createContent(), BorderLayout.CENTER);
		addPropertyChangeListener(listener);
		setResizable(false);
		setAlwaysOnTop(true);
		pack();
		moveToScreenCenter();
	}

	private JPanel createContent() {
		JPanel content = new JPanel(new BorderLayout());

		autoComplete.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		paramsTable.setModel(new ParametersTableModel());

		JPanel buttons = new JPanel(new GridLayout(1, 2, 200, 0));
		buttons.setBorder(BorderFactory.createEmptyBorder(20, 100, 0, 100));
		buttons.add(new JButton(getAction("add-template-OK")));
		buttons.add(new JButton(getAction("add-template-cancel")));

		content.add(autoComplete, BorderLayout.NORTH);
		content.add(new JScrollPane(paramsTable), BorderLayout.CENTER);
		content.add(buttons, BorderLayout.SOUTH);

		content.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
		return content;
	}

	/**
	 * Method provides code for frame actions.
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		MessagesFrame messager = new MessagesFrame(this);
		switch (evt.getPropertyName()) {
		case "add-template-cancel":
			setVisible(false);
			break;
		case "add-template-OK":
			TableModel tableModel = paramsTable.getModel();
			for (int i = 0; i < tableModel.getRowCount(); i++) {
				boolean isRequired = tableModel.getValueAt(i, REQUIRED_COLUMN)
						.equals("true");
				boolean isNull = tableModel.getValueAt(i, VALUE_COLUMN).equals(
						"null");
				if (isRequired && isNull) {
					messager.showWarning("It seems that required templates parameters are empty!");
					return;
				}
			}
			TemplateDeclaration template = new TemplateDeclaration(
					autoComplete.getInputedText(), params);

			for (int i = 0; i < tableModel.getRowCount(); i++) {
				template.putValue(tableModel.getValueAt(i, NAME_COLUMN)
						.toString(), tableModel.getValueAt(i, VALUE_COLUMN)
						.toString());
			}
			firePropertyChange("add-template", null, template);
			setVisible(false);
			break;
		case "add-template-add":
			String templateName = autoComplete.getInputedText();
			if (!templateName.isEmpty()) {
				if (autoComplete.searchStringInProposed(templateName) == -1) {
					if (!messager.showQuestion("It seems that inputed "
							+ "template name does not exists. Do you want "
							+ "to add it anyway?")) {
						return;
					}
				}
				try {
					params = MediaWikiFacade
							.getTemplateParameters(templateName);
				} catch (IOException e) {
					messager.showError(e.getMessage());
				}
				if (params.length == 0) {
					return;
				}
				String[][] paramsData = new String[params.length][4];
				for (int i = 0; i < params.length; i++) {
					paramsData[i][NAME_COLUMN] = params[i].getName();
					paramsData[i][VALUE_COLUMN] = params[i].getDefault() == null ? ""
							: params[i].getDefault();
					paramsData[i][DESCRIPTION_COLUMN] = params[i]
							.getDescription() == null ? "" : params[i]
							.getDescription();
					paramsData[i][REQUIRED_COLUMN] = params[i].isRequired()
							|| params[i].isSuggested() ? "true" : "false";
				}
				paramsTable.setModel(new ParametersTableModel(paramsData));
			}
			break;
		}
	}

	/**
	 * Returns array, initialized by actions objects.
	 */
	@Override
	protected AbstractAction[] createActions() {
		List<AbstractAction> actions = new ArrayList<AbstractAction>();
		actions.add(new Action(this, "Cancel", "add-template-cancel",
				"Cancel all changes", "", "res\\cancel_big.png", 0));
		actions.add(new Action(this, "OK", "add-template-OK",
				"Apply all changes", "", "res\\ok_big.png", 0));
		actions.add(new Action(this, "", "add-template-add", "Add teplate", "",
				"res\\add_small.png", 0));
		return actions.toArray(new AbstractAction[actions.size()]);
	}
}