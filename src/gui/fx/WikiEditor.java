/*
 * WikiEditor.java	20.05.2015
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
package gui.fx;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * 
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 20.05.2015
 */
public class WikiEditor extends Application {

	private static final String APPLICATION_ROOT_FILE =
			"res/ApplicationRoot.fxml";

	private Stage primaryStage;
	private BorderPane applicationRoot;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Intelligent Wiki Bot 0.1");
		showApplicationRoot();
	}

	private void showApplicationRoot() {
		try {
			URL fxml =
					new File(WikiEditor.APPLICATION_ROOT_FILE).toURI().toURL();

			ResourceBundle i18n =
					ResourceBundleFactory.getBundle(new Locale("uk", "ua"));

			applicationRoot = (BorderPane) new FXMLLoader(fxml, i18n).load();
		} catch (IOException e) {
			Dialogs.showError(e);
		}

		Scene scene = new Scene(applicationRoot);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	// TODO: this is code for debug application starting. Will be removed later!
	public static void main(String[] args) {
		Application.launch(args);
	}
}