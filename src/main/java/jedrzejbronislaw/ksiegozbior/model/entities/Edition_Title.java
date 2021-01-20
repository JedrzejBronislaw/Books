package jedrzejbronislaw.ksiegozbior.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@IdClass(Edition_TitleId.class)
//@NoArgsConstructor
//@RequiredArgsConstructor
public class Edition_Title {
	
	@Id
	@Column(name = "edition_id")
	@Getter @Setter
	private Long editionId;


	@Id
	@Column(name = "title_id")
	@Getter @Setter
	private Long titleId;
	
	@ManyToOne
	@JoinColumn(name = "edition_id", insertable = false, updatable = false)
	@Getter @Setter
	private Edition edition;

	@ManyToOne
	@JoinColumn(name = "title_id", insertable = false, updatable = false)
	@Getter @Setter
	private Title titleObj;
	
	@Column
	@Getter @Setter
	private String title;
	
	@Column
	@Lob
	@Getter @Setter
	private String subtitle;

	public Edition_Title(){}
	
	public Edition_Title(Edition edition_, Title title_) {
		setEditionId(edition_.getId());
		setTitleId(title_.getId());
	}

	public Edition_Title(Edition edition_, Title title_, String titleStr, String subtitleStr) {
		setEditionId(edition_.getId());
		setTitleId(title_.getId());
		setTitle(titleStr);
		setSubtitle(subtitleStr);
	}
}
