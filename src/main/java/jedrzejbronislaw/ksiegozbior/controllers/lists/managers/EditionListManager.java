package jedrzejbronislaw.ksiegozbior.controllers.lists.managers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jedrzejbronislaw.ksiegozbior.controllers.entitypreviews.EditionPreviewController;
import jedrzejbronislaw.ksiegozbior.controllers.lists.ListManager;
import jedrzejbronislaw.ksiegozbior.model.entities.Edition;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.repositories.EditionRepository;

@Component
public class EditionListManager extends ListManager {

	@Autowired private EditionRepository repository;
	@Autowired private EditionPreviewController editionPreviewController;


	@Override
	public boolean delete(Ent entity) {
		return (entity instanceof Edition) ? setAsRemoved((Edition) entity) : false;
	}

	private boolean setAsRemoved(Edition edition) {
		try {
			
			edition.setRemoved(true);
			repository.save(edition);
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
		if (entity instanceof Edition)
			editionPreviewController.setEdition((Edition) entity);
	}
	
	@Override
	public List<? extends Ent> get() {
		return repository.findAllNotRemoved();
	}
}
