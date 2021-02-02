package jedrzejbronislaw.ksiegozbior.model.projections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.entities.collections.BookCollection;
import jedrzejbronislaw.ksiegozbior.model.repositories.BookCollectionRepository;
import jedrzejbronislaw.ksiegozbior.tools.MyList;
import jedrzejbronislaw.ksiegozbior.tools.Named;
import jedrzejbronislaw.ksiegozbior.tools.StringNumber;
import lombok.NonNull;
import lombok.Setter;

@Component
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TheBookCollection extends TheCollection {

	@Autowired private BookCollectionRepository bookCollectionRepository;
	
	@Setter @NonNull private BookCollection collection;
	
	
	@Override
	public String getName() {
		return collection.getName();
	}

	@Override
	public StringNumber<Long> getNumberOfElements() {
		return new StringNumber<Long>(
				bookCollectionRepository.numberOfElements(collection.getId())
				);
	}

	@Override
	public MyList<String> getSupercollections() {
		return new MyList<>(
				collection.getSupercollections()
				);
	}

	@Override
	public MyList<String> getSubcollections() {
		return new MyList<>(
					Named.convertListToNamesList(
						bookCollectionRepository.findSubcollections(collection.getId())
					)
				);
	}

	@Override
	public MyList<String> getElements() {
		return new MyList<>(
					Named.convertListToNamesList(
						bookCollectionRepository.findElements(collection.getId())
					)
				);
	}

	@Override
	public boolean setEnt(Ent entity) {
		if (!(entity instanceof BookCollection)) return false;
		
		setCollection((BookCollection) entity);
		return true;
	}
}
