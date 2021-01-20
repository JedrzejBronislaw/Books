package jedrzejbronislaw.ksiegozbior.model.entities.collections;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import jedrzejbronislaw.ksiegozbior.model.entities.Title;
import lombok.Getter;
import lombok.Setter;

@Entity
@IdClass(CollectionLinkId.class)
public class TitleCollectionLink {

	@Id
	@Column(name="collection_id")
	@Getter @Setter
	private Long collectionId;
	
	@Id
	@Column(name="element_Id")
	@Getter @Setter
	private Long elementId;
	
	@JoinColumn(name="element_id", insertable=false, updatable=false)
	@ManyToOne
	@Getter @Setter
	private Title element;

	@JoinColumn(name="collection_id", insertable=false, updatable=false)
	@ManyToOne(fetch=FetchType.EAGER)
	@Getter @Setter
	private TitleCollection collection;
	
	@Column
	@Getter @Setter
	private short number;

	public TitleCollectionLink(){}
	
	public TitleCollectionLink(TitleCollection collection, Title title, short number) {
		setCollectionId(collection.getId());
		setElementId(title.getId());
		setNumber(number);
	}
}
