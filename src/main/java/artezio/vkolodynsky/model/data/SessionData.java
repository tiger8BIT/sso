package artezio.vkolodynsky.model.data;

import artezio.vkolodynsky.model.Session;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class SessionData {
    private Integer id;
    private Timestamp createTime;
    private String jwt;
    public SessionData(Session session) {
        id = session.getId();
        createTime = session.getCreateTime();
        jwt = session.getJwt();
    }
}
