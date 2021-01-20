package jedrzejbronislaw.ksiegozbior.controllers;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import jedrzejbronislaw.ksiegozbior.model.entities.Author;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.repositories.AuthorRepository;
import lombok.Getter;

@Component
public class NewAuthorPaneController implements Initializable, EntityFormController{

	@Autowired
	private AuthorRepository autorRepository;

	@FXML
	@Getter
	private GridPane fieldsPane;
	
	@FXML
	private TextField nameField;
	
	@FXML
	private TextField surnameField;

	@FXML
	private DatePicker birthDateField;

	@FXML
	private DatePicker deathDateField;

	@FXML
	private ComboBox<String> nationalityField;
	
	@FXML
	private TextArea descriptionField;
	
	
	@FXML
	public void addAuthorAction() {
		save();
		clearFields();
	}
	
	@Override
	public Author save() {
		Author newAuthor = new Author();
		
		//TODO validation
		
		newAuthor.setName(nameField.getText());
		newAuthor.setSurname(surnameField.getText());
		newAuthor.setDescription(descriptionField.getText());
		newAuthor.setBirthDate(birthDateField.getValue() == null ? null : Date.valueOf(birthDateField.getValue()));
		newAuthor.setDeathDate(deathDateField.getValue() == null ? null : Date.valueOf(deathDateField.getValue()));
		
		autorRepository.save(newAuthor);
		
		return newAuthor;
	}

	@Override
	public void set(Ent ent) {
		clearFields();
		if(ent instanceof Author) {
			Author author = (Author)ent;
			nameField.setText(author.getName());
			surnameField.setText(author.getSurname());
			if(author.getBirthDate() != null)
				birthDateField.setValue(author.getBirthDate().toLocalDate());
			if(author.getDeathDate() != null)
				deathDateField.setValue(author.getDeathDate().toLocalDate());
			descriptionField.setText(author.getDescription());
		}
	}
	
//	@Override
//	public void setEnable(boolean enable) {
//		fieldsPane.setDisable(!enable);
//	}

	public void clearFields(){
		nameField.clear();
		surnameField.clear();
		birthDateField.setValue(null);
		deathDateField.setValue(null);
//		nationalityField.itemsProperty()
		descriptionField.clear();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
