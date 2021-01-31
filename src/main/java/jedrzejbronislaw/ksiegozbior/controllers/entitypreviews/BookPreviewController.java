package jedrzejbronislaw.ksiegozbior.controllers.entitypreviews;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import jedrzejbronislaw.ksiegozbior.model.entities.Book;
import jedrzejbronislaw.ksiegozbior.model.projections.TheBook;

@Component
public class BookPreviewController implements Initializable {
	
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

	
	public void setBook(Book book) {
		theBook.setBook(book);
		refresh();
	}
	
	private void refresh() {
		if (theBook == null) return;
		clear();
		
		titleLabel          .setText(theBook.getTitle());
		subtitleLabel       .setText(theBook.getSubtitle());
		authorsLabel        .setText(theBook.getAuthors().serialize_newLine());
		publisherLabel      .setText(theBook.getPublisherName());
		languageLabel       .setText(theBook.getLanguageName());
		purchaseDateLabel   .setText(theBook.getPurchaseDate());
		libraryLabel        .setText(theBook.getLibrary());
		publicationDateLabel.setText(theBook.getPublicationYear().str());
		pagesLabel          .setText(theBook.getPages().str());
		hardCoverLabel      .setText(theBook.isHardCoverStr());
		
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
		clear();
	}
}
