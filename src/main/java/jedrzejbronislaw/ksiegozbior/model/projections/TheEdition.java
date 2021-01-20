package jedrzejbronislaw.ksiegozbior.model.projections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jedrzejbronislaw.ksiegozbior.lang.Internationalization;
import jedrzejbronislaw.ksiegozbior.model.entities.Author;
import jedrzejbronislaw.ksiegozbior.model.entities.Authorship;
import jedrzejbronislaw.ksiegozbior.model.entities.Edition;
import jedrzejbronislaw.ksiegozbior.model.entities.Edition_Title;
import jedrzejbronislaw.ksiegozbior.model.entities.Title;
import jedrzejbronislaw.ksiegozbior.tools.ISBN;
import jedrzejbronislaw.ksiegozbior.tools.MyList;
import jedrzejbronislaw.ksiegozbior.tools.NotNullString;
import jedrzejbronislaw.ksiegozbior.tools.RomanNumber;
import jedrzejbronislaw.ksiegozbior.tools.StringNumber;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TheEdition implements TheEnt {

	public static final String noTitleName = "["+Internationalization.get("no_title")+"]";
	
	@NonNull
	private Edition edition;
	
	private NotNullString notnullTitle = new NotNullString(noTitleName).emptyAsNull(true);
	
	
	public String getTitle(){
		if(edition.getTitle() != null && !edition.getTitle().isBlank())
			return edition.getTitle();
		else if (edition.getTitles() != null && edition.getTitles().size() > 1)
			return "";
		else if (edition.getTitles() != null && edition.getTitles().size() > 0) {
						
			Edition_Title et = edition.getTitles().iterator().next();
			if(et.getTitle() != null && !et.getTitle().isBlank())
				return notnullTitle.get(et.getTitle().strip());
			
			Title title = et.getTitleObj();
			if(title != null && title.getTitle() != null)
				return notnullTitle.get(title.getTitle().strip());
			else 
				return "";
			
		} else {
			return "";	
		}
	}
	
	public String getTitlesText(){
		
//		System.out.println(" --- " + edition.getTitles() + " (" + ((edition.getTitles()==null ? "-" : edition.getTitles().size())) + ")");
		
		if (edition.getTitles() != null && edition.getTitles().size() > 1) {
//			System.out.println("?");
			
			
			StringBuffer titlesSB = new StringBuffer();
			
			for(Edition_Title et : edition.getTitles()) 
				if(et.getTitle() != null && !et.getTitle().isBlank())
					titlesSB.append(notnullTitle.get(et.getTitle()) + ", ");
				else
					titlesSB.append(notnullTitle.get(et.getTitleObj().getTitle()) + ", ");
			
			titlesSB.deleteCharAt(titlesSB.length()-2);
			return titlesSB.toString().strip();
			
			
		} else if (edition.getTitle() != null)
			return edition.getTitle();
		else if (edition.getTitles() != null && edition.getTitles().size() > 0 && edition.getTitles().iterator().next().getTitle() != null)
			return edition.getTitles().iterator().next().getTitle();
		else if (edition.getTitles() != null && edition.getTitles().size() > 0 && edition.getTitles().iterator().next().getTitleObj().getTitle() != null)
			return edition.getTitles().iterator().next().getTitleObj().getTitle();
		else if (edition.getTitles() != null && edition.getTitles().size() == 0)
			return "";
		else
			return notnullTitle.get(null);
		
	}
	
	public Set<Title> getTitles(){
		Set<Title> outcome = new HashSet<Title>();
		
		if (edition.getTitles() != null)
			for(Edition_Title et : edition.getTitles()) 
				outcome.add(et.getTitleObj());
		
		return Collections.unmodifiableSet(outcome);
	}
	
	public String getSubtitle(){
		if(edition.getTitle() != null && !edition.getTitle().isBlank()) {
			if(edition.getSubtitle() != null)
				return edition.getSubtitle();
			else
				return "";
		}
		
		else if (edition.getTitles() != null && edition.getTitles().size() > 1)
			return "";
		else if (edition.getTitles() != null && edition.getTitles().size() == 1) {
						
			Edition_Title et = edition.getTitles().iterator().next();
			if(et.getTitle() != null && !et.getTitle().isBlank()) {
				if (et.getSubtitle() != null)
					return et.getSubtitle().strip();
				else
					return "";
			}
			
			Title title = et.getTitleObj();
			if(title != null && title.getSubtitle() != null)
				return title.getSubtitle().strip();
			else 
				return "";
			
		} else {
			return "";	
		}

	}
	
	public MyList<Author> getAuthors(){
		Set<Author> authorsSet = new HashSet<Author>();
		List<Author> authorsList = new ArrayList<Author>();
		
		if(edition.getTitles() != null && edition.getTitles().size() > 0)
			for(Edition_Title et : edition.getTitles()) {
				List<Authorship> authorships = et.getTitleObj().getAuthors();
				if (authorships != null && authorships.size() > 0) 
					for(Authorship as : authorships)
						authorsSet.add(as.getAuthor());
			}
		
		authorsSet.forEach(a -> authorsList.add(a));
		
		return new MyList<Author>(authorsList);
	}
	
//	public MyList getAuthorsStrings() {
//		Set<Author> authors = getAuthors();
//		List<String> authorsStrings = new ArrayList<String>();
//		
//		authors.forEach(a -> authorsStrings.add(a.toString()));
//		
//		return new MyList(authorsStrings);
//	}
	
	public StringNumber<Short> getNumer() {
		return new StringNumber<Short>(edition.getNumber()).setZero("");
	}

	public String getNumerRoman() {
		if (edition.getNumber() > 0)
			return RomanNumber.toRoman(edition.getNumber());
		else
			return "";
	}

	public String getLanguageName() {
		if(edition.getLanguage() != null)
			return edition.getLanguage().getName();
		else
			return "";
	}

	public String getPublisherName() {
		if(edition.getPublishingHouse() != null)
			return edition.getPublishingHouse().getName();
		else
			return "";
	}

	public StringNumber<Short> getPages() {
		return new StringNumber<Short>(edition.getNumOfPages()).setZero("");
	}

	public boolean getHardCover() {
		return edition.isHardCover();
	}
	public String getHardCoverStr()
	{
		return getHardCover() ? 
				Internationalization.get("yes") : 
				Internationalization.get("no");
	}

	public Long getISBN() {
		return edition.getISBN();
	}
	public String getISBNFormatted() {
		if (edition.getISBN() == null || edition.getISBN() == 0) 
			return "";

		return new ISBN(edition.getISBN()).getFormattedString();	
	}

	public StringNumber<Short> getPublicationYear() {
		return new StringNumber<Short>(edition.getYear()).setZero("");
	}

	public String getDescription() {
		return edition.getDescription();
	}

	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append(notnullTitle.get(getTitle()));
		
		if (!getPublisherName().isBlank()) {
			sb.append(", ");
			sb.append(getPublisherName());
		}
		
		if (getPublicationYear().num() != 0) {
			sb.append(", ");
			sb.append(getPublicationYear().str());
		}
		
		if (getNumer().num() > 0) {
			sb.append(", ");
			sb.append(Internationalization.get("edition"));
			sb.append(": " + getNumerRoman());
		}
		
		return sb.toString();
	}

	@Override
	public String getLabel() {
		return toString();
	}
}
