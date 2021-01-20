package jedrzejbronislaw.ksiegozbior.model.entities;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import jedrzejbronislaw.ksiegozbior.model.entities.collections.BookCollectionLink;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Book implements Ent{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter
	private long id;

	@ManyToOne
	@Getter @Setter
	private Edition edition;
	
	@ManyToOne
	@Getter @Setter
	private Location location;
	
	@Column
	@Getter @Setter
	private Date purchaseDate;
	
	@ManyToOne
	@Getter @Setter
	private Library library;
	
	@Column
	@Getter @Setter
	private byte visibility;
	
	@Column
	@Getter @Setter
	private boolean removed;
	
	
	@ManyToMany(fetch=FetchType.EAGER)
	@Getter @Setter
	private Set<BookComment> comments = new HashSet<BookComment>();
	
	@OneToMany(mappedBy="element")
	@Getter @Setter
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
