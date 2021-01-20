package jedrzejbronislaw.ksiegozbior.model.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Location implements HierarhicalEnt{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter
	private long id;

	@Column
	@Getter @Setter
	private String name;

	@Column
	@Getter @Setter
	private String description;
	
	@ManyToOne
	@Getter @Setter
	private Location superLocation;
	
	@Column
	@Getter @Setter
	private boolean removed;
	
	@OneToMany(mappedBy="superLocation")
	@Getter @Setter
	private Set<Location> subLocations;
	
	@OneToMany(mappedBy="location")
	@Getter @Setter
	private Set<Book> books;
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public HierarhicalEnt getSuper() {
		return getSuperLocation();
	}
}
