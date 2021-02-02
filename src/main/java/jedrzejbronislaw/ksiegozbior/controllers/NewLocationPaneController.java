package jedrzejbronislaw.ksiegozbior.controllers;

import static jedrzejbronislaw.ksiegozbior.controllers.EntityFormTools.getText;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import jedrzejbronislaw.ksiegozbior.model.entities.Location;
import jedrzejbronislaw.ksiegozbior.model.repositories.LocationRepository;
import lombok.Getter;

@Component
public class NewLocationPaneController implements Initializable, EntityFormController {

	@Autowired private LocationRepository locationRepository;

	@Getter
	@FXML private GridPane fieldsPane;
	@FXML private TextField nameField;
	@FXML private ComboBox<Location> superlocationField;
	@FXML private TextArea descriptionField;

	
	@FXML
	public void addAction() {
		saveLocation();
		clearFields();
	}
	
	private void saveLocation() {
		Location newLocation = new Location();
		
		newLocation.setName(       getText(nameField));
		newLocation.setDescription(getText(descriptionField));
		newLocation.setSuperLocation(superlocationField.getValue());
		
		locationRepository.save(newLocation);
	}


	public void clearFields(){
		nameField.clear();
		superlocationField.setValue(null);
		descriptionField.clear();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		updateLocationList();
		
		superlocationField.setOnShowing(e -> updateLocationList());
	}

	private void updateLocationList() {
		Iterable<Location> locations = locationRepository.findAll();
		
		superlocationField.getItems().clear();
		
		locations.forEach(superlocationField.getItems()::add);
	}
}
