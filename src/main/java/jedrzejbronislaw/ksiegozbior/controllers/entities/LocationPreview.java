package jedrzejbronislaw.ksiegozbior.controllers.entities;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import jedrzejbronislaw.ksiegozbior.model.entities.Location;
import jedrzejbronislaw.ksiegozbior.model.projections.TheLocation;

@Component
public class LocationPreview extends VBox implements Initializable {

	@Autowired private TheLocation theLocation;
	
	@FXML private Label nameLabel;
	@FXML private Label superlocationLabel;
	
	@FXML private TextArea descriptionField;
	@FXML private Label elementsLabel;
	@FXML private Label sublocationsLabel;
	

	public void setLocation(Location location) {
		theLocation.setLocation(location);
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
