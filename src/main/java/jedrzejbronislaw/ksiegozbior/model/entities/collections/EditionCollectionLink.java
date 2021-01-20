package jedrzejbronislaw.ksiegozbior.model.entities.collections;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import jedrzejbronislaw.ksiegozbior.model.entities.Edition;
import lombok.Getter;
import lombok.Setter;

@Entity
@IdClass(CollectionLinkId.class)
public class EditionCollectionLink {

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
	private Edition element;

	@JoinColumn(name="collection_id", insertable=false, updatable=false)
	@ManyToOne
	@Getter @Setter
	private EditionCollection collection;
		
	@Column
	@Getter @Setter
	private short number;

	public EditionCollectionLink(){}
	
	public EditionCollectionLink(EditionCollection collection, Edition edition, short number) {
		setCollectionId(collection.getId());
		setElementId(edition.getId());
		setNumber(number);
	}
}
