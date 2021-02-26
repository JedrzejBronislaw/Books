package jedrzejbronislaw.ksiegozbior.controllers.forms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import jedrzejbronislaw.ksiegozbior.controllers.EditEvent;
import jedrzejbronislaw.ksiegozbior.controllers.EditRequestEvent;
import jedrzejbronislaw.ksiegozbior.lang.Internationalization;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;

public abstract class EntityForm<T extends Ent> extends VBox implements Initializable {

	protected abstract Class<T> getEntityClass();
	
	@Autowired private ApplicationEventPublisher eventPublisher;
	
	public    abstract Pane getFieldsPane();
	public    abstract void clear();
	protected abstract void fill(T entity);
	public    abstract T    save();
	
	@FXML protected Button addButton;
	
	protected T entity = null;
	
	
	@FXML
	public void add() {
		entity = save();
		clear();
		
		eventPublisher.publishEvent(new EditEvent(this, entity));
		entity = null;
		
		addButton.setText(Internationalization.get("add"));
	}
	
	@EventListener
	public void eventListener(EditRequestEvent event) {
		set(event.getEntity());
	}

	@SuppressWarnings("unchecked")
	public final void set(Ent entity) {
		if (entity == null) return;
		if (entity.getClass() != getEntityClass()) return;

		clear();
		this.entity = (T)entity;
		fill((T)entity);

		addButton.setText(Internationalization.get("update"));
	}
}
