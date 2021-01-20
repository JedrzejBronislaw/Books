package jedrzejbronislaw.ksiegozbior.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import jedrzejbronislaw.ksiegozbior.model.entities.Title;
import jedrzejbronislaw.ksiegozbior.model.entities.collections.TitleCollection;

public interface TitleCollectionRepository extends CrudRepository<TitleCollection, Long>{

	@Query(value="SELECT count(*) FROM title_collection WHERE super_collection_id = :collectionId",
			nativeQuery = true)
	Long numberOfSubcollections(Long collectionId);


	@Query(value="SELECT * FROM title_collection WHERE super_collection_id = :collectionId",
			nativeQuery = true)
	List<TitleCollection> findSubcollections(Long collectionId);
	
//	@Query(value="SELECT * FROM title_collection WHERE id = :collectionId",
//			nativeQuery = true)
//	TitleCollection findSupercollection(Long collectionId);

	@Query(value="SELECT t FROM TitleCollectionLink tcl "
			+ "JOIN Title t ON t.id = tcl.elementId "
			+ "WHERE tcl.collectionId = :collectionId")
	List<Title> findElements(Long collectionId);
	
	@Query(value="SELECT count(*) FROM title_collection_link WHERE collection_id = :collectionId",
			nativeQuery = true)
	Long numberOfElements(Long collectionId);
}
