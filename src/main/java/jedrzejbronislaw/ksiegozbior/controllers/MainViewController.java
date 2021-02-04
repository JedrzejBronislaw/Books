package jedrzejbronislaw.ksiegozbior.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import jedrzejbronislaw.ksiegozbior.lang.Internationalization;
import jedrzejbronislaw.ksiegozbior.lang.Languages;
import jedrzejbronislaw.ksiegozbior.tools.Injection;
import jedrzejbronislaw.ksiegozbior.view.View;
import jedrzejbronislaw.ksiegozbior.view.Views;
import lombok.Getter;
import lombok.Setter;

@Component
public class MainViewController extends VBox implements Initializable {

	@Setter private Consumer<Languages> changeGUILanguage;
	
	@FXML private Label label1;
	
	@FXML @Getter private AnchorPane mainPane;
	@FXML @Getter private Pane formBox; 
	@FXML @Getter private AnchorPane sPane; 
	@FXML @Getter private AnchorPane previewPane; 
	@FXML @Getter private Label header1;
	
	@FXML private Menu languageMenu;
	
	@Setter private View view;
	
	
	@FXML
	private void showWelcomeView() {//TODO
		showView(Views.AUTHORS);
	}

	@FXML
	private void showAuthors() {
		showView(Views.AUTHORS);
	}

	@FXML
	private void showTitles() {
		showView(Views.TITLES);
	}

	@FXML
	private void showBooks() {
		showView(Views.BOOKS);
	}

	@FXML
	private void showEditions() {
		showView(Views.EDITIONS);
	}

	@FXML
	private void showBookCollections() {
		showView(Views.BOOK_COLLECTIONS);
	}

	@FXML
	private void showTitleCollections() {
		showView(Views.TITLE_COLLECTIONS);
	}

	@FXML
	private void showEditionCollections() {
		showView(Views.EDITION_COLLECTIONS);
	}

	@FXML
	private void showLanguages() {
		showView(Views.LANGUAGES);
	}

	@FXML
	private void showLocations() {
		showView(Views.LOCATIONS);
	}

	@FXML
	private void showPublishingHouses() {
		showView(Views.PUBLISHING_HOUSES);
	}

	private void showView(Views viewType) {
		if(view != null) view.setView(viewType);
	}

	public void setLanguageMenu(Languages current, Languages...languages) {
		languageMenu.getItems().clear();

		if (languages == null) return;
		
		RadioMenuItem menuItem;
		ToggleGroup group = new ToggleGroup();
		
		for (Languages lang : languages) {
			
			menuItem = new RadioMenuItem(Internationalization.get(lang.getLabel()));
			menuItem.setToggleGroup(group);
			menuItem.setSelected(lang.equals(current));
			menuItem.setOnAction(e -> Injection.run(changeGUILanguage, lang));

			languageMenu.getItems().add(menuItem);
		}
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		languageMenu.getItems().clear();
	}
}
