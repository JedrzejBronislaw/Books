package jedrzejbronislaw.ksiegozbior.model.repositories;

import org.springframework.data.repository.CrudRepository;

import jedrzejbronislaw.ksiegozbior.model.entities.collections.CollectionLinkId;
import jedrzejbronislaw.ksiegozbior.model.entities.collections.TitleCollectionLink;

public interface TitleCollectionLinkRepository extends CrudRepository<TitleCollectionLink, CollectionLinkId> {

}
