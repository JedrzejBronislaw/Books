package jedrzejbronislaw.ksiegozbior.model.entities;

import jedrzejbronislaw.ksiegozbior.lang.Internationalization;
import lombok.Getter;

public enum Visibility {
	Default((byte)0, "visibility.default"),
	OwnerOnly((byte)1, "visibility.ownerOnly"),
	Friends((byte)2, "visibility.friends"),
	All((byte)3, "visibility.all");

	@Getter
	private byte value;
	private String description;
	
	private Visibility(byte val, String desc) {
		value = val;
		description = desc;
	}
	
	@Override
	public String toString() {
		try{
			return Internationalization.get(description);
		} catch (Exception e) {
			return description;
		}
	}

}
