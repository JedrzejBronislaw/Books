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
@IdClass(Edition_TitleId.class)
@NoArgsConstructor
@Getter @Setter
public class Edition_Title {
	
	@Id @Column(name = "edition_id") private Long editionId;
	@Id @Column(name = "title_id")   private Long titleId;
	
	@ManyToOne
	@JoinColumn(name = "edition_id", insertable = false, updatable = false)
	private Edition edition;

	@ManyToOne
	@JoinColumn(name = "title_id", insertable = false, updatable = false)
	private Title titleObj;
	
	private String title;
	@Lob
	private String subtitle;

	
	public Edition_Title(Edition edition, Title title) {
		setEditionId(edition.getId());
		setTitleId(title.getId());
	}

	public Edition_Title(Edition edition, Title title, String titleStr, String subtitleStr) {
		setEditionId(edition.getId());
		setTitleId(title.getId());
		setTitle(titleStr);
		setSubtitle(subtitleStr);
	}
}
