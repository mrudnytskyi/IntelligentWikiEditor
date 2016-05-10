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

import intelligent.wiki.editor.spring.SpringFXMLLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

/**
 * Main class of wiki editor application, containing code to run it
 * ({@link #start(Stage)}) and load GUI.
 *
 * @author Myroslav Rudnytskyi
 * @version 20.09.2015
 */
public class WikiEditor extends Application {

	private static final String WIKI_EDITOR_ROOT_FILE = "src/main/resources/WikiEditorRoot.fxml";
	private static final Logger LOG = Logger.getLogger(WikiEditor.class.getName());

	private Stage primaryStage;

	/**
	 * Method, called on wiki editor GUI start-up.
	 */
	@Override
	public void start(Stage primaryStage) {
		assert primaryStage != null;
		LOG.info("Starting application...");
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Intelligent Wiki Editor");
		showApplicationRoot(createApplicationRoot());
	}

	private Parent createApplicationRoot() {
		try {
			return loadApplicationRoot();
		} catch (IOException e) {
			e.printStackTrace();
			LOG.severe("Fatal exception: can not load GUI because " + e);
			System.exit(1);
		}
		return null; // unreachable
	}

	private Parent loadApplicationRoot() throws IOException {
		SpringFXMLLoader loader = new SpringFXMLLoader(ResourceBundleFactory.getBundle());
		URL fxml = new File(WIKI_EDITOR_ROOT_FILE).toURI().toURL();
		Parent applicationRoot = loader.load(fxml);
		primaryStage.setOnCloseRequest(loader.getController());
		LOG.info("Application root loaded successfully!");
		return applicationRoot;
	}

	private void showApplicationRoot(Parent root) {
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}