package jedrzejbronislaw.ksiegozbior.model.projections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jedrzejbronislaw.ksiegozbior.model.entities.collections.EditionCollection;
import jedrzejbronislaw.ksiegozbior.model.repositories.EditionCollectionRepository;
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
public class TheEditionCollection extends TheCollection {
	
	@Autowired private EditionCollectionRepository editionCollectionRepository;
	
	@Setter @NonNull private EditionCollection collection;

	
	@Override
	public String getName() {
		return collection.getName();
	}

	@Override
	public StringNumber<Long> getNumberOfElements() {
		return new StringNumber<Long>(
				editionCollectionRepository.numberOfElements(collection.getId())
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
						editionCollectionRepository.findSubcollections(collection.getId())
					)
				);
	}

	@Override
	public MyList<String> getElements() {
		return new MyList<>(
					Named.convertListToNamesList(
						editionCollectionRepository.findElements(collection.getId())
					)
				);
	}
}
