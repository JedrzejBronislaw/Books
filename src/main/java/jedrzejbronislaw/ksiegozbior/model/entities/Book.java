package jedrzejbronislaw.ksiegozbior.model.entities;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import jedrzejbronislaw.ksiegozbior.model.entities.collections.BookCollectionLink;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Book implements Ent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	         private Date purchaseDate;
	@NotNull private Visibility visibility = Visibility.Default;
	         private boolean removed;

	@NotNull @ManyToOne private Edition edition;
	         @ManyToOne private Location location;
	@NotNull @ManyToOne private Library library;
	
	@ManyToMany(fetch=FetchType.EAGER)
	private Set<BookComment> comments = new HashSet<>();
	
	@OneToMany(mappedBy="element")
	private Set<BookCollectionLink> collections;
	
	
	@Override
	public String toString() {
		return "B: " + edition.toString();
	}

	@Override
	public String getName() {
		return toString();
	}
}
