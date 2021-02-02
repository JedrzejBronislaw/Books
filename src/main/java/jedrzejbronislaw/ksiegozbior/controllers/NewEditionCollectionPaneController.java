package jedrzejbronislaw.ksiegozbior.controllers;

import static jedrzejbronislaw.ksiegozbior.controllers.EntityFormTools.getText;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import jedrzejbronislaw.ksiegozbior.model.entities.collections.EditionCollection;
import jedrzejbronislaw.ksiegozbior.model.repositories.EditionCollectionRepository;
import jedrzejbronislaw.ksiegozbior.view.MyComboboxRefresher;
import lombok.Getter;

@Component
public class NewEditionCollectionPaneController implements Initializable, EntityFormController {

	@Autowired private EditionCollectionRepository editionCollectionRepository;

	@Getter
	@FXML private GridPane fieldsPane;
	@FXML private TextField nameField;
	@FXML private ComboBox<EditionCollection> supercollectionField;
	
	
	@FXML
	public void addAction() {
		saveCollection();
		clearFields();
	}
	
	private void saveCollection() {
		EditionCollection collection = new EditionCollection();
		
		collection.setName(getText(nameField));
		collection.setSuperCollection(supercollectionField.getValue());
		
		editionCollectionRepository.save(collection);
	}


	public void clearFields(){
		nameField.clear();
		supercollectionField.setValue(null);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		MyComboboxRefresher<EditionCollection> supercollectionRefresher = new MyComboboxRefresher<EditionCollection>(supercollectionField, editionCollectionRepository);
		
		supercollectionField.setOnShowing(e -> supercollectionRefresher.refresh());
		supercollectionRefresher.refresh();
	}
}
