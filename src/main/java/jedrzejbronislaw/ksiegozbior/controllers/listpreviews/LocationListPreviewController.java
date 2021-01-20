package jedrzejbronislaw.ksiegozbior.controllers.listpreviews;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jedrzejbronislaw.ksiegozbior.controllers.entitypreviews.LocationPreviewController;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.entities.Location;
import jedrzejbronislaw.ksiegozbior.model.repositories.LocationRepository;

@Component
public class LocationListPreviewController extends MultiEntityViewControllerStrategy{

	@Autowired
	private LocationRepository repository;

	@Autowired
	private LocationPreviewController previewController;
	
	@Override
	public boolean delAction(Ent entity) {
		if(entity instanceof Location) {
			Location location = (Location) entity;
			try {
				location.setRemoved(true);
				repository.save(location);
				return true;
			} catch (Exception e) {
				return false;
			}
		} else
			return false;
	}

	@Override
	public void addAction() {//TODO
		System.out.println("ADD");
	}

	@Override
	public void listClickAction(Ent entity) {
		System.out.println("Klik! -> " + entity.toString());
		if (entity instanceof Location)
			previewController.setLocation((Location) entity);
	}
	
	@Override
	public List<? extends Ent> getList() {
		return repository.findAllNotRemoved();
	}

}
