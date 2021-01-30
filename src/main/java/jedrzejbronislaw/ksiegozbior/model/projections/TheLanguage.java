package jedrzejbronislaw.ksiegozbior.model.projections;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jedrzejbronislaw.ksiegozbior.model.entities.Book;
import jedrzejbronislaw.ksiegozbior.model.entities.Edition;
import jedrzejbronislaw.ksiegozbior.model.entities.Language;
import jedrzejbronislaw.ksiegozbior.model.entities.Title;
import jedrzejbronislaw.ksiegozbior.model.repositories.LanguageRepository;
import jedrzejbronislaw.ksiegozbior.tools.MyList;
import jedrzejbronislaw.ksiegozbior.tools.Named;
import jedrzejbronislaw.ksiegozbior.tools.StringNumber;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Component
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@NoArgsConstructor
@RequiredArgsConstructor
public class TheLanguage implements TheEnt {
	
	@Autowired LanguageRepository languageRepository;
	
	@Setter @NonNull private Language language;
	
	
	public String getName() {
		return language.getName();
	}

	public String getAbbrev() {
		return language.getAbbr();
	}

	public StringNumber<Long> getNumberOfBooks() {
		return new StringNumber<Long>(
				languageRepository.numberOfBooks(language.getId())
				);
	}

	public StringNumber<Long> getNumberOfEditinos() {
		return new StringNumber<Long>(
				languageRepository.numberOfEditions(language.getId())
				);
	}

	public StringNumber<Long> getNumberOfTitles() {
		return new StringNumber<Long>(
				languageRepository.numberOfTitles(language.getId())
				);
	}

	public List<Book> getBooks() {
		return languageRepository.getBooks(language);
	}
	public List<Edition> getEditions() {
		return languageRepository.getEditions(language);
	}
	public List<Title> getTitles() {
		return languageRepository.getTitles(language);
	}

	public MyList<String> getBooksNames() {
		return new MyList<>(
			Named.convertListToNamesList(getBooks())
			);
	}
	public MyList<String> getEditionsNames() {
		return new MyList<>(
			Named.convertListToNamesList(getEditions())
			);
	}
	public MyList<String> getTitlesNames() {
		return new MyList<>(
			Named.convertListToNamesList(getTitles())
			);
	}

	@Override
	public String getLabel() {
		return getName();
	}
}
