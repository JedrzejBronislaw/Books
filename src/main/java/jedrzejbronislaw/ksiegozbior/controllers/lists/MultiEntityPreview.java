package jedrzejbronislaw.ksiegozbior.controllers.lists;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import lombok.Setter;

public abstract class MultiEntityPreview extends VBox {
	
	@Setter protected ListManager listManager;
	
	abstract protected void set(String header, List<Ent> elements);
	abstract protected void refresh(List<Ent> elements);
	abstract protected Ent getSelectedItem();
	abstract protected boolean isSelectedItem();
	

	public void setContent(String header, List<? extends Ent> elements) {
		set(header, convert(elements));
	}
	
	@FXML
	protected void addAction() {
		if(listManager == null) return;
		
		listManager.add();
	}
	
	@FXML
	protected void editAction() {
	}
	
	@FXML
	protected void deleteAction() {
		if(listManager == null) return;

		if (listManager.delete(getSelectedItem()))
			refresh(convert(listManager.get()));
	}
	
	private List<Ent> convert(List<? extends Ent> listIn) {
		List<Ent> list = new ArrayList<>(listIn.size());
		
		listIn.stream().forEach(list::add);
		
		return list;
	}

	@FXML
	protected void clickAction(MouseEvent event) {
		if(listManager == null) return;
		
		if (isSelectedItem() && event.getClickCount() == 1)
			listManager.clickAction(getSelectedItem());
	}
}
