package jedrzejbronislaw.ksiegozbior.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
import jedrzejbronislaw.ksiegozbior.view.MyComboxCallBack;
import lombok.Getter;
import lombok.Setter;




@Component
public class NewEditionPaneController implements Initializable, EntityFormController{


	@Autowired
	private TitleRepository titleRepository;
	@Autowired
	private Edition_TitleRepository edition_TitleRepository;
	@Autowired
	private EditionRepository editionRepository;
	@Autowired
	private PublishingHouseRepository publishingHouseRepository;
	@Autowired
	private LanguageRepository languageRepository;

//	@Setter
//	private Consumer<? extends Ent> onChange;

	
	@FXML
	@Getter
	private GridPane fieldsPane;
	
	
	@FXML
	private VBox titlesBox;
	
	@FXML
	private ComboBox<Title> newTitleField;
	
	@FXML
	private Button addToTitleListButton;
	
	@FXML
	private ListView<Title> titleListField;
	
	@FXML
	private Button removeFromTitleListButton;
	
	
	@FXML
	private CheckBox titleCheckbox;
	
	@FXML
	private TextField titleField;

	@FXML
	private CheckBox subtitleCheckbox;
	
	@FXML
	private TextField subtitleField;
	
	@FXML
	private ComboBox<Language> languageField;
	
	@FXML
	private ComboBox<PublishingHouse> publisherField;

	@FXML
	private TextField yearField;

	@FXML
	private TextField pagesField;

	@FXML
	private TextField isbnField;

	@FXML
	private TextField editionNumberField;
	
	@FXML
	private CheckBox hardCoverCheckbox;

	@FXML
	private TextArea descriptionField;

	
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
		try {
			year = Short.parseShort(yearField.getText());
		} catch (NumberFormatException e) {
			year = 0;
		}

		try {
			pages = Short.parseShort(pagesField.getText());
		} catch (NumberFormatException e) {
			pages = 0;
		}

		try {
			number = Short.parseShort(editionNumberField.getText());
		} catch (NumberFormatException e) {
			number = 0;
		}

		try {
			isbn = Long.parseLong(isbnField.getText());
		} catch (NumberFormatException e) {
			isbn= 0;
		}

		newEdition.setYear(year);
		if(!titleCheckbox.isSelected())
			newEdition.setTitle(titleField.getText());
		if(!subtitleCheckbox.isSelected())
			newEdition.setSubtitle(subtitleField.getText());
		newEdition.setPublishingHouse(publisherField.getValue());
		newEdition.setNumOfPages(pages);
		newEdition.setNumber(number);
		newEdition.setLanguage(languageField.getValue());
		newEdition.setISBN(isbn);
		newEdition.setHardCover(hardCoverCheckbox.isSelected());
		newEdition.setDescription(descriptionField.getText().strip().isEmpty() ? null : descriptionField.getText());


		editionRepository.save(newEdition);
		
		if(titleListField.getItems().size() > 0) {
			for(Title t : titleListField.getItems()) {
				Edition_Title ET = new Edition_Title();
				ET.setEditionId(newEdition.getId());
				ET.setTitleId(t.getId());
				
				edition_TitleRepository.save(ET);
			}
		}
		
