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

package intelligent.wiki.editor.spring;

import javafx.fxml.FXMLLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class is wrapper over {@link FXMLLoader} to load FXML controllers using Spring framework.
 *
 * @author Myroslav Rudnytskyi
 * @version 23.10.2015
 */
public class SpringFXMLLoader {

	private static final ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
	private final ResourceBundle i18n;
	private FXMLLoader loader;

	/**
	 * Constructs loader with specified resources, used to resolve resource key attribute values
	 *
	 * @param i18n resources bundle
	 */
	public SpringFXMLLoader(ResourceBundle i18n) {
		this.i18n = i18n;
	}

	/**
	 * Loads an object from FXML document using Spring and {@link FXMLLoader}.
	 *
	 * @param url path to FXML document
	 * @param <T> type of loaded object
	 * @return loaded object
	 * @throws IOException if an I/O error occurs
	 */
	public <T> T load(URL url) throws IOException {
		loader = new FXMLLoader(url, i18n);
		loader.setControllerFactory(applicationContext::getBean);
		return loader.load();
	}

	/**
	 * Just redirects call to {@link FXMLLoader actual loader}.
	 *
	 * @param <T> type of controller object
	 * @return controller, associated with root object
	 */
	public <T> T getController() {
		return loader.getController();
	}
}
