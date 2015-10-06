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

import intelligent.wiki.editor.gui.fx.dialogs.Dialogs;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Main class of wiki editor application, containing code to run it
 * ({@link #start(Stage)}) and load GUI.
 *
 * @author Myroslav Rudnytskyi
 * @version 20.09.2015
 */
public class WikiEditor extends Application {

	private static final String WIKI_EDITOR_ROOT_FILE =
			"src/main/resources/WikiEditorRoot.fxml";

	private Stage primaryStage;
	private BorderPane applicationRoot;

	/**
	 * Method, called on wiki editor start-up.
	 */
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Intelligent Wiki Editor 0.1");
		showApplicationRoot();
	}

	private void showApplicationRoot() {
		try {
			URL fxml = new File(WikiEditor.WIKI_EDITOR_ROOT_FILE).toURI().toURL();
			ResourceBundle i18n = ResourceBundleFactory.getBundle(new Locale("uk", "ua"));
			FXMLLoader loader = new FXMLLoader(fxml, i18n);
			applicationRoot = loader.load();
			((WikiEditorController) loader.getController()).registerCloseHandler(primaryStage);
		} catch (IOException e) {
			Dialogs.showError(e);
		}

		Scene scene = new Scene(applicationRoot);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}