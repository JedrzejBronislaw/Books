package jedrzejbronislaw.ksiegozbior.controllers.entitypreviews;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import jedrzejbronislaw.ksiegozbior.model.entities.collections.BookCollection;
import jedrzejbronislaw.ksiegozbior.model.entities.collections.EditionCollection;
import jedrzejbronislaw.ksiegozbior.model.entities.collections.TitleCollection;
import jedrzejbronislaw.ksiegozbior.model.projections.TheBookCollection;
import jedrzejbronislaw.ksiegozbior.model.projections.TheCollection;
import jedrzejbronislaw.ksiegozbior.model.projections.TheEditionCollection;
import jedrzejbronislaw.ksiegozbior.model.projections.TheTitleCollection;

@Component
public class CollectionPreviewController extends VBox implements Initializable {

	@Autowired private TheBookCollection    theBookCollection;
	@Autowired private TheEditionCollection theEditionCollection;
	@Autowired private TheTitleCollection   theTitleCollection;
	
	private TheCollection theCollection;
	
	@FXML private Label nameLabel;
	@FXML private Label supercollectionLabel;

	@FXML private Label elementsLabel;
	

	public void setTitleCollection(TitleCollection collection) {
		theTitleCollection.setCollection(collection);
		theCollection = theTitleCollection;
		refresh();
	}

	public void setEditionCollection(EditionCollection collection) {
		theEditionCollection.setCollection(collection);
		theCollection = theEditionCollection;
		refresh();	
	}

	public void setBookCollection(BookCollection collection) {
		theBookCollection.setCollection(collection);
		theCollection = theBookCollection;
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
