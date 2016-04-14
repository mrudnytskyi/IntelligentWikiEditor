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

import intelligent.wiki.editor.bot.io.wiki.WikiOperations;
import intelligent.wiki.editor.common.io.FilesFacade;
import intelligent.wiki.editor.core_api.ASTNode;
import intelligent.wiki.editor.core_api.Project;
import intelligent.wiki.editor.gui.fx.actions.JavaFxActions;
import intelligent.wiki.editor.gui.fx.dialogs.DialogsFactory;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.WindowEvent;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import static intelligent.wiki.editor.gui.actions.ActionId.*;
import static intelligent.wiki.editor.gui.fx.actions.JavaFxAction.toButton;
import static intelligent.wiki.editor.gui.fx.actions.JavaFxAction.toMenuItem;

/**
 * Controller class for main window of wiki editor. Contains methods to make different actions.
 * Note, that all these actions are <code>public</code> to be referred from <code>fxml</code> file.
 *
 * @author Myroslav Rudnytskyi
 * @version 20.09.2015
 */
public class WikiEditorController implements Initializable, EventHandler<WindowEvent> {
	@Inject
	private final Project project;
	@Inject
	private final TreeItemFactory<ASTNode> treeItemFactory;
	private final TextUpdateTracker updateTracker = new TextUpdateTracker();
	@Inject
	private final DialogsFactory dialogs;
	@Inject
	private final WikiOperations wiki;
	@FXML
	public Menu editMenuItem;
	@FXML
	public ToolBar toolbar;
	private ObservableArticle article;
	private ResourceBundle i18n;
	private File currentOpenedFile;
	@FXML
	private Tab tab;
	@FXML
	private ObservableCodeArea text;
	@FXML
	private TreeView<ASTNode> tree;
	private JavaFxActions actions;

	/**
	 * Note, that parameters can not be <code>null</code>!
	 *
	 * @param wiki    object, performing operations with Wikipedia
	 * @param dialogs object, creating dialogs
	 * @param project project object
	 * @param treeItems factory for tree items
	 */
	public WikiEditorController(WikiOperations wiki, DialogsFactory dialogs, Project project, TreeItemFactory<ASTNode> treeItems) {
		this.wiki = Objects.requireNonNull(wiki, "Null wiki operations object!");
		this.dialogs = Objects.requireNonNull(dialogs, "Null dialogs factory object!");
		this.project = Objects.requireNonNull(project, "Null project object!");
		this.treeItemFactory = Objects.requireNonNull(treeItems, "Tree item factory null!");
		article = new ObservableArticleAdapter(project, treeItems);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		i18n = resources;
		text.codeProperty().addListener(updateTracker);
		actions = new JavaFxActions(text);
		editMenuItem.getItems().addAll(
				toMenuItem(actions.get(CUT)), toMenuItem(actions.get(COPY)), toMenuItem(actions.get(PASTE)));
		toolbar.getItems().addAll(new Separator(Orientation.VERTICAL),
				toButton(actions.get(CUT)), toButton(actions.get(COPY)), toButton(actions.get(PASTE)));
		text.contentMenuProperty().setValue(createCodeAreaMenu());
	}

	private ContextMenu createCodeAreaMenu() {
		ContextMenu menu = new ContextMenu();
		menu.getItems().addAll(
				toMenuItem(actions.get(CUT)), toMenuItem(actions.get(COPY)), toMenuItem(actions.get(PASTE)));
		return menu;
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
		File file = chooser.showOpenDialog(null);
		if (file != null) {
			currentOpenedFile = file;
			Platform.runLater(() -> openFile(file));
			updateTracker.clearUpdated();
		}
	}

	private void openFile(File file) {
		try {
			updateTracker.startIgnoringUpdating();
			String articleContent = FilesFacade.readTXT(file.getAbsolutePath());
			open(file.getName(), articleContent);
		} catch (IOException e) {
			dialogs.makeErrorDialog(e).show();
		} finally {
			updateTracker.stopIgnoringUpdating();
		}
	}

	//TODO 1.maybe change wpf to waf: not project, but article 2. constant extracting 3. method extracting
	private FileChooser createWikiArticleFileChooser() {
		FileChooser chooser = new FileChooser();
		chooser.setInitialDirectory(new File("."));
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
			Platform.runLater(() -> openURL(result.get()));
			updateTracker.clearUpdated();
		}
	}

	private void openURL(String articleNameURL) {
		try {
			updateTracker.startIgnoringUpdating();
			String articleContent = wiki.getArticleContent(articleNameURL);
			open(articleNameURL, articleContent);
		} catch (IOException e) {
			dialogs.makeErrorDialog(e).show();
		} finally {
			updateTracker.stopIgnoringUpdating();
		}
	}

	private void open(String articleTitle, String articleContent) {
		tab.setText(articleTitle);
		project.makeArticle(articleTitle, articleContent);
		article = new ObservableArticleAdapter(project, treeItemFactory);
		updateBindings();
		text.setCode(articleContent);
		text.moveStart();
	}

	private void updateBindings() {
		article.textProperty().bind(text.codeProperty());
		article.titleProperty().bind(tab.textProperty());
		tree.rootProperty().bind(article.rootProperty());
	}

	/**
	 * Saves article in current opened file or shows {@link FileChooser}
	 * dialog for future saving if there is no current file.
	 */
	public void actionSave() {
		if (currentOpenedFile != null) {
			Platform.runLater(() -> saveFileAs(currentOpenedFile));
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
		File file = chooser.showSaveDialog(null);
		if (file != null) {
			Platform.runLater(() -> saveFileAs(file));
			updateTracker.clearUpdated();
		}
	}

	private void saveFileAs(File file) {
		try {
			FilesFacade.writeTXT(file.getAbsolutePath(), text.getCode());
			tab.setText(file.getName());
		} catch (IOException e) {
			dialogs.makeErrorDialog(e).show();
		}
	}

	public void actionClose() {
		dialogs.makeNotImplementedErrorDialog().show();
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
		String selection = text.getSelectedCode();
		Optional<String> result = dialogs.makeInsertWikiLinkDialog(selection, selection).showAndWait();
		if (result.isPresent()) {
			text.replaceSelection(result.get());
		}
	}

	/**
	 * Shows dialog to input data for constructing link.
	 */
	public void actionInsertExternalLink() {
		String selection = text.getSelectedCode();
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
		Optional<String> result = dialogs.makeInsertHeadingDialog(text.getSelectedCode()).showAndWait();
		if (result.isPresent()) {
			String newLine = System.lineSeparator();
			text.replaceSelection(String.join("", newLine, result.get(), newLine));
		}
	}

	public void actionInsertSnippet() {
		dialogs.makeNotImplementedErrorDialog().show();
	}

	/**
	 * Shows dialog to input data for constructing template.
	 */
	public void actionInsertTemplate() {
		String selection = text.getSelectedCode();
		Optional<String> result = dialogs.makeInsertTemplateDialog(selection).showAndWait();
		if (result.isPresent()) {
			text.replaceSelection(result.get());
		}
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