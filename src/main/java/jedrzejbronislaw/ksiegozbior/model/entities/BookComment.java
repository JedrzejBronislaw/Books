package jedrzejbronislaw.ksiegozbior.model.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
public class BookComment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter
	private long id;

	public BookComment() {}
	
	public BookComment(String text) {
		setContent(text.strip());
	}


	@Column
	@Lob
	@Getter @Setter
	private String content;
	
	
	@ManyToMany(mappedBy="comments")
	@Getter @Setter
	private Set<Book> books;
}
