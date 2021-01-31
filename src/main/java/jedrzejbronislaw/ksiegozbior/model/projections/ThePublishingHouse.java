package jedrzejbronislaw.ksiegozbior.model.projections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jedrzejbronislaw.ksiegozbior.model.entities.PublishingHouse;
import jedrzejbronislaw.ksiegozbior.model.repositories.PublishingHouseRepository;
import jedrzejbronislaw.ksiegozbior.tools.StringNumber;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Component
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@NoArgsConstructor
@RequiredArgsConstructor
public class ThePublishingHouse implements TheEnt {
	
	@Autowired private PublishingHouseRepository publishingHouseRepository;
	
	@Setter @NonNull private PublishingHouse publisher;


	public String getAbbrev() {
		return publisher.getAbbr();
	}

	public String getName() {
		return publisher.getName();
	}

	public String getCity() {
		return publisher.getCity();
	}

	public StringNumber<Long> getNumberOfEditions() {
		return new StringNumber<Long>(
				publishingHouseRepository.NumberOfEditions(publisher.getId())
				);
	}
	
	@Override
	public String getLabel() {
		return getAbbrev();
	}
}
