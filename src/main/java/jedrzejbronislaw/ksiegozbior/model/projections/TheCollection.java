package jedrzejbronislaw.ksiegozbior.model.projections;

import jedrzejbronislaw.ksiegozbior.tools.MyList;
import jedrzejbronislaw.ksiegozbior.tools.StringNumber;

public abstract class TheCollection implements TheEnt {

	public abstract String getName();

	public abstract StringNumber<Long> getNumberOfElements();
	public abstract MyList<String> getSupercollections();
	public abstract MyList<String> getSubcollections();
	public abstract MyList<String> getElements();
	
	
	@Override
	public String getLabel() {
		return getName();
	}
}
