package jedrzejbronislaw.ksiegozbior.controllers.listpreviews;

import java.util.List;

import jedrzejbronislaw.ksiegozbior.model.entities.Ent;

public abstract class MultiEntityViewControllerStrategy {

	public abstract boolean delAction(Ent entity);
	public abstract void addAction();
	public abstract void listClickAction(Ent entity);
	public abstract List<? extends Ent> getList();
}
