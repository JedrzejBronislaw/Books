package jedrzejbronislaw.ksiegozbior.model.projections;

import jedrzejbronislaw.ksiegozbior.model.entities.collections.BookCollection;
import jedrzejbronislaw.ksiegozbior.model.repositories.Repositories;
import jedrzejbronislaw.ksiegozbior.tools.MyList;
import jedrzejbronislaw.ksiegozbior.tools.Named;
import jedrzejbronislaw.ksiegozbior.tools.StringNumber;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TheBookCollection extends TheCollection {

	@NonNull
	private BookCollection collection;
	
	@Override
	public String getName() {
		return collection.getName();
	}

	@Override
	public StringNumber<Long> getNumberOfElements() {
		return new StringNumber<Long>(
				Repositories.getBookCollectionRepository().numberOfElements(collection.getId())
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
						Repositories.getBookCollectionRepository().findSubcollections(collection.getId())
					)
				);
	}

	@Override
	public MyList<String> getElements() {
		return new MyList<>(
					Named.convertListToNamesList(
						Repositories.getBookCollectionRepository().findElements(collection.getId())
					)
				);
	}

}
