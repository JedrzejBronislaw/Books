package jedrzejbronislaw.ksiegozbior.model.projections;

import jedrzejbronislaw.ksiegozbior.model.entities.collections.EditionCollection;
import jedrzejbronislaw.ksiegozbior.model.repositories.Repositories;
import jedrzejbronislaw.ksiegozbior.tools.MyList;
import jedrzejbronislaw.ksiegozbior.tools.Named;
import jedrzejbronislaw.ksiegozbior.tools.StringNumber;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TheEditionCollection extends TheCollection {
	
	@NonNull
	private EditionCollection collection;

	@Override
	public String getName() {
		return collection.getName();
	}

	@Override
	public StringNumber<Long> getNumberOfElements() {
		return new StringNumber<Long>(
				Repositories.getEditionCollectionRepository().numberOfElements(collection.getId())
				);
	}

	@Override
	public MyList getSupercollections() {
		return new MyList(
				collection.getSupercollections()
				);
	}

	@Override
	public MyList getSubcollections() {
		return new MyList(
					Named.convertListToNamesList(
						Repositories.getEditionCollectionRepository().findSubcollections(collection.getId())
					)
				);
	}

	@Override
	public MyList getElements() {
		return new MyList(
					Named.convertListToNamesList(
						Repositories.getEditionCollectionRepository().findElements(collection.getId())
					)
				);
	}

}
