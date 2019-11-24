package artezio.vkolodynsky.service;
import artezio.vkolodynsky.model.User;
import java.util.List;

public interface UserService {
    List<User> findAll();
    User save(User value);
    void deleteByID(long id);
    User findByID(long id);
}
