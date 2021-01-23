package jedrzejbronislaw.ksiegozbior.controllers.entitypreviews;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import jedrzejbronislaw.ksiegozbior.model.entities.collections.BookCollection;
import jedrzejbronislaw.ksiegozbior.model.entities.collections.EditionCollection;
import jedrzejbronislaw.ksiegozbior.model.entities.collections.TitleCollection;
import jedrzejbronislaw.ksiegozbior.model.projections.TheBookCollection;
import jedrzejbronislaw.ksiegozbior.model.projections.TheCollection;
import jedrzejbronislaw.ksiegozbior.model.projections.TheEditionCollection;
import jedrzejbronislaw.ksiegozbior.model.projections.TheTitleCollection;

@Component
public class CollectionPreviewController implements Initializable {

	private TheCollection theCollection;
	
	@FXML private Label nameLabel;
	@FXML private Label supercollectionLabel;

	@FXML private Label elementsLabel;
	

	public void setTitleCollection(TitleCollection collection) {
		theCollection = new TheTitleCollection(collection);
		refresh();
	}

	public void setEditionCollection(EditionCollection collection) {
		theCollection = new TheEditionCollection(collection);
		refresh();	
	}

	public void setBookCollection(BookCollection collection) {
		theCollection = new TheBookCollection(collection);
		refresh();	
	}
	
	private void refresh() {
		if (theCollection == null) return;
		clear();
		
		nameLabel           .setText(theCollection.getName());
		supercollectionLabel.setText(theCollection.getSupercollections().serialize_newLine());
		
		elementsLabel.setText(
			"(" + theCollection.getNumberOfElements().str() + ")"
			+ System.lineSeparator() +
			theCollection.getElements().serialize_newLine()
		);
	}
	
	private void clear() {
		nameLabel.setText("");
		supercollectionLabel.setText("");

		elementsLabel.setText("");
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		clear();
	}
}
