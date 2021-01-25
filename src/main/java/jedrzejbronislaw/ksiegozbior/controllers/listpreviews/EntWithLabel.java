package jedrzejbronislaw.ksiegozbior.controllers.listpreviews;

import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.projections.TheEnt;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EntWithLabel {

	@NonNull @Getter private Ent entity;
	@NonNull @Getter private String label;

	public EntWithLabel(Ent entity) {
		this.entity = entity;
		this.label  = TheEnt.generateLabel(entity);
	}
}
