package jedrzejbronislaw.ksiegozbior.controllers.listpreviews;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import lombok.Setter;

public abstract class MultiEntityViewController extends VBox {
	
	@Setter
	protected MultiEntityViewControllerStrategy strategy;
	
	abstract protected void set(String header, List<Ent> elements);
	abstract protected void listRefresh(List<Ent> elements);
	abstract protected Ent getSelectedItem();
	abstract protected boolean isSelectedItem();
	

	public void setContent(String header, List<? extends Ent> elements) {
		set(header, convert(elements));
	}
	
	@FXML
	protected void addAction() {
		if(strategy == null) return;
		
		strategy.addAction();
	}
	
	@FXML
	protected void deleteAction() {
		if(strategy == null) return;

		if (strategy.delAction(getSelectedItem()))
			listRefresh(convert(strategy.getList()));
	}
	
	private List<Ent> convert(List<? extends Ent> listIn) {
		List<Ent> list = new ArrayList<>(listIn.size());
		
		listIn.stream().forEach(list::add);
		
		return list;
	}

	@FXML
	protected void clickAction(MouseEvent event) {
		if(strategy == null) return;
		
		if (isSelectedItem() && event.getClickCount() == 1)
			strategy.listClickAction(getSelectedItem());
	}
}
