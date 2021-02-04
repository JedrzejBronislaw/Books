package jedrzejbronislaw.ksiegozbior.lang;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import lombok.Getter;

public class Internationalization {
	public static final String RESOURCE_LOCATION = "jedrzejbronislaw.ksiegozbior.lang.Labels";
	private static ResourceBundle rb = ResourceBundle.getBundle(RESOURCE_LOCATION);
	
	@Getter private static Languages currentLanguage;
	
	
	public static String get(String key) {
		return rb.getString(key);
	}
	
	public static String get(String key, String def) {
		try {
			return rb.getString(key);
		} catch (MissingResourceException e) {
			return def;
		}
	}
	
	private static void refresh() {
		rb = ResourceBundle.getBundle(RESOURCE_LOCATION);
	}
	
	public static void setLanguage(Languages language) {
		Locale.setDefault(language.getLocale());
		refresh();
		currentLanguage = language;
	}
}
