package artezio.vkolodynsky.service;
import artezio.vkolodynsky.auth.JwtUtil;
import artezio.vkolodynsky.auth.SessionUtil;
import artezio.vkolodynsky.auth.TokenData;
import artezio.vkolodynsky.model.Role;
import artezio.vkolodynsky.model.Session;
import artezio.vkolodynsky.model.User;
import artezio.vkolodynsky.model.data.SessionData;
import artezio.vkolodynsky.model.data.UserData;
import artezio.vkolodynsky.repository.SessionRepository;
import artezio.vkolodynsky.repository.UserRepository;
import artezio.vkolodynsky.validation.EmailExistsException;
import artezio.vkolodynsky.validation.LoginExistsException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@PropertySource({"classpath:sso.properties"})
public class UserServiceImpl implements UserService {
    @Value("${sso.jwt.key}")
    private String secretKey;
    @Value("${sso.jwt.expiration.days}")
    private int expirationDays;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private AppService appService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired SessionService sessionService;

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User save(User value) {
        return userRepository.save(value);
    }

    @Override
    public void deleteByID(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findByID(int id) {
            return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public Optional<User> singin(String login, String password) {
        return userRepository.findByLoginAndPassword(login,password);
    }

    @Override
    @Transactional
    public boolean verifyToken(String token, SessionUtil.UserDeviceInfo userDeviceInfo) throws Exception {
        Claims tokenClaims = JwtUtil.decodeJWT(token, secretKey).orElseThrow();
        TokenData tokenData = new TokenData(tokenClaims);
        User user = userRepository.findById(tokenData.getUserId()).orElseThrow();
        if (!tokenData.getCreateTime().plusDays(expirationDays).isAfter(LocalDateTime.now())) throw new Exception();
        if (!user.getLogin().equals(tokenData.getUsername())) throw new Exception();
        SessionData sessionData = SessionUtil.SessionDataFrom(userDeviceInfo, user.getId());
        if (!tokenData.getSessionId().equals(sessionData.getId())) throw new Exception();
        Session session = sessionRepository.findById(sessionData.getId()).orElseThrow();
        if (!session.getCreateTime().toLocalDateTime().plusDays(expirationDays).isAfter(LocalDateTime.now()))
            throw new Exception();
        return true;
    }

    @Override
    @Transactional
    public Optional<String> createToken(UserData userData, HttpServletRequest request) {
        Optional<User> user = userRepository.findByLogin(userData.getLogin());
        if(user.isPresent()) {
            if(!checkIfValidPassword(user.get(), userData.getPassword())) return Optional.empty();
            SessionData sessionData = SessionUtil.SessionDataFrom(request, user.get().getId());
            Session session = sessionService.save(sessionData, sessionData.getIpAddress());
            String token = JwtUtil.createJWT
                    (user.get().getId(), session.getId(),
                            user.get().getLogin(), user.get().getPassword(),
                            secretKey, expirationDays);
            return Optional.of(token);
        } else return Optional.empty();
    }

    @Override
    public boolean checkIfValidPassword(final User user, final String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public List<User> findByUserRole(Role role) {
        return userRepository.findByUserRoles(List.of(role));
    }

    @Override
    @Transactional
    public User addRole(int userId, Role role) throws NonTransientDataAccessException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            List<Role> roles = user.get().getUserRoles();
            if (roles != null) {
                roles.add(role);
            } else {
                user.get().setUserRoles(new ArrayList<>(List.of(role)));
            }
            return userRepository.save(user.get());
        }
        else throw new NonTransientDataAccessException("User not found by id " + userId){};
    }
    @Override
    @Transactional
    public User deleteRole(int userId, Role role) throws NonTransientDataAccessException, NullPointerException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            List<Role> roles = user.get().getUserRoles();
            if (roles != null) {
                if(roles.remove(role)) return userRepository.save(user.get());
                else throw new NonTransientDataAccessException("User haven't role with id " + role.getId()){};
            } else {
                throw new NullPointerException("User roles is null");
            }
        }
        else throw new NonTransientDataAccessException("User not found by id " + userId){};
    }

    @Override
    public Boolean existsById(int id) {
        return userRepository.existsById(id);
    }

    @Override
    @Transactional
    public Boolean containsRole(int userId, Role role) throws NonTransientDataAccessException, NullPointerException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            List<Role> roles = user.get().getUserRoles();
            if (roles != null) {
                return roles.contains(role);
            } else {
                throw new NullPointerException("User roles is null");
            }
        }
        else throw new NonTransientDataAccessException("User not found by id " + userId){};
    }

    @Transactional
    @Override
    public User registerNewUserAccount(UserData userData)
            throws EmailExistsException, LoginExistsException {
        if (emailExist(userData.getEmail())) {
            throw new EmailExistsException(
                    "There is an account with that email adress: "
                            +  userData.getEmail());
        }
        if (loginExist(userData.getLogin())) {
            throw new LoginExistsException(
                    "There is an account with that login: "
                            +  userData.getLogin());
        }
        User newUser = User.from(userData);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }

    private boolean emailExist(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    private boolean loginExist(String login) {
        Optional<User> user = userRepository.findByLogin(login);
        return user.isPresent();
    }
}