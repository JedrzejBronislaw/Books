package jedrzejbronislaw.ksiegozbior.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import jedrzejbronislaw.ksiegozbior.model.entities.Title;

public interface TitleRepository extends CrudRepository<Title, Long> {

	@Query(value="SELECT * FROM title WHERE title=:title",
			nativeQuery=true)
	public List<Title> findByName(String title);

	@Query(value="SELECT t.* FROM  Title t JOIN authorship a ON a.title_id=t.id WHERE a.author_id=:authorId",
			nativeQuery=true)
	public List<Title> findByAuthorId(long authorId);

	@Query(value="SELECT t.* FROM  Title t "
			+ "JOIN Authorship a ON a.title_id=t.id "
//	TODO	+ "JOIN PenNameAuthorship pnas ON pnas.title_id=t.id "
//	TODO	+ "JOIN PenNames pn ON pnas.penname_id=pn.id "
			+ "WHERE a.author_id=:authorId "
//	TODO	+ "OR pn.name=:authorId"
			, nativeQuery=true)
	public List<Title> findByAuthorAndPenNames(long authorId);

	@Query(value = "SELECT t.* FROM title t WHERE removed=false",
			nativeQuery = true)
	public List<Title> findAllNotRemoved();
	
//	@Query("SELECT count(ta) FROM title_authors ta WHERE AUTHORS_ID = (:AuthorId)")
//	public int geTitleNumberbyAuthor(long AuthorId);

	@Query("SELECT DISTINCT t FROM Title t "
			+ "WHERE upper(t.title) LIKE concat('%', upper(:phrase),'%') "
			+ "AND removed=false"
			)
	public List<Title> findByTitlePhrase(String phrase);
}
