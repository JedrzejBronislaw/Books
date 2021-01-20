package jedrzejbronislaw.ksiegozbior.model.projections;

import java.util.List;

import jedrzejbronislaw.ksiegozbior.model.entities.Book;
import jedrzejbronislaw.ksiegozbior.model.entities.Edition;
import jedrzejbronislaw.ksiegozbior.model.entities.Language;
import jedrzejbronislaw.ksiegozbior.model.entities.Title;
import jedrzejbronislaw.ksiegozbior.model.repositories.Repositories;
import jedrzejbronislaw.ksiegozbior.tools.MyList;
import jedrzejbronislaw.ksiegozbior.tools.Named;
import jedrzejbronislaw.ksiegozbior.tools.StringNumber;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TheLanguage implements TheEnt {
	
	@NonNull
	private Language language;
	
	public String getName() {
		return language.getName();
	}

	public String getAbbrev() {
		return language.getAbbr();
	}

	public StringNumber<Long> getNumberOfBooks() {
		return new StringNumber<Long>(
				Repositories.getLanguageRepository().numberOfBooks(language.getId())
				);
	}

	public StringNumber<Long> getNumberOfEditinos() {
		return new StringNumber<Long>(
				Repositories.getLanguageRepository().numberOfEditions(language.getId())
				);
	}

	public StringNumber<Long> getNumberOfTitles() {
		return new StringNumber<Long>(
				Repositories.getLanguageRepository().numberOfTitles(language.getId())
				);
	}

	public List<Book> getBooks() {
		return Repositories.getLanguageRepository().getBooks(language);
		
	}
	public List<Edition> getEditions() {
		return Repositories.getLanguageRepository().getEditions(language);
	}
	public List<Title> getTitles() {
		return Repositories.getLanguageRepository().getTitles(language);
	}

	public MyList getBooksNames() {
		return new MyList(
			Named.convertListToNamesList(getBooks())
			);
	}
	public MyList getEditionsNames() {
		return new MyList(
			Named.convertListToNamesList(getEditions())
			);
	}
	public MyList getTitlesNames() {
		return new MyList(
			Named.convertListToNamesList(getTitles())
			);
	}

	@Override
	public String getLabel() {
		return getName();
	}

}
