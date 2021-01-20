package jedrzejbronislaw.ksiegozbior.controllers.listpreviews;

import java.util.LinkedList;
import java.util.List;

import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.projections.TheEnt;

public abstract class MultiEntityViewControllerStrategy{
	public abstract boolean delAction(Ent entity);
	public abstract void addAction();
	public abstract void listClickAction(Ent entity);
	public abstract List<? extends Ent> getList();
	
	
	public List<EntWithLabel> getLabeledList() {
		List<EntWithLabel> list = new LinkedList<EntWithLabel>();

		getList().forEach(e -> list.add(
				new EntWithLabel(e, TheEnt.generateLabel(e))
				));
		
		return list;
	}
}
