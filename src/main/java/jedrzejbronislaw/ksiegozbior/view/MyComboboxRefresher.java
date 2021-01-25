package jedrzejbronislaw.ksiegozbior.view;

import org.springframework.data.repository.CrudRepository;

import javafx.scene.control.ComboBox;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MyComboboxRefresher<T> {

	@NonNull private ComboBox<T> combobox;
	@NonNull private CrudRepository<T, Long> repo;
	
	public void refresh() {
		combobox.getItems().clear();
		repo.findAll().forEach(combobox.getItems()::add);
	}
}
