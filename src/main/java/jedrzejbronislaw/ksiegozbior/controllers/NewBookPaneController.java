package jedrzejbronislaw.ksiegozbior.controllers;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import jedrzejbronislaw.ksiegozbior.model.entities.Book;
import jedrzejbronislaw.ksiegozbior.model.entities.BookComment;
import jedrzejbronislaw.ksiegozbior.model.entities.Edition;
import jedrzejbronislaw.ksiegozbior.model.entities.Location;
import jedrzejbronislaw.ksiegozbior.model.entities.Visibility;
import jedrzejbronislaw.ksiegozbior.model.projections.TheEdition;
import jedrzejbronislaw.ksiegozbior.model.repositories.BookCommentRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.BookRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.EditionRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.LocationRepository;
import jedrzejbronislaw.ksiegozbior.view.MyComboboxRefresher;
import jedrzejbronislaw.ksiegozbior.view.MyComboxCallBack;
import lombok.Getter;

@Component
public class NewBookPaneController implements Initializable, EntityFormController{

	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private EditionRepository editionRepository;
	@Autowired
	private LocationRepository locationRepository;
	@Autowired
	private BookCommentRepository bookCommentRepository;

	MyComboboxRefresher<Edition> editionRefresher;
	MyComboboxRefresher<Location> locationRefresher;

	@FXML
	@Getter
	private GridPane fieldsPane;
	
	@FXML
	private Label ownerLabel;
	
	@FXML
	private ComboBox<Edition> editionField;
	
	@FXML
	private TextField autographField;
	
	@FXML
	private CheckBox autographCheck;

	@FXML
	private DatePicker purchaseDateField;
	
	@FXML
	private ComboBox<Visibility> visibilityField;
	
	@FXML
	private ComboBox<Location> locationField;
	
	@FXML
	private TextArea commentField;
	
	@FXML
	private Label editionTitle;
	@FXML
	private Label editionAuthor;
	@FXML
	private Label editionLanguage;
	@FXML
	private Label editionPublisher;
	@FXML
	private Label editionNumber;
	
	
	@FXML
	public void addBookAction() {
		save();
		clearFields();
	}
	
	@Override
	public Book save() {
		Book newBook = new Book();
		
		newBook.setEdition(editionField.getValue());
		newBook.setLocation(locationField.getValue());
		newBook.setPurchaseDate(purchaseDateField.getValue() == null ? null : Date.valueOf(purchaseDateField.getValue()));
		newBook.setVisibility(visibilityField.getValue().getValue());
//		newBook.setLibrary //TODO

		if(autographCheck.isSelected()) {
			BookComment autographComment = new BookComment();
			StringBuffer sb = new StringBuffer("autograph"); //TODO internationalization
			
			if (!autographField.getText().isBlank())
				sb.append(" (" + autographField.getText().strip() + ")");
			
			autographComment.setContent(sb.toString());
			
			bookCommentRepository.save(autographComment);
			
			newBook.getComments().add(autographComment);
		}
		
		if(!commentField.getText().isBlank()) {
			BookComment comment = new BookComment(commentField.getText());			
			bookCommentRepository.save(comment);
			
			newBook.getComments().add(comment);
		}
		
		
		
		bookRepository.save(newBook);
		
		return newBook;
//		
//		//TODO validation
//		
//		newBook.set
//		
//		newAuthor.setName(nameField.getText());
//		newAuthor.setSurname(surnameField.getText());
//		newAuthor.setDescription(descriptionField.getText());
//		newAuthor.setBirthDate(Date.valueOf(birthDateField.getValue()));
//		newAuthor.setDeathDate(Date.valueOf(deathDateField.getValue()));
//		
//		autorRepository.save(newAuthor);
	}


	public void clearFields(){
		ownerLabel.setText("");
		editionField.setValue(null);
		autographField.clear();
		autographCheck.setSelected(false);
		purchaseDateField.setValue(null);
		locationField.setValue(null);
		visibilityField.setValue(Visibility.Default);
		commentField.clear();
		
		editionTitle.setText("");
		editionAuthor.setText("");
		editionLanguage.setText("");
		editionPublisher.setText("");
		editionNumber.setText("");
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		editionRefresher = new MyComboboxRefresher<Edition>(editionField, editionRepository);
		locationRefresher = new MyComboboxRefresher<Location>(locationField, locationRepository);

		new MyComboxCallBack<Edition>(editionField, e-> new TheEdition(e).toString());
		new MyComboxCallBack<Location>(locationField);
		
		updateLists();
		
		editionField.setOnShowing(e -> editionRefresher.refresh());
		locationField.setOnShowing(e ->locationRefresher.refresh());
		
		autographCheck.setOnAction(e -> {
			autographField.setEditable(autographCheck.isSelected());
			if(autographCheck.isSelected())
				autographField.setStyle("-fx-text-fill: #000;");
			else
				autographField.setStyle("-fx-text-fill: #888;");
		});
		
		editionField.setOnAction(e -> {
			System.out.println("editionField.setOnAction");
			loadEditionDetails(editionField.getValue());
		});
		
		clearFields();
		autographCheck.getOnAction().handle(null);
		loadVisibilityList();
	}

	private void updateLists() {
		editionRefresher.refresh();
		locationRefresher.refresh();
	}
	
	private void loadVisibilityList() {
		Visibility[] vis = Visibility.values();
		
		visibilityField.getItems().clear();
		
		for(int i=0; i<vis.length; i++)
			visibilityField.getItems().add(vis[i]);
	}
	
	private void loadEditionDetails(Edition e) {
		if(e == null) return;
		
		TheEdition edition = new TheEdition(e);
		
		editionTitle.setText(edition.getTitle());	
		editionAuthor.setText(edition.getAuthors().serialize_newLine());
		editionLanguage.setText(edition.getLanguageName());
		editionPublisher.setText(edition.getPublisherName());
		editionNumber.setText(edition.getNumerRoman());
	}

	public void setEdition(Edition edition) {
		editionField.setValue(edition);
		editionField.setDisable(true);
		loadEditionDetails(edition);
	}
	
	@Override
	public void enableAllFields() {
		editionField.setDisable(false);
	}
}
