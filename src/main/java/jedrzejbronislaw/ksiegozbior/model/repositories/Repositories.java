package jedrzejbronislaw.ksiegozbior.model.repositories;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
public class Repositories implements ApplicationContextAware{

	@Getter
	private static TitleRepository titleRepository;

	@Getter
	private static BookRepository bookRepository;

	@Getter
	private static PublishingHouseRepository publishingHouseRepository;
	
	@Getter
	private static TitleCollectionRepository titleCollectionRepository;
	
	@Getter
	private static EditionCollectionRepository editionCollectionRepository;
	
	@Getter
	private static BookCollectionRepository bookCollectionRepository;
	
	@Getter
	private static LocationRepository locationRepository; 
	
	@Getter
	private static LanguageRepository languageRepository;
	
	
	@Autowired
	@Getter
	private static AuthorshipRepository authorshipRepository;

	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		titleRepository = applicationContext.getBean(TitleRepository.class);
		bookRepository = applicationContext.getBean(BookRepository.class);
		publishingHouseRepository = applicationContext.getBean(PublishingHouseRepository.class);
		titleCollectionRepository = applicationContext.getBean(TitleCollectionRepository.class);
		editionCollectionRepository = applicationContext.getBean(EditionCollectionRepository.class);
		bookCollectionRepository = applicationContext.getBean(BookCollectionRepository.class);
		locationRepository = applicationContext.getBean(LocationRepository.class);
		languageRepository = applicationContext.getBean(LanguageRepository.class);
	}


}
