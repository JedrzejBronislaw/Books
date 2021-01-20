package jedrzejbronislaw.ksiegozbior.model.projections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jedrzejbronislaw.ksiegozbior.lang.Internationalization;
import jedrzejbronislaw.ksiegozbior.model.entities.Author;
import jedrzejbronislaw.ksiegozbior.model.entities.Authorship;
import jedrzejbronislaw.ksiegozbior.model.entities.Language;
import jedrzejbronislaw.ksiegozbior.model.entities.Title;
import jedrzejbronislaw.ksiegozbior.model.entities.collections.TitleCollectionLink;
import jedrzejbronislaw.ksiegozbior.tools.MyList;
import jedrzejbronislaw.ksiegozbior.tools.StringNumber;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TheTitle implements TheEnt{
	
	@NonNull
	private Title title;
	
	public String getTitle() {
		return title.getTitle();
	}
	
	public String getSubtitle() {
		return title.getSubtitle();
	}
	
	public Language getLanguage() {
		return title.getLanguage();
	}
	
	public String getLanguageName() {
		if (title.getLanguage() != null)
			return title.getLanguage().getName();
		else
			return "";
	}
	
	public StringNumber<Short> getCreationYear() {
		return new StringNumber<Short>(title.getYear()).setZero("");
	}
	
//	public String getCreationYearString() {
//		return Short.toString(title.getYear());
//	}
	
	public String getDescription() {
		return title.getDescription();
	}
	
	
	public MyList<Author> getAuthors() {
		List<Author> authors = new ArrayList<Author>();
		List<Authorship> authorships = title.getAuthors();
		
		for(Authorship a : authorships)
			authors.add(a.getAuthor());
		
		return new MyList<Author>(authors);
	}
	
	
	public MyList getCollections() {
		List<String> collections = new ArrayList<String>();
		Set<TitleCollectionLink> links = title.getCollections();
		
		for(TitleCollectionLink tcl : links)
			collections.add(tcl.getCollection().getName());
		
		return new MyList(collections);
	}
	
	
//TODO	public List<String> getCategory() {};
//TODO	public List<String> getCategoryText() {};	
	
	@Override
	public String getLabel() {
		return (getTitle() != null) ? getTitle() : "[" + Internationalization.get("no_title") + "]";
	}

}
