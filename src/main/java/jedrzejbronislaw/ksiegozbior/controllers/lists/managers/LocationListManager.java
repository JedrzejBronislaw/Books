package jedrzejbronislaw.ksiegozbior.controllers.lists.managers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jedrzejbronislaw.ksiegozbior.controllers.entitypreviews.LocationPreviewController;
import jedrzejbronislaw.ksiegozbior.controllers.lists.ListManager;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.entities.Location;
import jedrzejbronislaw.ksiegozbior.model.repositories.LocationRepository;

@Component
public class LocationListManager extends ListManager {

	@Autowired private LocationRepository repository;
	@Autowired private LocationPreviewController previewController;
	
	
	@Override
	public boolean delete(Ent entity) {
		return (entity instanceof Location) ? setAsRemoved((Location) entity) : false;
	}

	private boolean setAsRemoved(Location location) {
		try {

			location.setRemoved(true);
			repository.save(location);
			return true;

		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void add() {//TODO
		System.out.println("ADD");
	}

	@Override
	public void clickAction(Ent entity) {
		System.out.println("Klik! -> " + entity.toString());

		if (entity instanceof Location)
			previewController.setLocation((Location) entity);
	}
	
	@Override
	public List<? extends Ent> get() {
		return repository.findAllNotRemoved();
	}
}
