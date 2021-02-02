package jedrzejbronislaw.ksiegozbior.controllers;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
import jedrzejbronislaw.ksiegozbior.view.Refresher;
import lombok.Getter;

@Component
public class NewBookPaneController implements Initializable, EntityFormController {

	@Autowired private BookRepository bookRepository;
	@Autowired private EditionRepository editionRepository;
	@Autowired private LocationRepository locationRepository;
	@Autowired private BookCommentRepository bookCommentRepository;
	
	@Autowired private TheEdition theEdition;

	@Getter
	@FXML private GridPane fieldsPane;
	@FXML private Label ownerLabel;
	@FXML private ComboBox<Edition> editionField;
	@FXML private TextField autographField;
	@FXML private CheckBox autographCheck;
	@FXML private DatePicker purchaseDateField;
	@FXML private ComboBox<Visibility> visibilityField;
	@FXML private ComboBox<Location> locationField;
	@FXML private TextArea commentField;
	
	@FXML private Label editionTitle;
	@FXML private Label editionAuthor;
	@FXML private Label editionLanguage;
	@FXML private Label editionPublisher;
	@FXML private Label editionNumber;
	
	
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
		Refresher.setOnShowing(editionField,  editionRepository);
		Refresher.setOnShowing(locationField, locationRepository);
		
		autographCheck.setOnAction(e -> autographClickAction());
		
		editionField.setOnAction(e -> {
			System.out.println("editionField.setOnAction");
			loadEditionDetails(editionField.getValue());
		});
		
		clearFields();
		autographClickAction();
		loadVisibilityList();
	}

	private void autographClickAction() {
		boolean autograph = autographCheck.isSelected();
		
		autographField.setEditable(autograph);
		if(autograph)
			autographField.setStyle("-fx-text-fill: #000;"); else
			autographField.setStyle("-fx-text-fill: #888;");
	}
	
	private void loadVisibilityList() {
		Visibility[] vis = Visibility.values();
		
		visibilityField.getItems().clear();
		visibilityField.getItems().addAll(vis);
	}
	
	private void loadEditionDetails(Edition edition) {
		if(edition == null) return;
		theEdition.setEdition(edition);
		
		editionTitle    .setText(theEdition.getTitle());
		editionAuthor   .setText(theEdition.getAuthors().serialize_newLine());
		editionLanguage .setText(theEdition.getLanguageName());
		editionPublisher.setText(theEdition.getPublisherName());
		editionNumber   .setText(theEdition.getNumerRoman());
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
