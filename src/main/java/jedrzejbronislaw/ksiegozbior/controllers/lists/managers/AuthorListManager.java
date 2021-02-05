package jedrzejbronislaw.ksiegozbior.controllers.lists.managers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jedrzejbronislaw.ksiegozbior.controllers.entitypreviews.AuthorPreviewController;
import jedrzejbronislaw.ksiegozbior.controllers.lists.ListManager;
import jedrzejbronislaw.ksiegozbior.model.entities.Author;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.repositories.AuthorRepository;

@Component
public class AuthorListManager extends ListManager {

	@Autowired private AuthorRepository repository;
	@Autowired private AuthorPreviewController previewController;


	@Override
	public boolean delete(Ent entity) {
		return (entity instanceof Author) ? setAsRemoved((Author) entity) : false;
	}

	private boolean setAsRemoved(Author author) {
		try {
			
			author.setRemoved(true);
			repository.save(author);
			return true;
			
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void add() {}

	@Override
	public void clickAction(Ent entity) {
		if (entity instanceof Author)
			previewController.setAuthor((Author) entity);
	}
	
	@Override
	public List<? extends Ent> get() {
		return repository.findAllNotRemoved();
	}
}
