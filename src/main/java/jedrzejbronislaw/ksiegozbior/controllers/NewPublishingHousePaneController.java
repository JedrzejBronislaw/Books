package jedrzejbronislaw.ksiegozbior.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import jedrzejbronislaw.ksiegozbior.model.entities.PublishingHouse;
import jedrzejbronislaw.ksiegozbior.model.repositories.PublishingHouseRepository;
import lombok.Getter;




@Component
public class NewPublishingHousePaneController implements Initializable, EntityFormController{

	@Autowired
	private PublishingHouseRepository publishingHouseRepository;

	@FXML
	@Getter
	private GridPane fieldsPane;
	
	@FXML
	private TextField nameField;
	
	@FXML
	private TextField abbrevField;
	
	@FXML
	private TextField cityField;

	
	@FXML
	public void addPublisherAction() {
		savePublishingHouse();
		clearFields();
	}
	
	private void savePublishingHouse() {
		PublishingHouse newPublishingHouse = new PublishingHouse();
		
		newPublishingHouse.setName(nameField.getText().isBlank() ? null : nameField.getText().strip());
		newPublishingHouse.setAbbr(abbrevField.getText().isBlank() ? null : abbrevField.getText().strip());
		newPublishingHouse.setCity(cityField.getText().isBlank() ? null : cityField.getText().strip());

		publishingHouseRepository.save(newPublishingHouse);
	}


	public void clearFields(){
		nameField.clear();
		abbrevField.clear();
		cityField.clear();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}



}
