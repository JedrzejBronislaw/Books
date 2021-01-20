package jedrzejbronislaw.ksiegozbior.controllers.listpreviews;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jedrzejbronislaw.ksiegozbior.controllers.entitypreviews.CollectionPreviewController;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.entities.collections.BookCollection;
import jedrzejbronislaw.ksiegozbior.model.repositories.BookCollectionRepository;

@Component
public class BookCollectionListPreviewController extends MultiEntityViewControllerStrategy{

	@Autowired
	private BookCollectionRepository repository;

	@Autowired
	private CollectionPreviewController previewController;
	
	@Override
	public boolean delAction(Ent entity) {
		if(entity instanceof BookCollection) {
			BookCollection collection = (BookCollection) entity;
		
			if(repository.numberOfElements(collection.getId()) != 0 ||
				repository.numberOfSubcollections(collection.getId()) != 0)
				return false;
				
			try {
				repository.delete(collection);
				return true;
			} catch (Exception e) {
				return false;
			}
		} else
			return false;
	}

	@Override
	public void addAction() {//TODO
		System.out.println("ADD");
	}

	@Override
	public void listClickAction(Ent entity) {
		System.out.println("Klik! -> " + entity.toString());
		if (entity instanceof BookCollection)
			previewController.setBookCollection((BookCollection) entity);
	}
	
	@Override
	public List<? extends Ent> getList() {
		List<Ent> list = new LinkedList<Ent>();

		repository.findAll().forEach(e -> list.add(e));
		
		return list;
	}


}
