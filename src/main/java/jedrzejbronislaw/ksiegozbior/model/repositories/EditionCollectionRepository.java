package jedrzejbronislaw.ksiegozbior.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import jedrzejbronislaw.ksiegozbior.model.entities.Book;
import jedrzejbronislaw.ksiegozbior.model.entities.Edition;
import jedrzejbronislaw.ksiegozbior.model.entities.collections.BookCollection;
import jedrzejbronislaw.ksiegozbior.model.entities.collections.EditionCollection;

public interface EditionCollectionRepository extends CrudRepository<EditionCollection, Long>{

	@Query(value="SELECT count(*) FROM edition_collection WHERE super_collection_id = :collectionId",
			nativeQuery = true)
	Long numberOfSubcollections(Long collectionId);
	
	@Query(value="SELECT count(*) FROM edition_collection_link WHERE collection_id = :collectionId",
			nativeQuery = true)
	Long numberOfElements(Long collectionId);
	
	@Query(value="SELECT * FROM edition_collection WHERE super_collection_id = :collectionId",
			nativeQuery = true)
	List<EditionCollection> findSubcollections(Long collectionId);

	@Query(value="SELECT e FROM EditionCollectionLink ecl "
			+ "JOIN Edition e ON e.id = ecl.elementId "
			+ "WHERE ecl.collectionId = :collectionId")
	List<Edition> findElements(Long collectionId);
}
