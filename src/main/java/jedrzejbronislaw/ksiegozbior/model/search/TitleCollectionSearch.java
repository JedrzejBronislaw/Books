package jedrzejbronislaw.ksiegozbior.model.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jedrzejbronislaw.ksiegozbior.model.entities.HierarhicalEnt;
import jedrzejbronislaw.ksiegozbior.model.repositories.TitleCollectionRepository;

@Component
public class TitleCollectionSearch implements Search {

	@Autowired
	private TitleCollectionRepository repository;
	
	@Override
	public Iterable<? extends HierarhicalEnt> all() {
		return repository.findAll();
	}

}
