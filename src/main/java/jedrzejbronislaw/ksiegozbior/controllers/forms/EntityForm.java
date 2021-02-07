package jedrzejbronislaw.ksiegozbior.controllers.forms;

import org.springframework.context.event.EventListener;

import javafx.scene.layout.Pane;
import jedrzejbronislaw.ksiegozbior.controllers.EditRequestEvent;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;

public interface EntityForm {

	Pane getFieldsPane();
	void clear();
	void set(Ent ent);
	Ent save();
	
	@EventListener
	public default void eventListener(EditRequestEvent event) {
		set(event.getEntity());
	}
}
