package jedrzejbronislaw.ksiegozbior.model.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.repositories.PublishingHouseRepository;

@Component
public class PublishingHouseSearch implements Search {

	@Autowired
	private PublishingHouseRepository repository;
	
	@Override
	public Iterable<? extends Ent> all() {
		return repository.findAll();
	}

}
