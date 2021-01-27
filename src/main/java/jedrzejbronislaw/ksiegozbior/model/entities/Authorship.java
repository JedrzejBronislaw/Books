package jedrzejbronislaw.ksiegozbior.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@IdClass(AuthorshipId.class)
@NoArgsConstructor
@Getter @Setter
public class Authorship {
	
	@Id @Column(name = "author_id") private Long authorId;
	@Id @Column(name = "title_id")  private Long titleId;
	
	@ManyToOne
	@JoinColumn(name = "author_id", insertable = false, updatable = false)
	private Author author;

	@ManyToOne
	@JoinColumn(name = "title_id", insertable = false, updatable = false)
	private Title title;
	
	@Lob
	private String description;
	private short number;


	public Authorship(Author author, Title title) {
		setAuthorId(author.getId());
		setTitleId(title.getId());
	}
}
