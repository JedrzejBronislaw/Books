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
import jedrzejbronislaw.ksiegozbior.model.entities.collections.TitleCollection;
import jedrzejbronislaw.ksiegozbior.model.repositories.TitleCollectionRepository;
import jedrzejbronislaw.ksiegozbior.view.Refresher;
import lombok.Getter;

@Component
public class NewTitleCollectionPaneController implements Initializable, EntityFormController {

	@Autowired private TitleCollectionRepository titleCollectionRepository;

	@Getter
	@FXML private GridPane fieldsPane;
	@FXML private TextField nameField;
	@FXML private ComboBox<TitleCollection> supercollectionField;
	
	
	@FXML
	public void addAction() {
		saveCollection();
		clearFields();
	}
	
	private void saveCollection() {
		TitleCollection collection = new TitleCollection();
		
		collection.setName(getText(nameField));
		collection.setSuperCollection(supercollectionField.getValue());
		
		titleCollectionRepository.save(collection);
	}


	public void clearFields(){
		nameField.clear();
		supercollectionField.setValue(null);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Refresher.setOnShowing(supercollectionField, titleCollectionRepository);
		Refresher.loadAll(supercollectionField, titleCollectionRepository);
	}
}
