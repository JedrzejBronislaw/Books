package jedrzejbronislaw.ksiegozbior.controllers;

import static jedrzejbronislaw.ksiegozbior.controllers.EntityFormTools.getText;
import static jedrzejbronislaw.ksiegozbior.controllers.EntityFormTools.parseShort;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import jedrzejbronislaw.ksiegozbior.model.entities.Author;
import jedrzejbronislaw.ksiegozbior.model.entities.Authorship;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.entities.Language;
import jedrzejbronislaw.ksiegozbior.model.entities.Title;
import jedrzejbronislaw.ksiegozbior.model.repositories.AuthorRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.AuthorshipRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.LanguageRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.TitleRepository;
import jedrzejbronislaw.ksiegozbior.view.Refresher;
import lombok.Getter;

@Component
public class NewTitlePaneController implements Initializable, EntityFormController {

	@Autowired private TitleRepository titleRepository;
	@Autowired private AuthorRepository authorsRepository;
	@Autowired private AuthorshipRepository authorshipRepository;
	@Autowired private LanguageRepository languageRepository;

	@Getter
	@FXML private GridPane fieldsPane;
	@FXML private TextField titleField;
	@FXML private TextField subtitleField;
	@FXML private ComboBox<Author> authorField;
	@FXML private TextField yearField;
	@FXML private ComboBox<Language> languageField;
	@FXML private TextArea descriptionField;	
	
	@FXML
	private void addTitleAction() {
		save();
		clearFields();
	}
	
	@Override
	public Title save() {
		Title newTitle = new Title();
		short year;
		
		//TODO validation
		year = parseShort(yearField.getText(), (short)0);
		
		newTitle.setTitle(   getText(titleField));
		newTitle.setSubtitle(getText(subtitleField));
		newTitle.setLanguage(languageField.getValue());

		if (year != 0) newTitle.setYear(year);
		newTitle.setDescription(getText(descriptionField));
		titleRepository.save(newTitle);
		
		if (authorField.getValue() != null) {
			Authorship authorship = new Authorship();
			authorship.setAuthorId(authorField.getValue().getId());
			authorship.setTitleId(newTitle.getId());
			
			authorship = authorshipRepository.save(authorship);
		}
		
		return newTitle;
	}

	@Override
	public void set(Ent ent) {
		clearFields();
		if(ent instanceof Title) {
			Title title = (Title) ent;
			
			titleField.setText(title.getTitle());
			subtitleField.setText(title.getSubtitle());
			languageField.setValue(title.getLanguage());
			yearField.setText(Short.toString(title.getYear()));
			descriptionField.setText(title.getDescription());
			
			List<Authorship> authors = title.getAuthors();
			if (authors.size() > 0)
				authorField.setValue(authors.get(0).getAuthor());
		}
	}
	
	public void clearFields(){
		titleField.clear();
		subtitleField.clear();
		languageField.setValue(null);
		yearField.clear();
		descriptionField.clear();
		authorField.setValue(null);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
		Refresher.setOnShowing(authorField,   authorsRepository);
		Refresher.setOnShowing(languageField, languageRepository);
		Refresher.loadAll(authorField,   authorsRepository);
		Refresher.loadAll(languageField, languageRepository);
	}

	public void setAuthor(Author author) {
		authorField.setValue(author);
		authorField.setDisable(true);
	}
	
	@Override
	public void enableAllFields() {
		authorField.setDisable(false);
	}
}
