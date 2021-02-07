package jedrzejbronislaw.ksiegozbior.controllers.forms;

import static jedrzejbronislaw.ksiegozbior.controllers.FormTools.getText;
import static lombok.AccessLevel.PROTECTED;

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
public class PublishingHouseForm extends EntityForm<PublishingHouse> implements Initializable {

	@Getter(PROTECTED) private Class<PublishingHouse> entityClass = PublishingHouse.class;

	@Autowired private PublishingHouseRepository publishingHouseRepository;

	@Getter
	@FXML private GridPane fieldsPane;
	
	@FXML private TextField nameField;
	@FXML private TextField abbrevField;
	@FXML private TextField cityField;

	
	@FXML
	public void addPublisherAction() {
		save();
		clear();
	}

	@Override
	public PublishingHouse save() {
		PublishingHouse newPublishingHouse = new PublishingHouse();
		
		newPublishingHouse.setName(getText(nameField));
		newPublishingHouse.setAbbr(getText(abbrevField));
		newPublishingHouse.setCity(getText(cityField));

		publishingHouseRepository.save(newPublishingHouse);
		
		return newPublishingHouse;
	}

	@Override
	public void clear(){
		nameField.clear();
		abbrevField.clear();
		cityField.clear();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {}

	@Override
	public void fill(PublishingHouse ent) {
		// TODO Auto-generated method stub
	}
}
