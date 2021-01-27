package jedrzejbronislaw.ksiegozbior.model.entities;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Edition_TitleId implements Serializable {

	private Long editionId;
	private Long titleId;

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof Edition_TitleId)) return false;
		
		Edition_TitleId object = (Edition_TitleId) o;
		return (this.editionId.equals(object.editionId) &&
				this.titleId.equals(object.titleId));	
	}

	@Override
	public int hashCode() {
		return Objects.hash(editionId, titleId);
	}
}
