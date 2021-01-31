package jedrzejbronislaw.ksiegozbior.controllers.entitypreviews;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import jedrzejbronislaw.ksiegozbior.model.entities.Edition;
import jedrzejbronislaw.ksiegozbior.model.projections.TheEdition;

@Component
public class EditionPreviewController implements Initializable {
	
	@Autowired private TheEdition theEdition;
	
	@FXML private Label titleLabel;
	@FXML private Label subtitleLabel;
	@FXML private Label authorsLabel;
	@FXML private Label publisherLabel;
	@FXML private Label languageLabel;
	@FXML private Label editionNumberLabel;
	@FXML private Label isbnLabel;
	@FXML private Label publicationDateLabel;
	@FXML private Label pagesLabel;
	@FXML private Label hardCoverLabel;

	@FXML private Label titlesLabel;
	

	public void setEdition(Edition edition) {
		theEdition.setEdition(edition);
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
		hardCoverLabel      .setText(theEdition.isHardCoverStr());
		
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
