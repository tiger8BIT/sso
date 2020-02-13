package artezio.vkolodynsky.auth;

import artezio.vkolodynsky.model.data.SessionData;
import eu.bitwalker.useragentutils.UserAgent;
import javafx.util.Pair;
import lombok.EqualsAndHashCode;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SessionUtil {
    @EqualsAndHashCode
    public static class UserDeviceInfo{
        public String locale;
        public String userAgentString;
        public String ipAddress;
    }
    public static int createId(String locale, String userAgentString, Integer userId){
        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);
        int id = List.of(locale.hashCode(), userAgent.getId(), userId).hashCode();
        return id;
    }
    public static String userAgentStringFrom(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }
    public static UserAgent userAgentFrom(String userAgentString) {
        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);
        return userAgent;
    }
    public static String localeStringFrom(HttpServletRequest request) {
        return request.getLocale().toString();
    }


    public static SessionData SessionDataFrom(HttpServletRequest request, Integer userId){
        String userAgentString = userAgentStringFrom(request);
        String ipAddress = request.getRemoteAddr();
        String locale = localeStringFrom(request);
        int sessionId =  createId(locale, userAgentString, userId);
        return new SessionData(sessionId, userId, locale, userAgentString, ipAddress);
    }

    public static SessionData SessionDataFrom(UserDeviceInfo userDeviceInfo, Integer userId){
        int sessionId =  createId(userDeviceInfo.locale, userDeviceInfo.userAgentString, userId);
        return new SessionData(sessionId, userId, userDeviceInfo.locale, userDeviceInfo.userAgentString, userDeviceInfo.ipAddress);
    }
}
