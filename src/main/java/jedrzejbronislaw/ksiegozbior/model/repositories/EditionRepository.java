package jedrzejbronislaw.ksiegozbior.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import jedrzejbronislaw.ksiegozbior.model.entities.Edition;

public interface EditionRepository extends CrudRepository<Edition, Long> {

	@Query(value="SELECT e.* FROM edition e "
			+ "JOIN edition_title et ON et.edition_id=e.id "
			+ "JOIN title t ON et.title_id=t.id "
			+ "WHERE e.title=:title "
			+ "OR et.title=:title "
			+ "OR t.title=:title",
			nativeQuery=true)
	public List<Edition> findByTitle(String title);

	@Query("SELECT e FROM Edition e WHERE removed=false")
	public List<Edition> findAllNotRemoved();
	
	@Query("SELECT DISTINCT e FROM Edition e "
			+ "JOIN Edition_Title et ON et.edition=e "
			+ "JOIN et.titleObj t "
			+ "WHERE upper(e.title) LIKE concat('%', upper(:phrase),'%') "
			+ "OR (e.title is null "
			+ "    AND upper(t.title) LIKE concat('%',upper(:phrase),'%'))"
			+ "AND e.removed=false"
			)
	public List<Edition> findByTitlePhrase(String phrase);
}
