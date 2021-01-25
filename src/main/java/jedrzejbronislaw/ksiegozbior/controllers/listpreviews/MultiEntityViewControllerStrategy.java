package jedrzejbronislaw.ksiegozbior.controllers.listpreviews;

import java.util.List;
import java.util.stream.Collectors;

import jedrzejbronislaw.ksiegozbior.model.entities.Ent;

public abstract class MultiEntityViewControllerStrategy {

	public abstract boolean delAction(Ent entity);
	public abstract void addAction();
	public abstract void listClickAction(Ent entity);
	public abstract List<? extends Ent> getList();
	
	
	public List<EntWithLabel> getLabeledList() {
		return getList().stream()
				.map(EntWithLabel::new)
				.collect(Collectors.toList());
	}
}
