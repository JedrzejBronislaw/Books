package jedrzejbronislaw.ksiegozbior.controllers.listpreviews;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;

@Component
public class ListPreviewController extends MultiEntityViewController implements Initializable {


	


	@FXML
	private Label title;
	@FXML
	private ListView<EntWithLabel> list;
	

	
	public void set(String header, List<EntWithLabel> elements) {
		title.setText(header);
		listRefresh(elements);
	}

	@Override
	protected void listRefresh(List<EntWithLabel> elements) {
		list.getItems().clear();
		
		if(elements != null)
			for(EntWithLabel e : elements) {
				list.getItems().add(e);
			}
	}
		
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		list.setCellFactory(new Callback<ListView<EntWithLabel>, ListCell<EntWithLabel>>() {
			
			@Override
			public ListCell<EntWithLabel> call(ListView<EntWithLabel> arg0) {

				return new ListCell<EntWithLabel>() {
					@Override
					protected void updateItem(EntWithLabel element, boolean empty) {
						super.updateItem(element, empty);
						if(!empty || element != null)
							setText(element.getLabel());
						else
							setText(null);
					}
				};
			}
		});
		
//		list.setCellFactory(new MyComboxCallBack<EntWithLabel>());
	}

	@Override
	protected Ent getSelectedItem() {
		return list.getSelectionModel().getSelectedItem().getEntity();
	}

	@Override
	protected boolean isSelectedItem() {
		return (list.getSelectionModel().getSelectedIndex() > -1);
	}



}
