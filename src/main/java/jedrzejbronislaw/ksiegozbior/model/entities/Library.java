package jedrzejbronislaw.ksiegozbior.model.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Library implements Ent {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String name;
	private byte bookVisibility;

	@ManyToMany(mappedBy = "libraries")
	private List<User> users;
	
	
	@Override
	public String toString() {
		return name;
	}
}
