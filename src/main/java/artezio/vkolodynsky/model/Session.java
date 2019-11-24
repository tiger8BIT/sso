package artezio.vkolodynsky.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the session database table.
 * 
 */
@Entity
@Data
@NoArgsConstructor
@NamedQuery(name="Session.findAll", query="SELECT s FROM Session s")
public class Session implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Column(name="create_time")
	private Timestamp createTime;

	private String jwt;

	//bi-directional many-to-one association to User
	@ManyToOne
	private User user;
}