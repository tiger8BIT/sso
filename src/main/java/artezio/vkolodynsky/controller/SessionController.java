package artezio.vkolodynsky.controller;

import artezio.vkolodynsky.auth.CookieUtil;
import artezio.vkolodynsky.auth.JwtUtil;
import artezio.vkolodynsky.model.Session;
import artezio.vkolodynsky.model.User;
import artezio.vkolodynsky.model.data.UserData;
import artezio.vkolodynsky.model.request.CheckTokenRequest;
import artezio.vkolodynsky.model.response.ServerResponse;
import artezio.vkolodynsky.security.SecurityService;
import artezio.vkolodynsky.service.SessionService;
import artezio.vkolodynsky.service.UserService;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping
@PropertySource({"classpath:sso.properties"})
public class SessionController {
    @Autowired
    private Environment env;
    private final String jwtTokenCookieName = env.getProperty("sso.jwt.cookie.name");
    private final String secretKey = env.getProperty("sso.jwt.key");
    private final int expirationDays = Integer.parseInt(Objects.requireNonNull(env.getProperty("sso.jwt.expiration.days")));
    @Autowired
    private UserService userService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private SessionService sessionService;


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

    @PostMapping("check")
    public @ResponseBody
    ResponseEntity check(@RequestBody CheckTokenRequest request, HttpServletRequest httpServletRequest) throws Exception {
        httpServletRequest.getHeader("User-Agent");
        return ServerResponse.success(userService.verify(request.token, request.appUrl, request.roleName));
    }


    @PostMapping
    public @ResponseBody
    ResponseEntity login(@RequestBody UserData userData, HttpServletRequest request) throws Exception {
        User user = userService.findByID(userData.getId()).get();
        String userAgentString = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);
        Optional<Session> optionalSession = sessionService.findByID(userAgent.getId());
        Session session = optionalSession.isEmpty() ? new Session() : optionalSession.get();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationData = now.plusDays(expirationDays);

        String token = JwtUtil.createJWT(user.getId().toString(), user.getLogin(), user.getPassword(), secretKey, expirationDays);
        session.setCreateTime(Timestamp.valueOf(now));
        session.setId(userAgent.getId());
        session.setUserAgent(userAgentString);
        return ServerResponse.success(token);
    }
}
