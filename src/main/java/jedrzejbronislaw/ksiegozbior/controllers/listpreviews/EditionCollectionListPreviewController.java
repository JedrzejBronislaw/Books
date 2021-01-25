package jedrzejbronislaw.ksiegozbior.controllers.listpreviews;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jedrzejbronislaw.ksiegozbior.controllers.entitypreviews.CollectionPreviewController;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.entities.collections.EditionCollection;
import jedrzejbronislaw.ksiegozbior.model.repositories.EditionCollectionRepository;

@Component
public class EditionCollectionListPreviewController extends MultiEntityViewControllerStrategy {

	@Autowired private EditionCollectionRepository repository;
	@Autowired private CollectionPreviewController previewController;


	@Override
	public boolean delAction(Ent entity) {
		if(entity instanceof EditionCollection) {

			EditionCollection collection = (EditionCollection) entity;
			return (isLeaf(collection)) ? delete(collection) : false;
			
		} else
			return false;
	}

	private boolean isLeaf(EditionCollection collection) {
		long id = collection.getId();
		
		return repository.numberOfElements(      id) == 0
			&& repository.numberOfSubcollections(id) == 0;
	}

	private boolean delete(EditionCollection collection) {
		try {
			
			repository.delete(collection);
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void addAction() {//TODO
		System.out.println("ADD");
	}

	@Override
	public void listClickAction(Ent entity) {
		System.out.println("Klik! -> " + entity.toString());

		if (entity instanceof EditionCollection)
			previewController.setEditionCollection((EditionCollection) entity);
	}
	
	@Override
	public List<? extends Ent> getList() {
		List<Ent> list = new LinkedList<>();

		repository.findAll().forEach(list::add);
		
		return list;
	}
}
