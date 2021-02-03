package jedrzejbronislaw.ksiegozbior.controllers2;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import jedrzejbronislaw.ksiegozbior.controllers.entitypreviews.AuthorPreviewController;
import jedrzejbronislaw.ksiegozbior.controllers.entitypreviews.BookPreviewController;
import jedrzejbronislaw.ksiegozbior.controllers.entitypreviews.EditionPreviewController;
import jedrzejbronislaw.ksiegozbior.controllers.entitypreviews.TitlePreviewController;
import jedrzejbronislaw.ksiegozbior.model.entities.Author;
import jedrzejbronislaw.ksiegozbior.model.entities.Book;
import jedrzejbronislaw.ksiegozbior.model.entities.Edition;
import jedrzejbronislaw.ksiegozbior.model.entities.Title;
import jedrzejbronislaw.ksiegozbior.model.projections.TheBook;
import jedrzejbronislaw.ksiegozbior.tools.MyFXMLLoader.NodeAndController;
import lombok.NonNull;
import jedrzejbronislaw.ksiegozbior.tools.MyList;

@Component
public class BookDetailsController implements Initializable {

	@FXML private VBox vbox;
	
	@Autowired private TheBook theBook;
	private Book book;
	
	private NodeAndController<BookPreviewController>    bookNAC;
	private NodeAndController<EditionPreviewController> editionNAC;
	private NodeAndController<TitlePreviewController>   titleNAC;
	private NodeAndController<AuthorPreviewController>  authorNAC;
	

	public void setBookPreview(@NonNull NodeAndController<BookPreviewController> nac) {
		bookNAC = nac;
		refreshPreview();
	}

	public void setEditionPreview(@NonNull NodeAndController<EditionPreviewController> nac) {
		editionNAC = nac;
		refreshPreview();
	}

	public void setTitlePreview(@NonNull NodeAndController<TitlePreviewController> nac) {
		titleNAC = nac;
		refreshPreview();
	}

	public void setAuthorPreview(@NonNull NodeAndController<AuthorPreviewController> nac) {
		authorNAC = nac;
		refreshPreview();
	}
	
	private void refreshPreview() {
		vbox.getChildren().clear();
		
		Stream.of(authorNAC, bookNAC, editionNAC, titleNAC)
			.filter(p -> p != null)
			.forEach(this::addPreview);
		
		set(book);
	}
	
	private void addPreview(NodeAndController<? extends Initializable> preview) {
		vbox.getChildren().add(preview.getNode());
	}
	
	
	public void set(Book book) {
		if (book == null) return;
		
		this.book = book;
		theBook.setBook(book);
		
		if(authorNAC != null) {
			MyList<Author> authors = theBook.getAuthors();
			if (authors.size() > 0)
				((AuthorPreviewController)authorNAC.getController()).setAuthor(authors.get(0));
		}
		
		if(titleNAC != null) {
			Set<Title> titles = theBook.getTitles();//TODO change Set to myList
			if(titles.size() > 0)
				((TitlePreviewController)titleNAC.getController()).setTitle(titles.iterator().next());
		}
		
		if(editionNAC != null) {
			Edition edition = theBook.getEdition();
			((EditionPreviewController)editionNAC.getController()).setEdition(edition);
		}
		
		if(bookNAC != null) {
			((BookPreviewController)bookNAC.getController()).setBook(book);
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {}
}
