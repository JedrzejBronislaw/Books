package jedrzejbronislaw.ksiegozbior.model.entities;

import jedrzejbronislaw.ksiegozbior.lang.Internationalization;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Visibility {
	Default  ("visibility.default"),
	OwnerOnly("visibility.ownerOnly"),
	Friends  ("visibility.friends"),
	All      ("visibility.all");

	private final String description;
	
	
	@Override
	public String toString() {
		return Internationalization.get(description, description);
	}
}
