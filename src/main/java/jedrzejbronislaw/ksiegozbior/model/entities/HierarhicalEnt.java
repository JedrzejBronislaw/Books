package jedrzejbronislaw.ksiegozbior.model.entities;

import java.util.ArrayList;
import java.util.List;

import jedrzejbronislaw.ksiegozbior.tools.Named;

public interface HierarhicalEnt extends Ent,Named {

	public HierarhicalEnt getSuper();
	
	public default List<String> getSupercollections() {
		ArrayList<String> supercollections = new ArrayList<String>();
		HierarhicalEnt supercoll;

		supercoll = this;
		while(supercoll.getSuper() != null) {
			supercoll = supercoll.getSuper();
			supercollections.add(supercoll.getName());
		}
		
			
		return supercollections;
	}
}
