package artezio.vkolodynsky.model;

import artezio.vkolodynsky.model.data.SessionData;
import lombok.*;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * The persistent class for the session database table.
 * 
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Session implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	private String locale;

	private String userAgent;

	@Column(name="create_time")
	private Timestamp createTime;

	@ManyToOne
	private User user;

	@OneToMany(mappedBy="session")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Set<RemoteAddress> remoteAddresses = new HashSet<>();

	public static Session from(SessionData data){
		Session session = new Session();
		session.setData(data);
		return session;
	}
	public void setData(SessionData data){
		createTime = data.getCreateTime();
		id = data.getId();
		locale = data.getLocale();
		userAgent = data.getUserAgent();
	}
}