package jedrzejbronislaw.ksiegozbior.controllers.lists.managers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jedrzejbronislaw.ksiegozbior.controllers.entities.CollectionPreview;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.entities.collections.BookCollection;
import jedrzejbronislaw.ksiegozbior.model.repositories.BookCollectionRepository;
import lombok.Getter;

@Component
public class BookCollListManager implements ListManager {

	@Getter private final ListType type = ListType.TREE;

	@Autowired private BookCollectionRepository repository;
	@Autowired private CollectionPreview preview;


	@Override
	public boolean delete(Ent entity) {
		if (entity instanceof BookCollection) {
			
			BookCollection collection = (BookCollection) entity;
			return (isLeaf(collection)) ? delete(collection) : false;
			
		} else
			return false;
	}

	private boolean delete(BookCollection collection) {
		try {
			
			repository.delete(collection);
			return true;
			
		} catch (Exception e) {
			return false;
		}
	}

	private boolean isLeaf(BookCollection collection) {
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

		if (entity instanceof BookCollection)
			preview.setBookCollection((BookCollection) entity);
	}
	
	@Override
	public List<? extends Ent> get() {
		List<Ent> list = new LinkedList<>();

		repository.findAll().forEach(list::add);
		
		return list;
	}
}
