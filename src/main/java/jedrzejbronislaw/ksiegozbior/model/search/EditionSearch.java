package jedrzejbronislaw.ksiegozbior.model.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.repositories.EditionRepository;

@Component
public class EditionSearch implements Search {

	@Autowired private EditionRepository repository;

	
	@Override
	public Iterable<? extends Ent> all() {
		return repository.findAll();
	}
}
