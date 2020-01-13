package artezio.vkolodynsky.model;

import artezio.vkolodynsky.model.data.RoleData;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the role database table.
 * 
 */
@Entity
@Data
@NoArgsConstructor
@NamedQuery(name="Role.findAll", query="SELECT r FROM Role r")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String description;

	@Column(name="role_name")
	private String roleName;

	//bi-directional many-to-one association to App
	@ManyToOne
	private App app;

	//bi-directional many-to-one association to RoleTable
	@ManyToMany(mappedBy="userRoles")
	private List<User> users;

	public Role (RoleData roleData, App app) {
		description = roleData.getDescription();
		roleName = roleData.getRoleName();
		this.app = app;
	}
	public void setData (RoleData roleData, App app) {
		description = roleData.getDescription();
		roleName = roleData.getRoleName();
		this.app = app;
	}
}