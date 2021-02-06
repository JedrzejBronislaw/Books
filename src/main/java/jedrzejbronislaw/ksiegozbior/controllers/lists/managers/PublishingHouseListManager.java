package jedrzejbronislaw.ksiegozbior.controllers.lists.managers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jedrzejbronislaw.ksiegozbior.controllers.entities.PublisherPreview;
import jedrzejbronislaw.ksiegozbior.controllers.lists.ListManager;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.entities.PublishingHouse;
import jedrzejbronislaw.ksiegozbior.model.repositories.PublishingHouseRepository;
import lombok.Getter;

@Component
public class PublishingHouseListManager extends ListManager {

	@Getter private final MultiEntityViewType type = MultiEntityViewType.LIST;

	@Autowired private PublishingHouseRepository repository;
	@Autowired private PublisherPreview preview;


	@Override
	public boolean delete(Ent entity) {
		return (entity instanceof PublishingHouse) ? setAsRemoved((PublishingHouse) entity) : false;
	}

	private boolean setAsRemoved(PublishingHouse publisher) {
		try {
			
			publisher.setRemoved(true);
			repository.save(publisher);
			return true;
			
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void add() {
		// TODO Auto-generated method stub
	}

	@Override
	public void clickAction(Ent entity) {
		if (entity instanceof PublishingHouse)
			preview.setPublisher((PublishingHouse) entity);
	}
	
	@Override
	public List<? extends Ent> get() {
		return repository.findAllNotRemoved();
	}
}
