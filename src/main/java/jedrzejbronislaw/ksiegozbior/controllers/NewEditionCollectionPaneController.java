package jedrzejbronislaw.ksiegozbior.controllers;

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
import jedrzejbronislaw.ksiegozbior.view.MyComboxCallBack;
import lombok.Getter;




@Component
public class NewEditionCollectionPaneController implements Initializable, EntityFormController{

	@Autowired
	private EditionCollectionRepository editionCollectionRepository;

	@FXML
	@Getter
	private GridPane fieldsPane;
	
	@FXML
	private TextField nameField;
	
	@FXML
	private ComboBox<EditionCollection> supercollectionField;
	
	
	@FXML
	public void addAction() {
		saveCollection();
		clearFields();
	}
	
	private void saveCollection() {
		EditionCollection collection = new EditionCollection();
		
		collection.setName(nameField.getText().isBlank() ? null : nameField.getText().strip());
		collection.setSuperCollection(supercollectionField.getValue());
		
		editionCollectionRepository.save(collection);
	}


	public void clearFields(){
		nameField.clear();
		supercollectionField.setValue(null);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		new MyComboxCallBack<EditionCollection>(supercollectionField);
		MyComboboxRefresher<EditionCollection> supercollectionRefresher = new MyComboboxRefresher<EditionCollection>(supercollectionField, editionCollectionRepository);
		
		supercollectionField.setOnShowing(e -> supercollectionRefresher.refresh());
		supercollectionRefresher.refresh();
	}


}
