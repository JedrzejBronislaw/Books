package jedrzejbronislaw.ksiegozbior.model.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jedrzejbronislaw.ksiegozbior.model.entities.HierarhicalEnt;
import jedrzejbronislaw.ksiegozbior.model.repositories.LocationRepository;

@Component
public class LocationSearch implements Search {

	@Autowired
	private LocationRepository repository;
	
	@Override
	public Iterable<? extends HierarhicalEnt> all() {
//	public Iterable<? extends Ent> all() {
		return repository.findAll();
	}

}
