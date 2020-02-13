package artezio.vkolodynsky.model.data;

import artezio.vkolodynsky.model.Session;
import artezio.vkolodynsky.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class SessionData {
    private Integer id;
    private Timestamp createTime;
    private String locale;
    private String userAgent;
    private String ipAddress;
    private Integer userId;

    public static SessionData from(Session session) {
        SessionData sessionData = new SessionData();
        sessionData.id = session.getId();
        sessionData.createTime = session.getCreateTime();
        sessionData.locale = session.getLocale();
        sessionData.userAgent = session.getUserAgent();
        return sessionData;
    }

    public SessionData(Integer id, Integer userId, String locale, String userAgent, String ipAddress) {
        this.id = id;
        this.userId = userId;
        LocalDateTime now = LocalDateTime.now();
        this.createTime = Timestamp.valueOf(now);
        this.locale = locale;
        this.userAgent = userAgent;
        this.ipAddress = ipAddress;
    }
}
