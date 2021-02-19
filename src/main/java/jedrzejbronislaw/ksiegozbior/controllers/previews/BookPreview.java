package jedrzejbronislaw.ksiegozbior.controllers.previews;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import jedrzejbronislaw.ksiegozbior.model.entities.Book;
import jedrzejbronislaw.ksiegozbior.model.projections.TheBook;
import jedrzejbronislaw.ksiegozbior.view.MyLabel;

@Component
public class BookPreview extends VBox implements Initializable {
	
	@Autowired private TheBook theBook;
	
	@FXML private Label titleLabel;
	@FXML private Label subtitleLabel;
	@FXML private Label authorsLabel;
	@FXML private Label publisherLabel;
	@FXML private Label languageLabel;
	@FXML private Label purchaseDateLabel;
	@FXML private Label libraryLabel;
	@FXML private Label publicationDateLabel;
	@FXML private Label pagesLabel;
	@FXML private Label hardCoverLabel;

	@FXML private Label titlesLabel;
	@FXML private TextArea commentsField;

	private MyLabel authorsMLabel;
	private MyLabel pubDateMLabel;
	private MyLabel pagesMLabel;
	private MyLabel hardCoverMLabel;

	
	public void setBook(Book book) {
		theBook.setBook(book);
		refresh();
	}
	
	private void refresh() {
		if (theBook == null) return;
		clear();
		
		titleLabel          .setText(theBook.getTitle());
		subtitleLabel       .setText(theBook.getSubtitle());
		authorsMLabel       .setText(theBook.getAuthors());
		publisherLabel      .setText(theBook.getPublisherName());
		languageLabel       .setText(theBook.getLanguageName());
		purchaseDateLabel   .setText(theBook.getPurchaseDate());
		libraryLabel        .setText(theBook.getLibraryName());
		pubDateMLabel       .setText(theBook.getPublicationYear());
		pagesMLabel         .setText(theBook.getPages());
		hardCoverMLabel     .setText(theBook.isHardCover());
		
		titlesLabel         .setText(theBook.getTitlesText());
		commentsField       .setText(theBook.getCommentsText());
	}
	
	private void clear() {
		titleLabel          .setText("");
		subtitleLabel       .setText("");
		authorsLabel        .setText("");
		publisherLabel      .setText("");
		languageLabel       .setText("");
		purchaseDateLabel   .setText("");
		libraryLabel        .setText("");
		publicationDateLabel.setText("");
		pagesLabel          .setText("");
		hardCoverLabel      .setText("");
		
		titlesLabel         .setText("");
		commentsField       .clear();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		authorsMLabel   = new MyLabel(authorsLabel);
		pubDateMLabel   = new MyLabel(publicationDateLabel);
		pagesMLabel     = new MyLabel(pagesLabel);
		hardCoverMLabel = new MyLabel(hardCoverLabel);
		clear();
	}
}
