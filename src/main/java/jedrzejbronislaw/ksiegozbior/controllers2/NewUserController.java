package jedrzejbronislaw.ksiegozbior.controllers2;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import jedrzejbronislaw.ksiegozbior.lang.Internationalization;
import jedrzejbronislaw.ksiegozbior.model.entities.Library;
import jedrzejbronislaw.ksiegozbior.model.entities.User;
import jedrzejbronislaw.ksiegozbior.model.entities.User.Mode;
import jedrzejbronislaw.ksiegozbior.model.entities.User.Role;
import jedrzejbronislaw.ksiegozbior.model.repositories.LibraryRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.UserRepository;
import jedrzejbronislaw.ksiegozbior.view2.MyButton;

@Component
public class NewUserController implements Initializable {

	@Autowired private UserRepository repository;
	@Autowired private LibraryRepository libRepository;
	
	@FXML private VBox vbox;

	@FXML private TextField loginField;
	@FXML private TextField firstNameField;
	@FXML private TextField lastNameField;
	
	private MyButton saveButton;
	
	
	private void clearFields() {
		loginField    .setText(null);
		firstNameField.setText(null);
		lastNameField .setText(null);
	}
	
	private void save() {
		User newUser       = saveUser();
		Library newLibrary = saveLibrary();
		
		addLibraryToUser(newUser, newLibrary);
		
		repository.save(newUser);
	}

	private User saveUser() {
		User newUser = new User();
		
		newUser.setLogin(        loginField.getText());
		newUser.setFirstName(firstNameField.getText());
		newUser.setLastName(  lastNameField.getText());
		newUser.setRegistrationTime(Timestamp.valueOf(LocalDateTime.now()));
		newUser.setMode(Mode.Ok);
		newUser.setRole(Role.User);
		
		return newUser;
	}
	
	private Library saveLibrary() {
		Library newLibrary = new Library();

		newLibrary.setName("user's default library");
		libRepository.save(newLibrary);
		
		return newLibrary;
	}

	private void addLibraryToUser(User newUser, Library library) {
		ArrayList<Library> libraries = new ArrayList<>();
		libraries.add(library);
		newUser.setLibraries(libraries);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		saveButton = new MyButton(Internationalization.get("save"));
		saveButton.setHeight(25);
		saveButton.setOnClicked(() -> {
			save();
			clearFields();
		});
		
		vbox.getChildren().add(saveButton.createNode());
	}
}
