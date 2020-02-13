package artezio.vkolodynsky.model;

import artezio.vkolodynsky.model.data.AppData;
import lombok.*;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the app database table.
 * 
 */
@Entity
@Data
@NoArgsConstructor
public class App implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(name="name", unique = true)
	private String name;
	@Column(name="url", unique = true)
	private String url;

	@OneToMany(mappedBy="app")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private List<Role> roles;

	public static App from(AppData data){
		App app = new App();
		app.setData(data);
		return app;
	}

	public void setData(AppData data) {
		id = data.getId();
		name = data.getName();
		url = data.getUrl();
	}
}