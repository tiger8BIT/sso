package artezio.vkolodynsky.model;

import artezio.vkolodynsky.model.data.RoleData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {"role_name" , "app_id"})})
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

    @EqualsAndHashCode.Exclude
	private String description;

	@Column(name="role_name")
	private String roleName;

	@ManyToOne
	private App app;

	@ManyToMany(mappedBy="userRoles")
    @EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<User> users;
}