package jedrzejbronislaw.ksiegozbior.controllers.listpreviews;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jedrzejbronislaw.ksiegozbior.controllers.entitypreviews.BookPreviewController;
import jedrzejbronislaw.ksiegozbior.model.entities.Book;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.repositories.BookRepository;

@Component
public class BookListPreviewController extends MultiEntityViewControllerStrategy{

	@Autowired
	private BookRepository repository;

	@Autowired
	private BookPreviewController bookPreviewController;
	
	@Override
	public boolean delAction(Ent entity) {
		if(entity instanceof Book) {
			Book book = (Book) entity;
			try {
				book.setRemoved(true);
				repository.save(book);
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
		if (entity instanceof Book)
			bookPreviewController.setBook((Book) entity);
	}
	
	@Override
	public List<? extends Ent> getList() {
		return repository.findAllNotRemoved();
	}

}
