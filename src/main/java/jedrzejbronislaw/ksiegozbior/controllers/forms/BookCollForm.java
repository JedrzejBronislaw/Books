package jedrzejbronislaw.ksiegozbior.controllers.forms;

import static jedrzejbronislaw.ksiegozbior.controllers.FormTools.getText;
import static lombok.AccessLevel.PROTECTED;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import jedrzejbronislaw.ksiegozbior.model.entities.collections.BookCollection;
import jedrzejbronislaw.ksiegozbior.model.repositories.BookCollectionRepository;
import jedrzejbronislaw.ksiegozbior.view.Refresher;
import lombok.Getter;

@Component
public class BookCollForm extends EntityForm<BookCollection> {

	@Getter(PROTECTED) private Class<BookCollection> entityClass = BookCollection.class;

	@Autowired private BookCollectionRepository bookCollectionRepository;

	@Getter
	@FXML private GridPane fieldsPane;
	@FXML private TextField nameField;
	@FXML private ComboBox<BookCollection> supercollectionField;
	
	
	@Override
	public BookCollection save() {
		BookCollection collection = (entity == null) ? new BookCollection() : entity;
		
		collection.setName(getText(nameField));
		collection.setSuperCollection(supercollectionField.getValue());
		
		bookCollectionRepository.save(collection);
		
		return collection;
	}

	@Override
	public void clear(){
		nameField.clear();
		supercollectionField.setValue(null);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Refresher.setOnShowing(supercollectionField, bookCollectionRepository);
	}

	@Override
	protected void fill(BookCollection collection) {
		nameField.setText(collection.getName());
		supercollectionField.setValue(collection.getSuperCollection());
	}
}
