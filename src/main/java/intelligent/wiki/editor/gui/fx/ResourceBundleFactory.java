package intelligent.wiki.editor.gui.fx;
/*
 * ResourceBundleFactory.java	13.06.2015
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

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.Objects;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * 
 * @author Myroslav Rudnytskyi
 * @version 0.1 13.06.2015
 */
public class ResourceBundleFactory {

	/**
	 * See <a
	 * href="http://www.gubber.ru/Razrabotka/ResourceBundle-and-UTF-8.html"
	 * >site</a> for details.
	 */
	private static class EncodingResourceBundleControl extends
			ResourceBundle.Control {

		private final String encoding;

		public EncodingResourceBundleControl() {
			this("UTF-8");
		}

		public EncodingResourceBundleControl(String encoding) {
			this.encoding = Objects.requireNonNull(encoding, "Null encoding!");
		}

		@Override
		public ResourceBundle newBundle(String baseName, Locale locale,
				String format, ClassLoader loader, boolean reload)
				throws IllegalAccessException, InstantiationException,
				IOException {

			String bundleName = toBundleName(baseName, locale);
			String resourceName = toResourceName(bundleName, "properties");
			URL resourceURL = loader.getResource(resourceName);
			if (resourceURL != null) {
				return new PropertyResourceBundle(new InputStreamReader(
						resourceURL.openStream(), encoding));
			}

			return super.newBundle(baseName, locale, format, loader, reload);
		}
	}

	private static final String LANGUAGE_BASE_FILE = "LangBundles";

	private static ClassLoader resourcesLoader;

	static {
		try {
			ResourceBundleFactory.resourcesLoader =
					new URLClassLoader(new URL[] { new File("res").toURI()
							.toURL() });
		} catch (MalformedURLException e) {
			Dialogs.showError(e);
		}
	}

	private ResourceBundleFactory() {}

	public static ResourceBundle getBundle() {
		return ResourceBundle.getBundle(
				ResourceBundleFactory.LANGUAGE_BASE_FILE, Locale.getDefault(),
				ResourceBundleFactory.resourcesLoader,
				new EncodingResourceBundleControl());
	}

	public static ResourceBundle getBundle(Locale locale) {
		if (locale == null) {
			return ResourceBundleFactory.getBundle();
		}
		return ResourceBundle.getBundle(
				ResourceBundleFactory.LANGUAGE_BASE_FILE, locale,
				ResourceBundleFactory.resourcesLoader,
				new EncodingResourceBundleControl());
	}

}