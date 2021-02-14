package jedrzejbronislaw.ksiegozbior.controllers.forms;

import static jedrzejbronislaw.ksiegozbior.controllers.FormTools.getText;
import static jedrzejbronislaw.ksiegozbior.controllers.FormTools.parseShort;
import static jedrzejbronislaw.ksiegozbior.controllers.FormTools.string;
import static lombok.AccessLevel.PROTECTED;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import jedrzejbronislaw.ksiegozbior.controllers.forms.elements.MultiSelector;
import jedrzejbronislaw.ksiegozbior.model.entities.Author;
import jedrzejbronislaw.ksiegozbior.model.entities.Authorship;
import jedrzejbronislaw.ksiegozbior.model.entities.Language;
import jedrzejbronislaw.ksiegozbior.model.entities.Title;
import jedrzejbronislaw.ksiegozbior.model.repositories.AuthorRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.AuthorshipRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.LanguageRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.TitleRepository;
import jedrzejbronislaw.ksiegozbior.view.Refresher;
import lombok.Getter;

@Component
public class TitleForm extends EntityForm<Title> {

	@Getter(PROTECTED) private Class<Title> entityClass = Title.class;

	@Autowired private TitleRepository titleRepository;
	@Autowired private AuthorRepository authorsRepository;
	@Autowired private AuthorshipRepository authorshipRepository;
	@Autowired private LanguageRepository languageRepository;

	@Getter
	@FXML private GridPane fieldsPane;
	@FXML private TextField titleField;
	@FXML private TextField subtitleField;
	@FXML private TextField yearField;
	@FXML private ComboBox<Language> languageField;
	@FXML private TextArea descriptionField;	
	
	private MultiSelector<Author> authorSelector;
	
	
	@Override
	public Title save() {
		Title newTitle = new Title();
		Short year;
		
		year = parseShort(yearField.getText(), null);
		
		newTitle.setTitle(   getText(titleField));
		newTitle.setSubtitle(getText(subtitleField));
		newTitle.setLanguage(languageField.getValue());

		newTitle.setYear(year);
		newTitle.setDescription(getText(descriptionField));
		titleRepository.save(newTitle);

		for (Author author : authorSelector.getItems()) {
			Authorship authorship = new Authorship();
			
			authorship.setAuthorId(author.getId());
			authorship.setTitleId(newTitle.getId());
			
			authorship = authorshipRepository.save(authorship);
		}
		
		return newTitle;
	}

	@Override
	public void fill(Title title) {
		titleField      .setText       (title.getTitle());
		subtitleField   .setText       (title.getSubtitle());
		languageField   .setValue      (title.getLanguage());
		yearField       .setText(string(title.getYear()));
		descriptionField.setText       (title.getDescription());
		
		authorSelector.fill(title.getAuthors()
				.stream()
				.map(a -> a.getAuthor())
				.collect(Collectors.toList()));
	}

	@Override
	public void clear(){
		titleField.clear();
		subtitleField.clear();
		languageField.setValue(null);
		yearField.clear();
		descriptionField.clear();
		authorSelector.clear();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		authorSelector = new MultiSelector<>(authorsRepository);
		fieldsPane.add(authorSelector, 1,2);
		
		Refresher.setOnShowing(languageField, languageRepository);
	}

	public void setAuthor(Author author) {
		authorSelector.addItem(author);
		authorSelector.setDisable(true);
	}
}
