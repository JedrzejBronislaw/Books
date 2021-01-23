package jedrzejbronislaw.ksiegozbior.controllers2;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import jedrzejbronislaw.ksiegozbior.Session;
import jedrzejbronislaw.ksiegozbior.lang.Internationalization;
import jedrzejbronislaw.ksiegozbior.model.entities.User;
import jedrzejbronislaw.ksiegozbior.model.repositories.UserRepository;
import jedrzejbronislaw.ksiegozbior.view2.MyButton;

@Component
public class LoginPanelController implements Initializable {

	@Autowired private UserRepository repository;
	@Autowired private Session session;
	
	@FXML private VBox vbox;
	@FXML private TextField loginField;
	
	private MyButton saveButton;
	
	
	private void clearFields() {
		loginField.setText(null);
	}

	private void logIn(String login) {
		User user = repository.findByLogin(login);
		
		if (user != null) session.logIn(user);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		saveButton = new MyButton(Internationalization.get("log_in"));
		saveButton.setHeight(25);
		saveButton.setOnClicked(() -> {
			logIn(loginField.getText());
			clearFields();
		});
		
		vbox.getChildren().add(saveButton.createNode());
	}
}
