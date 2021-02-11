package jedrzejbronislaw.ksiegozbior.controllers.forms;

import static jedrzejbronislaw.ksiegozbior.controllers.FormTools.getText;
import static jedrzejbronislaw.ksiegozbior.controllers.FormTools.parseLong;
import static jedrzejbronislaw.ksiegozbior.controllers.FormTools.parseShort;
import static jedrzejbronislaw.ksiegozbior.controllers.FormTools.textExists;
import static lombok.AccessLevel.PROTECTED;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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
	
	@FXML private VBox titlesBox;
	@FXML private ComboBox<Title> newTitleField;
	@FXML private Button addToTitleListButton;
	@FXML private ListView<Title> titleListField;
	@FXML private Button removeFromTitleListButton;
	
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

	
	@FXML
	public void addEditionAction() {
		save();
		clear();
	}
	
	@Override
	public Edition save() {
		Edition newEdition = new Edition();
		
		short year, pages, number;
		Long isbn;
		
		//TODO validation
		year   = parseShort(yearField.getText(), (short) 0);
		pages  = parseShort(pagesField.getText(), (short) 0);
		number = parseShort(editionNumberField.getText(), (short) 0);
		isbn   = parseLong(isbnField.getText(), null);

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
		
		for(Title title : titleListField.getItems()) {
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
		
		
		edition.getTitles().stream()
			.map(et -> et.getTitleObj())
			.forEach(titleListField.getItems()::add);
		
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

	private String string(Long l) {
		return (l != null) ? Long.toString(l) : "";
	}

	private String string(short s) {
		return (s > 0) ? Short.toString(s) : "";
	}

	@Override
	public void clear(){
		
		newTitleField.setValue(null);
		titleListField.getItems().clear();
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
		addToTitleListButton.setOnAction(e -> {
			Title newTitle = newTitleField.getValue();
			if(newTitle != null)
				titleListField.getItems().add(newTitle);
			
			onTitlesChange();
		});
		
		removeFromTitleListButton.setOnAction(e -> {
			Title title = titleListField.getSelectionModel().getSelectedItem();
			if(title != null)
				titleListField.getItems().remove(title);

			onTitlesChange();
		});
		

		titleField   .editableProperty().bind(titleCheckbox.selectedProperty().not());
		subtitleField.editableProperty().bind(titleCheckbox.selectedProperty().not());
		titleField    .disableProperty().bind(titleCheckbox.selectedProperty());
		subtitleField .disableProperty().bind(titleCheckbox.selectedProperty());
		
		
		titleCheckbox.setOnAction(e -> updateOriginalTitle());
		
		Refresher.setOnShowing(languageField, languageRepository);
		Refresher.setOnShowing(newTitleField, titleRepository);
		Refresher.setOnShowing(publisherField, publishingHouseRepository);
		
		onTitlesChange();
	}

	private void onTitlesChange() {
		if (titleListField.getItems().size() == 1) {
			titleCheckbox.setDisable(false);
		} else {
			titleCheckbox.setDisable(true);
			titleCheckbox.setSelected(false);
		}
		
		updateOriginalTitle();
	}

	private void updateOriginalTitle() {
		if (titleCheckbox.isSelected()) {
			titleField   .setText(titleListField.getItems().get(0).getTitle());
			subtitleField.setText(titleListField.getItems().get(0).getSubtitle());
		}
	}

	public void setTitle(Title title) {
		titleListField.getItems().add(title);
		titlesBox.setDisable(true);

		onTitlesChange();
	}
}