		return newEdition;
		
//		newTitle.setTitle(titleField.getText().strip().isEmpty() ? null : titleField.getText());
//		newTitle.setSubtitle(subtitleField.getText().strip().isEmpty() ? null : subtitleField.getText());
//
//		//TODO language
//		if (year!=0)
//		newTitle.setYear(year);
//		newTitle.setDescription(descriptionField.getText());
//		titleRepository.save(newTitle);
//		
//		if (authorField.getValue() != null) {
//			Authorship authorship = new Authorship();
//			authorship.setAuthorId(authorField.getValue().getId());
//			authorship.setTitleId(newTitle.getId());
//			
//			authorship = authorshipRepository.save(authorship);
//		}
	}

	@Override
	public void set(Ent ent) {
		clearFields();
		if(ent instanceof Edition) {
			Edition edition = (Edition)ent;
			
			newTitleField.setValue(null);
			titleListField.getItems().clear();
			edition.getTitles().forEach(et -> titleListField.getItems().add(et.getTitleObj()));
			titleCheckbox.setSelected(edition.getTitle() == null || edition.getTitle().isBlank());
			if(!titleCheckbox.isSelected())
				titleField.setText(edition.getTitle());
			subtitleCheckbox.setSelected(edition.getSubtitle() == null || edition.getSubtitle().isBlank());
			if(!subtitleCheckbox.isSelected())
				subtitleField.setText(edition.getSubtitle());
			languageField.setValue(edition.getLanguage());
			publisherField.setValue(edition.getPublishingHouse());
			yearField.setText(Short.toString(edition.getYear()));
			pagesField.setText(Short.toString(edition.getNumOfPages()));
			if(edition.getISBN() != null)
				isbnField.setText(Long.toString(edition.getISBN()));
			editionNumberField.setText(Short.toString(edition.getNumber()));
			hardCoverCheckbox.setSelected(edition.isHardCover());
			descriptionField.setText(edition.getDescription());;
		}
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
		// TODO Auto-generated method stub
		
//		Author a = new Author();
//		a.setName("Adam");
//		a.setSurname("Mickiewicz");
//		a.setBirthDate(new Date(0));
//		a.setDeathDate(new Date(50L*365*24*60*60*1000));
//		a.setDescription("Autor dziadów");
//		
//		authorsRepository.save(a);
		
//		updateAuthorList();
		
//		Callback<ListView<Language>, ListCell<Language>> langCellFActory = new Callback<ListView<Language>, ListCell<Language>>() {
//			
//			@Override
//			public ListCell<Language> call(ListView<Language> arg0) {
//				// TODO Auto-generated method stub
////				return null;
//				return new ListCell<Language>() {
//					@Override
//					protected void updateItem(Language lang, boolean empty) {
//						super.updateItem(lang, empty);
//						if(!empty || lang != null)
//							setText(lang.toString());
//						else
//							setGraphic(null);
//					}
//				};
//			}
//		};
//		
//		Callback<ListView<Author>, ListCell<Author>> callFactory = new Callback<ListView<Author>, ListCell<Author>>() {
//			
//			@Override
//			public ListCell<Author> call(ListView<Author> arg0) {
//				return new ListCell<Author>() {
//					protected void updateItem(Author author, boolean empty) {
//						super.updateItem(author, empty);
//						if(!empty || author != null) 
//							setText(author.toString());
//						else
//							setGraphic(null);
//					};
//				};
//			}
//		};
//		authorField.setButtonCell(callFactory.call(null));
//		authorField.setCellFactory(callFactory);
		
//		authorField.setOnShowing(e->updateAuthorList());
		
		addToTitleListButton.setOnAction(e -> {
			if(newTitleField.getValue() != null)
				titleListField.getItems().add(newTitleField.getValue());
			
			updateTitlePrompText();
		});
		
		removeFromTitleListButton.setOnAction(e ->{
			Title title = titleListField.getSelectionModel().getSelectedItem();
			if(title != null)
				titleListField.getItems().remove(title);
			
			updateTitlePrompText();
		});
		
		
		subtitleCheckbox.disableProperty().bind(titleCheckbox.selectedProperty().not());
//		subtitleCheckbox.sel
		
		titleCheckbox.setOnAction(e -> {
			titleField.setEditable(!titleCheckbox.isSelected());
			titleField.setText("");
//			if(titleCheckbox.isSelected() && newTitleField.getValue() != null) {
//				titleField.setp
//			}
			if(!titleCheckbox.isSelected()) {
				subtitleCheckbox.setSelected(false);
//				subtitleCheckbox.setDisable(true);
			} //else
//				subtitleCheckbox.setDisable(false);
			
			updateTitlePrompText();
		});
		subtitleCheckbox.setOnAction(e -> {
			subtitleField.setEditable(!subtitleCheckbox.isSelected());	
			subtitleField.setText("");
			
			updateTitlePrompText();
		});
		
		
		MyComboxCallBack<Language> langCellFactory = new MyComboxCallBack<Language>();
		languageField.setButtonCell(langCellFactory.call(null));
		languageField.setCellFactory(langCellFactory);
		
		MyComboxCallBack<Title> titleCellFactory = new MyComboxCallBack<Title>();
		newTitleField.setButtonCell(titleCellFactory.call(null));
		newTitleField.setCellFactory(titleCellFactory);

//		
//		Callback<ListView<Title>, ListCell<Title>> title2CellFactory = new Callback<ListView<Title>, ListCell<Title>>() {
//		
//		@Override
//		public ListCell<Title> call(ListView<Title> arg0) {
//			return new ListCell<Title>() {
//				protected void updateItem(Title author, boolean empty) {
//					super.updateItem(author, empty);
//					if(!empty || author != null) 
//						setText(author.toString());
//					else
//						setGraphic(null);
//				};
//			};
//		}
//	};
		
//		titleListField.setButtonCell(titleCellFactory.call(null));
//		MyComboxCallBack<Title> title2CellFactory = new MyComboxCallBack<Title>();
//		titleListField.set
		titleListField.setCellFactory(titleCellFactory);
//		titleListField
		
		MyComboxCallBack<PublishingHouse> publisherCellFactory = new MyComboxCallBack<PublishingHouse>();
		publisherField.setButtonCell(publisherCellFactory.call(null));
		publisherField.setCellFactory(publisherCellFactory);
		
//		labelTemp.setOnMouseClicked(e -> 
//			labelTemp.setText(String.valueOf(authorField.getValue().getId()))
//		);
	
		
		updateLists();
		newTitleField.setOnShowing(e -> updateTitleList());
		publisherField.setOnShowing(e -> updatePublisherList());
		languageField.setOnShowing(e -> updateLanguageList());
	}

	private void updateTitlePrompText() {
		if(titleListField.getItems().size() == 1) {

			if(titleCheckbox.isSelected())
				titleField.setPromptText(titleListField.getItems().get(0).getTitle());
			else
				titleField.setPromptText("");
			
			if(subtitleCheckbox.isSelected())
				subtitleField.setPromptText(titleListField.getItems().get(0).getSubtitle());
			else
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
		
		langs.forEach(lang -> {
			languageField.getItems().add(lang);
		});
	}

	private void updateTitleList() {
		Iterable<Title> titles = titleRepository.findAllNotRemoved();
		
		newTitleField.getItems().clear();
		
		titles.forEach(title -> {
			newTitleField.getItems().add(title);
		});
	}

	private void updatePublisherList() {
		Iterable<PublishingHouse> publishers = publishingHouseRepository.findAll();
		
		publisherField.getItems().clear();
		
		publishers.forEach(publisher ->{
			publisherField.getItems().add(publisher);
		});
	}

	public void setTitle(Title title) {
		titleListField.getItems().add(title);
		titlesBox.setDisable(true);

		updateTitlePrompText();
	}
	
	@Override
	public void enableAllFields() {
//		titleField.setDisable(false);
//		titleListField.setDisable(false);
		titlesBox.setDisable(false);
		publisherField.setDisable(false);
		languageField.setDisable(false);
	}

//	private void updateAuthorList() {
//		Iterable<Author> authors = authorsRepository.findAll();
//		
//		authorField.getItems().clear();
//	
//		authors.forEach(author ->{
//			authorField.getItems().add(author);
//		});
//	}

}
