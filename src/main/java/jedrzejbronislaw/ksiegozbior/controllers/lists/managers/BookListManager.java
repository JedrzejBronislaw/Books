package jedrzejbronislaw.ksiegozbior.controllers.lists.managers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jedrzejbronislaw.ksiegozbior.controllers.entitypreviews.BookPreviewController;
import jedrzejbronislaw.ksiegozbior.controllers.lists.ListManager;
import jedrzejbronislaw.ksiegozbior.model.entities.Book;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.repositories.BookRepository;

@Component
public class BookListManager extends ListManager {

	@Autowired private BookRepository repository;
	@Autowired private BookPreviewController bookPreviewController;
	
	
	@Override
	public boolean delete(Ent entity) {
		return (entity instanceof Book) ? setAsRemoved((Book) entity) : false;
	}

	private boolean setAsRemoved(Book book) {
		try {
			
			book.setRemoved(true);
			repository.save(book);
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
		if (entity instanceof Book)
			bookPreviewController.setBook((Book) entity);
	}
	
	@Override
	public List<? extends Ent> get() {
		return repository.findAllNotRemoved();
	}
}
