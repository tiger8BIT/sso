package artezio.vkolodynsky.service;
import artezio.vkolodynsky.auth.JwtUtil;
import artezio.vkolodynsky.auth.TokenData;
import artezio.vkolodynsky.model.Role;
import artezio.vkolodynsky.model.Session;
import artezio.vkolodynsky.model.User;
import artezio.vkolodynsky.model.data.UserData;
import artezio.vkolodynsky.repository.SessionRepository;
import artezio.vkolodynsky.repository.UserRepository;
import artezio.vkolodynsky.validation.EmailExistsException;
import artezio.vkolodynsky.validation.LoginExistsException;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
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
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return userRepository.findByLoginAndPassword(login,password);
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
    public User registerNewUserAccount(UserData accountDto)
            throws EmailExistsException, LoginExistsException {
        if (emailExist(accountDto.getEmail())) {
            throw new EmailExistsException(
                    "There is an account with that email adress: "
                            +  accountDto.getEmail());
        }
        if (loginExist(accountDto.getLogin())) {
            throw new LoginExistsException(
                    "There is an account with that login: "
                            +  accountDto.getLogin());
        }
        User newUser = accountDto.getUser();
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }

    @Override
    public boolean verify(String token, String appUrl, String role) throws Exception {
        try {
            Map<String, Object> tokenDataMap = JwtUtil.decodeJWT(token);
            TokenData tokenData = new TokenData(tokenDataMap);
            User user = userRepository.findById(Integer.valueOf(tokenData.getUserId()))
                    .orElseThrow(() -> new Exception("User absent"));
            if (!user.getLogin().equals(tokenData.getUsername())) throw new Exception("Incorrect username");
            if (!user.getPassword().equals(tokenData.getPassword())) throw new Exception("Incorrect password");
            if (tokenData.getExpirationDate().after(new Date())) throw new Exception("Session is very old");

            //Map<String, Object> userTokenDataMap = JwtUtil.decodeJWT(userToken, user.getPassword());
            //TokenData userTokenData = new TokenData(userTokenDataMap);

            return true;//userTokenData.equals(tokenData);
        } catch (Exception ex) {
            throw new Exception("Token corrupted");
        }
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