package jedrzejbronislaw.ksiegozbior.controllers.forms.elements;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import jedrzejbronislaw.ksiegozbior.model.entities.Edition;
import jedrzejbronislaw.ksiegozbior.model.projections.TheEdition;
import jedrzejbronislaw.ksiegozbior.tools.MyFXMLLoader;

@Component
public class EditionMiniPreview extends GridPane implements Initializable {

	@Autowired private TheEdition theEdition;

	@FXML private Label editionTitle;
	@FXML private Label editionAuthor;
	@FXML private Label editionLanguage;
	@FXML private Label editionPublisher;
	@FXML private Label editionNumber;
	
	public EditionMiniPreview() {
		MyFXMLLoader.create("view/firstVer/forms/elements/EditionMiniPreview.fxml", this);
	}
	
	public void clear(){
		editionTitle.setText("");
		editionAuthor.setText("");
		editionLanguage.setText("");
		editionPublisher.setText("");
		editionNumber.setText("");
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		clear();
	}

	public void setEdition(Edition edition) {
		if(edition == null) return;
		theEdition.setEdition(edition);
		
		editionTitle    .setText(theEdition.getTitle());
		editionAuthor   .setText(theEdition.getAuthors().serialize_newLine());
		editionLanguage .setText(theEdition.getLanguageName());
		editionPublisher.setText(theEdition.getPublisherName());
		editionNumber   .setText(theEdition.getNumerRoman());
	}
}
