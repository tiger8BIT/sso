package artezio.vkolodynsky.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


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

	//bi-directional many-to-one association to Session
	@OneToMany(mappedBy="user")
	private List<Session> sessions;

	//bi-directional many-to-one association to RoleTable
	@ManyToMany
	@JoinTable(
			name = "role_table",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> userRoles;
}