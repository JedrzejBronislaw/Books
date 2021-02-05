package jedrzejbronislaw.ksiegozbior.search;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javafx.application.Platform;
import jedrzejbronislaw.ksiegozbior.model.entities.Edition;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.entities.Title;
import jedrzejbronislaw.ksiegozbior.model.repositories.AuthorRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.BookRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.EditionRepository;
import jedrzejbronislaw.ksiegozbior.model.repositories.TitleRepository;
import jedrzejbronislaw.ksiegozbior.tools.Injection;
import lombok.Setter;

@Component
@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Searcher {
	
	@Autowired private BookRepository bookRepository;
	@Autowired private EditionRepository editionRepository;
	@Autowired private TitleRepository titleRepository;
	@Autowired private AuthorRepository authorRepository;
	
	@Setter private Runnable clearShearchResults;
	@Setter private Consumer<Ent> showSearchItem;
	@Setter private BiConsumer<String, Integer> showSearchInfo;
	
	
	public void newSearchPhrase(String phrase) {
		newSearch(phrase, bookRepository::findByTitlePhrase);
	}
	
	public void newSearchEditionOrTitle(String phrase) {

		newSearch(phrase, ph -> {
			List<Edition> resultsE = editionRepository.findByTitlePhrase(ph);
			List<Title>   resultsT = titleRepository  .findByTitlePhrase(ph);

			LinkedList<Ent> list = new LinkedList<>(resultsE);
			list.addAll(resultsT);
			
			return list;
		});
	}
	
	public void newSearchEdition(String phrase) {
		newSearch(phrase, editionRepository::findByTitlePhrase);
	}
	
	public void newSearchTitle(String phrase) {
		newSearch(phrase, titleRepository::findByTitlePhrase);
	}
	
	public void newSearchAuthor(String phrase) {
		newSearch(phrase, authorRepository::findByPhrase);
	}
	
	
	private void newSearch(String phrase, Function<String, List<? extends Ent>> search) {
		new Thread(()->{
			
			Platform.runLater(() -> Injection.run(clearShearchResults));
			if(phrase.isBlank()) return;
			
			List<? extends Ent> results = Injection.get(search, phrase, new ArrayList<>());

			if(showSearchItem != null) Platform.runLater(() -> results.forEach(showSearchItem));
			Platform.runLater(() -> Injection.run(showSearchInfo, phrase, results.size()));
			
		}).start();
	}
}
