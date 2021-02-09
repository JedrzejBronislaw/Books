package jedrzejbronislaw.ksiegozbior.controllers.lists;

import java.util.List;

import jedrzejbronislaw.ksiegozbior.model.entities.Ent;

public interface ListManager {

	public enum ListType{NONE, LIST, TREE};
	
	ListType getType();
	boolean delete(Ent entity);
	void add();
	void clickAction(Ent entity);
	List<? extends Ent> get();
}
