package jedrzejbronislaw.ksiegozbior.model.entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
public class User implements Ent {

	public enum Role {User, Admin};
	public enum Mode {Ok, Blocked};
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@NotNull private String login;
	private byte[] password;
	private String firstName;
	private String lastName;
	private String email;
	@CreatedDate
	private Timestamp registrationTime;
	@NotNull private Mode mode = Mode.Ok;
	@NotNull private Role role = Role.User;
	
	@ManyToMany private List<Library> libraries;
	
	
	@Override
	public String getName() {
		
		if (is(firstName) && is(lastName)) return firstName + " " + lastName; else
		if (is(firstName))                 return firstName; else
		                                   return login;
	}
	
	private boolean is(String text) {
		return text != null && !text.isBlank();
	}
}
