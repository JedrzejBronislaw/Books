package jedrzejbronislaw.ksiegozbior.controllers.entitypreviews;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import jedrzejbronislaw.ksiegozbior.model.entities.PublishingHouse;
import jedrzejbronislaw.ksiegozbior.model.projections.ThePublishingHouse;

@Component
public class PublisherPreviewController implements Initializable {

	@Autowired private ThePublishingHouse thePublisher;
	
	@FXML private Label abbrevLabel;
	@FXML private Label nameLabel;
	@FXML private Label cityLabel;

	@FXML private Label editionsLabel;
	

	public void setPublisher(PublishingHouse publisher) {
		thePublisher.setPublisher(publisher);
		refresh();
	}
	
	private void refresh() {
		if (thePublisher == null) return;
		clear();
		
		abbrevLabel.setText(thePublisher.getAbbrev());
		nameLabel  .setText(thePublisher.getName());
		cityLabel  .setText(thePublisher.getCity());
		
		editionsLabel.setText(thePublisher.getNumberOfEditions().str());
	}
	
	private void clear() {
		abbrevLabel.setText("");
		nameLabel.setText("");
		cityLabel.setText("");

		editionsLabel.setText("");
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		clear();
	}
}
