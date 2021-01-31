package jedrzejbronislaw.ksiegozbior.model.projections;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jedrzejbronislaw.ksiegozbior.model.entities.Book;
import jedrzejbronislaw.ksiegozbior.model.entities.Edition;
import jedrzejbronislaw.ksiegozbior.tools.MyList;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Component
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@NoArgsConstructor
public class TheBook extends TheEdition implements TheEnt {

	@NonNull private Book book;


	public void setBook(Book book) {
		setEdition(book.getEdition());
		this.book = book;
	}
	
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
