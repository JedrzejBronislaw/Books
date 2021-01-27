package jedrzejbronislaw.ksiegozbior.model.projections;

import java.util.List;
import java.util.stream.Collectors;

import jedrzejbronislaw.ksiegozbior.model.entities.Book;
import jedrzejbronislaw.ksiegozbior.model.entities.Edition;
import jedrzejbronislaw.ksiegozbior.tools.MyList;

public class TheBook extends TheEdition implements TheEnt {

	private Book book;


	public TheBook(Book book) {
		super(book.getEdition());
		this.book = book;
	}
	

	public String getPurchaseDate() {
		return (book.getPurchaseDate() != null) ? book.getPurchaseDate().toString() : "-";
	}

	public String getLibrary() {//TODO
		return "";
	}

	public MyList<String> getComments(){
		List<String> comments = book.getComments().stream()
			.map(c -> c.getContent())
			.collect(Collectors.toUnmodifiableList());
		
		return new MyList<>(comments);
	}
	
	public String getCommentsText() {
		return getComments().serialize(System.lineSeparator() + System.lineSeparator());
	}


	@Override
	public String getLabel() {
		return getTitle();
	}

	public Edition getEdition() {
		return book.getEdition();
	}
}
