package artezio.vkolodynsky.service;
import artezio.vkolodynsky.model.Role;
import artezio.vkolodynsky.model.User;
import org.springframework.dao.NonTransientDataAccessException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    User save(User value);
    void deleteByID(int id);
    Optional<User> findByID(int id);
    User getByLogin(String login);
    User getByLoginAndPassword(String login, String password);
    List<User> findByUserRole(Role role);
    User addRole(int id, Role role) throws NonTransientDataAccessException, NullPointerException;
    User deleteRole(int id, Role role) throws NonTransientDataAccessException, NullPointerException;
    Boolean existsById(int id);
    Boolean containsRole(int userId, Role role) throws NonTransientDataAccessException, NullPointerException;
}