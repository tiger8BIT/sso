package artezio.vkolodynsky.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode
public final class TokenData {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    @AllArgsConstructor
    private enum FieldNames {
        USER_ID("USER_ID"),
        SESSION_ID("SESSION_ID"),
        USERNAME("USERNAME"),
        CREATE_TIME("createTime");
        @Getter
        private String name;
    }
    @Getter private final Integer userId;
    @Getter private final Integer sessionId;
    @Getter private final String username;
    @Getter private final LocalDateTime createTime;
    @EqualsAndHashCode.Exclude
    private Map<String, Object> map = new HashMap<>();

    public TokenData(Integer userId, Integer sessionId, String username, LocalDateTime createTime) {
        this.userId = userId;
        this.sessionId = sessionId;
        this.username = username;
        this.createTime = createTime;
        map.put(FieldNames.USER_ID.name, userId);
        map.put(FieldNames.USERNAME.name, username);
        map.put(FieldNames.CREATE_TIME.name, createTime.format(formatter));
        map.put(FieldNames.SESSION_ID.name, sessionId);
    }

    public TokenData(Map<String, Object> map) {
        this.map = new HashMap<>(map);
        this.userId = (Integer) map.get(FieldNames.USER_ID.name);
        this.sessionId = (Integer) map.get(FieldNames.SESSION_ID.name);
        this.username = (String) map.get(FieldNames.USERNAME.name);
        this.createTime = LocalDateTime.parse((String)map.get(FieldNames.CREATE_TIME.name), formatter);
    }

    public Map<String, Object> getMap() {
        return new HashMap<>(map);
    }
}
