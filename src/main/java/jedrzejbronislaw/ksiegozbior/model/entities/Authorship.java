package jedrzejbronislaw.ksiegozbior.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@IdClass(AuthorshipId.class)
//@NoArgsConstructor
//@RequiredArgsConstructor
public class Authorship {
	
	@Id
	@Column(name = "author_id")
	@Getter @Setter
	private Long authorId;


	@Id
	@Column(name = "title_id")
	@Getter @Setter
	private Long titleId;
	
	@ManyToOne//(fetch=FetchType.EAGER)
	@JoinColumn(name = "author_id", insertable = false, updatable = false)
//	@NonNull
	@Getter @Setter
	private Author author;

//	@NonNull
	@JoinColumn(name = "title_id", insertable = false, updatable = false)
	@ManyToOne//(fetch=FetchType.EAGER)
	@Getter @Setter
	private Title title;
	
	@Column
	@Getter @Setter
	private short number;
	
	@Column
	@Lob
	@Getter @Setter
	private String description;

	public Authorship() {}
	
	public Authorship(Author author_, Title title_) {
		setAuthorId(author_.getId());
		setTitleId(title_.getId());
	}
}
