package jedrzejbronislaw.ksiegozbior.controllers.entitypreviews;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import jedrzejbronislaw.ksiegozbior.model.entities.Location;
import jedrzejbronislaw.ksiegozbior.model.projections.TheLocation;

@Component
public class LocationPreviewController implements Initializable {

	private TheLocation theLocation;
	
	@FXML private Label nameLabel;
	@FXML private Label superlocationLabel;
	
	@FXML private TextArea descriptionField;
	@FXML private Label elementsLabel;
	@FXML private Label sublocationsLabel;
	

	public void setLocation(Location location) {
		theLocation = new TheLocation(location);
		refresh();
	}
	
	private void refresh() {
		if (theLocation == null) return;
		clear();
		
		nameLabel         .setText(theLocation.getName());
		superlocationLabel.setText(theLocation.getSuperlocationsNames().serialize_newLine());
		
		descriptionField  .setText(theLocation.getDescription());
		sublocationsLabel .setText(theLocation.getSublocationsNames().serialize_newLine());
		
		elementsLabel.setText(
			"(" + theLocation.getNumberOfBooks().str() + ")"
			+ System.lineSeparator() +
			theLocation.getBooksNames().serialize_newLine()
		);
	}
	
	private void clear() {
		nameLabel.setText("");
		superlocationLabel.setText("");

		descriptionField.clear();
		sublocationsLabel.setText("");
		elementsLabel.setText("");
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		clear();
	}
}
