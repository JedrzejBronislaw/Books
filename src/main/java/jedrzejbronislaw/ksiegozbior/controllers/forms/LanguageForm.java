package jedrzejbronislaw.ksiegozbior.controllers.forms;

import static jedrzejbronislaw.ksiegozbior.controllers.FormTools.getText;
import static lombok.AccessLevel.PROTECTED;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import jedrzejbronislaw.ksiegozbior.model.entities.Language;
import jedrzejbronislaw.ksiegozbior.model.repositories.LanguageRepository;
import lombok.Getter;

@Component
public class LanguageForm extends EntityForm<Language> implements Initializable {

	@Getter(PROTECTED) private Class<Language> entityClass = Language.class;

	@Autowired private LanguageRepository languageRepository;

	@Getter
	@FXML private GridPane fieldsPane;
	@FXML private TextField langField;
	@FXML private TextField abbrevField;

	
	@FXML
	public void addLanguageAction() {
		save();
		clear();
	}

	@Override
	public Language save() {
		Language newLang = new Language();
		
		newLang.setName(getText(langField));
		newLang.setAbbr(getText(abbrevField));
		
		languageRepository.save(newLang);
		
		return newLang;
	}

	@Override
	public void clear(){
		langField.clear();
		abbrevField.clear();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {}

	@Override
	public void fill(Language language) {
		langField  .setText(language.getName());
		abbrevField.setText(language.getAbbr());
	}
}
