package jedrzejbronislaw.ksiegozbior.model.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Library implements Ent {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@NotNull private String name;
	@NotNull private Visibility bookVisibility = Visibility.Default;

	@ManyToMany(mappedBy = "libraries")
	private List<User> users;
	
	
	@Override
	public String toString() {
		return name;
	}
}
