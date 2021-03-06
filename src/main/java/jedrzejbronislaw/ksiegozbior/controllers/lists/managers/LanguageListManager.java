package jedrzejbronislaw.ksiegozbior.controllers.lists.managers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jedrzejbronislaw.ksiegozbior.controllers.previews.LanguagePreview;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.entities.Language;
import jedrzejbronislaw.ksiegozbior.model.repositories.LanguageRepository;
import lombok.Getter;

@Component
public class LanguageListManager implements ListManager {

	@Getter private final ListType type = ListType.LIST;

	@Autowired private LanguageRepository repository;
	@Autowired private LanguagePreview preview;
	
	
	@Override
	public boolean delete(Ent entity) {//TODO
//		if(entity instanceof Language) {
//			Language language = (Language) entity;
//			try {
//				
//				language.setRemoved(true);
//				repository.save(language);
//				return true;
//			} catch (Exception e) {
//				return false;
//			}
//		} else
			return false;
	}

	@Override
	public void add() {//TODO
		System.out.println("ADD");
	}

	@Override
	public void clickAction(Ent entity) {
		if (entity instanceof Language)
			preview.setLanguage((Language) entity);
	}
	
	@Override
	public List<? extends Ent> get() {
		ArrayList<Language> list = new ArrayList<>();
		
		repository.findAll().forEach(list::add);
		
		return list;
	}
}
