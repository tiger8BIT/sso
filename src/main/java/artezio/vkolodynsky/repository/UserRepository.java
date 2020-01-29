package artezio.vkolodynsky.repository;
import artezio.vkolodynsky.model.Role;
import artezio.vkolodynsky.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByLogin(String login);
    Optional<User> findByEmail(String email);
    Optional<User> findByLoginAndPassword(String login, String password);
    List<User> findByUserRoles(List<Role> roles);
}
