package jedrzejbronislaw.ksiegozbior.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import jedrzejbronislaw.ksiegozbior.model.entities.Book;
import jedrzejbronislaw.ksiegozbior.model.entities.Edition;
import jedrzejbronislaw.ksiegozbior.model.entities.Language;
import jedrzejbronislaw.ksiegozbior.model.entities.Title;
import lombok.NonNull;

public interface LanguageRepository extends CrudRepository<Language, Long> {

	@Query(value = "SELECT * FROM language l WHERE abbr = (:abbr)",
			nativeQuery = true)
	public Language[] findByAbbr(String abbr);

	@Query(value = "SELECT count(*) FROM book b "
			+ "JOIN edition e ON b.edition_id = e.id "
			+ "WHERE e.language_id=:languageId",
			nativeQuery = true)
	public long numberOfBooks(long languageId);

	@Query(value = "SELECT count(*) FROM edition e "
			+ "WHERE e.language_id=:languageId",
			nativeQuery = true)
	public long numberOfEditions(long languageId);

	@Query(value = "SELECT count(*) FROM title t "
			+ "WHERE t.language_id=:languageId",
			nativeQuery = true)
	public long numberOfTitles(long languageId);

	@Query("SELECT b FROM Book b "
			+ "JOIN Edition e ON b.edition = e.id "
			+ "WHERE e.language=:language")
	public List<Book> getBooks(@NonNull Language language);

	@Query("SELECT e FROM Edition e "
			+ "WHERE e.language=:language")
	public List<Edition> getEditions(@NonNull Language language);

	@Query("SELECT t FROM Title t "
			+ "WHERE t.language=:language")
	public List<Title> getTitles(@NonNull Language language);
}
