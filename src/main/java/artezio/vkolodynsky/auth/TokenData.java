package artezio.vkolodynsky.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode
public final class TokenData {
    @AllArgsConstructor
    private enum FieldNames {
        USER_ID("USER_ID"),
        USERNAME("USERNAME"),
        EXPIRATION_DATE("EXPIRATION_DATE"),
        PASSWORD("PASSWORD");
        @Getter
        private String name;
    }
    @Getter private final String userId;
    @Getter private final String username;
    @Getter private final String password;
    private Date expirationDate;
    @EqualsAndHashCode.Exclude
    private Map<String, Object> map = new HashMap<>();

    public TokenData(String userId, String username, String password, Date expirationDate) {
        this.userId = userId;
        this.username = username;
        this.expirationDate = new Date(expirationDate.getTime());
        this.password = password;
        map.put(FieldNames.USER_ID.name, userId);
        map.put(FieldNames.USERNAME.name, username);
        map.put(FieldNames.EXPIRATION_DATE.name, this.expirationDate);
        map.put(FieldNames.PASSWORD.name, password);
    }

    public TokenData(Map<String, Object> map) {
        this.map = new HashMap<>(map);
        this.userId = (String) map.get(FieldNames.USER_ID.name);
        this.username = (String) map.get(FieldNames.USERNAME.name);
        this.expirationDate = (Date) map.get(FieldNames.EXPIRATION_DATE.name);
        this.password = (String) map.get(FieldNames.PASSWORD.name);
    }

    public Date getExpirationDate(){
        return new Date(expirationDate.getTime());
    }

    public Map<String, Object> getMap() {
        return new HashMap<>(map);
    }
}
