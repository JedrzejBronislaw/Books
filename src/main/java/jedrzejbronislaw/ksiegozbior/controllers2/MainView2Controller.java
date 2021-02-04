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
import jedrzejbronislaw.ksiegozbior.tools.Injection;
import jedrzejbronislaw.ksiegozbior.view2.MyButton;
import jedrzejbronislaw.ksiegozbior.view2.ViewController;
import jedrzejbronislaw.ksiegozbior.view2.Views;
import lombok.Setter;

@Component
public class MainView2Controller extends VBox implements Initializable {

	@Autowired private Session session;
	@Autowired private ViewController view;
	
	@FXML private BorderPane mainPane;
	@FXML private VBox mainContent;
	
	@FXML private Rectangle searchRect;
	@FXML private Rectangle addBookRect;
	@FXML private Rectangle seeLibraryRect;

	@FXML private Text searchButtonText;
	@FXML private Text addBookText;
	@FXML private Text seeLibraryText;
	
	@FXML private TextField searchField;
	
	@FXML private VBox results;

	@FXML private GridPane searcherPane;
	@FXML private GridPane buttonsPane;
	@FXML private ScrollPane resultsPane;
	
	@FXML private Button backButton;
	@FXML private Button signInButton;
	@FXML private Button signUpButton;
	@FXML private Button logOutButton;
	@FXML private Label loginLabel;
	
	@Setter private Consumer<String> searchAction;
	
	private MyButton search;
	private MyButton addBook;
	private MyButton seeLibrary;

	
	private void updateLoginLabel(User user) {
		String userLabel = (user != null) ? user.getName() : Internationalization.get("guest");
		
		loginLabel.setText(userLabel);
		setLoginButtonsVisible(user != null);
	}

	private void setLoginButtonsVisible(boolean userLoggedIn) {
		setButtonVisible(signUpButton, !userLoggedIn);
		setButtonVisible(signInButton, !userLoggedIn);
		setButtonVisible(logOutButton,  userLoggedIn);
	}
	
	private void setButtonVisible(Button button, boolean visible) {
		button.setVisible(visible);
		button.setManaged(visible);
	}
	
	public void addResultItem(Pane item) {
		results.getChildren().add(item);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		search     = new MyButton(    searchRect, searchButtonText);
		addBook    = new MyButton(   addBookRect,      addBookText);
		seeLibrary = new MyButton(seeLibraryRect,   seeLibraryText);
		
		searchField.setOnKeyReleased(e -> search(searchField.getText()));
		
		view.setMainPane(mainPane);
		view.register(Views.MAIN, mainContent);
		
		seeLibrary  .setOnClicked(() -> setView(Views.LIBRARY, true));
		addBook     .setOnClicked(() -> setView(Views.NEW_BOOK, true));
		signUpButton.setOnAction(e   -> setView(Views.NEW_USER, true));
		signInButton.setOnAction(e   -> setView(Views.LOGIN_PANEL, true));
		backButton  .setOnAction(e   -> setView(Views.MAIN, false));
		logOutButton.setOnAction(e   -> session.logOut());
		
		search.setHeight(25);
		search.setWidth(100);
		
		session.addLogInListiner(user -> updateLoginLabel(user));
		session.addLogOutListiner(()  -> updateLoginLabel(null));
		
		updateLoginLabel(session.getUser());
	}

	private void search(String phrase) {
		System.out.println("Searching...");
		Injection.run(searchAction, phrase);
	}

	public VBox getSearchResultPane() {
		return results;
	}

	public void showBookDetailsPane() {
		view.set(Views.BOOK_DETAILS);
	}
	
	private void setView(Views viewType, boolean inScrollPane) {
		view.set(viewType);
	}
}
