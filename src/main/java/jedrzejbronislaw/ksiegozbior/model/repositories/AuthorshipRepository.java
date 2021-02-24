package jedrzejbronislaw.ksiegozbior.model.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import jedrzejbronislaw.ksiegozbior.model.entities.Authorship;
import jedrzejbronislaw.ksiegozbior.model.entities.AuthorshipId;
import jedrzejbronislaw.ksiegozbior.model.entities.Title;

public interface AuthorshipRepository extends CrudRepository<Authorship, AuthorshipId> {

	@Query("SELECT count(*) FROM Authorship WHERE author_id = ?1")
	long countTitlesByAuthorId(long authorId);

	@Modifying
	@Transactional
	@Query("DELETE FROM Authorship WHERE title = ?1")
	void deleteByTitle(Title title);
}
