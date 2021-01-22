package jedrzejbronislaw.ksiegozbior.model.entities;

import java.util.List;
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

import jedrzejbronislaw.ksiegozbior.model.entities.collections.TitleCollectionLink;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Title implements Ent{
	
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

	@OneToMany(mappedBy="title", fetch=FetchType.EAGER)
	@Getter @Setter
	private List<Authorship> authors;// = new HashSet<Author>();
	
	@ManyToOne
	@Getter @Setter
	private Language language;

	@Column
	@Getter @Setter
	private short year;
	
	@Column
	@Lob
	@Getter @Setter
	private String description;
	
	@Column
	@Getter @Setter
	private boolean removed;

	@OneToMany(mappedBy="titleObj")
	@Getter @Setter
	private Set<Edition_Title> editions;
	
	@OneToMany(mappedBy="element", fetch=FetchType.EAGER)
	@Getter @Setter
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
