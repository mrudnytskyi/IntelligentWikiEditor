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

import intelligent.wiki.editor.bot.core.WikiArticle;
import intelligent.wiki.editor.bot.io.FilesFacade;
import intelligent.wiki.editor.bot.io.MediaWikiFacade;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.controlsfx.control.textfield.TextFields;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import static intelligent.wiki.editor.gui.fx.Dialogs.*;

/**
 * Controller class for main window of wiki editor. Contains methods to make different actions.
 * Note, that all these actions are <code>public</code> to be referred from <code>fxml</code> file.
 *
 * @author Myroslav Rudnytskyi
 * @version 20.09.2015
 */
public class WikiEditorController implements Initializable, EventHandler<WindowEvent> {

	private final Clipboard clipboard = Clipboard.getSystemClipboard();
	private final WikiArticle article = new WikiArticle("");
	private ResourceBundle i18n;
	private String currentOpenedFile;

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
	private Tab tab;

	@FXML
	private TextArea text;

	private boolean textUpdate = false;

	private boolean ignoreTextUpdate = false;

	private Stage stage;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		i18n = resources;
		text.textProperty().bindBidirectional(article.textProperty());
		text.setWrapText(true);
		text.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!ignoreTextUpdate) {
				textUpdateOn();
			}
		});
		initCutCopyPasteBehavior();
	}

	private void initCutCopyPasteBehavior() {
		text.setOnMouseMoved(event -> {
			pasteButton.setDisable(!clipboard.hasString());
			pasteMenuItem.setDisable(!clipboard.hasString());
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

	private void textUpdateOn() {
		if (!textUpdate) {
			tab.setStyle("-fx-font-weight: bold;");
		}
		textUpdate = true;
	}

	private void textUpdateOff() {
		if (textUpdate) {
			tab.setStyle("-fx-font-weight: normal;");
		}
		textUpdate = false;
	}

	private void ignoreTextUpdateOn() {
		ignoreTextUpdate = true;
	}

	private void ignoreTextUpdateOff() {
		ignoreTextUpdate = false;
	}

	public void actionNew() {
		showNotImplementedError();
	}

	/**
	 * Shows {@link FileChooser} dialog to select file for future opening.
	 */
	public void actionOpenFile() {
		FileChooser chooser = new FileChooser();
		chooser.setInitialDirectory(new File("."));
		//TODO maybe change *.wpf to *.waf: not project, but article
		chooser.getExtensionFilters().add(
				new FileChooser.ExtensionFilter(i18n.getString("extension-filter_wpf"), "*.wpf"));
		File file = chooser.showOpenDialog(stage);
		if (file != null) {
			currentOpenedFile = file.getAbsolutePath();
			ignoreTextUpdateOn();
			try {
				text.setText(FilesFacade.readTXT(currentOpenedFile));
			} catch (IOException e) {
				showError(e);
			}
			ignoreTextUpdateOff();
			tab.setText(file.getName());
			textUpdateOff();
		}
	}

	/**
	 * Shows {@link TextInputDialog} to input article name for future loading.
	 */
	public void actionOpenURL() {
		TextInputDialog tid = makeArticleInputDialog();

		//TODO auto completion from list of all enabled articles
		TextFields.bindAutoCompletion(tid.getEditor(), "TODO");
		Optional<String> result = tid.showAndWait();

		if (result.isPresent()) {
			currentOpenedFile = null;
			ignoreTextUpdateOn();
			try {
				text.setText(MediaWikiFacade.getArticleText(result.get()));
			} catch (IOException e) {
				showError(e);
			}
			ignoreTextUpdateOff();
			tab.setText(result.get());
			textUpdateOff();
		}
	}

	/**
	 * Saves article in current opened file or shows {@link FileChooser}
	 * dialog for future saving if there is no current file.
	 */
	public void actionSave() {
		if (currentOpenedFile != null) {
			try {
				FilesFacade.writeTXT(currentOpenedFile, text.getText());
			} catch (IOException e) {
				showError(e);
			}
			textUpdateOff();
		} else {
			actionSaveAs();
		}
	}

	/**
	 * Shows {@link FileChooser} dialog to select file for future saving.
	 */
	public void actionSaveAs() {
		FileChooser chooser = new FileChooser();
		chooser.setInitialDirectory(new File("."));
		//TODO maybe change *.wpf to *.waf: not project, but article
		chooser.getExtensionFilters().add(
				new FileChooser.ExtensionFilter(i18n.getString("extension-filter_wpf"), "*.wpf"));
		File file = chooser.showSaveDialog(stage);
		if (file != null) {
			try {
				FilesFacade.writeTXT(file.getAbsolutePath(), text.getText());
			} catch (IOException e) {
				showError(e);
			}
			tab.setText(file.getName());
			textUpdateOff();
		}
	}

	public void actionClose() {
		showNotImplementedError();
	}

	/**
	 * Cuts selected text fragment.
	 */
	public void actionCut() {
		text.cut();
	}

	/**
	 * Copies selected text fragment.
	 */
	public void actionCopy() {
		text.copy();
	}

	/**
	 * Pastes selected text fragment.
	 */
	public void actionPaste() {
		text.paste();
	}

	/**
	 * Selects all article text.
	 */
	public void actionSelectAll() {
		text.selectAll();
	}

	/**
	 * Shows {@link TextInputDialog} to input article name for link. If there
	 * is no selected text - this name will be also caption for link.
	 */
	public void actionInsertWikiLink() {
		TextInputDialog tid = makeArticleInputDialog();

		//TODO auto completion from list of all enabled articles
		TextFields.bindAutoCompletion(tid.getEditor(), "TODO");
		Optional<String> result = tid.showAndWait();

		if (result.isPresent()) {
			String selected = text.getSelectedText();
			String replaced = !selected.isEmpty() ? (result.get().isEmpty() ? "" : "|") + selected : "";
			text.replaceSelection(String.join("", "[[", result.get(), replaced, "]]"));
		}
	}

	public void actionInsertExternalLink() {
		showNotImplementedError();
	}

	public void actionInsertHeading() {
		showNotImplementedError();
	}

	public void actionInsertSnippet() {
		showNotImplementedError();
	}

	public void actionInsertTemplate() {
		showNotImplementedError();
	}

	public void actionAddCategories() {
		showNotImplementedError();
	}

	public void actionHelp() {
		showNotImplementedError();
	}

	public void actionAbout() {
		showNotImplementedError();
	}

	/**
	 * Closes application. If there is unsaved changes, question dialog is showing.
	 */
	public void actionQuit() {
		if (textUpdate) {
			Optional<ButtonType> result = Dialogs.makeExitDialog().showAndWait();
			if (!result.isPresent() || result.get() != ButtonType.OK) {
				return;
			}
		}
		Platform.exit();
	}

	/**
	 * Links these controller object with stage object from parameter
	 *
	 * @param stage stage object. Note, that null values cause {@link NullPointerException}
	 */
	public void setStage(Stage stage) {
		this.stage = Objects.requireNonNull(stage, "Stage can not be null!");
		stage.setOnCloseRequest(this);
	}

	/**
	 * Method is called when user tries to close application using red cross on the window top.
	 */
	@Override
	public void handle(WindowEvent event) {
		actionQuit();
		event.consume(); // stop event handling
	}
}