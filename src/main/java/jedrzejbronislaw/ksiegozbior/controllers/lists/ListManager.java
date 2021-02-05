package jedrzejbronislaw.ksiegozbior.controllers.lists;

import java.util.List;

import jedrzejbronislaw.ksiegozbior.model.entities.Ent;

public abstract class ListManager {

	public abstract boolean delete(Ent entity);
	public abstract void add();
	public abstract void clickAction(Ent entity);
	public abstract List<? extends Ent> get();
}
