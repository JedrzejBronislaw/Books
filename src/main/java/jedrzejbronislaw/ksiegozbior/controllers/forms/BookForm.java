package jedrzejbronislaw.ksiegozbior.controllers.forms;

import static jedrzejbronislaw.ksiegozbior.controllers.FormTools.getDate;
import static jedrzejbronislaw.ksiegozbior.controllers.FormTools.getText;
import static jedrzejbronislaw.ksiegozbior.controllers.FormTools.localDate;
import static lombok.AccessLevel.PROTECTED;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import jedrzejbronislaw.ksiegozbior.controllers.forms.elements.EditionMiniPreview;
import jedrzejbronislaw.ksiegozbior.model.entities.Autograph;
import jedrzejbronislaw.ksiegozbior.model.entities.Book;
import jedrzejbronislaw.ksiegozbior.model.entities.BookComment;
import jedrzejbronislaw.ksiegozbior.model.entities.Edition;
import jedrzejbronislaw.ksiegozbior.model.entities.Library;
import jedrzejbronislaw.ksiegozbior.model.entities.Location;
import jedrzejbronislaw.ksiegozbior.model.entities.Visibility;
import jedrzejbronislaw.ksiegozbior.model.repositories.AutographRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.BookCommentRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.BookRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.EditionRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.LibraryRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.LocationRepository;
import jedrzejbronislaw.ksiegozbior.view.Refresher;
import lombok.Getter;

@Component
public class BookForm extends EntityForm<Book> {

	@Getter(PROTECTED) private Class<Book> entityClass = Book.class;

	@Autowired private BookRepository bookRepository;
	@Autowired private LibraryRepository libraryRepository;
	@Autowired private EditionRepository editionRepository;
	@Autowired private LocationRepository locationRepository;
	@Autowired private BookCommentRepository bookCommentRepository;
	@Autowired private AutographRepository autographRepository;
	
	@Getter
	@FXML private GridPane fieldsPane;
	@FXML private ComboBox<Library> libraryField;
	@FXML private ComboBox<Edition> editionField;
	@FXML private VBox editionBox;
	@FXML private TextField autographField;
	@FXML private CheckBox autographCheck;
	@FXML private DatePicker purchaseDateField;
	@FXML private ComboBox<Visibility> visibilityField;
	@FXML private ComboBox<Location> locationField;
	@FXML private TextArea commentField;
	
	@Autowired private EditionMiniPreview editionPreview;
	
	
	@Override
	public Book save() {
		Book book = (entity == null) ? new Book() : entity;
		
		book.setLibrary(libraryField.getValue());
		book.setEdition(editionField.getValue());
		book.setLocation(locationField.getValue());
		book.setPurchaseDate(getDate(purchaseDateField));
		book.setVisibility(visibilityField.getValue());

		if (autographCheck.isSelected())
			saveAutographComment(book);
		
		String commentContent = getText(commentField);
		if (commentContent != null)
			book.getComments().add(saveComment(commentContent));
		
		bookRepository.save(book);
		
		return book;
	}

	private Autograph saveAutographComment(Book book) {
		Autograph autograph = new Autograph(book, getText(autographField));
		autographRepository.save(autograph);
		return autograph;
	}

	private BookComment saveComment(String commentContent) {
		BookComment comment = new BookComment(commentContent);
		bookCommentRepository.save(comment);
		return comment;
	}

	@Override
	public void clear(){
		libraryField.setValue(null);
		editionField.setValue(null);
		autographField.clear();
		autographCheck.setSelected(false);
		purchaseDateField.setValue(null);
		locationField.setValue(null);
		visibilityField.setValue(Visibility.Default);
		commentField.clear();
		
		editionPreview.clear();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		addButton.disableProperty().bind(
				libraryField.valueProperty().isNull().or(
				editionField.valueProperty().isNull()
		));
		
		Refresher.setOnShowing(libraryField,  libraryRepository);
		Refresher.setOnShowing(editionField,  editionRepository);
		Refresher.setOnShowing(locationField, locationRepository);
		
		editionBox.getChildren().add(editionPreview);
		
		autographCheck.setOnAction(e -> autographClickAction());
		
		editionField.setOnAction(e -> editionPreview.setEdition(editionField.getValue()));
		
		clear();
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

	public void setEdition(Edition edition) {
		editionField.setValue(edition);
		editionField.setDisable(true);
		editionPreview.setEdition(edition);
	}

	@Override
	public void fill(Book book) {
		libraryField.setValue(book.getLibrary());
		editionField.setValue(book.getEdition());
		purchaseDateField.setValue(localDate(book.getPurchaseDate()));
		locationField.setValue(book.getLocation());
		visibilityField.setValue(book.getVisibility());
		commentField.setText(serializedComments(book));
		
		editionPreview.setEdition(book.getEdition());
	}
	
	private String serializedComments(Book book) {
		StringJoiner strJoiner = new StringJoiner("\n\n");

		book.getComments().stream()
			.map(c -> c.getContent())
			.forEach(strJoiner::add);
		
		return strJoiner.toString();
	}
}
