package jedrzejbronislaw.ksiegozbior.controllers.listpreviews;

import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EntWithLabel {

	@NonNull
	@Getter
	private Ent entity;
	
	@NonNull
	@Getter
	private String label;

}
