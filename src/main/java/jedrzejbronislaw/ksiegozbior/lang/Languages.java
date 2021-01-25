package jedrzejbronislaw.ksiegozbior.lang;

import java.util.Locale;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Languages {
	POLISH(new Locale("pl","PL"), "polish"),
	ENGLISH(Locale.ENGLISH, "english");
	
	private static Languages defaultLanguage = ENGLISH;
	
	@Getter private final Locale locale;
	@Getter private final String label;


	Languages get(Locale language) {
		return Stream.of(Languages.values())
				.filter(l -> l.locale.equals(language))
				.findAny()
				.orElse(defaultLanguage);
	}
}
