package jedrzejbronislaw.ksiegozbior.controllers2;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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
import jedrzejbronislaw.ksiegozbior.tools.MyList;
import lombok.Getter;

@Component
public class BookDetailsController implements Initializable {

	private Book book;
	
	@FXML
	private VBox vbox;
	
	
	private NodeAndController bookNAC;
	private NodeAndController editionNAC;
	private NodeAndController titleNAC;
	private NodeAndController authorNAC;
	

	public void setBookPreview(NodeAndController nac) {
		if(nac == null) bookNAC = null;
		else if(nac.getController() instanceof BookPreviewController) 
			bookNAC = nac;
		
		refreshPreview();
	}

	public void setEditionPreview(NodeAndController nac) {
		if(nac == null) editionNAC = null;
		else if(nac.getController() instanceof EditionPreviewController) 
			editionNAC = nac;
		refreshPreview();
	}

	public void setTitlePreview(NodeAndController nac) {
		if(nac == null) titleNAC = null;
		else if(nac.getController() instanceof TitlePreviewController)
			titleNAC = nac;
		refreshPreview();
	}

	public void setAuthorPreview(NodeAndController nac) {
		if(nac == null) authorNAC = null;
		else if(nac.getController() instanceof AuthorPreviewController) 
			authorNAC = nac;
		refreshPreview();
	}
	
	private void refreshPreview() {
		NodeAndController[] previews = new NodeAndController[] {
				authorNAC,
				bookNAC,
				editionNAC,
				titleNAC
		};
		
		vbox.getChildren().clear();
		
		for(NodeAndController preview : previews) 
			if(preview != null)
				vbox.getChildren().add(preview.getNode());
		
		set(book);
	}
	
	
	public void set(Book book) {
		if (book == null) return;
		this.book = book;
		
		TheBook theBook = new TheBook(book);
		
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
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
}
