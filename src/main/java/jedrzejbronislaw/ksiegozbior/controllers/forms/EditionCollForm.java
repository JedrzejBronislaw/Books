package jedrzejbronislaw.ksiegozbior.controllers.forms;

import static jedrzejbronislaw.ksiegozbior.controllers.FormTools.getText;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import jedrzejbronislaw.ksiegozbior.model.entities.collections.EditionCollection;
import jedrzejbronislaw.ksiegozbior.model.repositories.EditionCollectionRepository;
import jedrzejbronislaw.ksiegozbior.view.Refresher;
import lombok.Getter;

@Component
public class EditionCollForm extends EntityForm<EditionCollection> {

	@Getter private Class<EditionCollection> entityClass = EditionCollection.class;

	@Autowired private EditionCollectionRepository editionCollectionRepository;

	@Getter
	@FXML private GridPane fieldsPane;
	@FXML private TextField nameField;
	@FXML private ComboBox<EditionCollection> supercollectionField;
	
	
	@Override
	public EditionCollection save() {
		EditionCollection collection = new EditionCollection();
		
		collection.setName(getText(nameField));
		collection.setSuperCollection(supercollectionField.getValue());
		
		editionCollectionRepository.save(collection);
		
		return collection;
	}

	@Override
	public void clear(){
		nameField.clear();
		supercollectionField.setValue(null);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Refresher.setOnShowing(supercollectionField, editionCollectionRepository);
	}

	@Override
	public void fill(EditionCollection collection) {
		nameField.setText(collection.getName());
		supercollectionField.setValue(collection.getSuperCollection());
	}
}
