package artezio.vkolodynsky.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the app database table.
 * 
 */
@Entity
@Data
@NoArgsConstructor
@NamedQuery(name="App.findAll", query="SELECT a FROM App a")
public class App implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String name;
	private String url;

	//bi-directional many-to-one association to Role
	@OneToMany(mappedBy="app")
	private List<Role> roles;
}