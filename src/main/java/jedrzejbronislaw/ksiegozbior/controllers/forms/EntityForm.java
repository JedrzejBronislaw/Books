package jedrzejbronislaw.ksiegozbior.controllers.forms;

import javafx.scene.layout.Pane;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;

public interface EntityForm {

	Pane getFieldsPane();
	void clear();
	void set(Ent ent);
	Ent save();
}
