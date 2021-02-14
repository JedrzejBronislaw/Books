package jedrzejbronislaw.ksiegozbior.controllers.forms.elements;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.data.repository.CrudRepository;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.tools.Injection;
import jedrzejbronislaw.ksiegozbior.tools.MyFXMLLoader;
import jedrzejbronislaw.ksiegozbior.view.Refresher;
import lombok.Setter;

public class MultiSelector<T extends Ent> extends VBox implements Initializable {

	@FXML private ComboBox<T> comboBox;
	@FXML private ListView<T> list;
	
	@Setter private CrudRepository<T, Long> repository;
	@Setter private Runnable onListChnage;
	
	
	public MultiSelector(CrudRepository<T, Long> repository) {
		this.repository = repository;
		MyFXMLLoader.create("view/firstVer/forms/elements/MultiSelector.fxml", this);
	}
	
	public void fill(List<T> newList) {
		clear();
		newList.forEach(list.getItems()::add);
	}

	public void clear() {
		comboBox.setValue(null);
		list.getItems().clear();
	}

	@FXML
	public void add() {
		T newItem = comboBox.getValue();
		if(newItem != null)
			list.getItems().add(newItem);
		
		Injection.run(onListChnage);
	}
	
	@FXML
	public void remove() {
		T selectedItem = list.getSelectionModel().getSelectedItem();
		if(selectedItem != null)
			list.getItems().remove(selectedItem);

		Injection.run(onListChnage);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Refresher.setOnShowing(comboBox, repository);
	}
	
	public T getItem(int i) {
		return list.getItems().get(i);
	}
	
	public List<T> getItems() {
		return list.getItems();
	}
	
	public T firstItem() {
		return getItem(0);
	}

	public int size() {
		return list.getItems().size();
	}

	public void addItem(T item) {
		list.getItems().add(item);
	}
}
