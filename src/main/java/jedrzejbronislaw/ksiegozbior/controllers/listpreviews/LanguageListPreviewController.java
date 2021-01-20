package jedrzejbronislaw.ksiegozbior.controllers.listpreviews;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jedrzejbronislaw.ksiegozbior.controllers.entitypreviews.LanguagePreviewController;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.entities.Language;
import jedrzejbronislaw.ksiegozbior.model.repositories.LanguageRepository;

@Component
public class LanguageListPreviewController extends MultiEntityViewControllerStrategy{

	@Autowired
	private LanguageRepository repository;

	@Autowired
	private LanguagePreviewController previewController;
	
	@Override
	public boolean delAction(Ent entity) {//TODO
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
	public void addAction() {//TODO
		System.out.println("ADD");
	}

	@Override
	public void listClickAction(Ent entity) {
//		System.out.println("Klik! -> " + entity.toString());
		if (entity instanceof Language)
			previewController.setLanguage((Language) entity);
	}
	
	@Override
	public List<? extends Ent> getList() {
		ArrayList<Language> list = new ArrayList<Language>();
		
		repository.findAll().forEach(l -> list.add(l));
		
		return list;
	}

}
