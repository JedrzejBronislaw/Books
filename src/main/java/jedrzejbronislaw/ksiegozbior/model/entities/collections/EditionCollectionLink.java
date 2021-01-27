package jedrzejbronislaw.ksiegozbior.model.entities.collections;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import jedrzejbronislaw.ksiegozbior.model.entities.Edition;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@IdClass(CollectionLinkId.class)
@Getter @Setter
@NoArgsConstructor
public class EditionCollectionLink {

	@Id
	@Column(name="collection_id")
	private Long collectionId;
	
	@Id
	@Column(name="element_Id")
	private Long elementId;
	
	@JoinColumn(name="element_id", insertable=false, updatable=false)
	@ManyToOne
	private Edition element;

	@JoinColumn(name="collection_id", insertable=false, updatable=false)
	@ManyToOne
	private EditionCollection collection;
		
	@Column
	private short number;

	
	public EditionCollectionLink(EditionCollection collection, Edition edition, short number) {
		setCollectionId(collection.getId());
		setElementId(edition.getId());
		setNumber(number);
	}
}
