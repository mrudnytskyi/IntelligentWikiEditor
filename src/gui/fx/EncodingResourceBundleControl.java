package gui.fx;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Locale;
import java.util.Objects;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * See <a
 * href="http://www.gubber.ru/Razrabotka/ResourceBundle-and-UTF-8.html">site</a>
 * for details.
 */
public class EncodingResourceBundleControl extends ResourceBundle.Control {

	private static final Logger log = Logger
			.getLogger(EncodingResourceBundleControl.class.getName());

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
			throws IllegalAccessException, InstantiationException, IOException {

		String bundleName = toBundleName(baseName, locale);
		String resourceName = toResourceName(bundleName, "properties");
		URL resourceURL = loader.getResource(resourceName);
		if (resourceURL != null) {
			try {
				return new PropertyResourceBundle(new InputStreamReader(
						resourceURL.openStream(), encoding));
			} catch (Exception e) {
				EncodingResourceBundleControl.log.log(Level.FINE,
						"exception thrown during bundle initialization", e);
			}
		}

		return super.newBundle(baseName, locale, format, loader, reload);
	}
}