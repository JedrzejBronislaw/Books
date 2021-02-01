package jedrzejbronislaw.ksiegozbior.controllers.listpreviews;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.projections.TheEnt;

@Component
public class ListPreviewController extends MultiEntityViewController implements Initializable {

	@FXML private Label title;
	@FXML private ListView<Ent> list;

	
	protected void set(String header, List<Ent> elements) {
		title.setText(header);
		listRefresh(elements);
	}

	@Override
	protected void listRefresh(List<Ent> elements) {
		list.getItems().clear();
		
		if(elements != null)
			list.getItems().addAll(elements);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		list.setCellFactory(createCellFactory(TheEnt::generateLabel));
		
		//list.setCellFactory(new MyComboxCallBack<EntWithLabel>());
	}

	private Callback<ListView<Ent>, ListCell<Ent>> createCellFactory(Function<Ent, String> converter) {
		return new Callback<ListView<Ent>, ListCell<Ent>>() {
			
			@Override
			public ListCell<Ent> call(ListView<Ent> arg0) {
				
				return new ListCell<Ent>() {
					@Override
					protected void updateItem(Ent element, boolean empty) {
						super.updateItem(element, empty);
						if(!empty || element != null)
							setText(converter.apply(element)); else
							setText(null);
					}
				};
			}
		};
	}

	@Override
	protected Ent getSelectedItem() {
		return list.getSelectionModel().getSelectedItem();
	}

	@Override
	protected boolean isSelectedItem() {
		return (list.getSelectionModel().getSelectedIndex() > -1);
	}
}
