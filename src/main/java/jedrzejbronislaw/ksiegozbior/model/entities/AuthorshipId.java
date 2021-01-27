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
public class AuthorshipId implements Serializable {

	private Long authorId;
	private Long titleId;

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof AuthorshipId)) return false;
		
		AuthorshipId object = (AuthorshipId) o;
		return (this.authorId.equals(object.authorId) &&
				this.titleId.equals(object.titleId));	
	}

	@Override
	public int hashCode() {
		return Objects.hash(authorId, titleId);
	}
}
