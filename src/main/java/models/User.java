package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class User extends BaseDomain{
	@NotEmpty
	@Size(min=3)
	private String name;
	@NotEmpty
	@Email
	@Column(name="email",unique=true)
	private String email;
	public String username;
    public String password;
	
    public User() {}
    public User(String username, String password) {
        this.username = username;
        this.password = password;
//        this.fullname = fullname;
    }
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
