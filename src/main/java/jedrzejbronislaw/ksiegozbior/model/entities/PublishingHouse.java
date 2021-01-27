package jedrzejbronislaw.ksiegozbior.model.entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter @Getter
public class PublishingHouse implements Ent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name;
	private String city;
	private String abbr;
	private boolean removed;
	
	@OneToMany(mappedBy="publishingHouse")
	private Set<Edition> editions;
	
	
	@Override
	public String toString() {
		return abbr;
	}
}
