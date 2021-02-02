package jedrzejbronislaw.ksiegozbior.model.projections;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jedrzejbronislaw.ksiegozbior.lang.Internationalization;
import jedrzejbronislaw.ksiegozbior.model.entities.Author;
import jedrzejbronislaw.ksiegozbior.model.entities.Edition;
import jedrzejbronislaw.ksiegozbior.model.entities.Edition_Title;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.entities.Language;
import jedrzejbronislaw.ksiegozbior.model.entities.PublishingHouse;
import jedrzejbronislaw.ksiegozbior.model.entities.Title;
import jedrzejbronislaw.ksiegozbior.tools.ISBN;
import jedrzejbronislaw.ksiegozbior.tools.MyList;
import jedrzejbronislaw.ksiegozbior.tools.NotNullString;
import jedrzejbronislaw.ksiegozbior.tools.RomanNumber;
import jedrzejbronislaw.ksiegozbior.tools.StringNumber;
import lombok.NonNull;
import lombok.Setter;

@Component
@Primary
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TheEdition implements TheEnt {

	public static final String NO_TITLE_NAME = "[" + Internationalization.get("no_title") + "]";
	public static final String DEF_SUBTITLE  = "";
	
	@Setter @NonNull private Edition edition;
	
	private NotNullString notnullTitle = new NotNullString(NO_TITLE_NAME).emptyAsNull(true);
	
	
	public String getTitle(){
		if (is(editionTitle()))    return editionTitle(); else
		if (numberOfTitles()  > 1) return ""; else
		if (numberOfTitles() == 1) return title(getFirstET()); else
			return "";	
	}
	
	public String getTitlesText() {
		
		if (numberOfTitles() > 1) {
			
			List<String> titleList = titles().stream()
				.map(this::title)
				.collect(Collectors.toList());
			
			return String.join(", ", titleList);
			
		} else
		if (is(editionTitle()))    return editionTitle(); else
		if (numberOfTitles() == 1) return title(getFirstET()); else
			                       return notnullTitle.get(null);
	}
	
	private String title(Edition_Title et) {
		String titleInEdition = et.getTitle();
		String orygTitle      = et.getTitleObj().getTitle();
		
		if (is(titleInEdition)) return titleInEdition; else
		if (is(orygTitle))      return orygTitle;      else
			                    return notnullTitle.get(null);
	}

	private Edition_Title getFirstET() {
		return titles().iterator().next();
	}
	
	public Set<Title> getTitles(){
		return titles().stream()
				.map(t -> t.getTitleObj())
				.collect(Collectors.toSet());
	}
	
	public String getSubtitle(){
		if (is(editionTitle()))    return subtitle(edition.getSubtitle()); else
		if (numberOfTitles()  > 1) return DEF_SUBTITLE; else
		if (numberOfTitles() == 1) {
						
			Edition_Title et = getFirstET();
			if (is(et.getTitle())) return subtitle(et.getSubtitle());
			
			Title title = et.getTitleObj();
			return (title != null) ? subtitle(title.getSubtitle()) : DEF_SUBTITLE;
			
		} else
			return DEF_SUBTITLE;
	}

	private String subtitle(String subtitle) {
		return (subtitle != null) ? subtitle : DEF_SUBTITLE;
	}

	private String editionTitle() {
		return edition.getTitle();
	}
	
	public MyList<Author> getAuthors() {
		
		List<Author> authors = titles().stream()
			.map(    t -> t.getTitleObj().getAuthors())
			.filter( a -> a != null)
			.flatMap(a -> a.stream())
			.map(    a -> a.getAuthor())
			.distinct()
			.collect(Collectors.toUnmodifiableList());
		
		return new MyList<Author>(authors);
	}

	private boolean is(String text) {
		return text != null && !text.isBlank();
	}

	private int numberOfTitles() {
		return titles().size();
	}

	private Set<Edition_Title> titles() {
		Set<Edition_Title> titles = edition.getTitles();
		return (titles != null) ? titles : Collections.emptySet();
	}
	
	public StringNumber<Short> getNumer() {
		return new StringNumber<Short>(edition.getNumber()).setZero("");
	}

	public String getNumerRoman() {
		short number = edition.getNumber();
		return (number > 0) ? RomanNumber.toRoman(number) : "";
	}

	public String getLanguageName() {
		Language language = edition.getLanguage();
		return (language != null) ? language.getName() : "";
	}

	public String getPublisherName() {
		PublishingHouse publishingHouse = edition.getPublishingHouse();
		return (publishingHouse != null) ? publishingHouse.getName() : "";
	}

	public StringNumber<Short> getPages() {
		return new StringNumber<Short>(edition.getNumOfPages()).setZero("");
	}

	public boolean isHardCover() {
		return edition.isHardCover();
	}
	public String isHardCoverStr() {
		return isHardCover() ?
				Internationalization.get("yes") :
				Internationalization.get("no");
	}

	public Long getISBN() {
		return edition.getISBN();
	}
	public String getISBNFormatted() {
		Long isbn = edition.getISBN();
		
		if (isbn == null || isbn == 0)
			return ""; else
			return new ISBN(isbn).getFormattedString();
	}

	public StringNumber<Short> getPublicationYear() {
		return new StringNumber<Short>(edition.getYear()).setZero("");
	}

	public String getDescription() {
		return edition.getDescription();
	}

	
	@Override
	public String toString() {
		StringJoiner str = new StringJoiner(", ");
		
		str.add(notnullTitle.get(getTitle()));

		if (is(getPublisherName()))          str.add(getPublisherName());
		if (getPublicationYear().num() != 0) str.add(getPublicationYear().str());
		if (getNumer().num() > 0)            str.add(Internationalization.get("edition") + ": " + getNumerRoman());
		
		return str.toString();
	}

	@Override
	public String getLabel() {
		return toString();
	}

	@Override
	public boolean setEnt(Ent entity) {
		if (!(entity instanceof Edition)) return false;
		
		setEdition((Edition) entity);
		return true;
	}
}
