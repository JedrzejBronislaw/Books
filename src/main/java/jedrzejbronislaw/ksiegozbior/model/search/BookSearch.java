package jedrzejbronislaw.ksiegozbior.model.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jedrzejbronislaw.ksiegozbior.model.entities.Book;
import jedrzejbronislaw.ksiegozbior.model.repositories.BookRepository;

@Component
public class BookSearch implements Search{

	@Autowired
	BookRepository repository;
	
	public Iterable<Book> all(){
		return repository.findAll();
	}
}
