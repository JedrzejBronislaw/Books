package jedrzejbronislaw.ksiegozbior.model.entities.collections;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import jedrzejbronislaw.ksiegozbior.model.entities.HierarhicalEnt;
import lombok.Getter;
import lombok.Setter;

@Entity
public class TitleCollection implements HierarhicalEnt{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter
	private long id;
	
	@Column
	@Getter @Setter
	private String name;
	
	@ManyToOne
	@Getter @Setter
	private TitleCollection superCollection;
	
	
	@OneToMany(mappedBy="superCollection", fetch=FetchType.EAGER)
	@Getter @Setter
	public Set<TitleCollection> subCollections;
	
	@OneToMany(mappedBy="collection")
	@Getter @Setter
	public Set<TitleCollectionLink> elements;

	public TitleCollection() {}
	
	public TitleCollection(String name) {
		setName(name);
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public HierarhicalEnt getSuper() {
		return getSuperCollection();
	}
}
