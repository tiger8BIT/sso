package artezio.vkolodynsky.controller;

import artezio.vkolodynsky.auth.CookieUtil;
import artezio.vkolodynsky.auth.JwtUtil;
import artezio.vkolodynsky.auth.SessionUtil;
import artezio.vkolodynsky.auth.TokenData;
import artezio.vkolodynsky.model.Session;
import artezio.vkolodynsky.model.User;
import artezio.vkolodynsky.model.data.SessionData;
import artezio.vkolodynsky.model.data.UserData;
import artezio.vkolodynsky.model.request.CheckTokenRequest;
import artezio.vkolodynsky.model.response.ServerResponse;
import artezio.vkolodynsky.security.SecurityService;
import artezio.vkolodynsky.service.SessionService;
import artezio.vkolodynsky.service.UserService;
import eu.bitwalker.useragentutils.UserAgent;
import io.jsonwebtoken.Claims;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping
@PropertySource({"classpath:sso.properties"})
public class SessionController {
    @Value("${sso.jwt.cookie.name}")
    private String jwtTokenCookieName;
    @Value("${sso.jwt.key}")
    private String secretKey;
    @Value("${sso.jwt.expiration.days}")
    private int expirationDays;

    @Autowired
    private UserService userService;


    @GetMapping("token/name")
    public @ResponseBody
    ResponseEntity getJWTName() {
        return ServerResponse.success(jwtTokenCookieName);
    }

    @GetMapping("user")
    public @ResponseBody
    ResponseEntity check(HttpServletRequest httpServletRequest) {
        String userAgentString = httpServletRequest.getHeader("User-Agent");
        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);
        return ServerResponse.success(userAgent);
    }

    @GetMapping("userl")
    public @ResponseBody
    ResponseEntity check2(HttpServletRequest httpServletRequest) {
        return ServerResponse.success(httpServletRequest.getLocale());
    }

    @GetMapping("useri")
    public @ResponseBody
    ResponseEntity check3(HttpServletRequest httpServletRequest) {
        String ipAddress = httpServletRequest.getHeader("X-FORWARDED-FOR");
        return ServerResponse.success(httpServletRequest.getRemoteAddr());
    }

   /* @PostMapping("check")
    public @ResponseBody
    ResponseEntity check(@RequestBody CheckTokenRequest request, HttpServletRequest httpServletRequest) throws Exception {
        httpServletRequest.getHeader("User-Agent");
        return ServerResponse.success(userService.verify(request.token, request.appUrl, request.roleName));
    }*/

    @PostMapping("verify")
    public @ResponseBody
    ResponseEntity verify(@RequestBody CheckTokenRequest checkTokenRequest) throws Exception {
        userService.verifyToken(checkTokenRequest.token, checkTokenRequest.userDeviceInfo);
        return ServerResponse.success("");
    }

    @PostMapping("singin")
    public String login(@RequestBody UserData userData, HttpServletRequest request, HttpServletResponse response) {
        String token = userService.createToken(userData, request).orElseThrow();
        HostAndPort hostAndPort = getHostAndPortFrom(request).orElseThrow();
        CookieUtil.create(response, jwtTokenCookieName, token, false, expirationDays, hostAndPort.host);
        return "redirect://" + hostAndPort;
    }

    private boolean iSTokenExists(HttpServletRequest request){
        return CookieUtil.getValue(request, jwtTokenCookieName).isPresent();
    }

    private static class HostAndPort{
        String host;
        Optional<Integer> port;
        @Override
        public String toString() {
            return host + (port.map(integer -> (':' + integer.toString())).orElse(""));
        }
    }
    private Optional<HostAndPort> getHostAndPortFrom(HttpServletRequest request){
        HostAndPort hostAndPort = new HostAndPort();
        String header = request.getHeader("Host");
        if (StringUtils.hasText(header)) {
            String[] hosts = StringUtils.commaDelimitedListToStringArray(header);
            String hostToUse = hosts[0];
            if (hostToUse.contains(":")) {
                String[] hostAndPortStrings = StringUtils.split(hostToUse, ":");
                hostAndPort.host = hostAndPortStrings[0];
                hostAndPort.port = Optional.of(Integer.valueOf(hostAndPortStrings[1]));
                return Optional.of(hostAndPort);
            }
            else {
                hostAndPort.host = header;
                hostAndPort.port = Optional.empty();
                return Optional.of(hostAndPort);
            }
        } else  return Optional.empty();

    }
}
