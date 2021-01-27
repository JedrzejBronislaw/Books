package jedrzejbronislaw.ksiegozbior.model.entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import jedrzejbronislaw.ksiegozbior.model.entities.collections.EditionCollectionLink;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Edition implements Ent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String title;
	private String subtitle;
	private short year;
	private short numOfPages;
	private Long ISBN;
	private short number;
	private boolean hardCover;
	@Lob
	private String description;
	private boolean removed;
	
	@ManyToOne private Language language;
	@ManyToOne private PublishingHouse publishingHouse;
	
	@OneToMany(mappedBy="element")
	private Set<EditionCollectionLink> collections;

	@OneToMany(mappedBy="edition", fetch=FetchType.EAGER)
	private Set<Edition_Title> titles;
	
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append(title);
		sb.append(", ");
		sb.append(publishingHouse);
		sb.append(", ");
		sb.append("Wydanie: " + number); //TODO internationalization
		
		return sb.toString();
	}

	@Override
	public String getName() {
		return toString();
	}
}
