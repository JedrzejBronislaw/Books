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
//	abstract protected void listRefresh(Iterable<? extends Ent> elements);
	abstract protected void listRefresh(List<EntWithLabel> elements);
	abstract protected Ent getSelectedItem();
	abstract protected boolean isSelectedItem();
//	void setStrategy(MultiEntityViewControllerStrategy strategy);
	
	@FXML
	protected void addAction() {
		if(strategy != null) strategy.addAction();
	}
	
	@FXML
	protected void deleteAction() {
		if(strategy != null) {
			Ent entity = getSelectedItem();//list.getSelectionModel().getSelectedItem();
			if(strategy.delAction(entity)) {
//				list.getItems().remove(entity);
				listRefresh(strategy.getLabeledList());
			}
		}
	}

	@FXML
	protected void clickAction(MouseEvent event) {
//		int selectedIndex = list.getSelectionModel().getSelectedIndex();
		if (isSelectedItem()/*selectedIndex > -1*/ && event.getClickCount() == 1) {			
			if(strategy != null)
				strategy.listClickAction(getSelectedItem());//list.getSelectionModel().getSelectedItem());
		}
	}
}
