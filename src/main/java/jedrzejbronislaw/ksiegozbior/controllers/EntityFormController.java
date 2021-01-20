package jedrzejbronislaw.ksiegozbior.controllers;

import javafx.scene.layout.Pane;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;

public interface EntityFormController {

	Pane getFieldsPane();
	public void clearFields();
	default void set(Ent ent) {
		//TODO delete default
	}
	default void enableAllFields() {
		//TODO delete default
	}
//	void save();
	default Ent save() {
		return null;
		//TODO delete default
	}
	
//	default void setOnChange(Consumer<? extends Ent> onChange) {
//		//TODO delete default
//	}
//
//	default void getUnsavedEnt(Ent ent) {
//		//TODO delete default
//	}
	
	
}
