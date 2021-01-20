package jedrzejbronislaw.ksiegozbior.model.projections;

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

public interface TheEnt {

	public String getLabel();
	
	public static TheEnt generate(Ent entity) {
		if(entity instanceof Author)
			return new TheAuthor((Author) entity);
		if(entity instanceof Book)
			return new TheBook((Book) entity);
		if(entity instanceof Title)
			return new TheTitle((Title) entity);
		if(entity instanceof Edition)
			return new TheEdition((Edition) entity);
		

		if(entity instanceof BookCollection)
			return new TheBookCollection((BookCollection) entity);
		if(entity instanceof EditionCollection)
			return new TheEditionCollection((EditionCollection) entity);
		if(entity instanceof TitleCollection)
			return new TheTitleCollection((TitleCollection) entity);
		
		if(entity instanceof Language)
			return new TheLanguage((Language) entity);
		if(entity instanceof Location)
			return new TheLocation((Location) entity);
		if(entity instanceof PublishingHouse)
			return new ThePublishingHouse((PublishingHouse) entity);
		
		//TODO ect.
		
		return null;
	}

	public static String generateLabel(Ent e) {
		return generate(e).getLabel();
	}
}
