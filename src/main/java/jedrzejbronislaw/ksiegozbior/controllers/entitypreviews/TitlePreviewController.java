package jedrzejbronislaw.ksiegozbior.controllers.entitypreviews;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import jedrzejbronislaw.ksiegozbior.model.entities.Title;
import jedrzejbronislaw.ksiegozbior.model.projections.TheTitle;

@Component
public class TitlePreviewController implements Initializable{//, EntityPreviewControler{
	
	private TheTitle theTitle;
	
	@FXML
	private Label titleLabel;
	@FXML
	private Label subtitleLabel;
	@FXML
	private Label authorsLabel;
	@FXML
	private Label languageLabel;
	@FXML
	private Label creationYearLabel;

	@FXML
	private TextArea descriptionField;
	@FXML
	private Label collectionsLabel;
	

	public void setTitle(Title title) {
		theTitle = new TheTitle(title);
		refresh();
	}
	
	private void refresh() {
		if (theTitle == null) return;
		clear();
		
		titleLabel.setText(theTitle.getTitle());
		subtitleLabel.setText(theTitle.getSubtitle());
		authorsLabel.setText(theTitle.getAuthors().serialize_newLine());
		languageLabel.setText(theTitle.getLanguageName());
		creationYearLabel.setText(theTitle.getCreationYear().str());
		
		descriptionField.setText(theTitle.getDescription());
		collectionsLabel.setText(theTitle.getCollections().serialize_newLine());
	}
	
	private void clear() {
		titleLabel.setText("");
		subtitleLabel.setText("");
		authorsLabel.setText("");
		languageLabel.setText("");
		creationYearLabel.setText("");
		
		descriptionField.clear();
		collectionsLabel.setText("");
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		clear();
	}

}
