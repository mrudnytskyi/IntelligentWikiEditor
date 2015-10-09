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
import intelligent.wiki.editor.gui.fx.dialogs.DialogsFactory;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

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
	private final TextUpdateTracker updateTracker = new TextUpdateTracker();
	private final DialogsFactory dialogs = new DialogsFactory();
	private ResourceBundle i18n;
	private String currentOpenedFile;
	private Stage stage;
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		i18n = resources;
		text.textProperty().bindBidirectional(article.textProperty());
		text.setWrapText(true);
		text.textProperty().addListener(updateTracker);
		enableCutAction(false);
		enableCopyAction(false);
		enablePasteAction(clipboard.hasString());
		text.setOnMouseMoved(event -> enablePasteAction(clipboard.hasString()));
		text.selectedTextProperty().addListener(listener -> {
			boolean isSelection = text.getSelectedText().length() != 0;
			enableCutAction(isSelection);
			enableCopyAction(isSelection);
		});
	}

	private void enableCutAction(boolean state) {
		cutButton.setDisable(!state);
		cutMenuItem.setDisable(!state);
	}

	private void enableCopyAction(boolean state) {
		copyButton.setDisable(!state);
		copyMenuItem.setDisable(!state);
	}

	private void enablePasteAction(boolean state) {
		pasteButton.setDisable(!state);
		pasteMenuItem.setDisable(!state);
	}

	/**
	 * Sets this object as handler of closing requests. See {@link #handle(WindowEvent)} for details.
	 */
	public void registerCloseHandler(Stage stage) {
		this.stage = stage;
		if (stage != null) {
			stage.setOnCloseRequest(this);
		}
	}

	/**
	 * Method is called when user tries to close application using red cross on the window top.
	 */
	@Override
	public void handle(WindowEvent event) {
		actionQuit();
		event.consume(); // stop event handling
	}

	public void actionNew() {
		dialogs.makeNotImplementedErrorDialog().show();
	}

	/**
	 * Shows {@link FileChooser} dialog to select file for future opening.
	 */
	public void actionOpenFile() {
		FileChooser chooser = createWikiArticleFileChooser();
		File file = chooser.showOpenDialog(stage);
		if (file != null) {
			currentOpenedFile = file.getAbsolutePath();
			updateTracker.startIgnoringUpdating();
			try {
				text.setText(FilesFacade.readTXT(currentOpenedFile));
				tab.setText(file.getName());
			} catch (IOException e) {
				dialogs.makeErrorDialog(e).show();
			}
			updateTracker.stopIgnoringUpdating();
			updateTracker.clearUpdated();
		}
	}

	private FileChooser createWikiArticleFileChooser() {
		FileChooser chooser = new FileChooser();
		chooser.setInitialDirectory(new File("."));
		//TODO maybe change *.wpf to *.waf: not project, but article
		chooser.getExtensionFilters().add(
				new FileChooser.ExtensionFilter(i18n.getString("extension-filter.wpf"), "*.wpf"));
		return chooser;
	}

	/**
	 * Shows dialog to input article name for future loading.
	 */
	public void actionOpenURL() {
		Optional<String> result = dialogs.makeArticleNameInputDialog().showAndWait();

		if (result.isPresent()) {
			currentOpenedFile = null;
			updateTracker.startIgnoringUpdating();
			try {
				text.setText(MediaWikiFacade.getArticleText(result.get()));
				tab.setText(result.get());
			} catch (IOException e) {
				dialogs.makeErrorDialog(e).show();
			}
			updateTracker.stopIgnoringUpdating();
			updateTracker.clearUpdated();
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
				dialogs.makeErrorDialog(e).show();
			}
			updateTracker.clearUpdated();
		} else {
			actionSaveAs();
		}
	}

	/**
	 * Shows {@link FileChooser} dialog to select file for future saving.
	 */
	public void actionSaveAs() {
		FileChooser chooser = createWikiArticleFileChooser();
		File file = chooser.showSaveDialog(stage);
		if (file != null) {
			try {
				FilesFacade.writeTXT(file.getAbsolutePath(), text.getText());
				tab.setText(file.getName());
			} catch (IOException e) {
				dialogs.makeErrorDialog(e).show();
			}
			updateTracker.clearUpdated();
		}
	}

	public void actionClose() {
		dialogs.makeNotImplementedErrorDialog().show();
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
	 * Shows dialog to input article name for link. If there
	 * is no selected text - this name will be also caption for link.
	 */
	public void actionInsertWikiLink() {
		String selection = text.getSelectedText();
		Optional<String> result = dialogs.makeInsertWikiLinkDialog(selection, selection).showAndWait();
		if (result.isPresent()) {
			text.replaceSelection(result.get());
		}
	}

	/**
	 * Shows dialog to input data for constructing link.
	 */
	public void actionInsertExternalLink() {
		String selection = text.getSelectedText();
		Optional<String> result = dialogs.makeInsertExternalLinkDialog(selection, selection).showAndWait();
		if (result.isPresent()) {
			text.replaceSelection(result.get());
		}
	}

	/**
	 * Shows dialog to choose type of inserted heading. If there
	 * is no selected text - heading will have empty caption.
	 */
	public void actionInsertHeading() {
		Optional<String> result = dialogs.makeInsertHeadingDialog(text.getSelectedText()).showAndWait();
		if (result.isPresent()) {
			String newLine = System.lineSeparator();
			text.replaceSelection(String.join("", newLine, result.get(), newLine));
		}
	}

	public void actionInsertSnippet() {
		dialogs.makeNotImplementedErrorDialog().show();
	}

	public void actionInsertTemplate() {
		dialogs.makeNotImplementedErrorDialog().show();
	}

	public void actionAddCategories() {
		dialogs.makeNotImplementedErrorDialog().show();
	}

	public void actionHelp() {
		dialogs.makeNotImplementedErrorDialog().show();
	}

	/**
	 * Shows message about author.
	 */
	public void actionAbout() {
		dialogs.makeAboutDialog().show();
	}

	/**
	 * Closes application. If there is unsaved changes, question dialog is showing.
	 */
	public void actionQuit() {
		if (updateTracker.isTextUpdated()) {
			Optional result = dialogs.makeExitDialog().showAndWait();
			boolean noResult = !result.isPresent();
			boolean noOKResult = result.get() != ButtonType.OK;
			if (noResult || noOKResult) {
				return;
			}
		}
		Platform.exit();
	}

	private class TextUpdateTracker implements ChangeListener<String> {
		private boolean textUpdate = false;
		private boolean ignoreTextUpdate = false;

		void setUpdated() {
			if (!textUpdate) {
				tab.setStyle("-fx-font-weight: bold;");
			}
			textUpdate = true;
		}

		void clearUpdated() {
			if (textUpdate) {
				tab.setStyle("-fx-font-weight: normal;");
			}
			textUpdate = false;
		}

		void startIgnoringUpdating() {
			ignoreTextUpdate = true;
		}

		void stopIgnoringUpdating() {
			ignoreTextUpdate = false;
		}

		boolean isTextUpdated() {
			return textUpdate;
		}

		@Override
		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			if (!ignoreTextUpdate) {
				setUpdated();
			}
		}
	}
}