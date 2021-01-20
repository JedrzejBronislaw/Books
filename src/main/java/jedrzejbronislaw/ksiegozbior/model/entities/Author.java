package jedrzejbronislaw.ksiegozbior.model.entities;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Author implements Ent{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter
	private long id;
	
	@Column
	@Getter @Setter
	private String name;
	
	@Column
	@Getter @Setter
	private String surname;
	
	@Column
	@Getter @Setter
	private Date birthDate;
	
	@Column
	@Getter @Setter
	private Date deathDate;
	
//	private narodowosc;
	
	@Column
	@Lob
	@Getter @Setter
	private String description;
	
	@Column
	@Getter @Setter
	private boolean removed;

	
	@OneToMany(mappedBy="author")
	@Getter @Setter
	private Set<Authorship> titles;// HashSet<Title>();
	

	@Override
	public String toString() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
		StringBuilder sb = new StringBuilder("L ");
		
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
