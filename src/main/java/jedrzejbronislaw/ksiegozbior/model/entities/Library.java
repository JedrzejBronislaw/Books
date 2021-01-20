package jedrzejbronislaw.ksiegozbior.model.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Library implements Ent{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter @Setter
	private long id;
	
	@Column
	@Getter @Setter
	private String name;

	@Column
	@Getter @Setter
	private byte bookVisibility;

	@ManyToMany(mappedBy = "libraries")
	@Getter @Setter
	private List<User> users;
}
