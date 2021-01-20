package jedrzejbronislaw.ksiegozbior.model.entities.collections;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@NoArgsConstructor
@AllArgsConstructor
public class CollectionLinkId implements Serializable{

	@Getter @Setter
	private Long elementId;

	@Getter @Setter
	private Long collectionId;
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof CollectionLinkId)) return false;
		
		CollectionLinkId object = (CollectionLinkId) o;
		return (this.elementId.equals(object.elementId) &&
				this.collectionId.equals(object.collectionId));
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(elementId, collectionId);
	}
}