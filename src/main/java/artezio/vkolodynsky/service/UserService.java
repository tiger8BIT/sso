package artezio.vkolodynsky.service;
import artezio.vkolodynsky.model.Role;
import artezio.vkolodynsky.model.User;
import artezio.vkolodynsky.model.data.UserData;
import artezio.vkolodynsky.validation.EmailExistsException;
import artezio.vkolodynsky.validation.LoginExistsException;
import org.springframework.dao.NonTransientDataAccessException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    User save(User value);
    void deleteByID(int id);
    Optional<User> findByID(int id);
    Optional<User> findByLogin(String login);
    Optional<User> findByLoginAndPassword(String login, String password);
    List<User> findByUserRole(Role role);
    User addRole(int id, Role role) throws NonTransientDataAccessException, NullPointerException;
    User deleteRole(int id, Role role) throws NonTransientDataAccessException, NullPointerException;
    Boolean existsById(int id);
    Boolean containsRole(int userId, Role role) throws NonTransientDataAccessException, NullPointerException;
    User registerNewUserAccount(UserData accountDto)
            throws EmailExistsException, LoginExistsException;
    boolean verify(String token, String appUrl, String role) throws Exception;
}