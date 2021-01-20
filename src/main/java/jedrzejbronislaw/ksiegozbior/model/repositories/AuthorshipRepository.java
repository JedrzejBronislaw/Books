package jedrzejbronislaw.ksiegozbior.model.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import jedrzejbronislaw.ksiegozbior.model.entities.Authorship;
import jedrzejbronislaw.ksiegozbior.model.entities.AuthorshipId;

public interface AuthorshipRepository extends CrudRepository<Authorship, AuthorshipId> {

	@Query("SELECT count(*) FROM Authorship WHERE author_id = ?1")
	long countTitlesByAuthorId(long authorId);
}
