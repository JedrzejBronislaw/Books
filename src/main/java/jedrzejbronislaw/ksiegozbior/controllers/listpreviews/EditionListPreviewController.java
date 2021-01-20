package jedrzejbronislaw.ksiegozbior.controllers.listpreviews;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jedrzejbronislaw.ksiegozbior.controllers.entitypreviews.EditionPreviewController;
import jedrzejbronislaw.ksiegozbior.model.entities.Edition;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.repositories.EditionRepository;

@Component
public class EditionListPreviewController extends MultiEntityViewControllerStrategy{

	@Autowired
	private EditionRepository repository;

	@Autowired
	private EditionPreviewController editionPreviewController;
	
	@Override
	public boolean delAction(Ent entity) {
		if(entity instanceof Edition) {
			Edition edition = (Edition) entity;
			try {
				edition.setRemoved(true);
				repository.save(edition);
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
//		System.out.println("Kliknięto na książkę: " + entity.toString());//TODO delete
		if (entity instanceof Edition)
			editionPreviewController.setEdition((Edition) entity);
	}
	
	@Override
	public List<? extends Ent> getList() {
		return repository.findAllNotRemoved();
	}

}
