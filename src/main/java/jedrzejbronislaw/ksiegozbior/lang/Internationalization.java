package jedrzejbronislaw.ksiegozbior.lang;

import java.util.Locale;
import java.util.ResourceBundle;

import lombok.Getter;

public class Internationalization {
	static final String resourceLocation = "jedrzejbronislaw.ksiegozbior.lang.Labels";
	private static ResourceBundle rb = ResourceBundle.getBundle(resourceLocation);
	
	
	@Getter
	private static Languages currentLanguage;
	
	
	public static String get(String key) {
		return rb.getString(key);
	}
	
	private static void refresh() {
		rb = ResourceBundle.getBundle(resourceLocation);
	}
	
	public static void setLanguage(Languages language) {
		Locale.setDefault(language.getLocale());
		refresh();
		currentLanguage = language;
	}
}
