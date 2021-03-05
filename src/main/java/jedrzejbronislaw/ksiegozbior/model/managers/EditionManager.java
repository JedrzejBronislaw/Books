package jedrzejbronislaw.ksiegozbior.model.managers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jedrzejbronislaw.ksiegozbior.model.entities.Edition;
import jedrzejbronislaw.ksiegozbior.model.entities.Edition_Title;
import jedrzejbronislaw.ksiegozbior.model.entities.Edition_TitleId;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.entities.Title;
import jedrzejbronislaw.ksiegozbior.model.repositories.EditionRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.Edition_TitleRepository;
import jedrzejbronislaw.ksiegozbior.tools.Transaction;

@Component
public class EditionManager {

	@Autowired private EditionRepository editionRepository;
	@Autowired private Edition_TitleRepository edition_TitleRepository;

	@Autowired private Transaction transaction;


	public void save(Edition edition, List<Title> titles) {
		transaction.run(() -> saveEdition(edition, titles));
	}

	private void saveEdition(Edition edition, List<Title> titles) {
		editionRepository.save(edition);
		
		edition_TitleRepository.deleteByEditionExcept(edition, titles);
		
		for (Title title : titles) {
			long titleId = title.getId();
			long editionId = edition.getId();
			Edition_TitleId id = new Edition_TitleId(editionId, titleId);

			edition_TitleRepository.findById(id).orElseGet(() -> {
				Edition_Title et = new Edition_Title();
				et.setEditionId(editionId);
				et.setTitleId(titleId);

				return edition_TitleRepository.save(et);
			});
		}
	}
	
	public boolean setAsRemoved(Edition edition) {
		try {
			
			edition.setRemoved(true);
			editionRepository.save(edition);
			return true;
			
		} catch (Exception e) {
			return false;
		}
	}

	public List<? extends Ent> getAll() {
		return editionRepository.findAllNotRemoved();
	}
}
