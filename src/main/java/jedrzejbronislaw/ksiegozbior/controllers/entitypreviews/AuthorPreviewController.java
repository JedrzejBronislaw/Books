package jedrzejbronislaw.ksiegozbior.controllers.entitypreviews;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import jedrzejbronislaw.ksiegozbior.model.entities.Author;
import jedrzejbronislaw.ksiegozbior.model.projections.TheAuthor;

@Component
public class AuthorPreviewController implements Initializable {
	
	private TheAuthor theAuthor;
	
	@FXML private Label   firstNameLabel;
	@FXML private Label    lastNameLabel;
	@FXML private Label   birthDateLabel;
	@FXML private Label   deathDateLabel;
	@FXML private Label nationalityLabel;
	@FXML private TextArea descField;

	@FXML private Label   titlesLabel;
	@FXML private Label penNamesLabel;
	
	
	public void setAuthor(Author author) {
		theAuthor = new TheAuthor(author);
		refresh();
	}
	
	private void refresh() {
		if (theAuthor == null) return;
		clear();

		firstNameLabel  .setText(theAuthor.getName());
		lastNameLabel   .setText(theAuthor.getSurname());
		birthDateLabel  .setText(theAuthor.getBirthDate());
		deathDateLabel  .setText(theAuthor.getDeathDate());
		nationalityLabel.setText(theAuthor.getNationality());
		descField       .setText(theAuthor.getDecsription());

		titlesLabel     .setText(theAuthor.getTitlesText());
		penNamesLabel   .setText(theAuthor.getPenNamesText());
	}
	
	private void clear() {
		firstNameLabel  .setText("");
		lastNameLabel   .setText("");
		birthDateLabel  .setText("");
		deathDateLabel  .setText("");
		nationalityLabel.setText("");
		descField       .clear();

		titlesLabel     .setText("");
		penNamesLabel   .setText("");
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		clear();
	}
}
