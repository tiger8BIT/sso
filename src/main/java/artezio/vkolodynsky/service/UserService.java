package artezio.vkolodynsky.service;
import artezio.vkolodynsky.model.Role;
import artezio.vkolodynsky.model.User;
import java.util.List;

public interface UserService {
    List<User> findAll();
    User save(User value);
    void deleteByID(int id);
    User findByID(int id);
    User getByLogin(String login);
    User getByLoginAndPassword(String login, String password);
    List<User> findByUserRole(Role role);
}
