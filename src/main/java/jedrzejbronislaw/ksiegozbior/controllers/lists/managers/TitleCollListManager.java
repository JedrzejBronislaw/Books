package jedrzejbronislaw.ksiegozbior.controllers.lists.managers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jedrzejbronislaw.ksiegozbior.controllers.entitypreviews.CollectionPreviewController;
import jedrzejbronislaw.ksiegozbior.controllers.lists.ListManager;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.entities.collections.TitleCollection;
import jedrzejbronislaw.ksiegozbior.model.repositories.TitleCollectionRepository;

@Component
public class TitleCollListManager extends ListManager {

	@Autowired private TitleCollectionRepository repository;
	@Autowired private CollectionPreviewController previewController;


	@Override
	public boolean delete(Ent entity) {
		if(entity instanceof TitleCollection) {

			TitleCollection collection = (TitleCollection) entity;
			return (isLeaf(collection)) ? delete(collection) : false;

		} else
			return false;
	}

	private boolean delete(TitleCollection collection) {
		try {
			
			repository.delete(collection);
			return true;
			
		} catch (Exception e) {
			return false;
		}
	}

	private boolean isLeaf(TitleCollection collection) {
		long id = collection.getId();
		
		return repository.numberOfElements(      id) == 0
			&& repository.numberOfSubcollections(id) == 0;
	}

	@Override
	public void add() {//TODO
		System.out.println("ADD");
	}

	@Override
	public void clickAction(Ent entity) {
		System.out.println("Klik! -> " + entity.toString());
		
		if (entity instanceof TitleCollection)
			previewController.setTitleCollection((TitleCollection) entity);
	}
	
	@Override
	public List<? extends Ent> get() {
		List<Ent> list = new LinkedList<>();

		repository.findAll().forEach(list::add);
		
		return list;
	}
}
