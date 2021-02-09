package jedrzejbronislaw.ksiegozbior.controllers.lists.managers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jedrzejbronislaw.ksiegozbior.controllers.previews.TitlePreview;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.entities.Title;
import jedrzejbronislaw.ksiegozbior.model.repositories.TitleRepository;
import lombok.Getter;

@Component
public class TitleListManager implements ListManager {

	@Getter private final ListType type = ListType.LIST;

	@Autowired private TitleRepository repository;
	@Autowired private TitlePreview preview;
	
	
	@Override
	public boolean delete(Ent entity) {
		return (entity instanceof Title) ? setAsRemoved((Title) entity) : false;
	}

	private boolean setAsRemoved(Title title) {
		try {
			
			title.setRemoved(true);
			repository.save(title);
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
		if (entity instanceof Title)
			preview.setTitle((Title) entity);
	}
	
	@Override
	public List<? extends Ent> get() {
		return repository.findAllNotRemoved();
	}
}
