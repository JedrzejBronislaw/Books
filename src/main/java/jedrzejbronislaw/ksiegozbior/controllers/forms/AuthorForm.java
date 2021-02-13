package jedrzejbronislaw.ksiegozbior.controllers.forms;

import static jedrzejbronislaw.ksiegozbior.controllers.FormTools.getDate;
import static jedrzejbronislaw.ksiegozbior.controllers.FormTools.getText;
import static jedrzejbronislaw.ksiegozbior.controllers.FormTools.localDate;
import static lombok.AccessLevel.PROTECTED;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import jedrzejbronislaw.ksiegozbior.model.entities.Author;
import jedrzejbronislaw.ksiegozbior.model.repositories.AuthorRepository;
import lombok.Getter;

@Component
public class AuthorForm extends EntityForm<Author> {

	@Getter(PROTECTED) private Class<Author> entityClass = Author.class;
	
	@Autowired private AuthorRepository autorRepository;

	@Getter
	@FXML private GridPane fieldsPane;
	@FXML private TextField nameField;
	@FXML private TextField surnameField;
	@FXML private DatePicker birthDateField;
	@FXML private DatePicker deathDateField;
	@FXML private ComboBox<String> nationalityField;
	@FXML private TextArea descriptionField;
	
	
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
	protected void fill(Author author) {
		nameField       .setText(author.getName());
		surnameField    .setText(author.getSurname());
		birthDateField  .setValue(localDate(author.getBirthDate()));
		deathDateField  .setValue(localDate(author.getDeathDate()));
		descriptionField.setText(author.getDescription());
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
