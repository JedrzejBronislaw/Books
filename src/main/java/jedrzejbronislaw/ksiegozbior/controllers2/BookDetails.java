package jedrzejbronislaw.ksiegozbior.controllers2;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import jedrzejbronislaw.ksiegozbior.controllers.entities.AuthorPreview;
import jedrzejbronislaw.ksiegozbior.controllers.entities.BookPreview;
import jedrzejbronislaw.ksiegozbior.controllers.entities.EditionPreview;
import jedrzejbronislaw.ksiegozbior.controllers.entities.TitlePreview;
import jedrzejbronislaw.ksiegozbior.model.entities.Author;
import jedrzejbronislaw.ksiegozbior.model.entities.Book;
import jedrzejbronislaw.ksiegozbior.model.entities.Edition;
import jedrzejbronislaw.ksiegozbior.model.entities.Title;
import jedrzejbronislaw.ksiegozbior.model.projections.TheBook;
import jedrzejbronislaw.ksiegozbior.tools.MyList;
import lombok.NonNull;

@Component
public class BookDetails extends StackPane implements Initializable {

	@FXML private VBox vbox;
	
	@Autowired private TheBook theBook;
	private Book book;
	
	private BookPreview       bookPreview;
	private EditionPreview editionPreview;
	private TitlePreview     titlePreview;
	private AuthorPreview   authorPreview;
	

	public void setBookPreview(@NonNull BookPreview nac) {
		bookPreview = nac;
		refreshPreview();
	}

	public void setEditionPreview(@NonNull EditionPreview nac) {
		editionPreview = nac;
		refreshPreview();
	}

	public void setTitlePreview(@NonNull TitlePreview nac) {
		titlePreview = nac;
		refreshPreview();
	}

	public void setAuthorPreview(@NonNull AuthorPreview nac) {
		authorPreview = nac;
		refreshPreview();
	}
	
	private void refreshPreview() {
		vbox.getChildren().clear();
		
		Stream.of(authorPreview, bookPreview, editionPreview, titlePreview)
			.filter(p -> p != null)
			.forEach(this::addPreview);
		
		set(book);
	}
	
	private void addPreview(Node preview) {
		vbox.getChildren().add(preview);
	}
	
	
	public void set(Book book) {
		if (book == null) return;
		
		this.book = book;
		theBook.setBook(book);
		
		if(authorPreview != null) {
			MyList<Author> authors = theBook.getAuthors();
			if (authors.size() > 0)
				authorPreview.setAuthor(authors.get(0));
		}
		
		if(titlePreview != null) {
			Set<Title> titles = theBook.getTitles();//TODO change Set to myList
			if(titles.size() > 0)
				titlePreview.setTitle(titles.iterator().next());
		}
		
		if(editionPreview != null) {
			Edition edition = theBook.getEdition();
			editionPreview.setEdition(edition);
		}
		
		if(bookPreview != null) {
			bookPreview.setBook(book);
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {}
}
