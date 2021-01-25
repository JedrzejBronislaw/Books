package jedrzejbronislaw.ksiegozbior.controllers.listpreviews;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import lombok.Setter;

public abstract class MultiEntityViewController {
	
	@Setter
	protected MultiEntityViewControllerStrategy strategy;
	
	abstract public void set(String header, List<EntWithLabel> elements);
	abstract protected void listRefresh(List<EntWithLabel> elements);
	abstract protected Ent getSelectedItem();
	abstract protected boolean isSelectedItem();
	
	@FXML
	protected void addAction() {
		if(strategy == null) return;
		
		strategy.addAction();
	}
	
	@FXML
	protected void deleteAction() {
		if(strategy == null) return;

		if (strategy.delAction(getSelectedItem()))
			listRefresh(strategy.getLabeledList());
	}

	@FXML
	protected void clickAction(MouseEvent event) {
		if(strategy == null) return;
		
		if (isSelectedItem() && event.getClickCount() == 1)
			strategy.listClickAction(getSelectedItem());
	}
}
