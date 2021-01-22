package jedrzejbronislaw.ksiegozbior.model.projections;

import jedrzejbronislaw.ksiegozbior.model.entities.collections.TitleCollection;
import jedrzejbronislaw.ksiegozbior.model.repositories.Repositories;
import jedrzejbronislaw.ksiegozbior.tools.MyList;
import jedrzejbronislaw.ksiegozbior.tools.Named;
import jedrzejbronislaw.ksiegozbior.tools.StringNumber;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TheTitleCollection extends TheCollection{
	
	@NonNull
	private TitleCollection collection;


	public String getName() {
		return collection.getName();
	}


	@Override
	public StringNumber<Long> getNumberOfElements() {
		return new StringNumber<Long>(
				Repositories.getTitleCollectionRepository().numberOfElements(collection.getId())
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
						Repositories.getTitleCollectionRepository().findSubcollections(collection.getId())
					)
				);
	}

	@Override
	public MyList<String> getElements() {
		return new MyList<>(
					Named.convertListToNamesList(
						Repositories.getTitleCollectionRepository().findElements(collection.getId())
					)
				);
	}
	
	
}
