package jedrzejbronislaw.ksiegozbior.model.search;

import jedrzejbronislaw.ksiegozbior.model.entities.Ent;

public interface Search {
	
	Iterable<? extends Ent> all();
}
