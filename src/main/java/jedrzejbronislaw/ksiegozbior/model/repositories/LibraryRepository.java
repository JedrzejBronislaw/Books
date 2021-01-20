package jedrzejbronislaw.ksiegozbior.model.repositories;

import org.springframework.data.repository.CrudRepository;

import jedrzejbronislaw.ksiegozbior.model.entities.Library;

public interface LibraryRepository extends CrudRepository<Library, Long> {
	
}
