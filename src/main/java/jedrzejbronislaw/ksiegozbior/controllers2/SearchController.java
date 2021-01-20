package jedrzejbronislaw.ksiegozbior.controllers2;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javafx.application.Platform;
import jedrzejbronislaw.ksiegozbior.model.entities.Author;
import jedrzejbronislaw.ksiegozbior.model.entities.Book;
import jedrzejbronislaw.ksiegozbior.model.entities.Edition;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.entities.Title;
import jedrzejbronislaw.ksiegozbior.model.repositories.AuthorRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.BookRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.EditionRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.TitleRepository;
import lombok.Setter;

@Component
@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SearchController {
	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private EditionRepository editionRepository;
	@Autowired
	private TitleRepository titleRepository;
	@Autowired
	private AuthorRepository authorRepository;
	
	@Setter
	private Runnable clearShearchResults;
	@Setter
	private Consumer<Ent> showSearchItem;
	@Setter
	private BiConsumer<String, Integer> showSearchInfo;
	
//	TODO private List<Thread> threads = new ArrayList<Thread>();
	//TODO thread menager class and thread class with context setter and kill possibility
	
	
	public void newSearchPhrase(String phrase) {
		Thread t = new Thread(()->{
			
			if(clearShearchResults != null)
				Platform.runLater(clearShearchResults);
			
	
			if(phrase.isBlank()) return; 
			
			List<Book> results = bookRepository.findByTitlePhrase(phrase);
			
			if(showSearchItem != null)
				Platform.runLater(() -> results.forEach(showSearchItem));
			
			if(showSearchInfo != null)
				Platform.runLater(() -> showSearchInfo.accept(phrase, results.size()));
		});
		
		t.start();
//		threads.add(t);
		
	}
	
	public void newSearchEditionOrTitle(String phrase) {
		Thread t = new Thread(()->{
			
			if(clearShearchResults != null)
				Platform.runLater(clearShearchResults);
			
	
			if(phrase.isBlank()) return; 
			
			List<Edition> resultsE = editionRepository.findByTitlePhrase(phrase);
			
			if(showSearchItem != null)
				Platform.runLater(() -> resultsE.forEach(showSearchItem));	
			
			List<Title> resultsT = titleRepository.findByTitlePhrase(phrase);
			
			if(showSearchItem != null)
				Platform.runLater(() -> resultsT.forEach(showSearchItem));
			
			if(showSearchInfo != null)
				Platform.runLater(() -> showSearchInfo.accept(phrase, resultsE.size()+resultsT.size()));	
		});
		
		t.start();
//		threads.add(t);	
	}
	
	public void newSearchEdition(String phrase) {
		Thread t = new Thread(()->{
			
			if(clearShearchResults != null)
				Platform.runLater(clearShearchResults);
			
	
			if(phrase.isBlank()) return; 
			
			List<Edition> resultsE = editionRepository.findByTitlePhrase(phrase);
			
			if(showSearchItem != null)
				Platform.runLater(() -> resultsE.forEach(showSearchItem));	
			
			if(showSearchInfo != null)
				Platform.runLater(() -> showSearchInfo.accept(phrase, resultsE.size()));
		});
		
		t.start();
//		threads.add(t);	
	}
	
	public void newSearchTitle(String phrase) {
		Thread t = new Thread(()->{
		
			if(clearShearchResults != null)
				Platform.runLater(clearShearchResults);
//				clearShearchResults.run();
			
	
			if(phrase.isBlank()) return; 
			
			List<Title> results = titleRepository.findByTitlePhrase(phrase);
			
			
			if(showSearchItem != null)
				Platform.runLater(() -> results.forEach(showSearchItem));
//				results.forEach(showSearchItem);	
			
			if(showSearchInfo != null)
				Platform.runLater(() -> showSearchInfo.accept(phrase, results.size()));	
		});
		
		t.start();
//		threads.add(t);
	}
	
	public void newSearchAuthor(String phrase) {
		Thread t = new Thread(()->{
			
			if(clearShearchResults != null)
				Platform.runLater(clearShearchResults);
			
	
			if(phrase.isBlank()) return; 
			
			List<Author> results = authorRepository.findByPhrase(phrase);
			
			if(showSearchItem != null)
				Platform.runLater(() -> results.forEach(showSearchItem));	
			
			if(showSearchInfo != null)
				Platform.runLater(() -> showSearchInfo.accept(phrase, results.size()));
			
		});
		
		t.start();
		//TODO add pen names searching
		
	}

}
