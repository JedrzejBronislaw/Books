package jedrzejbronislaw.ksiegozbior.model.managers;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jedrzejbronislaw.ksiegozbior.model.entities.Edition;
import jedrzejbronislaw.ksiegozbior.model.entities.Edition_Title;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.entities.Title;
import jedrzejbronislaw.ksiegozbior.model.repositories.EditionRepository;
import jedrzejbronislaw.ksiegozbior.tools.Transaction;

@Component
public class EditionManager {

	@Autowired private EditionRepository editionRepository;
	@Autowired private Transaction transaction;


	public void save(Edition edition, List<Title> titles) {
		transaction.run(() -> {
			editionRepository.save(edition);
			updateTitles(edition, titles);
			editionRepository.save(edition);
		});
	}
	
	private void updateTitles(Edition edition, List<Title> newTitles) {
		if (edition.getTitles() == null) edition.setTitles(new HashSet<>());
		
		edition.getTitles().removeIf(et -> !newTitles.contains(et.getTitleObj()));
		List<Title> oldTitles = edition.getTitles().stream()
				.map(et -> et.getTitleObj())
				.collect(Collectors.toList());
		
		newTitles.stream()
			.filter(title -> !oldTitles.contains(title))
			.map(title -> new Edition_Title(edition, title))
			.forEach(edition.getTitles()::add);
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
