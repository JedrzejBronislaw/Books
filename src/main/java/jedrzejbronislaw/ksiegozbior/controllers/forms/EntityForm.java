package jedrzejbronislaw.ksiegozbior.controllers.forms;

import javafx.scene.layout.Pane;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;

public interface EntityForm {

	Pane getFieldsPane();
	public void clearFields();
	
	default void set(Ent ent) {
		//TODO delete default
	}
	
	default void enableAllFields() {
		//TODO delete default
	}

	default Ent save() {
		return null;
		//TODO delete default
	}
}
