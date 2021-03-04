package jedrzejbronislaw.ksiegozbior.controllers.lists.managers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jedrzejbronislaw.ksiegozbior.controllers.previews.EditionPreview;
import jedrzejbronislaw.ksiegozbior.model.entities.Edition;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.managers.EditionManager;
import lombok.Getter;

@Component
public class EditionListManager implements ListManager {

	@Getter private final ListType type = ListType.LIST;

	@Autowired private EditionManager manager;
	@Autowired private EditionPreview preview;


	@Override
	public boolean delete(Ent entity) {
		return (entity instanceof Edition) ? manager.setAsRemoved((Edition) entity) : false;
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
		return manager.getAll();
	}
}
