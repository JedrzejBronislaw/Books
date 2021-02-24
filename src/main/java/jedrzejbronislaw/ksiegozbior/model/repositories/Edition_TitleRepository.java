package jedrzejbronislaw.ksiegozbior.model.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import jedrzejbronislaw.ksiegozbior.model.entities.Edition;
import jedrzejbronislaw.ksiegozbior.model.entities.Edition_Title;
import jedrzejbronislaw.ksiegozbior.model.entities.Edition_TitleId;

public interface Edition_TitleRepository extends CrudRepository<Edition_Title, Edition_TitleId> {

	@Query("SELECT count(*) FROM Edition_Title WHERE edition_id = ?1")
	long countTitlesByEdition(long editionId);

	@Modifying
	@Transactional
	@Query("DELETE FROM Edition_Title WHERE edition = ?1")
	void deleteByEdition(Edition edition);
}
