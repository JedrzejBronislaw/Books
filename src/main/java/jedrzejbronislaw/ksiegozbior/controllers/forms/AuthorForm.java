package jedrzejbronislaw.ksiegozbior.controllers.forms;

import static jedrzejbronislaw.ksiegozbior.controllers.EntityFormTools.getText;
import static jedrzejbronislaw.ksiegozbior.controllers.EntityFormTools.getDate;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
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
import javafx.scene.layout.VBox;
import jedrzejbronislaw.ksiegozbior.model.entities.Author;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.repositories.AuthorRepository;
import lombok.Getter;

@Component
public class AuthorForm extends VBox implements Initializable, EntityForm {

	@Autowired private AuthorRepository autorRepository;

	@Getter
	@FXML private GridPane fieldsPane;
	@FXML private TextField nameField;
	@FXML private TextField surnameField;
	@FXML private DatePicker birthDateField;
	@FXML private DatePicker deathDateField;
	@FXML private ComboBox<String> nationalityField;
	@FXML private TextArea descriptionField;
	
	
	@FXML
	public void addAuthorAction() {
		save();
		clear();
	}
	
	@Override
	public Author save() {
		Author newAuthor = new Author();
		
		newAuthor.setName(       getText(nameField));
		newAuthor.setSurname(    getText(surnameField));
		newAuthor.setDescription(getText(descriptionField));
		newAuthor.setBirthDate(  getDate(birthDateField));
		newAuthor.setDeathDate(  getDate(deathDateField));
		
		autorRepository.save(newAuthor);
		
		return newAuthor;
	}

	@Override
	public void set(Ent ent) {
		clear();
		
		if (!(ent instanceof Author)) return;

		Author author = (Author) ent;
		
		nameField       .setText(author.getName());
		surnameField    .setText(author.getSurname());
		birthDateField  .setValue(getLocalDate(author.getBirthDate()));
		deathDateField  .setValue(getLocalDate(author.getDeathDate()));
		descriptionField.setText(author.getDescription());
	}
	
	private LocalDate getLocalDate(Date date) {
		return (date == null) ? null : date.toLocalDate();
	}

	@Override
	public void clear(){
		nameField.clear();
		surnameField.clear();
		birthDateField.setValue(null);
		deathDateField.setValue(null);
		descriptionField.clear();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {}
}
