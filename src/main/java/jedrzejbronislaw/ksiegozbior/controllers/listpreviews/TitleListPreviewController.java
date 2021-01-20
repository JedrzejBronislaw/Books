package jedrzejbronislaw.ksiegozbior.controllers.listpreviews;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jedrzejbronislaw.ksiegozbior.controllers.entitypreviews.TitlePreviewController;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.entities.Title;
import jedrzejbronislaw.ksiegozbior.model.repositories.TitleRepository;

@Component
public class TitleListPreviewController extends MultiEntityViewControllerStrategy{

	@Autowired
	private TitleRepository repository;

	@Autowired
	private TitlePreviewController titlePreviewController;
	
	@Override
	public boolean delAction(Ent entity) {
		if(entity instanceof Title) {
			Title title = (Title) entity;
			try {
				title.setRemoved(true);
				repository.save(title);
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
		if (entity instanceof Title)
			titlePreviewController.setTitle((Title) entity);
	}
	
	@Override
	public List<? extends Ent> getList() {
		return repository.findAllNotRemoved();
	}

}
