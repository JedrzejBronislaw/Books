package jedrzejbronislaw.ksiegozbior.model.projections;

import java.util.HashMap;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import jedrzejbronislaw.ksiegozbior.model.entities.Author;
import jedrzejbronislaw.ksiegozbior.model.entities.Book;
import jedrzejbronislaw.ksiegozbior.model.entities.Edition;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.entities.Language;
import jedrzejbronislaw.ksiegozbior.model.entities.Location;
import jedrzejbronislaw.ksiegozbior.model.entities.PublishingHouse;
import jedrzejbronislaw.ksiegozbior.model.entities.Title;
import jedrzejbronislaw.ksiegozbior.model.entities.collections.BookCollection;
import jedrzejbronislaw.ksiegozbior.model.entities.collections.EditionCollection;
import jedrzejbronislaw.ksiegozbior.model.entities.collections.TitleCollection;

@Component
public class TheEntGenerator implements ApplicationContextAware {
	
	private static HashMap<Class<? extends Ent>, Class<? extends TheEnt>> transformations = new HashMap<>();
	
	static {
		transformations.put(Author.class,  TheAuthor.class);
		transformations.put(Book.class,    TheBook.class);
		transformations.put(Title.class,   TheTitle.class);
		transformations.put(Edition.class, TheEdition.class);
		
		transformations.put(Language.class,        TheLanguage.class);
		transformations.put(Location.class,        TheLocation.class);
		transformations.put(PublishingHouse.class, ThePublishingHouse.class);
		
		transformations.put(BookCollection.class,    TheBookCollection.class);
		transformations.put(EditionCollection.class, TheEditionCollection.class);
		transformations.put(TitleCollection.class,   TheTitleCollection.class);
	}
	
	private ApplicationContext context;

	
	public TheEnt generate(Ent entity) {
		Class<? extends TheEnt> theEntClass = transformations.get(entity.getClass());
		if (theEntClass == null) throw new IllegalArgumentException("There is no transform for " + entity.getClass() + " type.");
		
		TheEnt theEnt = context.getBean(theEntClass);
		
		theEnt.setEnt(entity);
		
		return theEnt;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}
}
