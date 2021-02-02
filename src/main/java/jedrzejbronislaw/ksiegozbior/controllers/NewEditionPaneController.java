package jedrzejbronislaw.ksiegozbior.controllers;

import static jedrzejbronislaw.ksiegozbior.controllers.EntityFormTools.getText;
import static jedrzejbronislaw.ksiegozbior.controllers.EntityFormTools.parseLong;
import static jedrzejbronislaw.ksiegozbior.controllers.EntityFormTools.parseShort;

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
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.entities.Language;
import jedrzejbronislaw.ksiegozbior.model.entities.PublishingHouse;
import jedrzejbronislaw.ksiegozbior.model.entities.Title;
import jedrzejbronislaw.ksiegozbior.model.repositories.EditionRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.Edition_TitleRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.LanguageRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.PublishingHouseRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.TitleRepository;
import lombok.Getter;

@Component
public class NewEditionPaneController implements Initializable, EntityFormController {

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
	@FXML private CheckBox subtitleCheckbox;
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
		clearFields();
	}
	
	@Override
	public Edition save() {
		Edition newEdition = new Edition();
		
		short year, pages, number;
		long isbn;
		
		//TODO validation
		year   = parseShort(yearField.getText(), (short) 0);
		pages  = parseShort(pagesField.getText(), (short) 0);
		number = parseShort(editionNumberField.getText(), (short) 0);
		isbn   = parseLong(isbnField.getText(), 0L);

		newEdition.setYear(year);
		if(!titleCheckbox   .isSelected()) newEdition.setTitle(   titleField.getText());
		if(!subtitleCheckbox.isSelected()) newEdition.setSubtitle(subtitleField.getText());
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
	public void set(Ent ent) {
		clearFields();
		if (!(ent instanceof Edition)) return;
		
			Edition edition = (Edition) ent;
			
			newTitleField.setValue(null);
			titleListField.getItems().clear();
			edition.getTitles().forEach(et -> titleListField.getItems().add(et.getTitleObj()));
			titleCheckbox.setSelected(edition.getTitle() == null || edition.getTitle().isBlank());
			if (!titleCheckbox.isSelected()) titleField.setText(edition.getTitle());
			subtitleCheckbox.setSelected(edition.getSubtitle() == null || edition.getSubtitle().isBlank());
			if (!subtitleCheckbox.isSelected()) subtitleField.setText(edition.getSubtitle());
			languageField.setValue(edition.getLanguage());
			publisherField.setValue(edition.getPublishingHouse());
			yearField.setText(Short.toString(edition.getYear()));
			pagesField.setText(Short.toString(edition.getNumOfPages()));
			if (edition.getISBN() != null) isbnField.setText(Long.toString(edition.getISBN()));
			editionNumberField.setText(Short.toString(edition.getNumber()));
			hardCoverCheckbox.setSelected(edition.isHardCover());
			descriptionField.setText(edition.getDescription());
	}

	
	public void clearFields(){
		
		newTitleField.setValue(null);
		titleListField.getItems().clear();
		titleCheckbox.setSelected(true);
		titleField.clear();
		titleField.setPromptText("");
		subtitleCheckbox.setSelected(true);
		subtitleField.clear();
		subtitleField.setPromptText("");
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
			if(newTitleField.getValue() != null)
				titleListField.getItems().add(newTitleField.getValue());
			
			updateTitlePrompText();
		});
		
		removeFromTitleListButton.setOnAction(e -> {
			Title title = titleListField.getSelectionModel().getSelectedItem();
			if(title != null)
				titleListField.getItems().remove(title);
			
			updateTitlePrompText();
		});
		
		
		subtitleCheckbox.disableProperty().bind(titleCheckbox.selectedProperty().not());
		
		titleCheckbox.setOnAction(e -> {
			titleField.setEditable(!titleCheckbox.isSelected());
			titleField.setText("");
			
			if(!titleCheckbox.isSelected())
				subtitleCheckbox.setSelected(false);
			
			updateTitlePrompText();
		});
		subtitleCheckbox.setOnAction(e -> {
			subtitleField.setEditable(!subtitleCheckbox.isSelected());	
			subtitleField.setText("");
			
			updateTitlePrompText();
		});
		
		
		updateLists();
		newTitleField.setOnShowing(e -> updateTitleList());
		publisherField.setOnShowing(e -> updatePublisherList());
		languageField.setOnShowing(e -> updateLanguageList());
	}

	private void updateTitlePrompText() {
		if(titleListField.getItems().size() == 1) {

			if(titleCheckbox.isSelected())
				titleField.setPromptText(titleListField.getItems().get(0).getTitle()); else
				titleField.setPromptText("");
			
			if(subtitleCheckbox.isSelected())
				subtitleField.setPromptText(titleListField.getItems().get(0).getSubtitle()); else
				subtitleField.setPromptText("");
				
		} else {
			titleField.setPromptText("");
			subtitleField.setPromptText("");
		}
	}

	private void updateLists() {
		updateTitleList();
		updatePublisherList();
		updateLanguageList();
	}

	private void updateLanguageList() {
		Iterable<Language> langs = languageRepository.findAll();
		
		languageField.getItems().clear();
		
		langs.forEach(languageField.getItems()::add);
	}

	private void updateTitleList() {
		Iterable<Title> titles = titleRepository.findAllNotRemoved();
		
		newTitleField.getItems().clear();
		
		titles.forEach(newTitleField.getItems()::add);
	}

	private void updatePublisherList() {
		Iterable<PublishingHouse> publishers = publishingHouseRepository.findAll();
		
		publisherField.getItems().clear();
		
		publishers.forEach(publisherField.getItems()::add);
	}

	public void setTitle(Title title) {
		titleListField.getItems().add(title);
		titlesBox.setDisable(true);

		updateTitlePrompText();
	}
	
	@Override
	public void enableAllFields() {
		titlesBox.setDisable(false);
		publisherField.setDisable(false);
		languageField.setDisable(false);
	}
}
