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
public class Language implements Ent{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter
	private long id;

	@Column
	@Getter @Setter
	private String name;
	
	@Column
	@Getter @Setter
	private String abbr;
	


	@OneToMany(mappedBy="language")
	@Getter @Setter
	private Set<Title> titles;

	@OneToMany(mappedBy="language")
	@Getter @Setter
	private Set<Edition> editions;
	
	@Override
	public String toString() {
		return abbr;
	}
}
