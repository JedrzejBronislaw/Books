package jedrzejbronislaw.ksiegozbior.model.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jedrzejbronislaw.ksiegozbior.model.entities.HierarhicalEnt;
import jedrzejbronislaw.ksiegozbior.model.repositories.EditionCollectionRepository;

@Component
public class EditionCollectionSearch implements Search {

	@Autowired
	private EditionCollectionRepository repository;
	
	@Override
	public Iterable<? extends HierarhicalEnt> all() {
		return repository.findAll();
	}

}
