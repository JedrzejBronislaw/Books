package jedrzejbronislaw.ksiegozbior.controllers.entitypreviews;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import jedrzejbronislaw.ksiegozbior.lang.Internationalization;
import jedrzejbronislaw.ksiegozbior.model.entities.Language;
import jedrzejbronislaw.ksiegozbior.model.projections.TheLanguage;

@Component
public class LanguagePreviewController extends VBox implements Initializable {

	private final boolean lazy = true;
	
	@Autowired private TheLanguage theLanguage;
	
	@FXML private Label abbrevLabel;
	@FXML private Label nameLabel;

	@FXML private Label booksNumberLabel;
	@FXML private Label booksNamesLabel;

	@FXML private Label editionsNumberLabel;
	@FXML private Label editionsNamesLabel;

	@FXML private Label titlesNumberLabel;
	@FXML private Label titlesNamesLabel;
	
	private boolean booksShown    = false;
	private boolean editionsShown = false;
	private boolean titlesShown   = false;
	 

	public void setLanguage(Language language) {
		theLanguage.setLanguage(language);
		refresh();
	}
	
	private void refresh() {
		if (theLanguage == null) return;
		clear();
		
		abbrevLabel.setText(theLanguage.getAbbrev());
		nameLabel  .setText(theLanguage.getName());

		booksNumberLabel   .setText(theLanguage.getNumberOfBooks().str());
		editionsNumberLabel.setText(theLanguage.getNumberOfEditinos().str());
		titlesNumberLabel  .setText(theLanguage.getNumberOfTitles().str());
		
		if(!lazy) {
			booksRefresh();
			editionsRefresh();
			titlesRefresh();		
		}
	}
	
	private void clear() {
		abbrevLabel.setText("");
		nameLabel.setText("");

		booksNumberLabel.setText("");
		booksNamesLabel.setText(Internationalization.get("show"));

		editionsNumberLabel.setText("");
		editionsNamesLabel.setText(Internationalization.get("show"));

		titlesNumberLabel.setText("");
		titlesNamesLabel.setText(Internationalization.get("show"));

		booksShown    = false;
		editionsShown = false;
		titlesShown   = false;
	}
	
	private void booksRefresh() {
		booksNamesLabel.setText(theLanguage.getBooksNames().serialize_newLine());
	}
	
	private void editionsRefresh() {
		editionsNamesLabel.setText(theLanguage.getEditionsNames().serialize_newLine());
	}
	
	private void titlesRefresh() {
		titlesNamesLabel.setText(theLanguage.getTitlesNames().serialize_newLine());
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		clear();
		
		if(lazy) {
			booksNamesLabel.setOnMouseClicked(e -> {
				if(!booksShown) {
					booksRefresh();	
					booksShown=true;
				}
			});
			
			editionsNamesLabel.setOnMouseClicked(e -> {
				if(!editionsShown) {
					editionsRefresh();
					editionsShown=true;
				}
			});
			
			titlesNamesLabel.setOnMouseClicked(e -> {
				if(!titlesShown) {
					titlesRefresh();
					titlesShown=true;
				}
			});
		}
	}
}
