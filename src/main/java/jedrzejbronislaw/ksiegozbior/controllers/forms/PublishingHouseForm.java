package jedrzejbronislaw.ksiegozbior.controllers.forms;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import jedrzejbronislaw.ksiegozbior.model.entities.PublishingHouse;
import jedrzejbronislaw.ksiegozbior.model.repositories.PublishingHouseRepository;
import lombok.Getter;

import static jedrzejbronislaw.ksiegozbior.controllers.EntityFormTools.getText;

@Component
public class PublishingHouseForm extends VBox implements Initializable, EntityForm {

	@Autowired private PublishingHouseRepository publishingHouseRepository;

	@Getter
	@FXML private GridPane fieldsPane;
	
	@FXML private TextField nameField;
	@FXML private TextField abbrevField;
	@FXML private TextField cityField;

	
	@FXML
	public void addPublisherAction() {
		savePublishingHouse();
		clearFields();
	}
	
	private void savePublishingHouse() {
		PublishingHouse newPublishingHouse = new PublishingHouse();
		
		newPublishingHouse.setName(getText(nameField));
		newPublishingHouse.setAbbr(getText(abbrevField));
		newPublishingHouse.setCity(getText(cityField));

		publishingHouseRepository.save(newPublishingHouse);
	}


	public void clearFields(){
		nameField.clear();
		abbrevField.clear();
		cityField.clear();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {}
}
