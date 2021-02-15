package jedrzejbronislaw.ksiegozbior.model.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Autograph {

	@Id
	private long bookId;
	
	@MapsId
	@OneToOne
	private Book book;
	
	@Lob
	private String content;
	
	
	public Autograph(Book book, String content) {
		this.book = book;
		this.content = content;
	}
}
