package jedrzejbronislaw.ksiegozbior.model.projections;

import jedrzejbronislaw.ksiegozbior.model.entities.PublishingHouse;
import jedrzejbronislaw.ksiegozbior.model.repositories.Repositories;
import jedrzejbronislaw.ksiegozbior.tools.StringNumber;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ThePublishingHouse implements TheEnt {
	
	@NonNull private PublishingHouse publisher;


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
				Repositories.getPublishingHouseRepository().NumberOfEditions(publisher.getId())
				);
	}
	
	@Override
	public String getLabel() {
		return getAbbrev();
	}
}
