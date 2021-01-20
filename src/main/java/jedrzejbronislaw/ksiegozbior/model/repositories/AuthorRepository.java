package jedrzejbronislaw.ksiegozbior.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import jedrzejbronislaw.ksiegozbior.model.entities.Author;
import jedrzejbronislaw.ksiegozbior.model.entities.Title;

public interface AuthorRepository extends CrudRepository<Author, Long> {

	@Query(value = "SELECT * FROM AUTHOR a WHERE name = (:name) AND surname = (:surname)",
			nativeQuery = true)
	public List<Author> findByName(String name, String surname);

	@Query(value = "SELECT * FROM AUTHOR a WHERE surname = (:surname)",
			nativeQuery = true)
	public List<Author> findBySurname(String surname);

	@Query("SELECT a FROM Author a WHERE removed = false")
	public List<Author> findAllNotRemoved();

	@Query("SELECT a FROM Author a "
			+ "WHERE (upper(a.name) LIKE concat('%', upper(:phrase), '%') "
			+ "OR upper(a.surname) LIKE concat('%', upper(:phrase), '%')) "
			+ "AND removed = false")
	public List<Author> findByPhrase(String phrase);

}
