package jedrzejbronislaw.ksiegozbior.model.projections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.entities.collections.TitleCollection;
import jedrzejbronislaw.ksiegozbior.model.repositories.TitleCollectionRepository;
import jedrzejbronislaw.ksiegozbior.tools.MyList;
import jedrzejbronislaw.ksiegozbior.tools.Named;
import jedrzejbronislaw.ksiegozbior.tools.StringNumber;
import lombok.NonNull;
import lombok.Setter;

@Component
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TheTitleCollection extends TheCollection {
	
	@Autowired TitleCollectionRepository titleCollectionRepository;
	
	@Setter @NonNull private TitleCollection collection;


	public String getName() {
		return collection.getName();
	}

	@Override
	public StringNumber<Long> getNumberOfElements() {
		return new StringNumber<Long>(
				titleCollectionRepository.numberOfElements(collection.getId())
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
						titleCollectionRepository.findSubcollections(collection.getId())
					)
				);
	}

	@Override
	public MyList<String> getElements() {
		return new MyList<>(
					Named.convertListToNamesList(
						titleCollectionRepository.findElements(collection.getId())
					)
				);
	}

	@Override
	public boolean setEnt(Ent entity) {
		if (!(entity instanceof TitleCollection)) return false;
		
		setCollection((TitleCollection) entity);
		return true;
	}
}
