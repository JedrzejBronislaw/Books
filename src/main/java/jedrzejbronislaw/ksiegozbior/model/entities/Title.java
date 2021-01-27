package jedrzejbronislaw.ksiegozbior.model.entities;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import jedrzejbronislaw.ksiegozbior.model.entities.collections.TitleCollectionLink;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Title implements Ent {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String title;
	private String subtitle;
	private short year;
	@Lob
	private String description;
	private boolean removed;

	@ManyToOne private Language language;
	
	@OneToMany(mappedBy="title", fetch=FetchType.EAGER)
	private List<Authorship> authors;
	
	@OneToMany(mappedBy="titleObj")
	private Set<Edition_Title> editions;
	
	@OneToMany(mappedBy="element", fetch=FetchType.EAGER)
	private Set<TitleCollectionLink> collections;

	
	@Override
	public String toString() {
		return title;
	}

	@Override
	public String getName() {
		return title;
	}
}
