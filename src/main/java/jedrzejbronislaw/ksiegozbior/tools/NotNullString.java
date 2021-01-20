package jedrzejbronislaw.ksiegozbior.tools;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotNullString {

	private boolean emptyAsNull = false;
	
	@NonNull
	private String defaultString;
	
	public String get(String s) {
		if (s == null)
			return defaultString;
		else if (emptyAsNull && s.isBlank())
			return defaultString;
		else
			return s;
	}

	public NotNullString emptyAsNull(boolean b) {
		emptyAsNull = b;
		return this;
	}
}
