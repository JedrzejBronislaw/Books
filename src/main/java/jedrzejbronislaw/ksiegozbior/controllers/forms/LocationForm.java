package jedrzejbronislaw.ksiegozbior.controllers.forms;

import static jedrzejbronislaw.ksiegozbior.controllers.FormTools.getText;
import static lombok.AccessLevel.PROTECTED;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import jedrzejbronislaw.ksiegozbior.model.entities.Location;
import jedrzejbronislaw.ksiegozbior.model.repositories.LocationRepository;
import jedrzejbronislaw.ksiegozbior.view.Refresher;
import lombok.Getter;

@Component
public class LocationForm extends EntityForm<Location> {

	@Getter(PROTECTED) private Class<Location> entityClass = Location.class;

	@Autowired private LocationRepository locationRepository;

	@Getter
	@FXML private GridPane fieldsPane;
	@FXML private TextField nameField;
	@FXML private ComboBox<Location> superlocationField;
	@FXML private TextArea descriptionField;

	
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
	public void fill(Location location) {
		nameField         .setText( location.getName());
		superlocationField.setValue(location.getSuperLocation());
		descriptionField  .setText( location.getDescription());
	}
}
