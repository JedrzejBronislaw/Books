package jedrzejbronislaw.ksiegozbior.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import jedrzejbronislaw.ksiegozbior.model.entities.Book;
import jedrzejbronislaw.ksiegozbior.model.entities.Location;

public interface LocationRepository extends CrudRepository<Location, Long>{

	@Query(value="SELECT * FROM location WHERE name=:name",
			nativeQuery=true)
	public List<Location> findByName(String name);

	@Query(value="SELECT * FROM location WHERE removed=false",
			nativeQuery=true)
	public List<Location> findAllNotRemoved();

	@Query(value="SELECT count(*) FROM book WHERE location_id=:locationId",
			nativeQuery=true)
	public long numberOfBooks(long locationId);
	
	@Query(value="SELECT b FROM Book b WHERE location=:location")
	public List<Book> getBooks(Location location);
	
	@Query(value="SELECT l.name FROM Location l WHERE super_location_id=:locationId",
			nativeQuery=true)
	public List<String> getSublocationsNames(long locationId);
}
