package jedrzejbronislaw.ksiegozbior.model.entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Language implements Ent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull private String name;
	@NotNull private String abbr;

	@OneToMany(mappedBy="language")
	private Set<Title> titles;

	@OneToMany(mappedBy="language")
	private Set<Edition> editions;
	
	
	@Override
	public String toString() {
		return abbr;
	}
}
