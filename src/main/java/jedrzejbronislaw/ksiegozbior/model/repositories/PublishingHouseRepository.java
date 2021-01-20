package jedrzejbronislaw.ksiegozbior.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import jedrzejbronislaw.ksiegozbior.model.entities.PublishingHouse;

public interface PublishingHouseRepository extends CrudRepository<PublishingHouse, Long> {

	@Query(value="SELECT * FROM Publishing_house WHERE name LIKE %:abbr%",
			nativeQuery=true)
	List<PublishingHouse> findByName(String abbr);
	
	@Query(value="SELECT * FROM Publishing_house WHERE abbr = :abbr",
			nativeQuery=true)
	List<PublishingHouse> findByAbbr(String abbr);

	@Query(value="SELECT * FROM Publishing_house WHERE removed = false",
			nativeQuery=true)
	List<PublishingHouse> findAllNotRemoved();
	
	@Query(value=
			"SELECT count(*) FROM edition e WHERE e.publishing_house_id = :publisherId",
			nativeQuery=true)
	Long NumberOfEditions(Long publisherId);
}
