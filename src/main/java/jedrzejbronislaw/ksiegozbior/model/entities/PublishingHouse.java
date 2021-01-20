package jedrzejbronislaw.ksiegozbior.model.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
public class PublishingHouse implements Ent{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter @Getter
	private long id;
	
	@Column
	@Setter @Getter
	private String name;
	
	@Column
	@Setter @Getter
	private String city;
	
	@Column
	@Setter @Getter
	private String abbr;
	
	@Column
	@Setter @Getter
	private boolean removed;
	
	
	@OneToMany(mappedBy="publishingHouse")
	@Setter @Getter
	private Set<Edition> editions;
	
	@Override
	public String toString() {
		return abbr;
	}
}
