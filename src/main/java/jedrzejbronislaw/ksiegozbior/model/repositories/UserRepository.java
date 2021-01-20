package jedrzejbronislaw.ksiegozbior.model.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import jedrzejbronislaw.ksiegozbior.model.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {

	@Query("SELECT u FROM User u WHERE u.login = :login")
	User findByLogin(String login);
	
}
