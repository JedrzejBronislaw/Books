package jedrzejbronislaw.ksiegozbior.model.projections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jedrzejbronislaw.ksiegozbior.model.entities.Location;
import jedrzejbronislaw.ksiegozbior.model.repositories.LocationRepository;
import jedrzejbronislaw.ksiegozbior.tools.MyList;
import jedrzejbronislaw.ksiegozbior.tools.Named;
import jedrzejbronislaw.ksiegozbior.tools.StringNumber;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Component
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@NoArgsConstructor
@RequiredArgsConstructor
public class TheLocation implements TheEnt {
	
	@Autowired private LocationRepository locationRepository;
	
	@Setter @NonNull private Location location;
	
	
	public String getName() {
		return location.getName();
	}
	
	public String getDescription() {
		return location.getDescription();
	}

	public StringNumber<Long> getNumberOfBooks() {
		return new StringNumber<Long>(
					locationRepository.numberOfBooks(location.getId())
				);
	}
	
	public MyList<String> getSuperlocationsNames() {
		return new MyList<>(location.getSupercollections());
	}
	
	public MyList<String> getSublocationsNames() {
		return new MyList<>(
				locationRepository.getSublocationsNames(location.getId())
				);
	}

	public MyList<String> getBooksNames() {
		return new MyList<>(
					Named.convertListToNamesList(
						locationRepository.getBooks(location)
					)
				);
	}

	@Override
	public String getLabel() {
		return getName();
	}
}
