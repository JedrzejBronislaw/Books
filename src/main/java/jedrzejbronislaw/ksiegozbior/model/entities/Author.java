package jedrzejbronislaw.ksiegozbior.model.entities;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Author implements Ent{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private String surname;
	private Date birthDate;
	private Date deathDate;
//	private nationality;
	@Lob
	private String description;
	private boolean removed;

	@OneToMany(mappedBy="author")
	private Set<Authorship> titles;
	

	@Override
	public String toString() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
		StringBuilder sb = new StringBuilder();
		
		if (surname != null && !surname.trim().isEmpty())
			sb.append(surname + " ");
		
		if (name != null && !name.trim().isEmpty())
			sb.append(name + " ");
		
		if (birthDate != null || deathDate != null) {
			sb.append("(");
			if (birthDate != null)
				sb.append(formatter.format(birthDate));
			sb.append(" - ");
			if (deathDate != null)
				sb.append(formatter.format(deathDate));
			sb.append(")");
		}
		
		return sb.toString().trim();
	}
}
