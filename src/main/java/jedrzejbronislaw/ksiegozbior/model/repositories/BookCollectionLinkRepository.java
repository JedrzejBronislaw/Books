package jedrzejbronislaw.ksiegozbior.model.repositories;

import org.springframework.data.repository.CrudRepository;

import jedrzejbronislaw.ksiegozbior.model.entities.collections.BookCollectionLink;
import jedrzejbronislaw.ksiegozbior.model.entities.collections.CollectionLinkId;

public interface BookCollectionLinkRepository extends CrudRepository<BookCollectionLink, CollectionLinkId> {

}
