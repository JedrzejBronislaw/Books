package jedrzejbronislaw.ksiegozbior;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import jedrzejbronislaw.ksiegozbior.model.entities.User;

@Component
public class Session {

	private User loggedInUser;//TODO create dedicated class
	
	private List<Consumer<User>> logInListiners = new LinkedList<Consumer<User>>();
	private List<Runnable> logOutListiners = new LinkedList<Runnable>();
	
	
	public boolean logIn(User user) {
		if(user == null) return false;
		
		loggedInUser = user;
		logInListiners.forEach(l -> l.accept(user));
		return true;
	}
	
	public void logOut() {
		loggedInUser = null;
		logOutListiners.forEach(l -> l.run());
	}
	
	public boolean isLogged() {
		return (loggedInUser != null);
	}
	
	public User getUser() {
		return loggedInUser;
	}
	
	public void addLogInListiner(Consumer<User> listiner) {
		logInListiners.add(listiner);
	}
	
	public void addLogOutListiner(Runnable listiner) {
		logOutListiners.add(listiner);		
	}
}
