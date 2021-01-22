package jedrzejbronislaw.ksiegozbior.model.projections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jedrzejbronislaw.ksiegozbior.model.entities.Book;
import jedrzejbronislaw.ksiegozbior.model.entities.BookComment;
import jedrzejbronislaw.ksiegozbior.model.entities.Edition;
import jedrzejbronislaw.ksiegozbior.tools.MyList;

public class TheBook extends TheEdition implements TheEnt{

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
		Set<BookComment> comments = book.getComments();
		List<String> outcome = new ArrayList<String>();

//		comments.forEach(c -> outcome.add(" - " + c.getContent()));
		comments.forEach(c -> outcome.add(c.getContent()));
		
		return new MyList<>(outcome);
	}
	
	public String getCommentsText() {
		return getComments().serialize(System.lineSeparator()+System.lineSeparator());
	}


	@Override
	public String getLabel() {
		return getTitle();
	}


	public Edition getEdition() {
		return book.getEdition();
	}
}
