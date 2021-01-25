package jedrzejbronislaw.ksiegozbior.controllers;

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

import static jedrzejbronislaw.ksiegozbior.controllers.EntityFormTools.getText;

@Component
public class NewLanguagePaneController implements Initializable, EntityFormController {

	@Autowired private LanguageRepository languageRepository;

	@Getter
	@FXML private GridPane fieldsPane;
	@FXML private TextField langField;
	@FXML private TextField abbrevField;

	
	@FXML
	public void addLanguageAction() {
		saveLanguage();
		clearFields();
	}
	
	private void saveLanguage() {
		Language newLang = new Language();
		
		newLang.setName(getText(langField));
		newLang.setAbbr(getText(abbrevField));
		
		languageRepository.save(newLang);
	}

	public void clearFields(){
		langField.clear();
		abbrevField.clear();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {}
}
