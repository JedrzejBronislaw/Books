package jedrzejbronislaw.ksiegozbior.controllers.lists;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import jedrzejbronislaw.ksiegozbior.controllers.EditEvent;
import jedrzejbronislaw.ksiegozbior.controllers.EditRequestEvent;
import jedrzejbronislaw.ksiegozbior.controllers.lists.managers.ListManager;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import lombok.Setter;

public abstract class AbstractListPreview extends VBox {
	
	@Autowired private ApplicationEventPublisher eventPublisher;
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
		eventPublisher.publishEvent(new EditRequestEvent(this, getSelectedItem()));
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
	
	@EventListener
	public void eventListener(EditEvent event) {
		if(listManager == null) return;

		refresh(convert(listManager.get()));
	}
}
