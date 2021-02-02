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
import jedrzejbronislaw.ksiegozbior.model.entities.collections.BookCollection;
import jedrzejbronislaw.ksiegozbior.model.repositories.BookCollectionRepository;
import jedrzejbronislaw.ksiegozbior.view.MyComboboxRefresher;
import lombok.Getter;

@Component
public class NewBookCollectionPaneController implements Initializable, EntityFormController {

	@Autowired private BookCollectionRepository bookCollectionRepository;

	@Getter
	@FXML private GridPane fieldsPane;
	@FXML private TextField nameField;
	@FXML private ComboBox<BookCollection> supercollectionField;
	
	
	@FXML
	public void addAction() {
		saveCollection();
		clearFields();
	}
	
	private void saveCollection() {
		BookCollection collection = new BookCollection();
		
		collection.setName(getText(nameField));
		collection.setSuperCollection(supercollectionField.getValue());
		
		bookCollectionRepository.save(collection);
	}


	public void clearFields(){
		nameField.clear();
		supercollectionField.setValue(null);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		MyComboboxRefresher<BookCollection> supercollectionRefresher = new MyComboboxRefresher<>(supercollectionField, bookCollectionRepository);
		
		supercollectionField.setOnShowing(e -> supercollectionRefresher.refresh());
		supercollectionRefresher.refresh();
	}
}
