package jedrzejbronislaw.ksiegozbior.controllers.listpreviews;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jedrzejbronislaw.ksiegozbior.controllers.entitypreviews.AuthorPreviewController;
import jedrzejbronislaw.ksiegozbior.model.entities.Author;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.repositories.AuthorRepository;

@Component
public class AuthorListPreviewController extends MultiEntityViewControllerStrategy{

	@Autowired
	private AuthorRepository repository;

	@Autowired
	private AuthorPreviewController previewController;
	
	@Override
	public boolean delAction(Ent entity) {
		if(entity instanceof Author) {
			Author author = (Author) entity;
			try {
				author.setRemoved(true);
				repository.save(author);
				return true;
			} catch (Exception e) {
				return false;
			}
		} else
			return false;
	}

	@Override
	public void addAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void listClickAction(Ent entity) {
		if (entity instanceof Author)
			previewController.setAuthor((Author) entity);
	}
	
	@Override
	public List<? extends Ent> getList() {
		return repository.findAllNotRemoved();
	}

}
