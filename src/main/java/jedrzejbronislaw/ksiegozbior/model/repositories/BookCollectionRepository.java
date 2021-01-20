package jedrzejbronislaw.ksiegozbior.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import jedrzejbronislaw.ksiegozbior.model.entities.Book;
import jedrzejbronislaw.ksiegozbior.model.entities.collections.BookCollection;

public interface BookCollectionRepository extends CrudRepository<BookCollection, Long>{

	@Query(value="SELECT count(*) FROM book_collection WHERE super_collection_id = :collectionId",
			nativeQuery = true)
	Long numberOfSubcollections(Long collectionId);
	
	@Query(value="SELECT count(*) FROM book_collection_link WHERE collection_id = :collectionId",
			nativeQuery = true)
	Long numberOfElements(Long collectionId);
	
	@Query(value="SELECT * FROM book_collection WHERE super_collection_id = :collectionId",
			nativeQuery = true)
	List<BookCollection> findSubcollections(Long collectionId);

	@Query(value="SELECT b FROM BookCollectionLink bcl "
			+ "JOIN Book b ON b.id = bcl.elementId "
			+ "WHERE bcl.collectionId = :collectionId")
	List<Book> findElements(Long collectionId);
}
