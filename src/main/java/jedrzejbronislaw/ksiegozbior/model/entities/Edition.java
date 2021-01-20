package jedrzejbronislaw.ksiegozbior.model.entities;

import java.util.Set;

import javax.persistence.Column;
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
public class Edition implements Ent{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter
	private long id;

	@Column
	@Getter @Setter
	private String title;

	@Column
	@Getter @Setter
	private String subtitle;

	@ManyToOne
	@Getter @Setter
	private Language language;

	@ManyToOne
	@Getter @Setter
	private PublishingHouse publishingHouse;

	@Column
	@Getter @Setter
	private short year;

	@Column
	@Getter @Setter
	private short numOfPages;

	@Column
	@Getter @Setter
	private Long ISBN;
	
	@Column
	@Getter @Setter
	private short number;

	@Column
	@Getter @Setter
	private boolean hardCover;

	@Column
	@Lob
	@Getter @Setter
	private String description;

	@Column
	@Getter @Setter
	private boolean removed;
	
	
	@OneToMany(mappedBy="element")
	@Getter @Setter
	private Set<EditionCollectionLink> collections;

	@OneToMany(mappedBy="edition", fetch=FetchType.EAGER)
	@Getter @Setter
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
