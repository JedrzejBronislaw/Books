package jedrzejbronislaw.ksiegozbior.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import jedrzejbronislaw.ksiegozbior.model.entities.Book;

public interface BookRepository extends CrudRepository<Book, Long> {
	
	@Query("SELECT b FROM Book b WHERE removed=false")
	public List<Book> findAllNotRemoved();
	
	@Query(value="SELECT b.* FROM book b "
			+ "JOIN edition e ON b.edition_id=e.id "
			+ "JOIN edition_title et ON et.edition_id=e.id "
			+ "JOIN title t ON et.title_id=t.id "
			+ "WHERE e.title=:title "
			+ "OR et.title=:title "
			+ "OR t.title=:title",
			nativeQuery=true)
	public List<Book> findByTitle(String title);

//
//	@Query(value="SELECT DISTINCT b.* FROM book b "
//			+ "JOIN edition e ON b.edition_id=e.id "
//			+ "JOIN edition_title et ON et.edition_id=e.id "
//			+ "JOIN title t ON et.title_id=t.id "
//			+ "WHERE UPPER(e.title) LIKE %UPPER((:phrase))% ",
////			+ "OR UPPER(et.title) LIKE UPPER(%(:phrase)%) "
////			+ "OR UPPER(t.title) LIKE UPPER(%(:phrase)%)",
//			nativeQuery=true)
//	public List<Book> findByTitlePhrase( String phrase);
	

	@Query(value="SELECT DISTINCT b FROM Book b "
			+ "JOIN b.edition e "
			+ "JOIN Edition_Title et ON et.edition=e "
			+ "JOIN et.titleObj t "
			+ "WHERE upper(e.title) LIKE concat('%',upper(:phrase),'%') "
			+ "OR upper(et.title) LIKE concat('%',upper(:phrase),'%') "
			+ "OR upper(t.title) LIKE concat('%',upper(:phrase),'%')"
			+ "AND b.removed=false"
			)
	public List<Book> findByTitlePhrase(String phrase);
}
