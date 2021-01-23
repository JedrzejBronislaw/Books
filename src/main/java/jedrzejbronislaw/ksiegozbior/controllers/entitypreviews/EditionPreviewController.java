package jedrzejbronislaw.ksiegozbior.controllers.entitypreviews;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import jedrzejbronislaw.ksiegozbior.model.entities.Edition;
import jedrzejbronislaw.ksiegozbior.model.projections.TheEdition;

@Component
public class EditionPreviewController implements Initializable {
	
	private TheEdition theEdition;
	
	@FXML Label titleLabel;
	@FXML Label subtitleLabel;
	@FXML Label authorsLabel;
	@FXML Label publisherLabel;
	@FXML Label languageLabel;
	@FXML Label editionNumberLabel;
	@FXML Label isbnLabel;
	@FXML Label publicationDateLabel;
	@FXML Label pagesLabel;
	@FXML Label hardCoverLabel;

	@FXML Label titlesLabel;
	

	public void setEdition(Edition edition) {
		theEdition = new TheEdition(edition);
		refresh();
	}
	
	private void refresh() {
		if (theEdition == null) return;
		clear();
		
		titleLabel          .setText(theEdition.getTitle());
		subtitleLabel       .setText(theEdition.getSubtitle());
		authorsLabel        .setText(theEdition.getAuthors().serialize_newLine());
		publisherLabel      .setText(theEdition.getPublisherName());
		languageLabel       .setText(theEdition.getLanguageName());
		editionNumberLabel  .setText(theEdition.getNumerRoman());
		isbnLabel           .setText(theEdition.getISBNFormatted());
		publicationDateLabel.setText(theEdition.getPublicationYear().str());
		pagesLabel          .setText(theEdition.getPages().str());
		hardCoverLabel      .setText(theEdition.getHardCoverStr());
		
		titlesLabel         .setText(theEdition.getTitlesText());
	}
	
	private void clear() {
		titleLabel.setText("");
		subtitleLabel.setText("");
		authorsLabel.setText("");
		publisherLabel.setText("");
		languageLabel.setText("");
		editionNumberLabel.setText("");
		isbnLabel.setText("");
		publicationDateLabel.setText("");
		pagesLabel.setText("");
		hardCoverLabel.setText("");
		
		titlesLabel.setText("");
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		clear();
	}
}
