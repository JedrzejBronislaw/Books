package jedrzejbronislaw.ksiegozbior.model.projections;

import jedrzejbronislaw.ksiegozbior.model.entities.Location;
import jedrzejbronislaw.ksiegozbior.model.repositories.Repositories;
import jedrzejbronislaw.ksiegozbior.tools.MyList;
import jedrzejbronislaw.ksiegozbior.tools.Named;
import jedrzejbronislaw.ksiegozbior.tools.StringNumber;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TheLocation implements TheEnt {
	
	@NonNull private Location location;
	
	
	public String getName() {
		return location.getName();
	}
	
	public String getDescription() {
		return location.getDescription();
	}

	public StringNumber<Long> getNumberOfBooks() {
		return new StringNumber<Long>(
				Repositories.getLocationRepository().numberOfBooks(location.getId())
				);
	}
	
	public MyList<String> getSuperlocationsNames() {
		return new MyList<>(location.getSupercollections());
	}
	
	public MyList<String> getSublocationsNames() {
		return new MyList<>(
				Repositories.getLocationRepository().getSublocationsNames(location.getId())
				);
	}

	public MyList<String> getBooksNames() {
		return new MyList<>(
					Named.convertListToNamesList(
						Repositories.getLocationRepository().getBooks(location)
					)
				);
	}

	@Override
	public String getLabel() {
		return getName();
	}
}
