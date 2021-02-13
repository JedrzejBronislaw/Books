package jedrzejbronislaw.ksiegozbior.controllers.forms;

import org.springframework.context.event.EventListener;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import jedrzejbronislaw.ksiegozbior.controllers.EditRequestEvent;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;

public abstract class EntityForm<T extends Ent> extends VBox implements Initializable {

	protected abstract Class<T> getEntityClass();
	
	public    abstract Pane getFieldsPane();
	public    abstract void clear();
	protected abstract void fill(T entity);
	public    abstract T    save();
	
	@FXML protected Button addButton;
	
	
	@FXML
	public void add() {
		save();
		clear();
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
		fill((T)entity);
	}
}
