package jedrzejbronislaw.ksiegozbior.controllers2;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import jedrzejbronislaw.ksiegozbior.Session;
import jedrzejbronislaw.ksiegozbior.lang.Internationalization;
import jedrzejbronislaw.ksiegozbior.model.entities.User;
import jedrzejbronislaw.ksiegozbior.view2.MyButton;
import jedrzejbronislaw.ksiegozbior.view2.ViewController;
import jedrzejbronislaw.ksiegozbior.view2.Views;
import lombok.Setter;

@Component
public class MainView2Controller implements Initializable {

	@Autowired
	private Session session;
	
	@Autowired
	private ViewController view;
	
	@FXML
	private BorderPane mainPane;
	@FXML
	private VBox mainContent;
	
	@FXML
	private Rectangle searchRect;
	@FXML
	private Rectangle addBookRect;
	@FXML
	private Rectangle seeLibraryRect;

	@FXML
	private Text searchButtonText;
	@FXML
	private Text addBookText;
	@FXML
	private Text seeLibraryText;
	
	@FXML
	private TextField searchField;
	
	@FXML
	private VBox results;

	
	@FXML
	private GridPane searcherPane;
	@FXML
	private GridPane buttonsPane;
	@FXML
	private ScrollPane resultsPane;
	
	@FXML
	private Button backButton;
	@FXML
	private Button signInButton;
	@FXML
	private Button signUpButton;
	@FXML
	private Button logOutButton;
	@FXML
	private Label loginLabel;

	
	private MyButton search;
	private MyButton addBook;
	private MyButton seeLibrary;
	
	@Setter
	private Consumer<String> searchAction;

//	@Setter
//	private Pane newBookPane;
//	@Setter
//	private Pane bookDetailsPane;
//	@Setter
//	private Pane newUserPane;
//	@Setter
//	private Pane loginPanel;
//	@Setter
//	private Pane libraryPane;

	
	private void updateLoginLabel(User user) {
		if (user != null) {
			loginLabel.setText(user.getName());
			setLoginButtons(true);
		} else { 
			loginLabel.setText(Internationalization.get("guest"));
			setLoginButtons(false);
		}
	}

	private void setLoginButtons(boolean logged) {
		signUpButton.setVisible(!logged);
		signUpButton.setManaged(!logged);
		signInButton.setVisible(!logged);
		signInButton.setManaged(!logged);
		logOutButton.setVisible(logged);
		logOutButton.setManaged(logged);
	}
	
	public void addResultItem(Pane item) {
		results.getChildren().add(item);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		search = new MyButton(searchRect, searchButtonText);
		addBook = new MyButton(addBookRect, addBookText);
		seeLibrary = new MyButton(seeLibraryRect, seeLibraryText);
		
		searchField.setOnKeyReleased(e -> {
			System.out.println("Searching...");
			if (searchAction != null)
				searchAction.accept(searchField.getText());
		});
		

		view.setMainPane(mainPane);
		view.register(Views.Main, mainContent);
		
		seeLibrary  .setOnClicked(() -> setView(Views.Library, true));
		addBook     .setOnClicked(() -> setView(Views.NewBook, true));
		signUpButton.setOnAction(e   -> setView(Views.NewUser, true));
		signInButton.setOnAction(e   -> setView(Views.LoginPanel, true));
		backButton  .setOnAction(e   -> setView(Views.Main, false));
		logOutButton.setOnAction(e   -> session.logOut());
		
		search.setHeight(25);
		search.setWidth(100);
		
		
		session.addLogInListiner(user -> updateLoginLabel(user));
		session.addLogOutListiner(() -> updateLoginLabel(null));
		
		updateLoginLabel(session.getUser());
	}

	public VBox getSearchResultPane() {
		return results;
	}

	public void showBookDetailsPane() {
		view.set(Views.BookDetails);
//		view.set(bookDetailsPane, true);
	}
	
	private void setView(Views viewType, boolean inScrollPane) {
		view.set(viewType);
	}
//	private void setView(Pane pane, boolean inScrollPane) {
//		if(pane != null)
//			if(inScrollPane) {
//				ScrollPane scrollPane = new ScrollPane(pane);
//				scrollPane.setFitToWidth(true);
//				mainPane.setCenter(scrollPane);
//			} else
//				mainPane.setCenter(pane);
//		
//	}
}
