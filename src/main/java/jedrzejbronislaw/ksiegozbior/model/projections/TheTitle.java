package jedrzejbronislaw.ksiegozbior.model.projections;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jedrzejbronislaw.ksiegozbior.lang.Internationalization;
import jedrzejbronislaw.ksiegozbior.model.entities.Author;
import jedrzejbronislaw.ksiegozbior.model.entities.Ent;
import jedrzejbronislaw.ksiegozbior.model.entities.Language;
import jedrzejbronislaw.ksiegozbior.model.entities.Title;
import jedrzejbronislaw.ksiegozbior.tools.MyList;
import jedrzejbronislaw.ksiegozbior.tools.StringNumber;
import lombok.NonNull;
import lombok.Setter;

@Component
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TheTitle implements TheEnt {
	
	@Setter @NonNull private Title title;
	
	
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
			return title.getLanguage().getName(); else
			return "";
	}
	
	public StringNumber<Short> getCreationYear() {
		return new StringNumber<Short>(title.getYear()).setZero("");
	}
	
	public String getDescription() {
		return title.getDescription();
	}
	
	public MyList<Author> getAuthors() {
		List<Author> authors = title.getAuthors().stream()
				.map(a -> a.getAuthor())
				.collect(Collectors.toUnmodifiableList());
		
		return new MyList<Author>(authors);
	}
	
	
	public MyList<String> getCollections() {
		List<String> collections = title.getCollections().stream()
				.map(c -> c.getCollection().getName())
				.collect(Collectors.toUnmodifiableList());
		
		return new MyList<>(collections);
	}
	
	//TODO	public List<String> getCategory() {};
	//TODO	public List<String> getCategoryText() {};
	
	@Override
	public String getLabel() {
		return (getTitle() != null) ? getTitle() : "[" + Internationalization.get("no_title") + "]";
	}

	@Override
	public boolean setEnt(Ent entity) {
		if (!(entity instanceof Title)) return false;
		
		setTitle((Title) entity);
		return true;
	}
}
