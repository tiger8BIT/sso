package artezio.vkolodynsky.repository;
import artezio.vkolodynsky.model.Role;
import artezio.vkolodynsky.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User getByLogin(String login);
    User getByLoginAndPassword(String login, String password);
    List<User> findByUserRoles(List<Role> roles);
}
