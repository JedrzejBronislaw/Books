package jedrzejbronislaw.ksiegozbior.controllers.previews;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import jedrzejbronislaw.ksiegozbior.model.entities.Edition;
import jedrzejbronislaw.ksiegozbior.model.projections.TheEdition;
import jedrzejbronislaw.ksiegozbior.view.MyLabel;

@Component
public class EditionPreview extends VBox implements Initializable {
	
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

	private MyLabel authorsMLabel;
	private MyLabel pubDateMLabel;
	private MyLabel pagesMLabel;
	private MyLabel hardCoverMLabel;
	

	public void setEdition(Edition edition) {
		theEdition.setEdition(edition);
		refresh();
	}
	
	private void refresh() {
		if (theEdition == null) return;
		clear();
		
		titleLabel          .setText(theEdition.getTitle());
		subtitleLabel       .setText(theEdition.getSubtitle());
		authorsMLabel       .setText(theEdition.getAuthors());
		publisherLabel      .setText(theEdition.getPublisherName());
		languageLabel       .setText(theEdition.getLanguageName());
		editionNumberLabel  .setText(theEdition.getNumerRoman());
		isbnLabel           .setText(theEdition.getISBNFormatted());
		pubDateMLabel       .setText(theEdition.getPublicationYear());
		pagesMLabel         .setText(theEdition.getPages());
		hardCoverMLabel     .setText(theEdition.isHardCover());
		
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
		authorsMLabel   = new MyLabel(authorsLabel);
		pubDateMLabel   = new MyLabel(publicationDateLabel);
		pagesMLabel     = new MyLabel(pagesLabel);
		hardCoverMLabel = new MyLabel(hardCoverLabel);
		clear();
	}
}
