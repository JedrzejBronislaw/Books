package jedrzejbronislaw.ksiegozbior.model.entities;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
public class User implements Ent{

	public enum Role {User, Admin};
	public enum Mode {Ok, Blocked};
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter @Setter
	private long id;
	
	@Column
	@Getter @Setter
	private String login;

	@Column
	@Getter @Setter
	private byte[] password;

	@Column
	@Getter @Setter
	private String firstName;

	@Column
	@Getter @Setter
	private String lastName;

	@Column
	@Getter @Setter
	private String email;

	@Column
	@Getter @Setter
	private Timestamp registrationTime;

	@Column
	@Getter @Setter
	private Mode mode;

	@Column
	@Getter @Setter
	private Role role;
	
	
	@ManyToMany//(fetch=FetchType.EAGER)
	@Getter @Setter
	private List<Library> libraries;
	
	
	@Override
	public String getName() {
		
		if(firstName != null && !firstName.isBlank() && lastName != null && !lastName.isBlank())
			return firstName + " " + lastName;
		else if(firstName != null && !firstName.isBlank())
			return firstName;
		else 
			return login;
		
	}


}
