package jedrzejbronislaw.ksiegozbior.view;

import org.springframework.data.repository.CrudRepository;

import javafx.scene.control.ComboBox;
import lombok.NonNull;

public class Refresher {
	
	public static <U> void loadAll(
			@NonNull ComboBox<U> combobox, 
			@NonNull CrudRepository<U, Long> repo) {
		
		combobox.getItems().clear();
		repo.findAll().forEach(combobox.getItems()::add);
	}
	
	public static <U> void setOnShowing(
			@NonNull ComboBox<U> combobox, 
			@NonNull CrudRepository<U, Long> repo) {
		
		combobox.setOnShowing(e -> loadAll(combobox, repo));
	}
}
