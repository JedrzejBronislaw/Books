package jedrzejbronislaw.ksiegozbior.controllers.lists.managers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jedrzejbronislaw.ksiegozbior.controllers.entities.EditionPreview;
import jedrzejbronislaw.ksiegozbior.controllers.lists.ListManager;
import jedrzejbronislaw.ksiegozbior.model.entities.Edition;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.repositories.EditionRepository;
import lombok.Getter;

@Component
public class EditionListManager extends ListManager {

	@Getter private final MultiEntityViewType type = MultiEntityViewType.LIST;

	@Autowired private EditionRepository repository;
	@Autowired private EditionPreview preview;


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
			preview.setEdition((Edition) entity);
	}
	
	@Override
	public List<? extends Ent> get() {
		return repository.findAllNotRemoved();
	}
}
