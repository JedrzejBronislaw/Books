package jedrzejbronislaw.ksiegozbior.view;

import org.springframework.data.repository.CrudRepository;

import javafx.scene.control.ComboBox;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MyComboboxRefresher<T> {

	@NonNull
	private ComboBox<T> combobox;
	
	@NonNull
	private CrudRepository<T, Long> repo;
//	private Repo repo;
	
	public void refresh() {
		Iterable<T> elements = repo.findAll();
		
		combobox.getItems().clear();
		
		elements.forEach(elem -> combobox.getItems().add(elem));
	}
}
