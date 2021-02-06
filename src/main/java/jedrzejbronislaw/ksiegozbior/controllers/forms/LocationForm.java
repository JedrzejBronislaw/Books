package jedrzejbronislaw.ksiegozbior.controllers.forms;

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
import javafx.scene.layout.VBox;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.entities.Location;
import jedrzejbronislaw.ksiegozbior.model.repositories.LocationRepository;
import jedrzejbronislaw.ksiegozbior.view.Refresher;
import lombok.Getter;

@Component
public class LocationForm extends VBox implements Initializable, EntityForm {

	@Autowired private LocationRepository locationRepository;

	@Getter
	@FXML private GridPane fieldsPane;
	@FXML private TextField nameField;
	@FXML private ComboBox<Location> superlocationField;
	@FXML private TextArea descriptionField;

	
	@FXML
	public void addAction() {
		save();
		clear();
	}

	@Override
	public Location save() {
		Location newLocation = new Location();
		
		newLocation.setName(       getText(nameField));
		newLocation.setDescription(getText(descriptionField));
		newLocation.setSuperLocation(superlocationField.getValue());
		
		locationRepository.save(newLocation);
		
		return newLocation;
	}

	@Override
	public void clear(){
		nameField.clear();
		superlocationField.setValue(null);
		descriptionField.clear();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Refresher.setOnShowing(superlocationField, locationRepository);
	}

	@Override
	public void set(Ent ent) {
		// TODO Auto-generated method stub
	}
}