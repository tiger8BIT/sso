package artezio.vkolodynsky.model;

import artezio.vkolodynsky.model.data.UserData;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Data
@NoArgsConstructor
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String info;

	private String login;

	private String password;

	@OneToMany(mappedBy="user")
	private List<Session> sessions;

	@ManyToMany
	@JoinTable(
			name = "role_table",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> userRoles = new ArrayList<>();

	public User(UserData userData) {
		info = userData.getInfo();
		login = userData.getLogin();
		if(userData.getPassword() != null) {
			password = userData.getPassword();
		}
	}
	public void setData(UserData userData) {
		info = userData.getInfo();
		login = userData.getLogin();
		if(userData.getPassword() != null) {
			password = userData.getPassword();
		}
	}
}