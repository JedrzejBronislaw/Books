package jedrzejbronislaw.ksiegozbior.controllers.listpreviews;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.projections.TheEntGenerator;
import jedrzejbronislaw.ksiegozbior.view.SimpleCallback;

@Component
public class ListPreviewController extends MultiEntityViewController implements Initializable {

	@Autowired TheEntGenerator theEntGenerator;
	
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
		list.setCellFactory(new SimpleCallback<Ent>(entity -> theEntGenerator.generate(entity).getLabel()));
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
