package jedrzejbronislaw.ksiegozbior.controllers.listpreviews;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jedrzejbronislaw.ksiegozbior.controllers.entitypreviews.PublisherPreviewController;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.entities.PublishingHouse;
import jedrzejbronislaw.ksiegozbior.model.repositories.PublishingHouseRepository;

@Component
public class PublishingHouseListPreviewController extends MultiEntityViewControllerStrategy {

	@Autowired private PublishingHouseRepository repository;
	@Autowired private PublisherPreviewController previewController;


	@Override
	public boolean delAction(Ent entity) {
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
	public void addAction() {
		// TODO Auto-generated method stub
	}

	@Override
	public void listClickAction(Ent entity) {
		if (entity instanceof PublishingHouse)
			previewController.setPublisher((PublishingHouse) entity);
	}
	
	@Override
	public List<? extends Ent> getList() {
		return repository.findAllNotRemoved();
	}
}
