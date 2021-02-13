package jedrzejbronislaw.ksiegozbior.controllers.forms;

import static jedrzejbronislaw.ksiegozbior.controllers.FormTools.getText;
import static jedrzejbronislaw.ksiegozbior.controllers.FormTools.parseLong;
import static jedrzejbronislaw.ksiegozbior.controllers.FormTools.parseShort;
import static jedrzejbronislaw.ksiegozbior.controllers.FormTools.string;
import static jedrzejbronislaw.ksiegozbior.controllers.FormTools.textExists;
import static lombok.AccessLevel.PROTECTED;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import jedrzejbronislaw.ksiegozbior.controllers.forms.elements.MultiSelector;
import jedrzejbronislaw.ksiegozbior.model.entities.Edition;
import jedrzejbronislaw.ksiegozbior.model.entities.Edition_Title;
import jedrzejbronislaw.ksiegozbior.model.entities.Language;
import jedrzejbronislaw.ksiegozbior.model.entities.PublishingHouse;
import jedrzejbronislaw.ksiegozbior.model.entities.Title;
import jedrzejbronislaw.ksiegozbior.model.repositories.EditionRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.Edition_TitleRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.LanguageRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.PublishingHouseRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.TitleRepository;
import jedrzejbronislaw.ksiegozbior.view.Refresher;
import lombok.Getter;

@Component
public class EditionForm extends EntityForm<Edition> implements Initializable {

	@Getter(PROTECTED) Class<Edition> entityClass = Edition.class;
	
	@Autowired private TitleRepository titleRepository;
	@Autowired private Edition_TitleRepository edition_TitleRepository;
	@Autowired private EditionRepository editionRepository;
	@Autowired private PublishingHouseRepository publishingHouseRepository;
	@Autowired private LanguageRepository languageRepository;

	@Getter
	@FXML private GridPane fieldsPane;
	
	@FXML private CheckBox titleCheckbox;
	@FXML private TextField titleField;
	@FXML private TextField subtitleField;
	@FXML private ComboBox<Language> languageField;
	@FXML private ComboBox<PublishingHouse> publisherField;
	@FXML private TextField yearField;
	@FXML private TextField pagesField;
	@FXML private TextField isbnField;
	@FXML private TextField editionNumberField;
	@FXML private CheckBox hardCoverCheckbox;
	@FXML private TextArea descriptionField;

	private MultiSelector<Title> titleSelector;
	
	
	@FXML
	public void addEditionAction() {
		save();
		clear();
	}
	
	@Override
	public Edition save() {
		Edition newEdition = new Edition();
		
		Short year, pages, number;
		Long isbn;
		
		year   = parseShort         (yearField.getText(), null);
		pages  = parseShort        (pagesField.getText(), null);
		number = parseShort(editionNumberField.getText(), null);
		isbn   = parseLong          (isbnField.getText(), null);

		newEdition.setYear(year);
		if (!titleCheckbox.isSelected()) {
			newEdition.setTitle(      titleField.getText());
			newEdition.setSubtitle(subtitleField.getText());
		}
		newEdition.setPublishingHouse(publisherField.getValue());
		newEdition.setNumOfPages(pages);
		newEdition.setNumber(number);
		newEdition.setLanguage(languageField.getValue());
		newEdition.setISBN(isbn);
		newEdition.setHardCover(hardCoverCheckbox.isSelected());
		newEdition.setDescription(getText(descriptionField));


		editionRepository.save(newEdition);
		
		for (Title title : titleSelector.getItems()) {
			Edition_Title et = new Edition_Title();
			et.setEditionId(newEdition.getId());
			et.setTitleId(title.getId());
			
			edition_TitleRepository.save(et);
		}
		
		return newEdition;
	}

	@Override
	public void fill(Edition edition) {
		boolean titleExists = textExists(edition.getTitle());
		Long isbn = edition.getISBN();
		
		
		titleSelector.fill(edition.getTitles()
				.stream()
				.map(et -> et.getTitleObj())
				.collect(Collectors.toList()));
		
		titleCheckbox.setSelected(!titleExists);
		if (titleExists) {
			titleField   .setText(edition.getTitle());
			subtitleField.setText(edition.getSubtitle());
		}
		
		languageField     .setValue      (edition.getLanguage());
		publisherField    .setValue      (edition.getPublishingHouse());
		yearField         .setText(string(edition.getYear()));
		pagesField        .setText(string(edition.getNumOfPages()));
		editionNumberField.setText(string(edition.getNumber()));
		isbnField         .setText(string(isbn));
		descriptionField  .setText       (edition.getDescription());
		hardCoverCheckbox .setSelected   (edition.isHardCover());

		onTitlesChange();
	}

	@Override
	public void clear(){
		titleSelector.clear();
		titleCheckbox.setSelected(true);
		titleField.clear();
		subtitleField.clear();
		languageField.setValue(null);
		publisherField.setValue(null);
		yearField.clear();
		pagesField.clear();
		isbnField.clear();
		editionNumberField.clear();
		hardCoverCheckbox.setSelected(false);
		descriptionField.clear();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		titleSelector = new MultiSelector<Title>(titleRepository);
		titleSelector.setOnListChnage(this::onTitlesChange);
		fieldsPane.add(titleSelector, 1, 0);

		titleField   .editableProperty().bind(titleCheckbox.selectedProperty().not());
		subtitleField.editableProperty().bind(titleCheckbox.selectedProperty().not());
		titleField    .disableProperty().bind(titleCheckbox.selectedProperty());
		subtitleField .disableProperty().bind(titleCheckbox.selectedProperty());
		
		titleCheckbox.setOnAction(e -> updateOriginalTitle());
		
		Refresher.setOnShowing(languageField, languageRepository);
		Refresher.setOnShowing(publisherField, publishingHouseRepository);
		
		onTitlesChange();
	}

	private void onTitlesChange() {
		if (titleSelector.size() == 1) {
			titleCheckbox.setDisable(false);
			titleCheckbox.setSelected(true);
		} else {
			titleCheckbox.setDisable(true);
			titleCheckbox.setSelected(false);
		}
		
		updateOriginalTitle();
	}

	private void updateOriginalTitle() {
		if (titleCheckbox.isSelected()) {
			titleField   .setText(titleSelector.firstItem().getTitle());
			subtitleField.setText(titleSelector.firstItem().getSubtitle());
		}
	}

	public void setTitle(Title title) {
		titleSelector.setTitle(title);
		titleSelector.setDisable(true);

		onTitlesChange();
	}
}
