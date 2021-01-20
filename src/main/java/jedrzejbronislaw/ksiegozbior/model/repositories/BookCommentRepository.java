package jedrzejbronislaw.ksiegozbior.model.repositories;

import org.springframework.data.repository.CrudRepository;

import jedrzejbronislaw.ksiegozbior.model.entities.BookComment;

public interface BookCommentRepository extends CrudRepository<BookComment, Long> {

}
